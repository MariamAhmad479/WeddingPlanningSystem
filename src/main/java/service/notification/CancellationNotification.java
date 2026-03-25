package service.notification;

public class CancellationNotification extends Notification {
    private String appointmentId;

    public CancellationNotification(MessageSender sender, String appointmentId) {
        super(sender);
        this.appointmentId = appointmentId;
    }

    @Override
    public void send() {
        String msg = "Notice: The appointment with ID " + appointmentId + " has been cancelled.";
        sender.sendMessage(msg);
    }
}
