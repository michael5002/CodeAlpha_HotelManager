package alpha.code;

public class Room {
    private int roomNumber;
    private String category;
    private double price;
    private boolean available;

    //constructor
    public Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }

    //output room number
    public int getRoomNumber() {
        return roomNumber;
    }

    //output category
    public String getCategory() {
        return category;
    }

    //output price
    public double getPrice() {
        return price;
    }

    //sets availability
    public void setAvailable(boolean available){
        this.available = available;
    }

    //output availability
    public boolean isAvailable(){
        return available;
    }

    public void displayRoom(){
        if(available){
            System.out.println("Rm "+roomNumber + " | " + category + " | " + "#" + price + "| "+
                    "Available");
        }else
            System.out.println("Rm "+roomNumber + " | " + category + " | " + price + "| "+
                    "Booked!");


    }

}
