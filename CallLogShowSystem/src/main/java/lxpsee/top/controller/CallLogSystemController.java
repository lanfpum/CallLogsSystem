package lxpsee.top.controller;

import com.alibaba.fastjson.JSON;
import lxpsee.top.domain.CallLog;
import lxpsee.top.domain.CallLogRange;
import lxpsee.top.domain.StatCallLog;
import lxpsee.top.hive.HiveCallLogService;
import lxpsee.top.service.CallLogService;
import lxpsee.top.utils.CallLogUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/21 23:12.
 */
@RequestMapping("/callLog")
@Controller
public class CallLogSystemController {

    @Resource(name = "callLogService")
    private CallLogService     callLogService;
    @Resource(name = "hiveCallLogService")
    private HiveCallLogService hiveCallLogService;

    /**
     * 跳转到统计指定人员，指定月份的通话次数页面
     */
    @RequestMapping("/toStatCallLog")
    public String totoStatCallLogPage() {
        return "callLog/statCallLog";
    }

    /**
     * 统计指定人员，指定月份的通话次数
     */
    @RequestMapping("/statCallLog")
    public String statCallLog(@RequestParam("caller") String caller, @RequestParam("year") String year, Model model) {
        List<StatCallLog> statCallLogs = hiveCallLogService.findStatCallLogsByPhoneAndYear(caller, year);
        model.addAttribute("title", caller + "在" + year + "年各月份通话次数统计");
        model.addAttribute("list", statCallLogs);
        return "callLog/statCallLog";
    }

    /**
     * ajax 获取hbase数据库中的数据，json返回
     */
    @RequestMapping("/json/findAll")
    public String findAllJson(HttpServletResponse response) {
        List<CallLog> callLogList = callLogService.findAll();
        String callLogStr = JSON.toJSONString(callLogList);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(callLogStr.getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 进入查询最近通话记录页面
     */
    @RequestMapping("/toFindLatestCallLogPage")
    public String toFindLatestCallLogPage() {
        return "callLog/findLatestCallLog";
    }

    /**
     * 查询用户最近的通话记录
     */
    @PostMapping("/findLatestCallLog")
    public String findLatestCallLog(@RequestParam("caller") String caller, @RequestParam("limit") int limit, Model model) {
        List<CallLog> latestCallLog = hiveCallLogService.findLatestCallLog(caller, limit);
        model.addAttribute("callLogs", latestCallLog);
        return "callLog/latestCallLog";
    }

    /**
     * 进入查询通话记录的页面,form
     */
    @RequestMapping("/toFindCallLogPage")
    public String toFindCallLogPage() {
        return "callLog/findCallLog";
    }

    /**
     * 查询用户通话记录
     */
    @PostMapping("/findCallLog")
    public String findCallLog(@RequestParam("caller") String caller, @RequestParam("startTime") String startTime,
                              @RequestParam("endTime") String endTime, Model model) {
        List<CallLogRange> callLogRangeList = CallLogUtil.getCallLogRangeList(startTime, endTime);
        List<CallLog> callLogList = callLogService.findCallLogByUserAmdTime(caller, callLogRangeList);
        model.addAttribute("callLogs", callLogList);
        return "/callLog/callLog";
    }

    /**
     * 查询所有通话记录
     */
    @RequestMapping("/findAll")
    public String findAll(Model model) {
        List<CallLog> callLogs = callLogService.findAll();
        model.addAttribute("callLogs", callLogs);
        return "/callLog/callLogList";
    }
}
