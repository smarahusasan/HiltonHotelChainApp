package smart.repository.interfaces;

import smart.domain.Reservation;

import java.util.List;

public interface IReservationRepository {
    void makeReservation(Reservation reservation);
    void updateReservation(Reservation reservation);
    List<Reservation> getReservationsForHotel(int hotelId);
    Reservation getReservation(int reservationId);
}
