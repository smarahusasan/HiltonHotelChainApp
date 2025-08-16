package smart.repository;

import smart.domain.Room;
import smart.repository.interfaces.IRoomRepository;
import smart.utils.enums.RoomType;
import smart.utils.enums.Status;
import smart.utils.exceptions.RepoException;
import smart.utils.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RoomRepository implements IRoomRepository {
    private final JdbcUtils dbUtils;

    public RoomRepository(Properties properties) {
        this.dbUtils = new JdbcUtils(properties);
    }

    @Override
    public void addRoom(Room room) {
        String sql="insert into rooms(type, available, hotel_id) values(?,?,?)";
        Connection connection= dbUtils.getConnection();
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setString(1, room.getType().name());
            preparedStatement.setBoolean(2, room.isAvailable());
            preparedStatement.setInt(3, room.getHotelId());

            int res = preparedStatement.executeUpdate();
            if(res != 1){
                throw new RepoException("Room was not added!");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Room> getRoomsForHotel(int hotelId) {
        String sql="select * from rooms where hotel_id=?";
        List<Room> rooms=new ArrayList<>();
        Connection connection= dbUtils.getConnection();
        try( PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setInt(1, hotelId);

            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                int roomNumber=resultSet.getInt("room_number");
                RoomType type=RoomType.valueOf(resultSet.getString("type"));
                boolean available=resultSet.getBoolean("available");
                Room room=new Room(roomNumber, type, available, hotelId);
                rooms.add(room);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public Room getRoom(int roomId) {
        String sql="select * from rooms where room_number = ?";
        Connection connection= dbUtils.getConnection();
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setInt(1, roomId);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                RoomType type=RoomType.valueOf(resultSet.getString("type"));
                boolean available=resultSet.getBoolean("available");
                int hotelId=resultSet.getInt("hotel_id");
                return new Room(roomId, type, available, hotelId);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RepoException("There is no room with id "+roomId);
    }

    @Override
    public void updateRoom(Room room) {
        String sql = "update rooms set available=? where room_number=?";
        Connection connection= dbUtils.getConnection();
        try(PreparedStatement preparedStatement= connection.prepareStatement(sql)){
            preparedStatement.setBoolean(1, room.isAvailable());
            preparedStatement.setInt(2, room.getRoomNumber());

            int res = preparedStatement.executeUpdate();
            if(res != 1){
                throw new RepoException("Room was not updated!");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
