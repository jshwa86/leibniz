package com.joshbailey.personservice;

import java.net.MalformedURLException;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.message.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.joshbailey.leibnitz.servers.LeibnitzServer;
import com.joshbailey.leibnitz.tests.ServerDependentTest;
import com.joshbailey.liebniz.servers.StandardServer;

@ContextConfiguration(locations = "/itest-spring-config/personWebservice.xml")
public class PersonService3_IT extends ServerDependentTest{

	@Autowired
	private PersonService personServiceClient;

	private LeibnitzServer server;
	
	
	public PersonService3_IT(StandardServer leibnitzServer){
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
	public void doTest() throws MalformedURLException {
		String response = personServiceClient.greetPerson("Bubba");
		Assert.assertEquals("Hello Bubba!", response);
	}

}
