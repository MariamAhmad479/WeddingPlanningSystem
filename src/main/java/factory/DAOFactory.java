package factory;

import DAO.BookingDataAccess;
import DAO.BrideDB;
import DAO.GehazItemAccessor;
import DAO.GuestDB;
import DAO.ReviewAccessor;
import DAO.VendorDataAccess;
import DAO.WeddingChecklistAccessor;

public interface DAOFactory {
    BookingDataAccess getBookingDAO();
    BrideDB getBrideDAO();
    GehazItemAccessor getGehazItemDAO();
    GuestDB getGuestDAO();
    ReviewAccessor getReviewDAO();
    VendorDataAccess getVendorDAO();
    WeddingChecklistAccessor getWeddingChecklistDAO();
}
