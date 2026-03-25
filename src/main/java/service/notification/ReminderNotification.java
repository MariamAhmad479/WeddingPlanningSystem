package service.notification;

public class ReminderNotification extends Notification {
    private String recipient;
    private String eventType;

    public ReminderNotification(MessageSender sender, String recipient, String eventType) {
        super(sender);
        this.recipient = recipient;
        this.eventType = eventType;
    }

    @Override
    public void send() {
        String msg = "Hello " + recipient + ", this is a reminder for your upcoming event: " + eventType;
        sender.sendMessage(msg);
    }
}
