package smart.menu;

import smart.domain.Reservation;
import smart.service.Service;
import smart.utils.Input;
import smart.utils.exceptions.RepoException;
import smart.utils.exceptions.ValidationException;

import java.sql.Date;
import java.time.LocalDate;
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
                    int guestId=Input.getIntFromInput();

                    System.out.println("Enter hotel id:");
                    int hotelId=Input.getIntFromInput();

                    System.out.println("Enter check in date:");
                    Date checkInDate=Input.getDateFromInput();

                    System.out.println("Enter check out date:");
                    Date checkOutDate=Input.getDateFromInput();

                    Date resDate = Date.valueOf(LocalDate.now());

                    System.out.println("Enter room number:");
                    int roomNumber=Input.getIntFromInput();

                    Reservation reservation=new Reservation(guestId,roomNumber,checkInDate,checkOutDate,resDate,hotelId);
                    try{
                        service.makeReservation(reservation);
                        System.out.println("Reservation made.");
                        System.out.println("-------------------------------");
                    }catch (RepoException | ValidationException e){
                        System.out.println("Something went wrong: " + e.getMessage());
                    }
                    break;
                }case "2":{
                    System.out.println("Enter reservation id:");
                    int resId =Input.getIntFromInput();
                    try{
                        service.cancelReservation(resId);
                        System.out.println("Reservation cancelled.");
                        System.out.println("-------------------------------");
                    }catch (RepoException e){
                        System.out.println("Something went wrong: " + e.getMessage());
                    }
                    break;
                }
                case "3":{
                    System.out.println("Enter hotel id:");
                    int hotelId=Input.getIntFromInput();
                    try{
                        List<Reservation> reservations=service.getReservationsForHotel(hotelId);
                        System.out.println("Reservations: " + reservations.stream().map(Reservation::toString).collect(Collectors.joining(", ")));
                        System.out.println("-------------------------------");
                    }catch (RepoException e){
                        System.out.println("Something went wrong: " + e.getMessage());
                    }
                    break;
                }
                case "4": {
                    System.out.println("Going back to main menu.....");
                    System.out.println("-------------------------------");
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
