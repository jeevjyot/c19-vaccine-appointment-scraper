package appointment.watcher.clients;

import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

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

        if (response.statusCode() != 200) {
            System.out.println("non successful response");
            System.out.println("StatusCode =" + response.statusCode());
            System.out.println("ResponseBody=" + response.body());
            throw new Exception("Failed to get the response " + response.statusCode());
        }
        return response.body();
    }
}
