package smart.service;

import smart.domain.Guest;
import smart.domain.Hotel;
import smart.domain.Reservation;
import smart.domain.Room;
import smart.repository.interfaces.IGuestRepository;
import smart.repository.interfaces.IHotelRepository;
import smart.repository.interfaces.IReservationRepository;
import smart.repository.interfaces.IRoomRepository;
import smart.utils.enums.Status;
import smart.validator.GuestValidator;
import smart.validator.HotelValidator;
import smart.validator.ReservationValidator;
import smart.validator.Validator;

import java.util.List;

public class Service {
    private IGuestRepository guestRepository;
    private final Validator<Guest> guestValidator;
    private IHotelRepository hotelRepository;
    private final Validator<Hotel> hotelValidator;
    private IReservationRepository reservationRepository;
    private final Validator<Reservation> reservationValidator;
    private IRoomRepository roomRepository;

    public Service(IGuestRepository guestRepository, Validator<Guest> guestValidator, IHotelRepository hotelRepository, Validator<Hotel> hotelValidator, IReservationRepository reservationRepository, Validator<Reservation> reservationValidator, IRoomRepository roomRepository) {
        this.guestRepository = guestRepository;
        this.guestValidator = guestValidator;
        this.hotelRepository = hotelRepository;
        this.hotelValidator = hotelValidator;
        this.reservationRepository = reservationRepository;
        this.reservationValidator = reservationValidator;
        this.roomRepository = roomRepository;
    }

    void addHotel(Hotel hotel) {
        hotelValidator.validate(hotel);
        hotelRepository.addHotel(hotel);
    }

    Hotel getHotel(int hotelId) {
        Hotel hotelFound = hotelRepository.getHotel(hotelId);
        List<Room> roomsForHotel = roomRepository.getRoomsForHotel(hotelId);
        hotelFound.setRooms(roomsForHotel);
        List<Reservation> reservationsForHotel = reservationRepository.getReservationsForHotel(hotelId);
        hotelFound.setReservations(reservationsForHotel);
        List<Guest> guestsForHotel = guestRepository.getGuestsForHotel(hotelId);
        hotelFound.setGuests(guestsForHotel);

        return hotelFound;
    }

    void addRoom(Room room) {
        roomRepository.addRoom(room);
    }

    Room getRoom(int roomId) {
        return roomRepository.getRoom(roomId);
    }

    void addGuest(Guest guest) {
        guestValidator.validate(guest);
        guestRepository.addGuest(guest);
    }

    void makeReservation(Reservation reservation) {
        reservationValidator.validate(reservation);

        Room room = roomRepository.getRoom(reservation.getRoomId());
        if(room.isAvailable()){
            room.setAvailable(false);
            roomRepository.updateRoom(room);
            reservation.setStatus(Status.CONFIRMED);
        }else{
            reservation.setStatus(Status.NOT_CONFIRMED);
        }
        reservationRepository.makeReservation(reservation);
    }

    void cancelReservation(int reservationId) {
        Reservation reservation=reservationRepository.getReservation(reservationId);
        reservation.setStatus(Status.CANCELED);
        reservationRepository.updateReservation(reservation);

        Room room = roomRepository.getRoom(reservation.getRoomId());
        room.setAvailable(true);
        roomRepository.updateRoom(room);
    }
}
