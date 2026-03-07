package controller;

import java.util.List;
import java.util.UUID;

import DAO.GuestDB;
import entity.Guest;
import entity.GuestData;
import enumeration.RSVPStatus;
import service.NotificationService;

public class GuestListController {

    private String currentBrideId;

    private final GuestDB guestDB;
    private final NotificationService notificationService;

    public GuestListController() {
        this.guestDB = new factory.SqlDAOFactory().getGuestDAO();
        this.notificationService = NotificationService.getInstance();
    }

    public void setCurrentBride(String brideId) {
        this.currentBrideId = brideId;
    }

    public List<Guest> requestGuestList() {
        return guestDB.getGuests(currentBrideId);
    }

    public Guest createGuest(GuestData data) {

        Guest g = new Guest();
        g.setGuestId(UUID.randomUUID().toString());
        g.setBrideId(currentBrideId);

        g.setGuestName(data.getName());
        g.setEmail(data.getEmail()); 
        g.setGuestCategory(data.getCategory());
        g.setPlusOneCount(data.getPlusOneCount());
        g.setRSVPStatus(RSVPStatus.PENDING);

        guestDB.saveGuest(g);
        return g;
    }

    public void updateRSVP(String guestId, RSVPStatus status, int plusOneCount) {
        guestDB.updateRSVP(guestId, status, plusOneCount);
    }

    public List<Guest> getPendingGuests() {
        return guestDB.getPendingGuests(currentBrideId);
    }

    public void sendReminderToPendingGuests() {
        List<Guest> pendingGuests = guestDB.getPendingGuests(currentBrideId);
        for (Guest guest : pendingGuests) {
            notificationService.sendRSVPReminder(guest);
        }
    }
}
