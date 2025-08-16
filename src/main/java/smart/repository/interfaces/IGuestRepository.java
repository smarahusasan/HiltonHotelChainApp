package smart.repository.interfaces;

import smart.domain.Guest;

import java.util.List;

public interface IGuestRepository {
    void addGuest(Guest guest);
    List<Guest> getGuestsForHotel(int hotelId);
}
