package lxpsee.top;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/21 14:41.
 * <p>
 * Hbase的消费者
 */
public class HBaseConsumer {
    public static void main(String[] args) {
        ConsumerConfig consumerConfig = new ConsumerConfig(PropertiesUtil.properties);
        String topic = PropertiesUtil.getPro("topic");
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put(topic, new Integer(1));
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = Consumer.createJavaConsumerConnector(consumerConfig).createMessageStreams(map);
        List<KafkaStream<byte[], byte[]>> msgList = messageStreams.get(topic);
        HBaseDao hBaseDao = new HBaseDao();
        String msg;

        for (KafkaStream<byte[], byte[]> stream : msgList) {
            ConsumerIterator<byte[], byte[]> iterator = stream.iterator();

            while (iterator.hasNext()) {
                byte[] message = iterator.next().message();
                msg = new String(message);
                hBaseDao.put(msg);
            }
        }
    }
}
