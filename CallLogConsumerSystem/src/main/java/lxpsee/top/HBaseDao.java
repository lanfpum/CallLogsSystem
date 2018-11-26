package lxpsee.top;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/21 14:42.
 */
public class HBaseDao {
    private DecimalFormat decimalFormat;
    private String        flag;
    private Table         table;

    private int partitions;

    public HBaseDao() {
        try {
            Configuration configuration = HBaseConfiguration.create();
            Connection connection = ConnectionFactory.createConnection(configuration);
            TableName tableName = TableName.valueOf(PropertiesUtil.getPro("table.name"));
            table = connection.getTable(tableName);
            decimalFormat = new DecimalFormat(PropertiesUtil.getPro("hashcode.pattern"));
            flag = PropertiesUtil.getPro("caller.flag");
            partitions = Integer.parseInt(PropertiesUtil.getPro("partition.number"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void put(String log) {

        if (log == null || log.equals("")) {
            return;
        }

        try {
            String[] arr = log.split(",");

            if (arr != null && arr.length == 4) {
                String caller = arr[0];
                String callee = arr[1];
                String callTime = arr[2];
                callTime = callTime.replace("/", "");
                callTime = callTime.replace(" ", "");
                callTime = callTime.replace(":", "");
                String callDuration = arr[3];

                String rowKey = genRowKey(getHashCode(caller, callTime), caller, callTime, flag, callee, callDuration);
                Put put = new Put(Bytes.toBytes(rowKey));
                put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("caller"), Bytes.toBytes(caller));
                put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("callee"), Bytes.toBytes(callee));
                put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("callTime"), Bytes.toBytes(callTime));
                put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("duration"), Bytes.toBytes(callDuration));
                put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("falg"), Bytes.toBytes(flag));

                table.put(put);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    /**
     * 获取roeKey的分区号，取主叫的后四位异或上呼叫日期（取到月份），两位格式化 00 ~ 99
     *
     * @param caller
     * @param callTime
     * @return
     */
    public String getHashCode(String caller, String callTime) {
        String last4Caller = caller.substring(caller.length() - 4);
        String callMon = callTime.substring(0, 6);
        int hashCode = (Integer.parseInt(last4Caller) ^ Integer.parseInt(callMon)) % partitions;
        return decimalFormat.format(hashCode);
    }


    /**
     * 生成roowKwy, 顺序为 分区，主叫，呼叫时间，标志位，对方号码，呼叫时长 ，生成的都是主叫，被叫由协助理器生成
     *
     * @param hash
     * @param caller
     * @param callTime
     * @param flag
     * @param callee
     * @param callDuration
     * @return
     */
    public String genRowKey(String hash, String caller, String callTime, String flag, String callee, String callDuration) {
        return hash + "," + caller + "," + callTime + "," + flag + "," + callee + "," + callDuration;
    }

}
