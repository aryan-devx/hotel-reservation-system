import javax.sound.midi.Soundbank;
import java.sql.DriverManager;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.lang.Thread;

import static java.lang.System.exit;


public class Main {
    // connection constants
    private static final String url = "jdbc:mysql://127.0.0.1:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "aryan";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("driver loaded successfully");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            while(true){
                System.out.println("Hotel Management System");
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. New Reservation");
                System.out.println("2. Check Reservation");
                System.out.println("3. Get Room no");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("6. exit");
                System.out.println("Choose an option");
                int opt = scanner.nextInt();
                switch (opt){
                    case 1:
                        newReservation(connection, scanner);
                        break;
                    case 2:
                        checkReservation(connection, scanner);
                        break;
                    case 3:
                        getReservation(connection, scanner);
                        break;
                    case 4:
                        updateReservation(connection, scanner);
                        break;
                    case 5:
                        delectRservation(connection, scanner);
                        break;
                    case 6:
                        exitingSystem();
                        return;
                    default:
                        System.out.println("enter a valide input");
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    private static void exitingSystem() throws InterruptedException {
        System.out.print("Exiting System");
        int i=5;
        while (i!=0){
            System.out.print(".");
            Thread.sleep(500);
            i--;
        }
        System.out.println(" ");
        System.out.println("thanks for using Hotel Reservation System");
    }

    private static void newReservation(Connection connection, Scanner scanner) {

    }

    private static void checkReservation(Connection connection, Scanner scanner) {

    }

    private static void getReservation(Connection connection, Scanner scanner) {

    }

    private static void updateReservation(Connection connection, Scanner scanner) {

    }

    private static void delectRservation(Connection connection, Scanner scanner) {

    }

}