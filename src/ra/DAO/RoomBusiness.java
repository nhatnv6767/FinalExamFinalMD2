package ra.DAO;

import ra.database.JDBCUtil;
import ra.entity.Room;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomBusiness implements DAOInterface<Room> {

    private Connection conn = null;
    private CallableStatement callSt = null;

    private void openConnection() {
        conn = new JDBCUtil().openConnection();
    }

    private void closeConnection() {
        new JDBCUtil().closeConnection(conn, callSt);
    }

    private Room mapRoom(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoomId(rs.getInt("roomId"));
        room.setRoomNumber(rs.getString("roomNumber"));
        room.setRoomType(rs.getString("roomType"));
        room.setPrice(rs.getDouble("price"));
        room.setStatus(rs.getString("status"));
        return room;
    }


    @Override
    public void insert(Room room) {
        if (room == null) {
            System.out.println("Invalid room information");
            return;
        }
        try {
            openConnection();
            callSt = conn.prepareCall("{call addRoom(?, ?, ?, ?)}");
            callSt.setString(1, room.getRoomNumber());
            callSt.setString(2, room.getRoomType());
            callSt.setDouble(3, room.getPrice());
            callSt.setString(4, room.getStatus());
            callSt.executeUpdate();
            System.out.println("Room added successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void update(Room room) {
        if (room == null) {
            System.out.println("Invalid room information");
            return;
        }
        try {
            openConnection();
            callSt = conn.prepareCall("{call updateRoom(?, ?, ?, ?, ?)}");
            callSt.setInt(1, room.getRoomId());
            callSt.setString(2, room.getRoomNumber());
            callSt.setString(3, room.getRoomType());
            callSt.setDouble(4, room.getPrice());
            callSt.setString(5, room.getStatus());
            callSt.executeUpdate();
            System.out.println("Room updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void delete(Room room) {
        if (room == null || room.getRoomId() <= 0) {
            System.out.println("Invalid room information");
            return;
        }
        try {
            if (get(room.getRoomId()) == null) {
                System.out.println("Room not found");
                return;
            }
            openConnection();
            callSt = conn.prepareCall("{call deleteRoom(?)}");
            callSt.setInt(1, room.getRoomId());
            callSt.executeUpdate();
            System.out.println("Room deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public Room get(int id) {
        if (id <= 0) {
            System.out.println("Invalid room ID");
            return null;
        }
        try {
            openConnection();
            callSt = conn.prepareCall("{call getRoomById(?)}");
            callSt.setInt(1, id);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                return mapRoom(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return null;
    }

    @Override
    public Room get(String name) {
        return null;
    }

    public List<Room> searchRoomByType(String type) {
        List<Room> rooms = new ArrayList<>();
        if (type == null || type.trim().isEmpty()) {
            System.out.println("Invalid room type");
            return rooms;
        }
        try {
            openConnection();
            callSt = conn.prepareCall("{call searchRoomByType(?)}");
            callSt.setString(1, type);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                rooms.add(mapRoom(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return rooms;
    }

    public List<Room> searchRoomByPrice(double priceStart, double priceEnd) {
        if (priceStart < 0 || priceEnd < 0) {
            System.out.println("Invalid price range");
            return null;
        }
        List<Room> rooms = new ArrayList<>();
        try {
            openConnection();
            callSt = conn.prepareCall("{call searchRoomByPrice(?, ?)}");
            callSt.setDouble(1, priceStart);
            callSt.setDouble(2, priceEnd);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                rooms.add(mapRoom(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return rooms;
    }

    public List<Room> searchRoomByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            System.out.println("Invalid room status");
            return null;
        }
        List<Room> rooms = new ArrayList<>();
        try {
            openConnection();
            callSt = conn.prepareCall("{call searchRoomByStatus(?)}");
            callSt.setString(1, status);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                rooms.add(mapRoom(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return rooms;
    }

    public List<Room> getAvailableRoom() {
        List<Room> rooms = new ArrayList<>();
        try {
            openConnection();
            callSt = conn.prepareCall("{call getAvailableRooms()}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                rooms.add(mapRoom(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return rooms;
    }

    @Override
    public Room get(Room room) {
        return null;
    }

    @Override
    public Room[] getAll() {
        List<Room> rooms = new ArrayList<>();
        try {
            openConnection();
            callSt = conn.prepareCall("{call getAllRooms()}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                rooms.add(mapRoom(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return rooms.toArray(new Room[0]);
    }
}
