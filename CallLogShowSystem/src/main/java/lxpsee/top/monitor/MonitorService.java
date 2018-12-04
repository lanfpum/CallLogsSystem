package lxpsee.top.monitor;

import lxpsee.top.domain.HeartBeat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/3 09:39.
 */
@Service("monitorService")
public class MonitorService {
    private ReceiveThread receiveThread;

    public MonitorService() {
        receiveThread = new ReceiveThread();
        receiveThread.start();
    }

    public List<HeartBeat> getHeartBeat() {
        return new ArrayList<HeartBeat>(receiveThread.map.values());
    }

}
