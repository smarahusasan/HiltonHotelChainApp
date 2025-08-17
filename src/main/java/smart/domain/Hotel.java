package smart.domain;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private int hotelId;
    private String name;
    private String location;
    private List<Room> rooms;
    private List<Guest> guests;
    private List<Reservation> reservations;

    public Hotel(int hotelId, String name, String location) {
        this.hotelId = hotelId;
        this.name = name;
        this.location = location;
        this.rooms = new ArrayList<>();
        this.guests=new ArrayList<>();
        this.reservations=new ArrayList<>();
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
