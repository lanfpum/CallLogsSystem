package lxpsee.top.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/23 09:13.
 * <p>
 * 呼叫日志工作类
 */
public class CallLogUtil {
    private static DecimalFormat decimalFormat00 = new DecimalFormat("00");

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
