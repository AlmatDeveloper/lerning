package kz.learning;

import javax.annotation.PostConstruct;

@Profiling
public class TerminatorQuoter implements Quoter {

    @InjectRandomInt(min = 5, max = 10)
    private int repeat;

    private String message;

    public TerminatorQuoter() {
        System.out.println("Phase 1");
    }

    @PostConstruct
    public void init() {
        System.out.println("Phase 2");
    }

    @Override
    @PostProxy
    public void say() {
        System.out.println("Phase 3 or call");
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
