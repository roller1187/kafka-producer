package com.redhat.kafkaproducer;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaDemoApplication implements CommandLineRunner {
	
	public static Logger logger = LoggerFactory.getLogger(KafkaDemoApplication.class);

	public static void main(String[] args) throws GeneralSecurityException, IOException {
		Properties props = new Properties();
		props.load(KafkaDemoApplication.class.getClassLoader().getResourceAsStream("application.properties"));
		
		TrustStore.createFromCrtFile(props.getProperty("kafka.cert.path"),
				props.getProperty("spring.kafka.properties.ssl.truststore.location"),
				props.getProperty("spring.kafka.properties.ssl.truststore.password").toCharArray());
		SpringApplication.run(KafkaDemoApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {}

}
