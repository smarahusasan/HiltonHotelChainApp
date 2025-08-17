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

public class GuestsModule {
    private final Service service;

    public GuestsModule(Service service) {
        this.service = service;
    }

    public void run(){
        while(true){
            printForGuestsModule();

            String choice = Input.sc.nextLine();
            switch(choice){
                case "1": {
                    System.out.println("Enter guest id:");
                    int id;
                    while (true) {
                        try {
                            String line=Input.sc.nextLine();
                            id = Integer.parseInt(line);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid guest id. Please try again.");
                        }
                    }

                    System.out.println("Enter guest name:");
                    String guestName = Input.sc.nextLine().trim();

                    System.out.println("Enter guest email:");
                    String guestEmail = Input.sc.nextLine().trim();

                    System.out.println("Enter guest phone number:");
                    String guestPhoneNumber = Input.sc.nextLine().trim();

                    System.out.println("Enter hotel id:");
                    int hotelId;
                    while (true) {
                        try {
                            String line=Input.sc.nextLine();
                            hotelId = Integer.parseInt(line);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid hotel id. Please try again.");
                        }
                    }

                    Guest guest = new Guest(id, guestEmail, guestName, guestPhoneNumber, hotelId);
                    try {
                        service.addGuest(guest);
                        System.out.println("Guest added!");
                        System.out.println("-------------------------------");
                    } catch (RepoException | ValidationException e) {
                        System.out.println("Something went wrong: " + e.getMessage());
                    }
                    break;
                }case "2": {
                    System.out.println("Going back to main menu.....");
                    System.out.println("-------------------------------");
                    return;
                }case "3":{
                    System.out.println("Now exiting.....\nGoodbye!");
                    System.exit(0);
                } default:{
                    System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

    private void printForGuestsModule(){
        System.out.println("You selected Guests Module!");
        System.out.println("-------------------------------");
        System.out.println("Please select your choice: ");
        System.out.println("1. Add a new guest to a specific hotel");
        System.out.println("2. Exit Module");
        System.out.println("3. Exit App");
    }

}
