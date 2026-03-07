package factory;

import DAO.*;

public class InMemoryDAOFactory implements DAOFactory {

    @Override
    public BookingDataAccess getBookingDAO() {
        return new BookingDataAccess(); // Mock this in a real scenario
    }

    @Override
    public BrideDB getBrideDAO() {
        return new BrideDB(); // Mock this in a real scenario
    }

    @Override
    public GehazItemAccessor getGehazItemDAO() {
        return new GehazItemAccessor(); // Mock this in a real scenario
    }

    @Override
    public GuestDB getGuestDAO() {
        return new GuestDB(); // Mock this in a real scenario
    }

    @Override
    public ReviewAccessor getReviewDAO() {
        return new ReviewAccessor(); // Mock this in a real scenario
    }

    @Override
    public VendorDataAccess getVendorDAO() {
        return new VendorDataAccess(); // Mock this in a real scenario
    }

    @Override
    public WeddingChecklistAccessor getWeddingChecklistDAO() {
        return new WeddingChecklistAccessor(); // Mock this in a real scenario
    }
}
