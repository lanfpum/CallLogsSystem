package lxpsee.top.domain;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/22 09:31.
 * <p>
 * 呼叫时间范围类
 */
public class CallLogRange {
    private String startPoint;
    private String endPoint;

    public CallLogRange() {
    }

    public CallLogRange(String startPoint, String endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public String toString() {
        return "CallLogRange:" + startPoint + " - '" + endPoint;
    }
}
