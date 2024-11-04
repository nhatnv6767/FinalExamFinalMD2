package ra.DAO;

import ra.database.JDBCUtil;
import ra.entity.Booking;
import ra.entity.BookingDetails;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingBusiness implements BookingDAOInterface {

    private Connection conn = null;
    private CallableStatement callSt = null;

    private void openConnection() {
        conn = new JDBCUtil().openConnection();

    }

    private void closeConnection() {
        new JDBCUtil().closeConnection(conn, callSt);
    }

    private BookingDetails mapBookingDetails(ResultSet rs) throws SQLException {
        BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setBookingId(rs.getInt("bookingId"));
        bookingDetails.setCustomerName(rs.getString("customerName"));
        bookingDetails.setIdCard(rs.getString("idCard"));
        bookingDetails.setPhoneNumber(rs.getString("phoneNumber"));
        bookingDetails.setAddress(rs.getString("address"));
        bookingDetails.setCustomerType(rs.getString("customerType"));
        bookingDetails.setRoomNumber(rs.getString("roomNumber"));
        bookingDetails.setRoomType(rs.getString("roomType"));
        bookingDetails.setPrice(rs.getDouble("price"));
        bookingDetails.setArrivalDate(rs.getDate("arrivalDate").toLocalDate());
        bookingDetails.setDepartureDate(rs.getDate("departureDate").toLocalDate());
        bookingDetails.setNumberOfGuests(rs.getInt("numberOfGuests"));
        bookingDetails.setTotalPrice(rs.getDouble("totalPrice"));
        return bookingDetails;
    }

    @Override
    public void insert(Booking booking) {
        try {
            openConnection();
            callSt = conn.prepareCall("{call addBooking(?, ?, ?, ?, ?)}");
            callSt.setInt(1, booking.getCustomerId());
            callSt.setInt(2, booking.getRoomId());
            callSt.setDate(3, java.sql.Date.valueOf(booking.getArrivalDate()));
            callSt.setDate(4, java.sql.Date.valueOf(booking.getDepartureDate()));
            callSt.setInt(5, booking.getNumberOfGuests());
            callSt.executeUpdate();
            System.out.println("Booking added successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void update(Booking booking) {

    }

    @Override
    public void delete(Booking booking) {

    }

    @Override
    public Booking get(int id) {
        return null;
    }

    @Override
    public Booking get(String name) {
        return null;
    }

    @Override
    public Booking get(Booking booking) {
        return null;
    }

    @Override
    public Booking[] getAll() {
        return new Booking[0];
    }

    @Override
    public BookingDetails[] getAllDetails() {
        List<BookingDetails> bookingDetailsList = new ArrayList<>();
        try {
            openConnection();
            String sql = "{call getAllBookingsWithDetails()}";
            callSt = conn.prepareCall(sql);
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                BookingDetails bookingDetails = mapBookingDetails(rs);
                bookingDetailsList.add(bookingDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return bookingDetailsList.toArray(new BookingDetails[0]);
    }

    public double calculateTotalPrice(int bookingId) {
        double totalPrice = 0.0;
        try {
            openConnection();
            callSt = conn.prepareCall("{call calculateTotalPrice(?)}");
            callSt.setInt(1, bookingId);
            callSt.registerOutParameter(2, java.sql.Types.DECIMAL);
            callSt.execute();
            totalPrice = callSt.getDouble(2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return totalPrice;
    }

    public void printInvoice(int bookingId) {
        BookingDetails bookingDetails = null;
        try {
            openConnection();
            String sql = "{call getBookingDetailsById(?)}";
            callSt = conn.prepareCall(sql);
            callSt.setInt(1, bookingId);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                bookingDetails = mapBookingDetails(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        if (bookingDetails != null) {
            System.out.println("Invoice for Booking ID: " + bookingDetails.getBookingId());
            System.out.println("Customer Name: " + bookingDetails.getCustomerName());
            System.out.println("ID Card: " + bookingDetails.getIdCard());
            System.out.println("Phone Number: " + bookingDetails.getPhoneNumber());
            System.out.println("Address: " + bookingDetails.getAddress());
            System.out.println("Customer Type: " + bookingDetails.getCustomerType());
            System.out.println("Room Number: " + bookingDetails.getRoomNumber());
            System.out.println("Room Type: " + bookingDetails.getRoomType());
            System.out.println("Price per Night: " + bookingDetails.getPrice());
            System.out.println("Arrival Date: " + bookingDetails.getArrivalDate());
            System.out.println("Departure Date: " + bookingDetails.getDepartureDate());
            System.out.println("Number of Guests: " + bookingDetails.getNumberOfGuests());
            System.out.println("Total Price: " + bookingDetails.getTotalPrice());
        } else {
            System.out.println("No booking details found for Booking ID: " + bookingId);
        }
    }
}
