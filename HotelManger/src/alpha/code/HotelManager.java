package alpha.code;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HotelManager {
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;

    //outputs menu
    public void menu(){
        System.out.println();
        String menu = """
                ----Hotel System----
                
                Available actions:
                
                1 - Search Room
                
                2 - Book room
                
                3 - View booking
                
                4 - Make payment
                
                5 - Cancel reservation
                
                6 - Save
                
                0 - Exit
                
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
    }

    //Search room
    public void searchRoom(String category){
        for(Room rm : rooms){
            if(rm.getCategory().equalsIgnoreCase(category)){
                rm.displayRoom();
            }
        }
    }

    //Book room
    public void bookRoom(String customerName, int roomNumber){
        for(Room rm : rooms){
            if(rm.getRoomNumber() == roomNumber && rm.isAvailable()){

                rm.setAvailable(false);

                Reservation reservation = new Reservation(customerName, roomNumber);
                reservations.add(reservation);

                System.out.println("Room booked successfully");

                return;
            }
        }
        System.out.println(
                "Room Not Available");
    }

    //Cancel reservation
    public void cancelReservation(int roomNumber){
        for(Reservation re : reservations){
            for(Room rm : rooms){//loops through every rm in rooms arraylist

                if (rm.getRoomNumber() == roomNumber && !rm.isAvailable()){

                 rm.setAvailable(true);

                    reservations.remove(re);

                    System.out.println("Reservation for Room " + roomNumber + " has been cancelled");
                    return;

                }

            }
        }


    }

    //Display reservation
    public void viewReservation(){
        System.out.println();
        System.out.println("=======PARADISE HOTEL AND SUITE=======");
        for(Reservation re : reservations){

            System.out.printf(" %12s -> %s%n %15s -> %d%n %18s -> %s%n",
                    "Customer",re.getCustomerName(),
                    "Room Number",re.getRoomNumber(),
                    "Payment status",re.getPaymentStatus());

        }
        System.out.println("--".repeat(18));
        System.out.println();
    }

    //payment process
    public void makePayment(int roomNumber){
        for(Reservation re : reservations) {
            for (Room rm : rooms) {
                if (re.getRoomNumber() == roomNumber && !rm.isAvailable()) {
                    re.makePayment();

                    System.out.printf("Payment For Room %d %s Package Successful.",
                            re.getRoomNumber(), rm.getCategory());
                    System.out.println();
                    break;
                } else if (re.getRoomNumber() != roomNumber) {
                    System.out.println("Reservation Not Found");
                break;
                }
            }
        }


    }

    //stores booking
    public boolean saveToFile(){
        System.out.println("Saving...");

        try {

            PrintWriter write =
                    new PrintWriter("booking.txt");

            System.out.println(
                    "Reservations found: "
                            + reservations.size());

            for(Reservation re : reservations){

                System.out.println(
                        "Writing -> " + re);

                write.println(re);
            }

            write.close();

            System.out.println(
                    "Bookings Saved");

            return true;

        } catch(IOException e){

            System.out.println(
                    "Error saving file");

            return false;
        }

        //String filePath = "C:\\Users\\User\\Desktop\\booking.txt";

//        try {
//            PrintWriter write = new PrintWriter("booking.txt");
//
//            for(Reservation re : reservations){
//                write.println(re);
//
//            }
//            write.close();
//
//            System.out.println(
//                    "Bookings Saved");
//
//            return true;
//
//        }catch (IOException e){
//
//            System.out.println("Error saving file");
//            return false;
//        }



    }

    public void loadFile(){

        new MyFrame();
    }

    //carry out various operations
    public void processData(){
        HotelManager manager = new HotelManager();

        Scanner sc = new Scanner(System.in);

        int choice = 0;
        //manager.menu();
        do {
            manager.menu();

            while(true){
                try {
                    choice = sc.nextInt();

                    break;
                } catch (InputMismatchException e) {

                    System.out.println("Enter correct input!");
                    manager.menu();
                    sc.nextLine();


                }

            }

            switch (choice){
                case 1:
                    String category = " ";
                    System.out.println("""
                            Enter category of the room
                            1 -> Standard
                            
                            2 -> Deluxe
                            
                            3 -> Suite
                            """);

                    int val = sc.nextInt();
                    switch (val){
                        case 1:
                            category = "standard";
                            break;

                        case 2:
                            category = "deluxe";
                            break;

                        case 3:
                            category = "suite";
                            break;

                        default:
                            System.out.println("Wrong Input!");
                    }
                    manager.searchRoom(category.toUpperCase());
                    break;

                case 2:
                    System.out.println("Enter Full name: ");
                    sc.nextLine();
                    String fullName = sc.nextLine();

                    System.out.println("Enter room number");
                    int rmNum = sc.nextInt();

                    manager.bookRoom(fullName.toUpperCase(),rmNum);

                    break;

                case 3:
                    manager.viewReservation();
                    break;

                case 4:
                    System.out.println("Enter room number ");
                    int payRm = sc.nextInt();
                    System.out.println("""
                            Non Refundable Transaction
                            Are you sure you want to continue? (YES/NO)""");
                    String answer = sc.next();

                    if(answer.equalsIgnoreCase("YES")){
                        manager.makePayment(payRm);
                        break;
                    }

                    break;

                case 5:
                    System.out.println("Enter room number ");
                    int cancelRm = sc.nextInt();

                    manager.cancelReservation(cancelRm);
                    break;

                case 6:
                    //manager.saveToFile();
                    if(manager.saveToFile()) {
                        manager.loadFile();

                    }
                    break;

                default:
                    System.out.println("Wrong input");
                    break;
            }

        }while(choice != 6);
    }



}
