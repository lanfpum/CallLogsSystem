package lxpsee.top.coprossor;

import lxpsee.top.utils.CallLogUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.InternalScanner;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/23 14:58.
 * <p>
 * 协助理器
 */
public class CallLogRegionObserver extends BaseRegionObserver {

    private static final String REF_ROW_KEY         = "refrowkey";
    private static final String CALL_LOG_TABLE_NAME = "lpns:calllogs";
    private static final int    partitions          = 100;

    private static final String CALLEE_FAMILY = "f2";

    /**
     * 在插入数据之后生成被叫数据
     * 1.获取要操作的表名、当前的tablename对象名称，进行判断是否为指定的表
     * 2.获取rowkey，进行判断是否为被叫的rowkey
     * 3.组装成新的rowkey,存入hbase，列族为f2，值为主叫rowkey
     *
     * @param e
     * @param put
     * @param edit
     * @param durability
     * @throws IOException
     */
    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        super.postPut(e, put, edit, durability);

        String callLogsTableName = TableName.valueOf(CALL_LOG_TABLE_NAME).getNameAsString();
        String eTableName = e.getEnvironment().getRegion().getRegionInfo().getTable().getNameAsString();

        if (!callLogsTableName.equals(eTableName)) {
            return;
        }

        String rowKey = Bytes.toString(put.getRow());
        String[] arr = rowKey.split(",");

        if (arr[3].equals("1")) {
            return;
        }

        //hashcode,caller,time,flag,callee,duration
        String caller = arr[1];
        String callTime = arr[2];
        String callee = arr[4];
        String callDuration = arr[5];

        String hashCode = CallLogUtil.getHashCode(callee, callTime, partitions);
        String calleeRowKey = hashCode + "," + callee + "," + callTime + ",1," + caller + "," + callDuration;

        Put calleePut = new Put(Bytes.toBytes(calleeRowKey));
        calleePut.addColumn(Bytes.toBytes(CALLEE_FAMILY), Bytes.toBytes(REF_ROW_KEY), Bytes.toBytes(rowKey));
        TableName tableName = TableName.valueOf(CALL_LOG_TABLE_NAME);
        Table table = e.getEnvironment().getTable(tableName);
        table.put(calleePut);
    }

    private void printInfo(String str) {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            Socket socket = new Socket("192.168.217.205", 8888);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write((ip + ":" + str + "\r\n").getBytes());
            outputStream.flush();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 扫描结果：完成被叫查询，返回主叫结果。
     * 1.判断是否为指定的扫描表
     * 2。获取rowkey，根据flag判断，主叫则直接加入新的集合中
     * 3.对被叫进行查询，获取到被叫数据的result，存入集合中
     * 4.对集合列表进行清空，重新存入集合数据
     *
     * @param e
     * @param s
     * @param results
     * @param limit
     * @param hasMore
     * @return
     * @throws IOException
     */
    @Override
    public boolean postScannerNext(ObserverContext<RegionCoprocessorEnvironment> e, InternalScanner s, List<Result> results, int limit, boolean hasMore) throws IOException {
        boolean b = super.postScannerNext(e, s, results, limit, hasMore);

        List<Result> newList = new ArrayList<Result>();
        String eTableName = e.getEnvironment().getRegion().getRegionInfo().getTable().getNameAsString();

        if (eTableName.equals(CALL_LOG_TABLE_NAME)) {
            Table eTable = e.getEnvironment().getTable(TableName.valueOf(CALL_LOG_TABLE_NAME));

            for (Result result : results) {
                String rowKey = Bytes.toString(result.getRow());
                String[] arr = rowKey.split(",");

                if ("0".equals(arr[3])) {
                    newList.add(result);
                } else {
                    byte[] refRowKey = result.getValue(Bytes.toBytes(CALLEE_FAMILY), Bytes.toBytes(REF_ROW_KEY));
                    Result calleeResult = eTable.get(new Get(refRowKey));
                    newList.add(calleeResult);
                }
            }

            results.clear();
            results.addAll(newList);
        }

        return b;
    }
}
