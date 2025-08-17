package smart.repository;

import smart.domain.Reservation;
import smart.repository.interfaces.IReservationRepository;
import smart.utils.enums.Status;
import smart.utils.exceptions.RepoException;
import smart.utils.jdbc.JdbcUtils;

import java.sql.*;
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
        String sql;
        if(reservation.getReservationId()==0){
            sql="insert into reservations(guest_id, hotel_id, checkindate, checkoutdate, reservationdate, status, room_number) values(?,?,?,?,?,?,?)";
        }else{
            sql="insert into reservations(reservation_id, guest_id, hotel_id, checkindate, checkoutdate, reservationdate, status, room_number) values(?,?,?,?,?,?,?,?)";
        }
        Connection connection=dbUtils.getConnection();
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            int idx=1;
            if(reservation.getReservationId()!=0){
                preparedStatement.setInt(idx++, reservation.getReservationId());
            }
            preparedStatement.setInt(idx++, reservation.getGuestId());
            preparedStatement.setInt(idx++, reservation.getHotelId());
            preparedStatement.setDate(idx++, reservation.getCheckInDate());
            preparedStatement.setDate(idx++, reservation.getCheckOutDate());
            preparedStatement.setDate(idx++, reservation.getReservationDate());
            preparedStatement.setString(idx++,reservation.getStatus().name());
            preparedStatement.setInt(idx,reservation.getRoomId());

            int result=preparedStatement.executeUpdate();
            if(result!=1){
                throw new RepoException("Reservation was not added!");
            }
        }catch (SQLException e) {
            throw new RepoException("SQLException!");
        }
    }

    @Override
    public void updateReservation(Reservation reservation) {
        String sql="update reservations set status=? where reservation_id=?";
        Connection connection=dbUtils.getConnection();
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
            preparedStatement.setString(1, reservation.getStatus().name());
            preparedStatement.setInt(2, reservation.getReservationId());

            int result=preparedStatement.executeUpdate();
            if(result!=1){
                throw new RepoException("Reservation was not cancelled!");
            }
        }catch (SQLException e) {
            throw new RepoException("SQLException!");
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
                Date reservationDate=resultSet.getDate("reservationdate");
                Status status=Status.valueOf(resultSet.getString("status"));
                int roomId=resultSet.getInt("room_number");
                Reservation reservation=new Reservation(resId,guestId,roomId,checkInDate,checkOutDate,reservationDate,status,hotelId);

                reservations.add(reservation);
            }
        }catch (SQLException e) {
            throw new RepoException("SQLException!");
        }
        return reservations;
    }

    @Override
    public Reservation getReservation(int reservationId) {
        String sql="select * from reservations where reservation_id=?";
        Connection connection=dbUtils.getConnection();
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setInt(1, reservationId);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                int guestId=resultSet.getInt("guest_id");
                int hotelId=resultSet.getInt("hotel_id");
                Date checkInDate=resultSet.getDate("checkindate");
                Date checkOutDate=resultSet.getDate("checkoutdate");
                Date reservationDate=resultSet.getDate("reservationdate");
                Status status=Status.valueOf(resultSet.getString("status"));
                int roomId=resultSet.getInt("room_number");

                return new Reservation(reservationId,guestId,roomId,checkInDate,checkOutDate,reservationDate,status,hotelId);
            }
        }catch (SQLException e) {
            throw new RepoException("SQLException!");
        }
        throw new RepoException("Reservation with id "+reservationId+" not found!");
    }
}
