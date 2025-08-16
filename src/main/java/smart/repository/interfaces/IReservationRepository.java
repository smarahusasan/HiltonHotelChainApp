package smart.repository.interfaces;

import smart.domain.Reservation;

import java.util.List;

public interface IReservationRepository {
    void makeReservation(Reservation reservation);
    void cancelRoomReservation(int reservationId);
    List<Reservation> getReservationsForHotel(int hotelId);
}
