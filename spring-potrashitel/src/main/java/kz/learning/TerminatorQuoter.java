package kz.learning;

public class TerminatorQuoter implements Quoter{

    @InjectRandomInt(min = 5, max = 10)
    private int repeat;

    private String message;

    @Override
    public void say() {
        for (int i = 0; i < repeat; i++) {
            System.out.println(message);
        }
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
