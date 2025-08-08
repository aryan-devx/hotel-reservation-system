import java.sql.*;
import java.util.Scanner;
import java.lang.Thread;


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
                        checkReservation(connection);
                        break;
                    case 3:
                        getReservation(connection, scanner);
                        break;
                    case 4:
                        updateReservation(connection, scanner);
                        break;
                    case 5:
                        deleteRservation(connection, scanner);
                        break;
                    case 6:
                        exitingSystem();
                        scanner.close();
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

    // exit
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

    // New Reservation
    private static void newReservation(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter guest name");
        String guestname = scanner.next();
        System.out.println("Enter guest room number");
        int roomNo = scanner.nextInt();
        System.out.println("Enter contact number");
        String contactNo = scanner.next();

        String query = "INSERT INTO reservation (guest_name, room_number, contact_number) VALUES ('" + guestname + "', " + roomNo + ", '" + contactNo + "')";

        try(Statement stmt = connection.createStatement()){
            int rowaffected = stmt.executeUpdate(query);

            if (rowaffected >0)
                System.out.println("reservation successfull");
            else
                System.out.println("reservation failed");
        }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private static void checkReservation(Connection connection) {
        try{
            String sql = "select * from reservation";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                int reservartionId = rs.getInt("reservation_id");
                String guestName = rs.getString("guest_name");
                int roomNumber = rs.getInt("room_number");
                String contactNumber = rs.getString("contact_number");
                String reservatioinDate = rs.getTimestamp("reservation_date").toString();

                System.out.println("Reservation ID: " + reservartionId);
                System.out.println("Guest name:  "+ guestName);
                System.out.println("Room number: " + roomNumber);
                System.out.println("Contact number: " + contactNumber);
                System.out.println("Reservation date: " + reservatioinDate);
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void getReservation(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter reservation ID: ");
            int reservationId = scanner.nextInt();
            System.out.print("Enter guest name: ");
            String guestName = scanner.next();

            String sql = "SELECT room_number FROM reservations " +
                    "WHERE reservation_id = " + reservationId +
                    " AND guest_name = '" + guestName + "'";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    int roomNumber = resultSet.getInt("room_number");
                    System.out.println("Room number for Reservation ID " + reservationId +
                            " and Guest " + guestName + " is: " + roomNumber);
                } else {
                    System.out.println("Reservation not found for the given ID and guest name.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void updateReservation(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter reservation ID to update: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }

            System.out.print("Enter new guest name: ");
            String newGuestName = scanner.nextLine();
            System.out.print("Enter new room number: ");
            int newRoomNumber = scanner.nextInt();
            System.out.print("Enter new contact number: ");
            String newContactNumber = scanner.next();

            String sql = "UPDATE reservations SET guest_name = '" + newGuestName + "', " +
                    "room_number = " + newRoomNumber + ", " +
                    "contact_number = '" + newContactNumber + "' " +
                    "WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation updated successfully!");
                } else {
                    System.out.println("Reservation update failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static boolean reservationExists(Connection connection, int reservationId) {
        try {
            String sql = "SELECT reservation_id FROM reservations WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                return resultSet.next(); // If there's a result, the reservation exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle database errors as needed
        }
    }

    private static void deleteRservation(Connection connection, Scanner scanner) throws InterruptedException {
        try {
            System.out.print("Enter reservation ID to delete: ");
            int reservationId = scanner.nextInt();

            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }

            String sql = "DELETE FROM reservations WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation deleted successfully!");
                } else {
                    System.out.println("Reservation deletion failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int i = 5;
        while(i!=0){
            System.out.print(".");
            Thread.sleep(1000);
            i--;
        }
        System.out.println();
        System.out.println("ThankYou For Using Hotel Reservation System!!!");
    }

}