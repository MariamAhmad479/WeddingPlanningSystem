package service.notification;

public class ReminderNotification extends Notification {
    private String recipientName;
    private String recipientContact;
    private String eventType;

    public ReminderNotification(MessageSender sender, String recipientName, String recipientContact, String eventType) {
        super(sender);
        this.recipientName = recipientName;
        this.recipientContact = recipientContact;
        this.eventType = eventType;
    }

    @Override
    public void send() {
        String msg = "To: " + recipientContact
                + " | Dear " + recipientName
                + ", this is a reminder for your upcoming event: "
                + eventType;
        sender.sendMessage(msg);
    }
}

