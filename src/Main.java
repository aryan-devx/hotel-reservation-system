import java.sql.DriverManager;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;


class HotelReservationSystem{
    private static String url = "jdbc:mysql://127.0.0.1:3306/hotel_db";
    private static String username = "root";
    private static String password = "aryan";
}
public class Main {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("driver loaded successfully");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }




    }
}