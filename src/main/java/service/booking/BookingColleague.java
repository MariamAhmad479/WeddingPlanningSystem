package service.booking;

/**
 * Mediator Pattern — Abstract colleague base class.
 *
 * Every participant in the booking workflow holds a reference
 * to the mediator and communicates through it.
 */
public abstract class BookingColleague {

    protected BookingMediator mediator;

    public BookingColleague(BookingMediator mediator) {
        this.mediator = mediator;
    }

    public void setMediator(BookingMediator mediator) {
        this.mediator = mediator;
    }
}
