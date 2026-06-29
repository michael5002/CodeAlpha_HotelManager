package alpha.code;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HotelManager manager = new HotelManager();

//        manager.loadRooms();
//        manager.loadReservations();

        Scanner sc = new Scanner(System.in);

        int choice = 0;

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
                    System.out.println("""
                            Enter category of the room
                            1 -> Standard

                            2 -> Deluxe

                            3 -> Suite
                            """);

                    try {

                       int val = sc.nextInt();
                        if(val < 1 || val > 3 ){
                            System.out.println("Wrong Input");
                            break;
                        }
                        String[] categories = {
                                "Standard",
                                "Deluxe",
                                "Suite"
                        };
                        System.out.println("Searching for -> "+categories[val-1]  +" Category");
                        manager.searchRoom(categories[val - 1]);
                        break;
                    }catch (InputMismatchException e){
                        System.out.println("Please Enter Digits!");

                    }
                    break;

                case 2:
                    System.out.println("Enter Full name: ");
                    sc.nextLine();
                    String fullName = sc.nextLine();

                    System.out.println("Enter room number");
                    try{
                        int rmNum = sc.nextInt();

                        manager.bookRoom(fullName.toUpperCase(),rmNum);

                        break;
                    }catch (InputMismatchException e){
                        System.out.println("Invalid Input!");
                        System.out.println("\nYou can try again\nEnsure you enter the correct details asked from you.");
                        break;
                    }



                case 3:
                    manager.viewReservation();
                    break;

                case 4:
                    System.out.println("Enter room number ");
                    try{
                        int payRm = sc.nextInt();
                        System.out.println("""
                            Non Refundable Transaction
                            Are you sure you want to continue? (YES/NO)""");
                        String answer = sc.next();

                        if(answer.equalsIgnoreCase("YES")){
                            manager.makePayment(payRm);
                            break;
                        }
                    }catch (InputMismatchException e){
                        System.out.println("Invalid Input!");
                        System.out.println("\nYou can try again\nEnsure you enter the correct details asked from you.");

                        break;
                    }

                    break;

                case 5:
                    System.out.println("Enter room number ");
                    try{
                        int cancelRm = sc.nextInt();

                        manager.cancelReservation(cancelRm);
                        break;
                    }catch (InputMismatchException e){
                        System.out.println("Invalid Input!");
                        System.out.println("\nYou can try again\nEnsure you enter the correct details asked from you.");

                        break;
                    }



                case 6:
                    //manager.saveToFile();
                    manager.saveBookings();
                    manager.saveRooms();
                    break;

                default:
                    System.out.println("Wrong input");
                    break;
            }

        }while(choice != 6);

    }
}
