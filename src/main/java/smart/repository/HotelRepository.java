package smart.repository;

import smart.domain.Hotel;
import smart.repository.interfaces.IHotelRepository;
import smart.utils.exceptions.RepoException;
import smart.utils.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class HotelRepository implements IHotelRepository {
    private final JdbcUtils dbUtils;

    public HotelRepository(Properties props) {
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public void addHotel(Hotel hotel) {
        String sql="insert into hotels values(?,?,?)";
        Connection connection=dbUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,hotel.getHotelId());
            preparedStatement.setString(2,hotel.getName());
            preparedStatement.setString(3,hotel.getLocation());

            int res = preparedStatement.executeUpdate();
            if(res!=1)
                throw new RepoException("Hotel was not added!");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Hotel getHotel(int hotelId) {
        String sql="select * from hotels where hotelId=?";
        Connection connection=dbUtils.getConnection();
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setInt(1,hotelId);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString("name");
                String location=resultSet.getString("location");
                return new Hotel(hotelId,name,location);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RepoException("Hotel with id "+hotelId+" not found!");
    }
}
