package lxpsee.top.service.impl;

import lxpsee.top.domain.CallLog;
import lxpsee.top.domain.CallLogRange;
import lxpsee.top.service.CallLogService;
import lxpsee.top.utils.CallLogUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/21 18:39.
 * <p>
 * 呼叫日志业务
 */
@Service("callLogService")
public class CallLogServiceImpl implements CallLogService {

    private Table table;

    public CallLogServiceImpl() {
        try {
            Configuration conf = HBaseConfiguration.create();
            Connection conn = ConnectionFactory.createConnection(conf);
            TableName name = TableName.valueOf("lpns:calllogs");
            table = conn.getTable(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有log
     */
    public List<CallLog> findAll() {
        List<CallLog> callLogList = new ArrayList<CallLog>();
        try {
            Scan scan = new Scan();
            ResultScanner resultScanner = table.getScanner(scan);
            byte[] f = Bytes.toBytes("f1");
            byte[] caller = Bytes.toBytes("caller");
            byte[] callee = Bytes.toBytes("callee");
            byte[] callTime = Bytes.toBytes("callTime");
            byte[] duration = Bytes.toBytes("duration");
            CallLog callLog;
            Iterator<Result> resultIterator = resultScanner.iterator();
            while (resultIterator.hasNext()) {
                Result result = resultIterator.next();
                String[] arr = Bytes.toString(result.getRow()).split(",");
                callLog = new CallLog(
                        Bytes.toString(result.getValue(f, caller)),
                        Bytes.toString(result.getValue(f, callee)),
                        CallLogUtil.formatDateString(Bytes.toString(result.getValue(f, callTime))),
                        Bytes.toString(result.getValue(f, duration)));
                callLog.setFlag(arr[3].equals("0") ? true : false);
                callLogList.add(callLog);
            }
            return callLogList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return callLogList;
    }

    /**
     * 根据范围查询通话记录
     *
     * @param selectPhone
     * @param callLogRangeList
     * @return
     */
    public List<CallLog> findCallLogByUserAmdTime(String selectPhone, List<CallLogRange> callLogRangeList) {
        List<CallLog> callLogList = new ArrayList<CallLog>();

        try {
            for (CallLogRange callLogRange : callLogRangeList) {

                Scan scan = new Scan();
                scan.setStartRow(Bytes.toBytes(CallLogUtil.getStartRowKey(selectPhone,
                        callLogRange.getStartPoint(), 100)));
                scan.setStopRow(Bytes.toBytes(CallLogUtil.getStopRowKey(selectPhone,
                        callLogRange.getStartPoint(), 100, callLogRange.getEndPoint())));
                ResultScanner resultScanner = table.getScanner(scan);
                byte[] f = Bytes.toBytes("f1");
                byte[] caller = Bytes.toBytes("caller");
                byte[] callee = Bytes.toBytes("callee");
                byte[] callTime = Bytes.toBytes("callTime");
                byte[] duration = Bytes.toBytes("duration");
                CallLog callLog;
                Iterator<Result> resultIterator = resultScanner.iterator();
                while (resultIterator.hasNext()) {
                    Result result = resultIterator.next();
                    String flag = Bytes.toString(result.getRow()).split(",")[3];
                    callLog = new CallLog(
                            Bytes.toString(result.getValue(f, caller)),
                            Bytes.toString(result.getValue(f, callee)),
                            CallLogUtil.formatDateString(Bytes.toString(result.getValue(f, callTime))),
                            Bytes.toString(result.getValue(f, duration)));
                    callLog.setFlag(flag.equals("0") ? true : false);
                    callLogList.add(callLog);
                }
            }

            return callLogList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
