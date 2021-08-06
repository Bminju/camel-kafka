package com.example.camel.route.b;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRouter extends RouteBuilder{

	@Override
	public void configure() throws Exception { // 코드 쓴 다음에 explorer refresh하면 자동으로 폴더 생김
		from("file:files/input")   //input 폴더에 파일을 넣으면
		.log("${body}")			   // log에 파일 내용이 찍힌 후 바로 output 파일로 이동.
		.to("file:files/output");  
		
	}
	
}
