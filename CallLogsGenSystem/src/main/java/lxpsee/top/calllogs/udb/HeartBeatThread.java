package lxpsee.top.calllogs.udb;

import lxpsee.top.calllogs.utils.PropertiesUtil;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/30 22:16.
 * <p>
 * 心跳工具类
 */
public class HeartBeatThread extends Thread {
    private static final String HEARTBEAT_UDP_SEND_PORT     = "heartbeat.udp.send.port";
    private static final String HEARTBEAT_UDP_SEND_FLAG     = "heartbeat.udp.send.flag";
    private static final String HEARTBEAT_UDP_SEND_BCADDR   = "heartbeat.udp.send.bcaddr";
    private static final String HEARTBEAT_UDP_SEND_BCPORT   = "heartbeat.udp.send.bcport";
    private static final String HEARTBEAT_UDP_SEND_SLEEP_MS = "heartbeat.udp.send.sleep.ms";

    private DatagramSocket socket;

    public HeartBeatThread() {
        try {
            socket = new DatagramSocket(PropertiesUtil.getIntPro(HEARTBEAT_UDP_SEND_PORT));
            this.setDaemon(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        byte[] bytes = new byte[1];
        bytes[0] = (byte) PropertiesUtil.getIntPro(HEARTBEAT_UDP_SEND_FLAG);
        DatagramPacket packet = new DatagramPacket(bytes, 1);
        String bcAddr = PropertiesUtil.getStrPro(HEARTBEAT_UDP_SEND_BCADDR);
        int bcPort = PropertiesUtil.getIntPro(HEARTBEAT_UDP_SEND_BCPORT);
        int sleepTime = PropertiesUtil.getIntPro(HEARTBEAT_UDP_SEND_SLEEP_MS);
        packet.setSocketAddress(new InetSocketAddress(bcAddr, bcPort));

        while (true) {
            try {
                socket.send(packet);
                System.out.println("发送了一次心跳");
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
