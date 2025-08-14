package smart.repository.interfaces;

import smart.domain.Hotel;

public interface IHotelRepository {
    void addHotel(Hotel hotel);
    Hotel getHotel(int hotelId);
}
