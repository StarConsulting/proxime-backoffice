package app.proxime.lambda.framework.time;

import java.time.Duration;
import java.time.Instant;

public class Timer {

    private static Timer instance;

    private Instant start;
    private Instant end;

    private Timer(){}

    public static Timer getTimer(){
        if(instance == null){
            instance = new Timer();
        }
        return instance;
    }

    public void start(){
        start = Instant.now();
    }

    public void finish(){
        end = Instant.now();
    }

    public long getDuration(){
        return Duration.between(this.start, this.end).toMillis();
    }

}
