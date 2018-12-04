package lxpsee.top.calllogs.gendata;

import lxpsee.top.calllogs.udb.HeartBeatThread;
import lxpsee.top.calllogs.utils.PropertiesUtil;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/19 19:52.
 */
public class GenCallLogData {
    // 定义属性名常量
    private static final String GEN_DATA_INTERVAL_MS = "gen.data.interval.ms";
    private static final String CALL_DURATION_FORMAT = "call.duration.format";
    private static final String CALL_DURATION_MAX    = "call.duration.max";
    private static final String CALL_TIME_FORMAT     = "call.time.format";
    private static final String LOG_FILE_PATH        = "log.file";
    private static final String CALL_YEAR            = "call.year";

    private static Map<String, String> callers      = new HashMap<String, String>();
    private static List<String>        phoneNumbers = new ArrayList<String>();
    private static Random              random       = new Random();

    static {
        callers.put("15810092493", "史玉龙");
        callers.put("18000696806", "赵贺彪");
        callers.put("15151889601", "张倩");
        callers.put("13269361119", "王世昌");
        callers.put("15032293356", "张涛");
        callers.put("17731088562", "张阳");
        callers.put("15338595369", "李进全");
        callers.put("15733218050", "杜泽文");
        callers.put("15614201525", "任宗阳");
        callers.put("15778423030", "梁鹏");
        callers.put("18641241020", "郭美彤");
        callers.put("15732648446", "刘飞飞");
        callers.put("13341109505", "段光星");
        callers.put("13560190665", "唐会华");
        callers.put("18301589432", "杨力谋");
        callers.put("13520404983", "温海英");
        callers.put("18332562075", "朱尚宽");
        callers.put("18620192711", "刘能宗");
        phoneNumbers.addAll(callers.keySet());
    }

    public static void main(String[] args) throws Exception {
        new HeartBeatThread().start();
        genCallLogs();
    }

    private static void genCallLogs() throws Exception {
        FileWriter fileWriter = new FileWriter(PropertiesUtil.getStrPro(LOG_FILE_PATH), true);
        DecimalFormat decimalFormat = new DecimalFormat(PropertiesUtil.getStrPro(CALL_DURATION_FORMAT));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PropertiesUtil.getStrPro(CALL_TIME_FORMAT));

        while (true) {
            String caller = phoneNumbers.get(random.nextInt(callers.size()));
            String callee;

            while (true) {
                callee = phoneNumbers.get(random.nextInt(callers.size()));

                if (!callee.equals(caller)) {
                    break;
                }
            }

            // 通话时长，不超过十分钟,设置三位数格式
            String duration = decimalFormat.format(random.nextInt(PropertiesUtil.getIntPro(CALL_DURATION_MAX)) + 1);

            // 通话时间设置,转换成日期格式
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, PropertiesUtil.getIntPro(CALL_YEAR));
            calendar.set(Calendar.MONTH, random.nextInt(12));
            calendar.set(Calendar.DAY_OF_MONTH, random.nextInt(29) + 1);
            calendar.set(Calendar.HOUR_OF_DAY, random.nextInt(24));
            calendar.set(Calendar.MINUTE, random.nextInt(60));
            calendar.set(Calendar.SECOND, random.nextInt(60));
            Date callTime = calendar.getTime();
            Date now = new Date();

            if (callTime.compareTo(now) > 0) {
                continue;
            }

            String callDate = simpleDateFormat.format(callTime);
            String callLog = caller + "," + callee + "," + callDate + "," + duration;

            System.out.println(callLog);
            fileWriter.write(callLog + "\r\n");
            fileWriter.flush();

            Thread.sleep(PropertiesUtil.getIntPro(GEN_DATA_INTERVAL_MS));
        }
    }


}
