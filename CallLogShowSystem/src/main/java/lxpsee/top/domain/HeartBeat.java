package lxpsee.top.domain;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/3 09:22.
 */
public class HeartBeat {
    private String ip;
    private int    flag;
    private long   timestamp;

    public HeartBeat() {
    }

    public HeartBeat(String ip, int flag, long timestamp) {
        this.ip = ip;
        this.flag = flag;
        this.timestamp = timestamp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
