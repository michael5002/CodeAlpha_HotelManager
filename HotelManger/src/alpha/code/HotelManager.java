package alpha.code;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HotelManager {
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;

    //outputs menu
    public void menu() {
        System.out.println();
        String menu = """
                ----Hotel System----
                
                Available actions:
                
                1 - Search Room
                
                2 - Book room
                
                3 - View booking
                
                4 - Make payment
                
                5 - Cancel reservation
                
                6 - Save & Exit
                
                Enter a number for which action you want to do:
                """;
        System.out.println(menu);
    }

    public HotelManager() {

        rooms = new ArrayList<>();
        reservations = new ArrayList<>();

        rooms.add(new Room(101,
                "STANDARD", 5000));

        rooms.add(new Room(102,
                "DELUXE", 10000));

        rooms.add(new Room(103,
                "SUITE", 20000));

        loadRooms();
        loadReservations();

    }

    //Search room
    public void searchRoom(String category) {

        for (Room rm : rooms) {
            if (rm.getCategory().equalsIgnoreCase(category)) {
                rm.displayRoom();
            }
        }
    }

    //Book room
    public void bookRoom(String customerName, int roomNumber) {
        for (Room rm : rooms) {
            if (rm.getRoomNumber() == roomNumber && rm.isAvailable()) {

                rm.setAvailable(false);

                Reservation reservation = new Reservation(customerName, roomNumber);
                reservations.add(reservation);

                System.out.println("Room booked successfully");

                return;
            }
        }
        System.out.println(
                "Room Not Available");
        System.out.println(
                "Try booking a different room."
        );
    }

    //Cancel reservation
    public void cancelReservation(int roomNumber) {
        boolean found = false;
        for (Reservation re : reservations) {

            for (Room rm : rooms) {//loops through every rm in rooms arraylist

                if (rm.getRoomNumber() == roomNumber && !rm.isAvailable() && re.getRoomNumber() == roomNumber) {
                    reservations.remove(re);

                    System.out.println("Reservation for Room " + roomNumber + " has been cancelled");
                    rm.setAvailable(true);

                    return;

                }

            }
        }

        System.out.println("Room " + roomNumber + " not found!");
    }

    //Display reservation
    public void viewReservation() {
        System.out.println();
        System.out.println("=======PARADISE HOTEL AND SUITE=======");
        for (Reservation re : reservations) {

            System.out.printf(" %12s -> %s%n %15s -> %d%n %18s -> %s%n",
                    "Customer", re.getCustomerName(),
                    "Room Number", re.getRoomNumber(),
                    "Payment status", re.getPaymentStatus());

        }
        System.out.println("--".repeat(18));
        System.out.println();
    }

    //payment process
    public void makePayment(int roomNumber) {
        boolean found = false;

        for (Room rm : rooms) {

            if (rm.getRoomNumber() == roomNumber && !rm.isAvailable()) {

                for (Reservation re : reservations) {

                    if (re.getRoomNumber() == roomNumber) {

                        re.makePayment();

                        System.out.printf(
                                "Payment For Room %d %s Package Successful.%n",
                                rm.getRoomNumber(),
                                rm.getCategory());

                        found = true;
                        break;
                    }
                }

                break; // Stop checking rooms once we've found the room number
            }
//            else {
//                System.out.println("Rm" + roomNumber + " not Found!");
//                break;
//            }
        }

        if (!found) {
            System.out.println("Reservation Not Found!\nor Room does not exist.");
            System.out.println("Please Ensure You Enter Correct Input Next Time.");
        }
    }

    //stores booking
    public void saveBookings() {
        System.out.println("Saving...");

        try {

            PrintWriter write =
                    new PrintWriter("bookings.txt");


            for (Reservation re : reservations) {

//                System.out.println(
//                        "Writing -> " + re);

                write.println(re);
            }

            write.close();

            System.out.println(
                    "Bookings Saved");


        } catch (IOException e) {

            System.out.println(
                    "Error saving file");


        }

    }



    public void saveRooms() {
        try {
            PrintWriter writer = new PrintWriter("rooms.txt");

            for (Room rm : rooms) {
                writer.println(rm);
            }
            writer.close();

        } catch (IOException e) {

            System.out.print("Error saving file");
        }
    }

    public void loadRooms() {
        File file = new File("rooms.txt");

        if (!file.exists() || file.length() == 0) {
            return; // Keep the default rooms
        }
        try {
            Scanner read = new Scanner(file);
            rooms.clear();

            while (read.hasNextLine()) {

                String[] data = read.nextLine().split(",");

                Room rm = new Room(Integer.parseInt(data[0]),
                        data[1], Double.parseDouble(data[2]));

                rm.setAvailable(Boolean.parseBoolean(data[3]));
                rooms.add(rm);
            }
        } catch (Exception e) {
            System.out.println("No room File Found");
        }
    }

    public void loadReservations() {
        try {
            Scanner read = new Scanner(new File("bookings.txt"));
            reservations.clear();

            while (read.hasNextLine()) {
                String[] data = read.nextLine().split(",");

                Reservation re = new Reservation(data[0], Integer.parseInt(data[1]));

                if (data[2].equalsIgnoreCase("Paid")) {
                    re.makePayment();

                }
                reservations.add(re);

                for(Room rm : rooms){
                    if(rm.getRoomNumber()==re.getRoomNumber()){
                        rm.setAvailable(false);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("No booking file found");
        }
    }

}


