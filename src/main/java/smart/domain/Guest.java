package smart.domain;

public record Guest(int guestId,String email,String name, String phone, int hotelId) {
    @Override
    public String toString() {
        return "Guest{" +
                "guestId=" + guestId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
