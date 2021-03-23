package appointment.watcher.service;

import appointment.watcher.clients.AppointmentClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class WatcherService {

    private final AppointmentClient appointmentClient;

    public WatcherService() {
        this.appointmentClient = new AppointmentClient();
    }

    public boolean checkIfAppointmentAvailable() throws Exception {
        List<String> availability = getAvailability();
        return availability.stream()
                .anyMatch(s -> !s.equals("No appointments available"));
    }

    public List<String> getAvailability() throws Exception {
        String htmlResponse = appointmentClient.getAppointmentResponse();
        Element appointmentSection = Jsoup.parse(htmlResponse)
                .body()
                .getAllElements()
                .get(39)
                .getElementById("appointments-section");
        Elements appointmentsTable = appointmentSection.getElementsByClass("table mb-6");
        Elements tableRows = appointmentsTable.select("tr");
        List<String> appointmentAvailability = new ArrayList<>();

        for (Element element : tableRows) {
            Elements tableData = element.select("td");
            //traversing all the table rows
            for (int i = 1; i < tableData.size(); i++) {
                String text = tableData.select("p").text();
                appointmentAvailability.add(text);
            }
        }
        return appointmentAvailability;
    }
}
