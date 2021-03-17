package appointment.watcher;

import appointment.watcher.service.WatcherService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class Watcher {

    public static void main(String[] args) throws Exception {
        System.out.println("watching appointment");
        WatcherService watcherService = new WatcherService();

        //TODO: remove infinite loop and introduce scheduler or cron job
        while (true) {
            boolean available;
            try {
                //TODO: send text only when appointment is available
                available = watcherService.checkIfAppointmentAvailable();
                sendText("Appointment available = " + available);
            } catch (Exception e) {
                System.out.println(e.getCause());
                sendText("Something went wrong=" + e.getMessage());
            }
            Thread.sleep(10_000); //feel free to change the frequency
        }

    }

    //TODO: Send the link with the text message
    public static void sendText(String status) {
        // Find your Account Sid and Auth Token at twilio.com/console
        // Create account from here  www.twilio.com/referral/QMkbwm
        String ACCOUNT_SID =
                "PASTE YOUR ACCOUNT_SID";
        String AUTH_TOKEN =
                "PASTE YOUR AUTH_TOKEN ";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        try {
            Message message = Message
                    .creator(new PhoneNumber("your number"), // to
                            new PhoneNumber("select number from twilio"), // from
                            status)
                    .create();
            System.out.println(message.getSid());
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }
}
