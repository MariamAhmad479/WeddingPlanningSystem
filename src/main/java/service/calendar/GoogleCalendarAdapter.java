package service.calendar;

import java.util.Date;

public class GoogleCalendarAdapter implements CalendarSync {
    private GoogleCalendarAPI googleApi;

    public GoogleCalendarAdapter(GoogleCalendarAPI googleApi) {
        this.googleApi = googleApi;
    }

    @Override
    public void addAppointment(String title, Date date) {
        System.out.println("Adapter converting CalendarSync addition to GoogleCalendarAPI format...");
        long timestampMillis = date.getTime();
        googleApi.createEvent(title, timestampMillis);
    }

    @Override
    public void removeAppointment(String title) {
        System.out.println("Adapter converting CalendarSync removal to GoogleCalendarAPI format...");
        googleApi.deleteEvent(title);
    }
}
