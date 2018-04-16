/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActiveMQ;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author TGMaster
 */
public class Producer {
    // Ở đây tôi sử dụng TCP Protocol để gửi message đi,
    // ActiveMQ có hỗ trợ nhiều Protocol khác các bạn có thể tham khảo thêm ở đây: http://activemq.apache.org/protocols.html
    // 61616 là cổng mặc định của ActiveMQ
    private static String url = "tcp://18.233.148.58:61616";
 
    private static String subject = "TESTQUEUE";
 
    public static void main(String[] args) throws JMSException {
        // Lấy JMS connection từ server
        ActiveMQConnectionFactory jmsConnectionFactory = new ActiveMQConnectionFactory(url);
        // Tên đăng nhập và mật khẩu truy cập mặc định vào ActiveMQ-Broker bạn có thay đổi ở đây conf/users.properties
        jmsConnectionFactory.setUserName("admin");
        jmsConnectionFactory.setPassword("admin");
        // Khởi tạo connection.
        Connection connection = jmsConnectionFactory.createConnection();
        connection.start();
 
        // JMS messages sẽ sử dụng Session để gửi và nhận.
        // Ở đây chúng ta sẽ tạo một đối tượng non-transactional session
        // Nếu muốn sử dụng đối tượng transactions thì ta sẽ gán true cho tham số đầu tiên.
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
 
        // Tạo một đích đến là queue ‘TESTQUEUE’ trên JMS Server (ở đấy là ActiveMQ-Broker)
        // Nếu queue chưa có trên Broker thì queue sẽ được tự động tạo.
        Destination destination = session.createQueue(subject);
 
        // MessageProducer dùng để gửi message đến Queue
        MessageProducer producer = session.createProducer(destination);
 
        // Message gửi đi, ở đây là một xml text có thông điệp gửi đi là Hello World
        String msg = "Hello World!";
        TextMessage message = session.createTextMessage(msg);
        producer.send(message);
 
        System.out.println("DONE");
 
        // Don't forget to close your resources <img draggable="false" class="emoji" alt="😀" src="https://s0.wp.com/wp-content/mu-plugins/wpcom-smileys/twemoji/2/svg/1f600.svg">
        producer.close();
        System.exit(0);
    }
}