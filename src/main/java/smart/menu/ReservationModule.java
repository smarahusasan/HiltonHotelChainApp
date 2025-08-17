package smart.menu;

import smart.domain.Guest;
import smart.domain.Hotel;
import smart.domain.Reservation;
import smart.domain.Room;
import smart.service.Service;
import smart.utils.Input;
import smart.utils.exceptions.RepoException;
import smart.utils.exceptions.ValidationException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationModule {
    private final Service service;

    public ReservationModule(Service service) {
        this.service = service;
    }

    public void run(){
        while(true){
            printForReservationModule();

            String choice = Input.sc.nextLine();
            switch(choice){
                case "1":{
                    System.out.println("Enter guest id:");
                    int guestId;
                    while(true){
                        try{
                            String line = Input.sc.nextLine();
                            guestId = Integer.parseInt(line);
                            break;
                        }catch(NumberFormatException e){
                            System.out.println("Invalid guest id. Please try again.");
                        }
                    }

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

                    System.out.println("Enter check in date:");
                    Date checkInDate;
                    while(true){
                        try{
                            checkInDate = Date.valueOf(Input.sc.nextLine());
                            break;
                        }catch (IllegalArgumentException e){
                            System.out.println("Invalid check in date. Please try again.");
                        }
                    }

                    System.out.println("Enter check out date:");
                    Date checkOutDate;
                    while(true){
                        try{
                            checkOutDate = Date.valueOf(Input.sc.nextLine());
                            break;
                        }catch (IllegalArgumentException e){
                            System.out.println("Invalid check out date. Please try again.");
                        }
                    }

                    Date resDate = Date.valueOf(LocalDate.now());

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

                    Reservation reservation=new Reservation(guestId,roomNumber,checkInDate,checkOutDate,resDate,hotelId);
                    try{
                        service.makeReservation(reservation);
                    }catch (RepoException | ValidationException e){
                        System.out.println("Something went wrong: " + e.getMessage());
                    }
                    break;
                }case "2":{
                    System.out.println("Enter reservation id:");
                    int resId;
                    while(true){
                        try{
                            String line = Input.sc.nextLine();
                            resId = Integer.parseInt(line);
                            break;
                        }catch(NumberFormatException e){
                            System.out.println("Invalid reservation id. Please try again.");
                        }
                    }
                    service.cancelReservation(resId);
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
                    List<Reservation> reservations=service.getReservationsForHotel(hotelId);
                    System.out.println("Reservations: " + reservations.stream().map(Reservation::toString).collect(Collectors.joining(", ")));
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

    private void printForReservationModule(){
        System.out.println("You selected Reservation Module!");
        System.out.println("-------------------------------");
        System.out.println("Please select your choice: ");
        System.out.println("1. Make a reservation");
        System.out.println("2. Cancel a reservation");
        System.out.println("3. View all reservations of a specific hotel");
        System.out.println("4. Exit Module");
        System.out.println("5. Exit App");
    }

}
