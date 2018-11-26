package lxpsee.top.utils;

import lxpsee.top.domain.CallLogRange;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/23 09:13.
 * <p>
 * 呼叫日志工作类
 */
public class CallLogUtil {
    private static DecimalFormat decimalFormat00 = new DecimalFormat("00");

    /**
     * 根据起始时间和结束时间，取得查询时间范围
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<CallLogRange> getCallLogRangeList(String startTime, String endTime) {
        try {
            // 分别对起始时间和结束时间进行格式化，取出起始时间和结束时间的年月值，再对结束时间加1天，包含到设置的结束时间
            SimpleDateFormat dateFormatYMD = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dateFormatYM = new SimpleDateFormat("yyyyMM");

            List<CallLogRange> callLogRangeList = new ArrayList<CallLogRange>();

            String startPrefix = startTime.substring(0, 6);
            String endPrefix = endTime.substring(0, 6);
            int endDay = Integer.parseInt(endTime.substring(6, 8));
            String endPoint = endPrefix + decimalFormat00.format(endDay + 1);

            if (startPrefix.equals(endPrefix)) {
                // 同年月情况：直接设置即可
                CallLogRange callLogRange = new CallLogRange(startTime, endPoint);
                callLogRangeList.add(callLogRange);
            } else {
                // 非同年月：对月份加1 设置，存入list
                CallLogRange callLogRange = new CallLogRange();
                callLogRange.setStartPoint(startTime);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateFormatYMD.parse(startTime));
                calendar.add(Calendar.MONTH, 1);
                callLogRange.setEndPoint(dateFormatYM.format(calendar.getTime()));
                callLogRangeList.add(callLogRange);

                while (true) {

                    if (endTime.startsWith(dateFormatYM.format(calendar.getTime()))) {
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
            }

            return callLogRangeList;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据用户和呼叫时间，分区数取得哈希编码
     *
     * @param caller
     * @param callTime
     * @param partitions
     * @return
     */
    public static String getHashCode(String caller, String callTime, int partitions) {
        String last4Caller = caller.substring(caller.length() - 4);
        String callMon = callTime.substring(0, 6);
        int hashCode = (Integer.parseInt(last4Caller) ^ Integer.parseInt(callMon)) % partitions;
        return decimalFormat00.format(hashCode);
    }

    /**
     * 取得scan对象的startRowKey
     *
     * @param caller
     * @param startTime
     * @param partitions
     * @return
     */
    public static String getStartRowKey(String caller, String startTime, int partitions) {
        return getHashCode(caller, startTime, partitions) + "," + caller + "," + startTime;
    }

    /**
     * 取得scan的stopruwKey
     *
     * @param caller
     * @param startTime
     * @param partitions
     * @param endTime
     * @return
     */
    public static String getStopRowKey(String caller, String startTime, int partitions, String endTime) {
        return getHashCode(caller, startTime, partitions) + "," + caller + "," + endTime;
    }

    /**
     * 对时间进行格式化，友好输出
     *
     * @param dateStr
     * @return
     */
    public static String formatDateString(String dateStr) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat friendDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return friendDateFormat.format(simpleDateFormat.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
