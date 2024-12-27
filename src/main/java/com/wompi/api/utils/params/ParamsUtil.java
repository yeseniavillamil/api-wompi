package com.wompi.api.utils.params;

import com.wompi.api.utils.GeneralUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


public class ParamsUtil
{
    public static final String ID_INITIAL = "{";
    public static final String ID_FINAL = "}";
    public static final String RGX_FORMAT = "\\"+ ID_INITIAL +"\\d+\\"+ID_FINAL;//[0-9]

    public static final Pattern PAT_RGX_FORMAT = Pattern.compile(RGX_FORMAT);


    private ParamsUtil(){
        GeneralUtil.notAllowInstantiation();
    }

    public static String processParameters(String pString, Object ... pParameters) {

        if(PAT_RGX_FORMAT.matcher(pString).find()) {
            if(pParameters!=null && pParameters.length>0) {
                for(int i=0 ; i<pParameters.length ; i++) {
                    Object parameter = pParameters[i];
                    String parameterValue = parameter==null? StringUtils.EMPTY:parameter.toString();

                    pString = pString.replace(ID_INITIAL +i+ID_FINAL, parameterValue);
                }
            }
            pString = pString.replaceAll(RGX_FORMAT,StringUtils.EMPTY);
        }

        return pString;
    }

    public static List<String> processString(String string){
        if (string==null || string.isEmpty()){
            return new ArrayList<>();
        }else {
            char chrFirstCharacter = string.charAt(0);
            String strFirtsCharacter = String.valueOf(chrFirstCharacter);

            String separate;
            if(Character.isLetterOrDigit(chrFirstCharacter))
            {
                separate=null;
            }else{
                separate=Pattern.quote(strFirtsCharacter);
                string = string.replaceAll("^"+separate, StringUtils.EMPTY);
            }

            return separate==null
                ?   Arrays.asList(string)
                :   Arrays.asList(string.split(separate))
                ;
        }
    }
}
