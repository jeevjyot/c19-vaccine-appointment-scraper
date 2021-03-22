package appointment.watcher.clients;

import appointment.watcher.exception.CustomException;
import appointment.watcher.exception.ExceptionType;
import lombok.extern.slf4j.Slf4j;

import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
public class AppointmentClient {

    private final HttpClient httpClient;

    public AppointmentClient() {
        this.httpClient = HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build();
    }

    public String getAppointmentResponse() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://www.calvax.org/reg/3092726101"))
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.of(3, SECONDS))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 302 &&
                response.headers().map().get("location").get(0).contains("Clinic+does+not+have+any+appointment+slots+available.")) {

            throw new CustomException(ExceptionType.SKIPPABLE, "No appointments.");
        } else if (response.statusCode() != 200) {

            log.warn("Non successful response. Status_code={} Body={}", response.statusCode(), response.body());
            throw new CustomException(ExceptionType.FATAL, "Failed to get the response " + response.statusCode());
        }

        return response.body();
    }
}
