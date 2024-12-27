package com.wompi.api.utils.time;


import com.wompi.api.utils.GeneralUtil;

public class WaitUtil
{

    private WaitUtil(){
        GeneralUtil.notAllowInstantiation();
    }

    public static void stopFor(long time){

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
