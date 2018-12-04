package lxpsee.top.monitor;

import lxpsee.top.domain.HeartBeat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/3 09:21.
 */
public class ReceiveThread extends Thread {
    private static int HEARTBEAT_UDP_RECEIVE_PORT = 9999;

    //IP地址和最后一次收到心跳时间
    public Map<String, HeartBeat> map = new HashMap<String, HeartBeat>();

    DatagramSocket socket;

    public ReceiveThread() {
        try {
            socket = new DatagramSocket(HEARTBEAT_UDP_RECEIVE_PORT);
            this.setDaemon(true);
            System.out.println("开始接受心跳！");
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        byte[] buf = new byte[1];
        DatagramPacket packet = new DatagramPacket(buf, 1);

        while (true) {
            try {
                socket.receive(packet);
                int flag = buf[0];
                String ip = ((InetSocketAddress) packet.getSocketAddress()).getAddress().getHostAddress();
                map.put(ip, new HeartBeat(ip, flag, System.currentTimeMillis()));
                System.out.println("收到心跳 : " + ip + "," + flag + "," + System.currentTimeMillis());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
