package lxpsee.top.monitor;


import org.junit.Test;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/3 10:13.
 */
public class ReceiveThreadTest {

    @Test
    public void test() {
        new ReceiveThread().start();
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}