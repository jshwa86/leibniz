package com.joshbailey.personservice;

import java.net.MalformedURLException;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.message.Message;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.joshbailey.leibniz.servers.LeibnitzServer;
import com.joshbailey.leibniz.tests.ServerDependentTest;
import com.joshbailey.liebniz.servers.JoshServer;

import junit.framework.Assert;

@ContextConfiguration(locations="/itest-spring-config/personWebservice.xml")
@DirtiesContext
public class PersonService_JoshProfile_IT extends ServerDependentTest{
	
	@Autowired
	private PersonService personServiceClient;
	
	private LeibnitzServer server;
	
	public PersonService_JoshProfile_IT(JoshServer leibnitzServer){
		System.out.println("** My leibnitzServer: "+leibnitzServer);
		this.server = leibnitzServer;
	}
	
	@Before
	public void setUpStringContext() throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(this.getClass().getAnnotation(ContextConfiguration.class).locations());
		personServiceClient = ctx.getBean("personServiceClient",PersonService.class);
		
		//
		String httpPort = server.getAssociatedContainer().getConfiguration().getPropertyValue("cargo.servlet.port");
		Client client = ClientProxy.getClient(personServiceClient);
			client.getRequestContext().put(Message.ENDPOINT_ADDRESS, "http://localhost:"+httpPort+"/CXFExampleService/personService") ;
		//
	}
	
	@Test
	public void world() throws MalformedURLException{
		String response = personServiceClient.greetPerson("World");
		Assert.assertEquals("Hello Josh!!", response);
	}
	
	@Test
	public void foo() throws MalformedURLException{
		String response = personServiceClient.greetPerson("Foo");
		Assert.assertEquals("Hello Josh!!", response);
	}
	
	@Test
	public void josh() throws MalformedURLException{
		String response = personServiceClient.greetPerson("Josh");
		Assert.assertEquals("Hello Josh!!", response);
	}
	
	@Test
	public void bob() throws MalformedURLException{
		String response = personServiceClient.greetPerson("Bob");
		Assert.assertEquals("Hello Josh!!", response);
	}
	
	@Test
	public void asfasdafasdf() throws MalformedURLException{
		String response = personServiceClient.greetPerson("asfasdafasdf");
		Assert.assertEquals("Hello Josh!!", response);
	}
	
}
