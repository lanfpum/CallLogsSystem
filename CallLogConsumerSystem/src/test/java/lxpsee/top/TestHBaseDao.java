package lxpsee.top;

import org.junit.Test;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/21 15:25.
 */
public class TestHBaseDao {
    @Test
    public void testPUt() {
        HBaseDao hBaseDao = new HBaseDao();
        hBaseDao.put("13520404983,15733218050,2018/08/04 02:39:07,095");
    }
}
