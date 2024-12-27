package com.wompi.api.utils.data;


import com.wompi.api.utils.GeneralUtil;

/**
 * Utility related to null data processing
 */
public class NullUtil
{
    public static final String ID_NULL = "%[NULL]%";

    private NullUtil(){
        GeneralUtil.notAllowInstantiation();
    }

    public static boolean isNull(String data) {

        return ID_NULL.equals(data);
    }
}
