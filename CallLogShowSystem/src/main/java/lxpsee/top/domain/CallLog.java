package lxpsee.top.domain;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/21 18:37.
 */
public class CallLog {
    private String caller;
    private String callee;
    private String callTime;

    private String callDuration;
    private boolean flag;
    private String callerName;

    private String calleeName;

    public CallLog() {
    }

    public CallLog(String caller, String callee, String callTime, String callDuration) {
        this.caller = caller;
        this.callee = callee;
        this.callTime = callTime;
        this.callDuration = callDuration;
    }

    public CallLog(String caller, String callee, String callTime, String callDuration, String callerName, String calleeName) {
        this.caller = caller;
        this.callee = callee;
        this.callTime = callTime;
        this.callDuration = callDuration;
        this.callerName = callerName;
        this.calleeName = calleeName;
    }

    public String getCallerName() {
        return callerName;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    public String getCalleeName() {
        return calleeName;
    }

    public void setCalleeName(String calleeName) {
        this.calleeName = calleeName;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getCallee() {
        return callee;
    }

    public void setCallee(String callee) {
        this.callee = callee;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }
}
