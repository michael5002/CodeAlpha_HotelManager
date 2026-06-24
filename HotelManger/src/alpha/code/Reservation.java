package alpha.code;

public class Reservation {
   private String customerName;
   private int roomNumber;
   private String paymentStatus;

    public Reservation(String customerName, int roomNumber) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.paymentStatus = "Not paid";
    }

    //output Customer Name
    public String getCustomerName() {
        return customerName;
    }

    //output room number
    public int getRoomNumber() {
        return roomNumber;
    }

    //output payment status
    public String getPaymentStatus() {
        return paymentStatus;
    }


    public String makePayment(){
        return paymentStatus = "Paid";
    }

    @Override
    public String toString() {
        return (customerName.toUpperCase() + " -> Room " + roomNumber + " -> " + paymentStatus);
    }
}
