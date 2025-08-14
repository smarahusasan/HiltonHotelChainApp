package smart.domain;

public class Guest {
    private int guestId;
    private String email;
    private String name;
    private String phone;
    private int hotelId;

    public Guest(int guestId, String email, String name, String phone, int hotelId) {
        this.guestId = guestId;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.hotelId = hotelId;
    }

    public Guest(int guestId, String email, String name, String phone) {
        this.guestId = guestId;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
}
