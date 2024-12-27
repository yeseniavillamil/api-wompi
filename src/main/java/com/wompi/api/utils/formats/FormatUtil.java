package com.wompi.api.utils.formats;

import com.wompi.api.models.scena.Protagonist;
import com.wompi.api.utils.GeneralUtil;
import com.wompi.api.utils.data.NullUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatUtil
{
    private FormatUtil(){
        GeneralUtil.notAllowInstantiation();
    }

    public static String validateTblGherkin(Map<String, String> tblGherkin, String key, String value)
    {
        if(FormatUtil.hasFormat(value)) {

            String pattern = "\\{\\[([^]]+)]}";
            Matcher matcher = Pattern.compile(pattern).matcher(value);
            while (matcher.find()) {
                String keyRef = matcher.group(1);
                String partialValueFormatted = validateTblGherkin(tblGherkin, keyRef,
                        tblGherkin.getOrDefault(
                                keyRef,
                                Protagonist.review().getTblGherkin().get(keyRef)
                        )
                );

                value = value.replace(
                        "{[" + matcher.group(1) + "]}",
                        partialValueFormatted == null
                                ? NullUtil.ID_NULL
                                : partialValueFormatted
                );
                matcher.reset(value);
            }

            value = validate(value);

            tblGherkin.replace(key, value);
        }

        return value;
    }

    private static boolean hasFormat(String value){

        String pattern = "\\{([^}]+)}";
        Matcher matcher = Pattern.compile(pattern).matcher(value);

        return matcher.find();
    }

    public static String validate(String value)
    {
        if(FormatUtil.nullIdentified(value)){
            value = null;
        }else{
            value = FormatUtil.replaceWithRandomNumber(value);
            value = FormatUtil.replaceWithDate(value);
            value = FormatUtil.replaceWithBlanks(value);
            value = FormatUtil.replaceWithPropertie(value);
            value = FormatUtil.replaceWithNullId(value);
        }

        return value;
    }

    private static boolean nullIdentified(String text)
    {
        String id = "[Nn][Uu][Ll]{2}";
        String pattern = "\\{("+id+")\\}";

        return text.matches(pattern);
    }

    private static String replaceWithPropertie(String text)
    {
        String id = "[A-Za-z0-9_-]+";
        String pattern = "\\{[pP]:("+id+")(:(.+))?\\}";

        Matcher matcher = Pattern.compile(pattern).matcher(text);

        if( matcher.find()  )
        {
            try{
                StringBuffer result = new StringBuffer();

                do {
                    matcher.appendReplacement(
                            result,
                            System.getProperty(matcher.group(1),matcher.group(3))
                    );
                }while  (   matcher.find()  );
                matcher.appendTail(result);

                return result.toString();

            }catch(Exception e){
                return text;
            }

        }else{
            return text;
        }
    }

    private static String replaceWithNullId(String text)
    {
        String id = "[Nn][Uu][Ll]{2}";
        String pattern = "\\{'"+id+"'\\}";

        Matcher matcher = Pattern.compile(pattern).matcher(text);

        if( matcher.find()  )
        {
            try{
                StringBuffer result = new StringBuffer();

                do {
                    matcher.appendReplacement(
                            result,
                            NullUtil.ID_NULL
                    );
                }while  (   matcher.find()  );
                matcher.appendTail(result);

                return result.toString();

            }catch(Exception e){
                return text;
            }

        }else{
            return text;
        }
    }

    private static String replaceWithBlanks(String text)
    {
        String id = "\\s";
        String patron = "\\{("+id+"*)\\}";

        Matcher matcher = Pattern.compile(patron).matcher(text);

        if( matcher.find()  )
        {
            StringBuffer result = new StringBuffer();

            do {
                matcher.appendReplacement(
                    result,
                    matcher.group(1)
                );
            }while  (   matcher.find()  );
            matcher.appendTail(result);

            return result.toString();
        }else{
            return text;
        }
    }

    private static String replaceWithRandomNumber(String text)
    {
        String id = "#";
        String pattern = "\\{("+id+"+)\\}";

        Matcher matcher = Pattern.compile(pattern).matcher(text);

        if( matcher.find()  )
        {
            StringBuffer result = new StringBuffer();
            int min = 0;
            BigInteger max;

            do {
                int totalNum = StringUtils.countMatches(matcher.group(1),id);
                max = new BigDecimal(matcher.group(1).replace("#","9")).toBigInteger();
                BigDecimal numero = BigDecimal.valueOf(min + Math.random()*(max.doubleValue()-min+1));

                matcher.appendReplacement(
                    result,
                    StringUtils.leftPad(String.valueOf(numero.toBigInteger()),totalNum,"0")
                );
            }while  (   matcher.find()  );
            matcher.appendTail(result);

            return result.toString();
        }else{
            return text;
        }
    }

    private static String replaceWithDate(String text)
    {
        String pattern = "\\{([dMyHhms]+)\\}";

        Matcher matcher = Pattern.compile(pattern).matcher(text);

        if( matcher.find() )
        {
            StringBuffer result = new StringBuffer();

            Calendar date = Calendar.getInstance();
            String dayDate = StringUtils.leftPad(String.valueOf(date.get(Calendar.DAY_OF_MONTH)),2,"0");
            String monthDate = StringUtils.leftPad(String.valueOf((date.get(Calendar.MONTH)+1)),2,"0");
            String yearDate = String.valueOf(date.get(Calendar.YEAR));
            String hour12Date = StringUtils.leftPad(String.valueOf(date.get(Calendar.HOUR)),2,"0");
            String hour24Date = StringUtils.leftPad(String.valueOf(date.get(Calendar.HOUR_OF_DAY)),2,"0");
            String minuteDate = StringUtils.leftPad(String.valueOf(date.get(Calendar.MINUTE)),2,"0");
            String secondDate = StringUtils.leftPad(String.valueOf(date.get(Calendar.SECOND)),2,"0");

            do{
                text = FormatUtil.replaceWithDateItem(matcher.group(1),"d",dayDate);
                text = FormatUtil.replaceWithDateItem(text,"M",monthDate);
                text = FormatUtil.replaceWithDateItem(text,"y",yearDate);
                text = FormatUtil.replaceWithDateItem(text,"H",hour24Date);
                text = FormatUtil.replaceWithDateItem(text,"h",hour12Date);
                text = FormatUtil.replaceWithDateItem(text,"m",minuteDate);
                text = FormatUtil.replaceWithDateItem(text,"s",secondDate);

                matcher.appendReplacement(result,text);
            }while  ( matcher.find()  );
            matcher.appendTail(result);

            return result.toString();
        }else{
            return text;
        }
    }

    private static String replaceWithDateItem(String text, String id, String valueDateItem)
    {
        StringBuffer result = new StringBuffer();
        Matcher matcher = Pattern.compile("("+id+"+)").matcher(text);
        boolean idYear = id.equals("y");
        while  (   matcher.find()  )
        {
            int totalId = StringUtils.countMatches(matcher.group(),id);

            int offset = idYear && totalId<4 ?valueDateItem.length()-totalId:0;
            String replace = StringUtils.truncate(
                StringUtils.leftPad(valueDateItem,totalId,"0"),
                offset,
                totalId
            );

            matcher.appendReplacement(result,replace);
        }
        matcher.appendTail(result);

        return result.toString();
    }

}
