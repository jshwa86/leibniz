package com.joshbailey.liebniz.wars;

import com.joshbailey.leibniz.wars.LeibnitzWar;
import com.joshbailey.leibniz.wars.LeibnitzWar.WarFilePathResolutionStrategy;

public class GreeterServiceWar extends LeibnitzWar{

	public GreeterServiceWar() {
		super("greeter-service.war", "http://localhost:8080/CXFExampleService/personService",WarFilePathResolutionStrategy.IMPORTED);
	}
	
}
