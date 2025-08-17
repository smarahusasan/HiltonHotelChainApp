package smart;

import smart.domain.Guest;
import smart.domain.Hotel;
import smart.domain.Reservation;
import smart.menu.*;
import smart.repository.GuestRepository;
import smart.repository.HotelRepository;
import smart.repository.ReservationRepository;
import smart.repository.RoomRepository;
import smart.repository.interfaces.*;
import smart.service.Service;
import smart.validator.GuestValidator;
import smart.validator.HotelValidator;
import smart.validator.ReservationValidator;
import smart.validator.Validator;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class Main {
    public static void main(String[] args) {
        Properties props=new Properties();
        try {
            props.load(new FileReader("db.config"));
        } catch (IOException e) {
            System.out.println("Cannot find db.config "+e);
        }

        IGuestRepository guestRepository=new GuestRepository(props);
        Validator<Guest> guestValidator=new GuestValidator();
        IHotelRepository hotelRepository=new HotelRepository(props);
        Validator<Hotel> hotelValidator=new HotelValidator();
        IReservationRepository reservationRepository=new ReservationRepository(props);
        Validator<Reservation> reservationValidator=new ReservationValidator();
        IRoomRepository roomRepository=new RoomRepository(props);

        Service service=new Service(guestRepository, guestValidator, hotelRepository, hotelValidator, reservationRepository, reservationValidator, roomRepository);

        HotelModule hotelModule=new HotelModule(service);
        GuestsModule guestsModule=new GuestsModule(service);
        ReservationModule reservationModule=new ReservationModule(service);
        RoomModule roomModule=new RoomModule(service);
        MainMenu gui=new MainMenu(hotelModule,reservationModule,guestsModule,roomModule);

        gui.run();
    }
}