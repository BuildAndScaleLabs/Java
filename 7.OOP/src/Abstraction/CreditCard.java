package Abstraction;

public class CreditCard implements Payment{

    @Override
    public void pay(){
        System.out.println("Payment from Credit Card");
    }
}
