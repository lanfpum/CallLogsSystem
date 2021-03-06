package lxpsee.top.hive.impl;

import lxpsee.top.domain.CallLog;
import lxpsee.top.domain.StatCallLog;
import lxpsee.top.hive.HiveCallLogService;
import lxpsee.top.utils.CallLogUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/26 08:41.
 */
@Service("hiveCallLogService")
public class HiveCallLogServiceImpl implements HiveCallLogService {

    @Resource(name = "sparkSQLDataSource")
    private DataSource dataSource;

    public List<CallLog> findLatestCallLog(String selectPhoneNum, int selectNum) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            //select * from ext_calllogs_in_hbase where id like '%17731088562%' order by callTime desc limit 3;
            String sql = "select * from ext_calllogs_in_hbase where id like '%" + selectPhoneNum + "%' order by callTime desc limit " + selectNum;
            ResultSet resultSet = statement.executeQuery(sql);
            List<CallLog> logList = new ArrayList<CallLog>();

            while (resultSet.next()) {
                //  public CallLog(String caller, String callee, String callTime, String callDuration)
                //  id string, caller string,callTime string,callee string,callDuration string
                CallLog callLog = new CallLog(resultSet.getString("caller"), resultSet.getString("callee"),
                        CallLogUtil.formatDateString(resultSet.getString("callTime")), resultSet.getString("callDuration"));
                logList.add(callLog);
            }

            resultSet.close();
            statement.close();
            connection.close();
            return logList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 查询指定人员指定年份中各个月份的通话次数
     */
    public List<StatCallLog> findStatCallLogsByPhoneAndYear(String caller, String year) {
        List<StatCallLog> statCallLogs = new ArrayList<StatCallLog>();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select count(*),substr(callTime,1,6) from lpdb.ext_calllogs_in_hbase where caller = '"
                    + caller + "' and substr(callTime,1,4) = '" + year + "' group by substr(callTime,1,6)";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                StatCallLog statCallLog = new StatCallLog(resultSet.getString(2), resultSet.getInt(1));
                statCallLogs.add(statCallLog);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statCallLogs;
    }

  /*  public List<StatCallLog> findStatCallLogsByPhoneAndYear3(String caller, String year) {
        SparkSession sparkSession = SparkSession.builder().appName("spark0007")
                .master("local[4]").enableHiveSupport().getOrCreate();
        String sql = "select count(*),substr(callTime,1,6) from lpdb.ext_calllogs_in_hbase where caller = '"
                + caller + "' and substr(callTime,1,4) = '" + year + "' group by substr(callTime,1,6)";
        Dataset<Row> dataset = sparkSession.sql(sql);
        List<Row> rows = dataset.collectAsList();
        List<StatCallLog> statCallLogs = new ArrayList<StatCallLog>();

        for (Row row : rows) {
            statCallLogs.add(new StatCallLog(row.getString(1), (int) row.getLong(0)));
        }

        return statCallLogs;
    }*/

    public List<StatCallLog> findStatCallLogsByPhoneAndYear2(String caller, String year) {
        List<StatCallLog> statCallLogs = new ArrayList<StatCallLog>();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select count(*),substr(callTime,1,6) from ext_calllogs_in_hbase where caller = '"
                    + caller + "' and substr(callTime,1,4) = '" + year + "' group by substr(callTime,1,6)";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                StatCallLog statCallLog = new StatCallLog(resultSet.getString(2), resultSet.getInt(1));
                statCallLogs.add(statCallLog);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statCallLogs;
    }
}
