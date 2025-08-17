package smart.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import smart.domain.Reservation;
import smart.domain.Room;
import smart.repository.interfaces.IReservationRepository;
import smart.repository.interfaces.IRoomRepository;
import smart.utils.enums.RoomType;
import smart.utils.enums.Status;
import smart.validator.Validator;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceTest {
    @Mock
    IReservationRepository reservationRepo;
    @Mock
    IRoomRepository roomRepo;
    @Mock
    Validator<Reservation> reservationValidator;

    @InjectMocks
    Service service;

    @Test
    void makeReservation_roomAvailable() {
        Room room=new Room(1,RoomType.SINGLE,true,1);

        Reservation reservation=new Reservation(1,1,1,Date.valueOf("2025-08-21"),Date.valueOf("2025-08-25"),Date.valueOf(LocalDate.now()),1);

        when(roomRepo.getRoom(1)).thenReturn(room);
        doNothing().when(reservationValidator).validate(any(Reservation.class));
        service.makeReservation(reservation);

        assertEquals(Status.CONFIRMED, reservation.getStatus());
        verify(roomRepo).getRoom(1);
        verify(roomRepo).updateRoom(room);
        verify(reservationRepo).makeReservation(reservation);
    }

    @Test
    void makeReservation_roomNotAvailable() {
        Room room=new Room(1,RoomType.SINGLE,true,1);

        Reservation reservation=new Reservation(1,1,1,Date.valueOf("2025-08-21"),Date.valueOf("2025-08-25"),Date.valueOf(LocalDate.now()),1);

        when(roomRepo.getRoom(1)).thenReturn(room);
        doNothing().when(reservationValidator).validate(any(Reservation.class));
        service.makeReservation(reservation);

        assertEquals(Status.CONFIRMED, reservation.getStatus());
        verify(roomRepo).getRoom(1);
        verify(roomRepo).updateRoom(room);
        verify(reservationRepo).makeReservation(reservation);

        Reservation reservationNotConfirmed = new Reservation(2,2,1,Date.valueOf("2025-08-21"),Date.valueOf("2025-08-25"),Date.valueOf(LocalDate.now()),1);

        service.makeReservation(reservationNotConfirmed);

        assertEquals(Status.NOT_CONFIRMED, reservationNotConfirmed.getStatus());
        verify(roomRepo, times(2)).getRoom(1);
        verify(roomRepo).updateRoom(room);
        verify(reservationRepo).makeReservation(reservationNotConfirmed);
    }

    @Test
    void cancelReservation() {
        Room room=new Room(1,RoomType.SINGLE,true,1);

        Reservation reservation=new Reservation(1,1,1,Date.valueOf("2025-08-21"),Date.valueOf("2025-08-25"),Date.valueOf(LocalDate.now()),1);

        when(roomRepo.getRoom(1)).thenReturn(room);
        doNothing().when(reservationValidator).validate(any(Reservation.class));
        service.makeReservation(reservation);

        assertEquals(Status.CONFIRMED, reservation.getStatus());
        verify(roomRepo).getRoom(1);
        verify(roomRepo).updateRoom(room);
        verify(reservationRepo).makeReservation(reservation);

        when(reservationRepo.getReservation(1)).thenReturn(reservation);
        assertTrue(service.cancelReservation(1));

        assertEquals(Status.CANCELED, reservation.getStatus());
        verify(reservationRepo).updateReservation(reservation);
        verify(roomRepo, times(2)).getRoom(1);
        verify(roomRepo, times(2)).updateRoom(room);
    }
}