package lxpsee.top.hive.impl;

import lxpsee.top.domain.CallLog;
import lxpsee.top.hive.HiveCallLogService;
import lxpsee.top.utils.CallLogUtil;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/26 08:41.
 */
@Service("hiveCallLogService")
public class HiveCallLogServiceImpl implements HiveCallLogService {

    // 设置hiveserver2连接串,驱动类,并注册驱动,没有到数据库会找不到表
    private static String url         = "jdbc:hive2://ip201:10000/lpdb";
    private static String driverClass = "org.apache.hive.jdbc.HiveDriver";

    static {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public List<CallLog> findLatestCallLog(String selectPhoneNum, int selectNum) {
        try {
            Connection connection = DriverManager.getConnection(url);
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
}
