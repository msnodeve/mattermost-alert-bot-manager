package kr.co.seok.utils;

import com.ibm.icu.util.ChineseCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;


public class LunarCalendar {
    static ArrayList<Holidays> holidaysArrayList = new ArrayList<>();

    /**
     * 음력날짜를 양력날짜로 변환
     *
     * @param 음력날짜 (yyyyMMdd)
     * @return 양력날짜 (yyyyMMdd)
     */
    public static String Lunar2Solar(String yyyymmdd) {
        ChineseCalendar cc = new ChineseCalendar();
        Calendar cal = Calendar.getInstance();

        if (yyyymmdd == null)
            return "";

        String date = yyyymmdd.trim();
        if (date.length() != 8) {
            if (date.length() == 4)
                date = date + "0101";
            else if (date.length() == 6)
                date = date + "01";
            else if (date.length() > 8)
                date = date.substring(0, 8);
            else
                return "";
        }

        cc.set(ChineseCalendar.EXTENDED_YEAR, Integer.parseInt(date.substring(0, 4)) + 2637);
        cc.set(ChineseCalendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
        cc.set(ChineseCalendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6)));

        cal.setTimeInMillis(cc.getTimeInMillis());

        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DAY_OF_MONTH);

        StringBuffer ret = new StringBuffer();
        ret.append(String.format("%04d", y));
        ret.append(String.format("%02d", m));
        ret.append(String.format("%02d", d));

        return ret.toString();
    }

    /**
     * 양력날짜를 음력날짜로 변환
     *
     * @param 양력날짜 (yyyyMMdd)
     * @return 음력날짜 (yyyyMMdd)
     */
    public static String Solar2Lunar(String yyyymmdd) {
        ChineseCalendar cc = new ChineseCalendar();
        Calendar cal = Calendar.getInstance();

        if (yyyymmdd == null)
            return "";

        String date = yyyymmdd.trim();
        if (date.length() != 8) {
            if (date.length() == 4)
                date = date + "0101";
            else if (date.length() == 6)
                date = date + "01";
            else if (date.length() > 8)
                date = date.substring(0, 8);
            else
                return "";
        }

        cal.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
        cal.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6)));

        cc.setTimeInMillis(cal.getTimeInMillis());

        // ChinessCalendar.YEAR 는 1~60 까지의 값만 가지고 ,
        // ChinessCalendar.EXTENDED_YEAR 는 Calendar.YEAR 값과 2637 만큼의 차이를 가진다.
        int y = cc.get(ChineseCalendar.EXTENDED_YEAR) - 2637;
        int m = cc.get(ChineseCalendar.MONTH) + 1;
        int d = cc.get(ChineseCalendar.DAY_OF_MONTH);

        StringBuffer ret = new StringBuffer();
        if (y < 1000) ret.append("0");
        else if (y < 100) ret.append("00");
        else if (y < 10) ret.append("000");
        ret.append(y);

        if (m < 10) ret.append("0");
        ret.append(m);

        if (d < 10) ret.append("0");
        ret.append(d);

        return ret.toString();
    }


    public static ArrayList<Holidays> holidayArray(String yyyy) {
        holidaysArrayList.clear(); // 데이터 초기화
        // 양력 휴일
        addHolidaysItem(yyyy, "0101", "신정");
        addHolidaysItem(yyyy, "0301", "삼일절");
        addHolidaysItem(yyyy, "0505", "어린이날");
        addHolidaysItem(yyyy, "0606", "현충일");
        addHolidaysItem(yyyy, "0815", "광복절");
        addHolidaysItem(yyyy, "1003", "개천절");
        addHolidaysItem(yyyy, "1009", "한글날");
        addHolidaysItem(yyyy, "1225", "성탄절");

        // 음력 휴일
        String prev_seol = String.valueOf(Integer.parseInt(Lunar2Solar(yyyy + "0101")) - 1);
        addHolidaysItem(yyyy, prev_seol.substring(4), "");
        addHolidaysItem(yyyy, SolarDays(yyyy, "0101"), "설날");
        addHolidaysItem(yyyy, SolarDays(yyyy, "0102"), "");
        addHolidaysItem(yyyy, SolarDays(yyyy, "0408"), "석탄일");
        addHolidaysItem(yyyy, SolarDays(yyyy, "0814"), "");
        addHolidaysItem(yyyy, SolarDays(yyyy, "0815"), "추석");
        addHolidaysItem(yyyy, SolarDays(yyyy, "0816"), "");

        try {
            // 어린이날 대체공휴일 검사 : 어린이날은 토요일, 일요일인 경우 그 다음 평일을 대체공유일로 지정
            int childDayChk = WeekendValue(yyyy + "0505");
            if (childDayChk == 1) addHolidaysItem(yyyy, "0506", "대체공휴일");
            if (childDayChk == 7) addHolidaysItem(yyyy, "0507", "대체공휴일");

            // 설날 대체공휴일 검사
            if (WeekendValue(Lunar2Solar(yyyy + "0101")) == 1) addHolidaysItem(yyyy, SolarDays(yyyy, "0103"), "대체공휴일");
            if (WeekendValue(Lunar2Solar(yyyy + "0101")) == 2) addHolidaysItem(yyyy, SolarDays(yyyy, "0103"), "대체공휴일");
            if (WeekendValue(Lunar2Solar(yyyy + "0102")) == 1) addHolidaysItem(yyyy, SolarDays(yyyy, "0103"), "대체공휴일");

            // 추석 대체공휴일 검사
            if (WeekendValue(Lunar2Solar(yyyy + "0814")) == 1) addHolidaysItem(yyyy, SolarDays(yyyy, "0817"), "대체공휴일");
            if (WeekendValue(Lunar2Solar(yyyy + "0815")) == 1) addHolidaysItem(yyyy, SolarDays(yyyy, "0817"), "대체공휴일");
            if (WeekendValue(Lunar2Solar(yyyy + "0816")) == 1) addHolidaysItem(yyyy, SolarDays(yyyy, "0817"), "대체공휴일");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Collections.sort(holidaysArrayList); // 오름차순 정렬

        return holidaysArrayList;
    }

    private static String SolarDays(String yyyy, String date) {
        return Lunar2Solar(yyyy + date).substring(4);
    }

    private static void addHolidaysItem(String year, String date, String name) {
        Holidays item = new Holidays();
        item.setYear(year);
        item.setDate(date);
        item.setName(name);
        holidaysArrayList.add(item);
    }

    private static int WeekendValue(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(date));
        return cal.get(Calendar.DAY_OF_WEEK);
        // Calendar.SUNDAY : 1
        // Calendar.SATURDAY : 7
    }

}