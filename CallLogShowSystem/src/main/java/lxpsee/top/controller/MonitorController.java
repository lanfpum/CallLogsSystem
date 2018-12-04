package lxpsee.top.controller;

import com.alibaba.fastjson.JSON;
import lxpsee.top.domain.HeartBeat;
import lxpsee.top.monitor.MonitorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/3 09:50.
 */
@Controller
public class MonitorController {
    @Resource(name = "monitorService")
    private MonitorService monitorService;

    @RequestMapping("/monitor/monitorPage")
    public String toMonotorPage() {
        return "monitor/monitor";
    }

    @RequestMapping("/json/monitor/getMonotorInfo")
    public String getMonotorInfo(HttpServletResponse response) {
        List<HeartBeat> heartBeatList = monitorService.getHeartBeat();
        String heartBeatJson = JSON.toJSONString(heartBeatList);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(heartBeatJson.getBytes("Utf-8"));
            outputStream.flush();
            outputStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return null;
    }


}
