package smart.menu;

import smart.domain.Guest;
import smart.domain.Hotel;
import smart.domain.Reservation;
import smart.domain.Room;
import smart.service.Service;
import smart.utils.Input;
import smart.utils.exceptions.RepoException;
import smart.utils.exceptions.ValidationException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class HotelModule {
    private final Service service;

    public HotelModule(Service service) {
        this.service = service;
    }

    public void run(){
        while(true){
            printForHotelModule();

            String choice = Input.sc.nextLine();
            switch(choice){
                case "1":{
                    System.out.println("Enter hotel id:");
                    int id;
                    while (true) {
                        try {
                            String line = Input.sc.nextLine();
                            id = Integer.parseInt(line);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid hotel id. Please try again.");
                        }
                    }

                    System.out.println("Enter hotel name:");
                    String hotelName = Input.sc.nextLine();

                    System.out.println("Enter hotel location:");
                    String hotelAddress = Input.sc.nextLine();

                    Hotel hotel = new Hotel(id, hotelName, hotelAddress);
                    try{
                        service.addHotel(hotel);
                    }catch (RepoException | ValidationException e){
                        System.out.println("Something went wrong: " + e.getMessage());
                    }
                    break;
                }case "2":{
                    System.out.println("Enter hotel id:");
                    int hotelId;
                    while (true) {
                        try {
                            String line = Input.sc.nextLine();
                            hotelId = Integer.parseInt(line);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid hotel id. Please try again.");
                        }
                    }
                    Hotel hotel = service.getHotel(hotelId);
                    System.out.println("Name: " + hotel.getName());
                    System.out.println("Location: " + hotel.getLocation());
                    System.out.println("Rooms: " + hotel.getRooms().stream().map(Room::toString).collect(Collectors.joining(", ")));
                    System.out.println("Guests: " + hotel.getGuests().stream().map(Guest::toString).collect(Collectors.joining(", ")));
                    System.out.println("Reservations: "+ hotel.getReservations().stream().map(Reservation::toString).collect(Collectors.joining(", ")));
                    break;
                }
                case "3":{
                    System.out.println("Enter hotel id:");
                    int hotelId;
                    while (true) {
                        try {
                            String line = Input.sc.nextLine();
                            hotelId = Integer.parseInt(line);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid hotel id. Please try again.");
                        }
                    }
                    List<Room> rooms=service.getRoomForHotel(hotelId);
                    System.out.println("Rooms: " + rooms.stream().map(Room::toString).collect(Collectors.joining(", ")));
                    break;
                }
                case "4": {
                    System.out.println("Going back to main menu.....");
                    return;
                }case "5":{
                    System.out.println("Now exiting.....\nGoodbye!");
                    System.exit(0);
                } default:{
                    System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

    private void printForHotelModule(){
        System.out.println("You selected Hotel Module!");
        System.out.println("-------------------------------");
        System.out.println("Please select your choice: ");
        System.out.println("1. Add a new hotel");
        System.out.println("2. View details of a specific hotel");
        System.out.println("3. View all the rooms of a specific hotel");
        System.out.println("4. Exit Module");
        System.out.println("5. Exit App");
    }
}
