package smart.utils;

import java.sql.Date;
import java.util.Scanner;

public class Input {
    public static final Scanner sc = new Scanner(System.in);

    public static int getIntFromInput(){
        int id;
        while (true) {
            try {
                String line=Input.sc.nextLine();
                id = Integer.parseInt(line);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid data. Please try again.");
            }
        }
        return id;
    }

    public static Date getDateFromInput(){
        Date date;
        while(true){
            try{
                date = Date.valueOf(Input.sc.nextLine().trim());
                break;
            }catch (IllegalArgumentException e){
                System.out.println("Invalid data. Please try again.");
            }
        }
        return date;
    }
}
