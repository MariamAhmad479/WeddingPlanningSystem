package service;

import java.time.LocalDateTime;

import entity.Guest;

public class NotificationService {

    private static NotificationService instance;

    private NotificationService() {
    }

    public static NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    public void sendRSVPReminder(Guest guest) {
        System.out.println(
                "Reminder sent to guest: " + guest.getGuestName()
                + " | Status: " + guest.getRsvpStatus()
                + " | Time: " + LocalDateTime.now()
        );
    }

    public void sendReminder(String brideId, String vendorId, LocalDateTime appointmentDateTime) {
        System.out.println(
                "Reminder sent to bride " + brideId
                + " for vendor " + vendorId
                + " at " + appointmentDateTime
        );
    }

    public void sendCancellationNotice(String appointmentId) {
        System.out.println(
                "Cancellation notice sent for appointment: " + appointmentId
        );
    }
}
