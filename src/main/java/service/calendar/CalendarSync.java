package service.calendar;

import java.util.Date;

public interface CalendarSync {
    void addAppointment(String title, Date date);
    void removeAppointment(String title);
}
