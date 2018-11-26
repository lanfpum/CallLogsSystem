package lxpsee.top.service;

import lxpsee.top.domain.CallLog;
import lxpsee.top.domain.CallLogRange;

import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/21 18:41.
 */
public interface CallLogService {
    public List<CallLog> findAll();

    List<CallLog> findCallLogByUserAmdTime(String selectPhone, List<CallLogRange> callLogRangeList);
}
