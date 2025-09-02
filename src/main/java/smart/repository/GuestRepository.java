package smart.repository;

import smart.domain.Guest;
import smart.repository.interfaces.IGuestRepository;
import smart.utils.exceptions.RepoException;
import smart.utils.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GuestRepository implements IGuestRepository {
    private final JdbcUtils dbUtils;

    public GuestRepository(Properties prop) {
        this.dbUtils=new JdbcUtils(prop);
    }

    @Override
    public void addGuest(Guest guest) {
        String sql="insert into guests values(?,?,?,?,?)";
        Connection connection=dbUtils.getConnection();
        try(PreparedStatement ps=connection.prepareStatement(sql)){
            ps.setInt(1,guest.guestId());
            ps.setString(2,guest.name());
            ps.setString(3,guest.email());
            ps.setString(4,guest.phone());
            ps.setInt(5,guest.hotelId());

            int result=ps.executeUpdate();
            if(result!=1)
                throw new RepoException("Guest was not added!");
        }catch (SQLException e){
            throw new RepoException("SQLException!");
        }
    }

    @Override
    public List<Guest> getGuestsForHotel(int hotelId) {
        String sql="select * from guests where hotel_id=?";
        List<Guest> guests=new ArrayList<>();
        Connection connection= dbUtils.getConnection();
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setInt(1,hotelId);

            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                int guestId=resultSet.getInt("guest_id");
                String name=resultSet.getString("name");
                String email=resultSet.getString("email");
                String phone=resultSet.getString("phone");

                Guest guest=new Guest(guestId,name,email,phone,hotelId);
                guests.add(guest);
            }
        }catch (SQLException e){
            throw new RepoException("SQLException!");
        }
        return guests;
    }
}
