package lxpsee.top.hive.impl;

import lxpsee.top.domain.CallLog;
import lxpsee.top.hive.HiveCallLogService;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/26 08:59.
 */
public class HiveCallLogServiceImplTest {

//    @Resource(name = "hiveCallLogService")
//    private HiveCallLogService hiveCallLogService;

    @Test
    public void findLatestCallLog() {
        HiveCallLogService hiveCallLogService = new HiveCallLogServiceImpl();
        List<CallLog> latestCallLog = hiveCallLogService.findLatestCallLog("17731088562", 3);

        for (CallLog callLog : latestCallLog) {
            System.out.println(callLog.getCaller());
        }

    }

    @Test
    public void testSpark() {
 /*       String year = "2018";
        String caller = "15810092493";
        SparkSession sparkSession = SparkSession.builder().appName("spark0007")
                .master("local[2]").enableHiveSupport().getOrCreate();
        String sql = "select count(*),substr(calltime,1,6) from lpdb.ext_calllogs_in_hbase where caller = '"
                + caller + "' and substr(calltime,1,4) = '" + year + "' group by substr(calltime,1,6)";
//        String sql = "select * from lpdb.ext_calllogs_in_hbase";
        Dataset<Row> dataset = sparkSession.sql(sql);
        List<Row> rows = dataset.collectAsList();
        List<StatCallLog> statCallLogs = new ArrayList<StatCallLog>();

        for (Row row : rows) {
            statCallLogs.add(new StatCallLog(row.getString(1), (int) row.getLong(0)));
        }

        System.out.println(statCallLogs.size());*/
    }

    @Test
    public void test1() {
        try {
            String year = "2018";
            String caller = "15810092493";
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            Connection connection = DriverManager.getConnection("jdbc:hive2://ip201:10000/lpdb");
            Statement statement = connection.createStatement();
            String sql = "select count(*),substr(callTime,1,6) from ext_calllogs_in_hbase where caller = '"
                    + caller + "' and substr(callTime,1,4) = '" + year + "' group by substr(callTime,1,6)";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println(resultSet.getString(2) + ": " + resultSet.getInt(1));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}