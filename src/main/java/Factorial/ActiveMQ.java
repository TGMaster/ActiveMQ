/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factorial;

import java.util.*;
import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author TGMaster
 */
public class ActiveMQ {

    // Ở đây tôi sử dụng TCP Protocol để gửi message đi,
    // ActiveMQ có hỗ trợ nhiều Protocol khác các bạn có thể tham khảo thêm ở đây: http://activemq.apache.org/protocols.html
    // 61616 là cổng mặc định của ActiveMQ
    private static final String url = "tcp://18.233.148.58:61616";

    // Lấy JMS connection từ server
    ActiveMQConnectionFactory jmsConnectionFactory = new ActiveMQConnectionFactory(url);

    public void Login() {
        // Tên đăng nhập và mật khẩu truy cập mặc định vào ActiveMQ-Broker bạn có thay đổi ở đây conf/users.properties
        jmsConnectionFactory.setUserName("admin");
        jmsConnectionFactory.setPassword("admin");
    }

    public void Producer(List<String> msg, String subject) throws JMSException {
        Login();

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
        for (String string : msg) {
            TextMessage message = session.createTextMessage(string);
            //System.out.println("Send message: " + message.getText() + "from " + Thread.currentThread().getName());
            producer.send(message);
        }

        System.out.println("*** Sent messages successfully!!! ***");

        // Don't forget to close your resources <img draggable="false" class="emoji" alt="😀" src="https://s0.wp.com/wp-content/mu-plugins/wpcom-smileys/twemoji/2/svg/1f600.svg">
        producer.close();
        session.close();
        connection.close();
    }

    public List Consumer(boolean wait, String subject) throws JMSException {
        Login();

        ConnectionFactory connectionFactory = jmsConnectionFactory;
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Creating session for seding messages
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Getting the queue 'TESTQUEUE'
        Destination destination = session.createQueue(subject);

        // Tạo một MessageConsumer để lắng nghe từ Queue trên
        MessageConsumer consumer = session.createConsumer(destination);
        
        // Create a list
        List<String> listOfString = new ArrayList<>();

        // If waiting for response
        if (wait) {
            // Listen if message comes
            MyListener listener = new MyListener();
            consumer.setMessageListener(listener);
            
        // If not
        } else {

            while (true) {
                // Wait for a message
                Message message = consumer.receive(1000);
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    listOfString.add(textMessage.getText());
                } else {
                    System.out.println("Queue empty! Calculating...");
                    connection.stop();
                    break;
                }
            }
            consumer.close();
            session.close();
            connection.close();
        }
        return listOfString;
    }

    public class MyListener implements MessageListener {

        public void onMessage(Message message) {
            // Read and handle message here.
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                Functions f = new Functions();
                try {
                    Number number = f.Receive(textMessage.getText());
                    System.out.println("Result: " + number.getResult());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
