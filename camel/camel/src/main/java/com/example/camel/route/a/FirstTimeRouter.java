package com.example.camel.route.a;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//@Component
public class FirstTimeRouter extends RouteBuilder {
	
	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean;
	
	@Autowired
	private SimpleLoggingProcessingComponent loggingComponent;

	@Override
	public void configure() throws Exception {
		// timer 
		// transformation
		// log  
		// Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
		from("timer:first-timer") // null
		.log("${body}") //null
		.transform().constant("My Constant Message")
		.log("${body}") // My Constant Message
		
		//.transform().constant("Time now is" + LocalDateTime.now())
		//.bean("getCurrentTimeBean") //@Autowired 없을 때는 "" 안에 메소드 명 적어서 호출함 
		
		//Processing 
		//Transformation
		
		.bean(getCurrentTimeBean) // (getCurrentTimeBean,"getCurrentTime") 형식 가능, 메소드명을 정확하게 써주어야 함.
		.log("${body}") //Time now is2021-08-05T16:00:27.546
		.bean(loggingComponent)
		.log("${body}")
		.process(new SimpleLoggingProcessor())
		.to("log:first-timer");  // database
	}

} 

@Component
class GetCurrentTimeBean {
	public String getCurrentTime() {
		return "Time now is" + LocalDateTime.now();
	}
}

@Component
class SimpleLoggingProcessingComponent {
	
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);
	
	public void process(String message) { //return을 안하는 대신 메시지 내용을 안에 넣을 수 있음 
		
		logger.info("SimpleLoggingProcessingComponent {}", message);
	}
}

class SimpleLoggingProcessor implements Processor {
	
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

	@Override
	public void process(Exchange exchange) throws Exception {
	
		logger.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());
	}

}