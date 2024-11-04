package ra.DAO;

import ra.entity.Booking;
import ra.entity.BookingDetails;

public interface BookingDAOInterface extends DAOInterface<Booking> {
    BookingDetails[] getAllDetails();
}
