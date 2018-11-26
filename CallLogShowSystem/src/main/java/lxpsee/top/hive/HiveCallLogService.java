package lxpsee.top.hive;

import lxpsee.top.domain.CallLog;

import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/26 08:48.
 */
public interface HiveCallLogService {
    public List<CallLog> findLatestCallLog(String selectPhoneNum, int selectNum);
}
