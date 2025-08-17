package smart.domain;

import smart.utils.enums.RoomType;

public class Room {
    private int roomNumber;
    private RoomType type;
    private boolean available;
    private int hotelId;

    public Room(int roomNumber, RoomType type, boolean available, int hotelId) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.available = available;
        this.hotelId = hotelId;
    }

    public Room(RoomType type, int hotelId) {
        this.type = type;
        this.hotelId = hotelId;
        this.available = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", type=" + type +
                ", available=" + available +
                ", hotelId=" + hotelId +
                '}';
    }
}
