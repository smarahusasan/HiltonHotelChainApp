package smart.menu;

import smart.service.Service;
import smart.utils.Input;

import java.util.Scanner;

public class MainMenu {
    private final HotelModule hotelModule;
    private final ReservationModule reservationModule;
    private final GuestsModule guestsModule;
    private final RoomModule roomModule;

    public MainMenu(HotelModule hotelModule, ReservationModule reservationModule, GuestsModule guestsModule, RoomModule roomModule) {
        this.hotelModule = hotelModule;
        this.reservationModule = reservationModule;
        this.guestsModule = guestsModule;
        this.roomModule = roomModule;
    }

   private void printModules(){
        System.out.println("\uD83C\uDFE8 Welcome to Hilton Hotel's App!");
        System.out.println("-------------------------------");
        System.out.println("Please select your choice: ");
        System.out.println("1. Hotel Module");
        System.out.println("2. Guests Module");
        System.out.println("3. Reservation Module");
        System.out.println("4. Room Module");
        System.out.println("5. Exit App");
    }

    public void run(){
        while(true){
            printModules();

            String choice = Input.sc.nextLine();
            switch(choice){
                case "1":{
                    hotelModule.run();
                    break;
                }case "2":{
                    guestsModule.run();
                    break;
                }
                case "3":{
                    reservationModule.run();
                    break;
                }
                case "4": {
                    roomModule.run();
                    break;
                }case "5":{
                    System.out.println("Now exiting.....\nGoodbye!");
                    return;
                } default:{
                    System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }
}
