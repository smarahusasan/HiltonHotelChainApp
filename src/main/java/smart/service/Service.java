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
import smart.validator.Validator;

import java.util.List;

public class Service {
    private final IGuestRepository guestRepository;
    private final Validator<Guest> guestValidator;
    private final IHotelRepository hotelRepository;
    private final Validator<Hotel> hotelValidator;
    private final IReservationRepository reservationRepository;
    private final Validator<Reservation> reservationValidator;
    private final IRoomRepository roomRepository;

    public Service(IGuestRepository guestRepository, Validator<Guest> guestValidator, IHotelRepository hotelRepository, Validator<Hotel> hotelValidator, IReservationRepository reservationRepository, Validator<Reservation> reservationValidator, IRoomRepository roomRepository) {
        this.guestRepository = guestRepository;
        this.guestValidator = guestValidator;
        this.hotelRepository = hotelRepository;
        this.hotelValidator = hotelValidator;
        this.reservationRepository = reservationRepository;
        this.reservationValidator = reservationValidator;
        this.roomRepository = roomRepository;
    }

    public void addHotel(Hotel hotel) {
        hotelValidator.validate(hotel);
        hotelRepository.addHotel(hotel);
    }

    public Hotel getHotel(int hotelId) {
        Hotel hotelFound = hotelRepository.getHotel(hotelId);
        List<Room> roomsForHotel = roomRepository.getRoomsForHotel(hotelId);
        hotelFound.setRooms(roomsForHotel);
        List<Reservation> reservationsForHotel = reservationRepository.getReservationsForHotel(hotelId);
        hotelFound.setReservations(reservationsForHotel);
        List<Guest> guestsForHotel = guestRepository.getGuestsForHotel(hotelId);
        hotelFound.setGuests(guestsForHotel);

        return hotelFound;
    }

    public void addRoom(Room room) {
        roomRepository.addRoom(room);
    }

    public Room getRoom(int roomId) {
        return roomRepository.getRoom(roomId);
    }

    public void addGuest(Guest guest) {
        guestValidator.validate(guest);
        guestRepository.addGuest(guest);
    }

    public void makeReservation(Reservation reservation) {
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

    public void cancelReservation(int reservationId) {
        Reservation reservation=reservationRepository.getReservation(reservationId);
        reservation.setStatus(Status.CANCELED);
        reservationRepository.updateReservation(reservation);

        Room room = roomRepository.getRoom(reservation.getRoomId());
        room.setAvailable(true);
        roomRepository.updateRoom(room);
    }

    public List<Room> getRoomForHotel(int hotelId) {
        hotelRepository.getHotel(hotelId);
        return roomRepository.getRoomsForHotel(hotelId);
    }

    public List<Reservation> getReservationsForHotel(int hotelId){
        hotelRepository.getHotel(hotelId);
        return reservationRepository.getReservationsForHotel(hotelId);
    }
}
