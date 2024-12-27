package com.wompi.api.interactions.waits;

import lombok.AllArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

@AllArgsConstructor
public class Debug implements Interaction {

    private String message;
    private boolean breakPoint;

    @Override
    public <T extends Actor> void performAs(T actor)
    {
        //LogUtil.info(this.message);
        if(this.breakPoint){
            this.breakPoint = !this.breakPoint;

        }

    }

    public static Debug enConsola(String message)
    {
        return Tasks.instrumented(Debug.class,message,false);
    }

    public static Debug breakPoint(String console)
    {
        return Tasks.instrumented(Debug.class,console,true);
    }
}
