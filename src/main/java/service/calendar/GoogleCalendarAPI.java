package service.calendar;

/**
 * Simulates a third-party Google Calendar SDK.
 * This acts as the Adaptee.
 */
public class GoogleCalendarAPI {
    public void createEvent(String eventName, long timestampMillis) {
        System.out.println("GoogleCalendarAPI: Created event '" + eventName + "' at timestamp " + timestampMillis);
    }
    
    public void deleteEvent(String eventName) {
        System.out.println("GoogleCalendarAPI: Deleted event '" + eventName + "'");
    }
}
