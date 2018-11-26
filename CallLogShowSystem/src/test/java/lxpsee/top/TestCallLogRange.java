package lxpsee.top;

import lxpsee.top.domain.CallLogRange;
import org.junit.Test;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/22 09:33.
 */
public class TestCallLogRange {
    @Test
    public void testRun() throws ParseException {
        // 分别对起始时间和结束时间进行格式化，取出起始时间和结束时间的年月值，再对结束时间加1天，包含到设置的结束时间
        SimpleDateFormat dateFormatYMD = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dateFormatYM = new SimpleDateFormat("yyyyMM");
        DecimalFormat decimalFormat00 = new DecimalFormat("00");

        List<CallLogRange> callLogRangeList = new ArrayList<CallLogRange>();

        String startStr = "20160506";
        String endStr = "20180722";
        String startPrefix = startStr.substring(0, 6);
        String endPrefix = endStr.substring(0, 6);
        int endDay = Integer.parseInt(endStr.substring(6, 8));
        String endPoint = endPrefix + decimalFormat00.format(endDay + 1);

        if (startPrefix.equals(endPrefix)) {
            // 同年月情况：直接设置即可
            CallLogRange callLogRange = new CallLogRange(startStr, endPoint);
            callLogRangeList.add(callLogRange);
        } else {
            // 非同年月：对月份加1 设置，存入list
            CallLogRange callLogRange = new CallLogRange();
            callLogRange.setStartPoint(startStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormatYMD.parse(startStr));
            calendar.add(Calendar.MONTH, 1);
            callLogRange.setEndPoint(dateFormatYM.format(calendar.getTime()));
            callLogRangeList.add(callLogRange);

            while (true) {

                if (endStr.startsWith(dateFormatYM.format(calendar.getTime()))) {
                    // 最后一个月，即结束月份
                    callLogRange = new CallLogRange(dateFormatYM.format(calendar.getTime()), endPoint);
                    callLogRangeList.add(callLogRange);
                    break;
                } else {
                    callLogRange = new CallLogRange();
                    callLogRange.setStartPoint(dateFormatYM.format(calendar.getTime()));
                    calendar.add(Calendar.MONTH, 1);
                    callLogRange.setEndPoint(dateFormatYM.format(calendar.getTime()));
                    callLogRangeList.add(callLogRange);
                }

            }

            System.out.println("");
        }
    }

}
