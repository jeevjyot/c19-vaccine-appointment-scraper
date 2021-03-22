package appointment.watcher;

import appointment.watcher.service.WatcherService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Watcher {

    private static final String TWILIO_ACCOUNT_SID = "<twilio account sid>";
    private static final String TWILIO_AUTH_TOKEN = "<twilio auth token>";
    private static final String FROM_PHONE_NUMBER = "<from phone number>"; // Select from Twilio
    private static final String TO_PHONE_NUMBER = "<to phone number>";

    public static void main(String[] args) throws Exception {
        log.info("Watching appointments");
        WatcherService watcherService = new WatcherService();

        //TODO: remove infinite loop and introduce scheduler or cron job
        while (true) {
            boolean available;
            try {
                //TODO: send text only when appointment is available
                available = watcherService.checkIfAppointmentAvailable();
                sendText("Appointment available = " + available);
            } catch (Exception e) {
                log.error("Error occurred: {}", e.getMessage());
                sendText("Something went wrong=" + e.getMessage());
            }
            Thread.sleep(10000); //feel free to change the frequency
        }
    }

    //TODO: Send the link with the text message
    public static void sendText(String status) {
        // Find your Account Sid and Auth Token at twilio.com/console
        // Create account from here  www.twilio.com/referral/QMkbwm

        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);

        try {
            Message message = Message
                    .creator(new PhoneNumber(TO_PHONE_NUMBER), // to
                            new PhoneNumber(FROM_PHONE_NUMBER), // from
                            status)
                    .create();
            log.info("Twilio sid={}", message.getSid());
        } catch (Exception e) {
            log.error("Error occurred: {}", e.getMessage());
        }
    }
}
