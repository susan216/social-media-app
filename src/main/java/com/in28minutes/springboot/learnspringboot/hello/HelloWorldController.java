package com.in28minutes.springboot.learnspringboot.hello;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
public class HelloWorldController {
	
	MessageSource messageSource ;
	
	
	public HelloWorldController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@GetMapping(path="/helloworld")
	public String helloWorld() {
		return "HelloWorld";
	}
	
	
	@GetMapping(path="/helloworldBean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World Bean");
	}
	
	
	@GetMapping(path="/helloworldBean/{name}")
	public HelloWorldBean helloWorldBeanPathVariable(@PathVariable  String name) {
		return new HelloWorldBean("Hello World Bean " + " " +name);
	}
	
	@GetMapping(path="/helloworld-i18n/{name}")
	public String helloWorldI18n(@PathVariable  String name) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "Default message", locale ) + " " + name;
		
	}
	
	
	
}
