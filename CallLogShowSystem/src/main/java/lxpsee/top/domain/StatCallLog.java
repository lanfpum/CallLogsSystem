package lxpsee.top.domain;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/28 12:03.
 * <p>
 * 指定年月查询的通话次数
 */
public class StatCallLog {
    private String yearMonth;
    private int    count;

    public StatCallLog() {
    }

    public StatCallLog(String yearMonth, int count) {
        this.yearMonth = yearMonth;
        this.count = count;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
