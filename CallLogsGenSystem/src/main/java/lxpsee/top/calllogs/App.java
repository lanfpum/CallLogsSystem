package lxpsee.top.calllogs;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/19 19:52.
 */
public class App {
    private static Random              random       = new Random();
    private static List<String>        phoneNumbers = new ArrayList<String>();
    private static Map<String, String> callers      = new HashMap<String, String>();

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
        if (args.length == 0 || args == null) {
            System.out.println("no args");
            System.exit(-1);
        }
        genCallLogs(args[0]);
    }

    private static void genCallLogs(String logFile) throws Exception {
//        FileWriter fileWriter = new FileWriter("D:/workDir/otherFile/scala/callLog/callLog.log", true);
        FileWriter fileWriter = new FileWriter(logFile, true);

        while (true) {
            String caller = phoneNumbers.get(random.nextInt(callers.size()));
//        String callerName = callers.get(caller);
            String callee;

            while (true) {
                callee = phoneNumbers.get(random.nextInt(callers.size()));

                if (!callee.equals(caller)) {
                    break;
                }
            }

//        String calleeName = callers.get(callee);
            // 通话时长，不超过十分钟,设置三位数格式
            DecimalFormat decimalFormat = new DecimalFormat("000");
            String duration = decimalFormat.format(random.nextInt(60 * 10) + 1);

            // 通话时间设置,转换成日期格式
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2018);
            calendar.set(Calendar.MONTH, random.nextInt(12));
            calendar.set(Calendar.DAY_OF_MONTH, random.nextInt(29) + 1);
            calendar.set(Calendar.HOUR_OF_DAY, random.nextInt(24));
            calendar.set(Calendar.MINUTE, random.nextInt(60));
            calendar.set(Calendar.SECOND, random.nextInt(60));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date callTime = calendar.getTime();
            Date now = new Date();

            if (callTime.compareTo(now) > 0) {
                continue;
            }

            String callDate = simpleDateFormat.format(callTime);

//        String callLog = caller + "," + callerName + "," + callee + "," + calleeName + "," + callDate + "," + duration;
            String callLog = caller + "," + callee + "," + callDate + "," + duration;
            System.out.println(callLog);
            fileWriter.write(callLog + "\r\n");
            fileWriter.flush();
            Thread.sleep(200);
        }
    }


}
