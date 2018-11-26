package lxpsee.top.hive.impl;

import lxpsee.top.domain.CallLog;
import lxpsee.top.hive.HiveCallLogService;
import org.junit.Test;

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
}