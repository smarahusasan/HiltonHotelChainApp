package smart.repository;

import smart.domain.Reservation;
import smart.repository.interfaces.IReservationRepository;
import smart.utils.enums.Status;
import smart.utils.exceptions.RepoException;
import smart.utils.jdbc.JdbcUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReservationRepository implements IReservationRepository {
    private final JdbcUtils dbUtils;

    public ReservationRepository(Properties properties) {
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public void makeReservation(Reservation reservation) {
        String sql="insert into reservations(guest_id, hotel_id, checkindate, checkoutdate, reservationdate, status, room_number) values(?,?,?,?,?,?,?)";
        Connection connection=dbUtils.getConnection();
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setInt(1, reservation.getGuestId());
            preparedStatement.setInt(2, reservation.getHotelId());
            preparedStatement.setDate(3, reservation.getCheckInDate());
            preparedStatement.setDate(4, reservation.getCheckOutDate());
            preparedStatement.setDate(5, Date.valueOf(LocalDateTime.now().toLocalDate()));
            preparedStatement.setString(6,reservation.getStatus().name());
            preparedStatement.setInt(7,reservation.getRoomId());

            int result=preparedStatement.executeUpdate();
            if(result!=1){
                throw new RepoException("Reservation was not added!");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelRoomReservation(int reservationId) {
        String sql="update reservations set status=? where reservation_id=?";
        Connection connection=dbUtils.getConnection();
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
            preparedStatement.setString(1, Status.CANCELED.name());
            preparedStatement.setInt(2, reservationId);

            int result=preparedStatement.executeUpdate();
            if(result!=1){
                throw new RepoException("Reservation was not cancelled!");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Reservation> getReservationsForHotel(int hotelId) {
        String sql="select * from reservations where hotel_id=?";
        Connection connection=dbUtils.getConnection();
        List<Reservation> reservations=new ArrayList<>();
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setInt(1, hotelId);

            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                int resId=resultSet.getInt("reservation_id");
                int guestId=resultSet.getInt("guest_id");
                Date checkInDate=resultSet.getDate("checkindate");
                Date checkOutDate=resultSet.getDate("checkoutdate");
                Status status=Status.valueOf(resultSet.getString("status"));
                int roomId=resultSet.getInt("room_number");
                Reservation reservation=new Reservation(resId,guestId,roomId,checkInDate,checkOutDate,status,hotelId);

                reservations.add(reservation);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
}
