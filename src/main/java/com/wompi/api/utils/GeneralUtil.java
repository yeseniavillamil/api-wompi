package com.wompi.api.utils;


public class GeneralUtil
{

    private GeneralUtil(){
        GeneralUtil.notAllowInstantiation();
    }

    public static void notAllowInstantiation(){
        throw new UnsupportedOperationException();
    }

}
