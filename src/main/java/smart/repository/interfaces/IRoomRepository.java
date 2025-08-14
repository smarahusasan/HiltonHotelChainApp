package smart.repository.interfaces;

import smart.domain.Room;

import java.util.List;

public interface IRoomRepository {
    void addRoom(Room room);
    List<Room> getRoomsForHotel(int hotelId);
    Room getRoom(int roomId);
}
