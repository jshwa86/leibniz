package com.joshbailey.liebniz.wars;

import com.joshbailey.leibnitz.wars.LeibnitzWar;
import com.joshbailey.leibnitz.wars.LeibnitzWar.WarFilePathResolutionStrategy;

public class GreeterServiceWar extends LeibnitzWar{

	public GreeterServiceWar() {
		super("greeter-service.war", "http://localhost:8080/CXFExampleService/personService",WarFilePathResolutionStrategy.IMPORTED);
	}
	
}
