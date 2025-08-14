package smart.domain;

public class Room {
    private int roomNumber;
    private String type;
    private boolean available;
    private int hotelId;

    public Room(int roomNumber, String type, boolean available, int hotelId) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.available = available;
        this.hotelId = hotelId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
}
