package Abstraction;

public class DebitCard implements Payment{
    @Override
    public void pay() {
        System.out.println("Payment from debit card");
    }
}
