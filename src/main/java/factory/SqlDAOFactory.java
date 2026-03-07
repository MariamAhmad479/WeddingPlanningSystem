package factory;

import DAO.BookingDataAccess;
import DAO.BrideDB;
import DAO.GehazItemAccessor;
import DAO.GuestDB;
import DAO.ReviewAccessor;
import DAO.VendorDataAccess;
import DAO.WeddingChecklistAccessor;

public class SqlDAOFactory implements DAOFactory {

    @Override
    public BookingDataAccess getBookingDAO() {
        return new BookingDataAccess();
    }

    @Override
    public BrideDB getBrideDAO() {
        return new BrideDB();
    }

    @Override
    public GehazItemAccessor getGehazItemDAO() {
        return new GehazItemAccessor();
    }

    @Override
    public GuestDB getGuestDAO() {
        return new GuestDB();
    }

    @Override
    public ReviewAccessor getReviewDAO() {
        return new ReviewAccessor();
    }

    @Override
    public VendorDataAccess getVendorDAO() {
        return new VendorDataAccess();
    }

    @Override
    public WeddingChecklistAccessor getWeddingChecklistDAO() {
        return new WeddingChecklistAccessor();
    }
}
