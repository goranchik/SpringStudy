package lab1.practice;


public class MessagePrinter implements Printer {

    private String message;

    @InjectRandomInt(10)
    private int count;

    public void print() {
        System.out.println(count);
        for (int i = 0; i < count; i++) {
            System.out.println(message);
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
