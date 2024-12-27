package com.wompi.api.interactions.waits;


import com.wompi.api.utils.time.WaitUtil;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;

import java.time.Duration;

public class Stop
{
    private long time;

    private Stop(long time)
    {
        this.time = time;
    }

    private class StopFor implements Interaction
    {
        private long milliseconds;

        private StopFor(long milliseconds){
            this.milliseconds = milliseconds;
        }

        @Override
        public <T extends Actor> void performAs(T actor)
        {
            WaitUtil.stopFor(this.milliseconds);
        }
    }

    public static Stop forA(long time)
    {
        return new Stop(time);
    }

    public StopFor seconds()
    {
        return new StopFor(Duration.ofSeconds(this.time).toMillis());
    }

    public StopFor minutes()
    {
        return new StopFor(Duration.ofMinutes(this.time).toMillis());
    }

    public StopFor milliseconds()
    {
        return new StopFor(this.time);
    }

}
