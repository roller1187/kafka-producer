package com.redhat.kafkaproducer;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KafkaDemoController {

    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    private KafkaTemplate<String, String> messageTemplate;

    @GetMapping("/")
    @ResponseBody
    public void rootPath() {
        KafkaDemoApplication.logger.info("Accessed root context path" );
    }
    
    @GetMapping("/hello")
    @ResponseBody
    public Message sayHello() {
        KafkaDemoApplication.logger.info("Sent: helloworld" );
    	this.messageTemplate.send(System.getenv("KAFKA_BACKEND_TOPIC"), "helloworld");
        return new Message(counter.incrementAndGet(), "Hello World!");
    }
    
    @GetMapping("/msg/{value}/{topic}")
    @ResponseBody
    public Message sendMessage(@PathVariable("value") String value, @PathVariable("topic") String topic) {
    	String messageBody;
    	if (!topic.isEmpty()) {
    		KafkaDemoApplication.logger.info("Sent: \"" + value + "\" to topic: \"" + topic + "\"");
        	this.messageTemplate.send(topic, value);
        	messageBody = "Sent: " + value;
    	} else {
    		messageBody = "Error, invalid message: \"" + value + " and topic: \"" + topic + "\"";
    	}
    	return new Message(counter.incrementAndGet(), messageBody);
    	
    }
    
}
