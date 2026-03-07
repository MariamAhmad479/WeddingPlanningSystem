package entity;

import java.time.LocalDateTime;

import enumeration.RSVPStatus;

public class Guest implements User {

    private String guestId;
    private String guestName;
    private String guestCategory;
    private int plusOneCount;
    private RSVPStatus rsvpStatus;
    private LocalDateTime rsvpTimestamp;
    private String brideId;
    private String email;

    public Guest() {
    }

    public Guest(String guestId,
            String brideId,
            String guestName,
            String guestCategory,
            int plusOneCount,
            RSVPStatus rsvpStatus) {

        this.guestId = guestId;
        this.brideId = brideId;
        this.guestName = guestName;
        this.guestCategory = guestCategory;
        this.plusOneCount = plusOneCount;
        this.rsvpStatus = rsvpStatus;
        this.rsvpTimestamp = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public void setGuestCategory(String guestCategory) {
        this.guestCategory = guestCategory;
    }

    public void setBrideId(String brideId) {
        this.brideId = brideId;
    }

    public void setGuestDetails(GuestData info) {
        this.guestName = info.getName();
        this.guestCategory = info.getCategory();
        this.plusOneCount = info.getPlusOneCount();
    }

    public void setRSVPStatus(RSVPStatus status) {
        this.rsvpStatus = status;
        this.rsvpTimestamp = LocalDateTime.now();
    }

    public void setPlusOneCount(int count) {
        this.plusOneCount = count;
    }

    public String getGuestId() {
        return guestId;
    }

    public String getBrideId() {
        return brideId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getGuestCategory() {
        return guestCategory;
    }

    public int getPlusOneCount() {
        return plusOneCount;
    }

    public void setRsvpTimestamp(LocalDateTime rsvpTimestamp) {
        this.rsvpTimestamp = rsvpTimestamp;
    }

    public RSVPStatus getRsvpStatus() {
        return rsvpStatus;
    }

    public LocalDateTime getRsvpTimestamp() {
        return rsvpTimestamp;
    }

    @Override
    public String getUserType() {
        return "GUEST";
    }
}
