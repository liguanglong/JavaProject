import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author LiGuanglong
 * @date 2018/5/2
 */
public class ProducerDemo extends Thread{
//    private  final KafkaProducer<Integer,String> producer;
//    private  final String topic;
//    private  final Properties props=new Properties();
//    public ProducerDemo(String topic){
//        props.put("bootstrap.servers", "47.98.183.125:9092");
//        props.put("acks","all");
//        props.put("retries",0);
//        props.put("batch.size",16384);
//        props.put("linger.ms",1);
//        props.put("buffer.memory",33554432);
//        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        producer=new KafkaProducer<Integer,String>(props);
//        this.topic=topic;
//    }
//    @Override
//    public void run() {
//        int messageNo=1;
//        while (messageNo<5){
//            String messageStr=new String("Message_"+messageNo);
//            System.out.println("Send:"+messageStr);
//            producer.send(new ProducerRecord<Integer, String>(topic, messageStr));
//            messageNo++;
//            try {
//                sleep(3000);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        }
//
//    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "47.98.183.125:9092");
        properties.put("zookeeper.connect", "47.98.183.125:2181");
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = null;
        String topic = "test";
        try {
            producer = new KafkaProducer<String, String>(properties);

            //列出topic的相关信息
            List<PartitionInfo> partitions = new ArrayList<PartitionInfo>() ;
            partitions = producer.partitionsFor(topic);
            for(PartitionInfo p:partitions)
            {
                System.out.println(p);
            }

            //生产消息
            for (int i = 0; i < 100; i++) {
                String msg = "Message " + i;
                producer.send(new ProducerRecord<String, String>("test", msg));
                System.out.println("Sent:" + msg);
            }
            System.out.println("send message over.");
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (producer != null) {
                producer.close();
            }
        }

    }

}
