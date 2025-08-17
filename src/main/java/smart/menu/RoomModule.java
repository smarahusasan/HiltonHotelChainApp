package smart.menu;

import smart.domain.Guest;
import smart.domain.Hotel;
import smart.domain.Reservation;
import smart.domain.Room;
import smart.service.Service;
import smart.utils.Input;
import smart.utils.enums.RoomType;
import smart.utils.exceptions.RepoException;
import smart.utils.exceptions.ValidationException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

public class RoomModule {
    private final Service service;

    public RoomModule(Service service) {
        this.service = service;
    }

    public void run(){
        while(true){
            printForRoomModule();

            String choice = Input.sc.nextLine();
            switch(choice){
                case "1":{
                    System.out.println("Enter room type:");
                    RoomType roomType;
                    while(true){
                        try{
                            roomType = RoomType.valueOf(Input.sc.nextLine().trim());
                            break;
                        }catch(IllegalArgumentException e){
                            System.out.println("Invalid room type. Please try again.");
                        }
                    }

                    System.out.println("Enter hotel id:");
                    int id;
                    while(true){
                        try{
                            String line = Input.sc.nextLine();
                            id = Integer.parseInt(line);
                            break;
                        }catch(NumberFormatException e){
                            System.out.println("Invalid hotel id. Please try again.");
                        }
                    }

                    Room room=new Room(roomType,id);
                    try{
                        service.addRoom(room);
                    }catch (RepoException | ValidationException e){
                        System.out.println("Something went wrong: " + e.getMessage());
                    }
                    break;
                }case "2":{
                    System.out.println("Enter room number:");
                    int roomNumber;
                    while(true){
                        try{
                            String line = Input.sc.nextLine();
                            roomNumber = Integer.parseInt(line);
                            break;
                        }catch(NumberFormatException e){
                            System.out.println("Invalid room number. Please try again.");
                        }
                    }
                    Room room=service.getRoom(roomNumber);
                    System.out.println(room.toString());
                    break;
                }
                case "3":{
                    System.out.println("Enter hotel id:");
                    int hotelId;
                    while(true){
                        try{
                            String line = Input.sc.nextLine();
                            hotelId = Integer.parseInt(line);
                            break;
                        }catch(NumberFormatException e){
                            System.out.println("Invalid hotel id. Please try again.");
                        }
                    }
                    List<Room> rooms=service.getRoomForHotel(hotelId);
                    System.out.println("Rooms: " +
                            rooms.stream().filter(Room::isAvailable).map(Room::toString).collect(Collectors.joining(", ")));
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

    private void printForRoomModule(){
        System.out.println("You selected Room Module!");
        System.out.println("-------------------------------");
        System.out.println("Please select your choice: ");
        System.out.println("1. Add a new room");
        System.out.println("2. View details of a specific room");
        System.out.println("3. View available rooms of a specific hotel");
        System.out.println("4. Exit Module");
        System.out.println("5. Exit App");
    }
}
