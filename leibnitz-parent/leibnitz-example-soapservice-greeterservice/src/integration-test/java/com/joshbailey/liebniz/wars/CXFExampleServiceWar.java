package com.joshbailey.liebniz.wars;

import com.joshbailey.leibniz.wars.LeibnitzWar;

public class CXFExampleServiceWar extends LeibnitzWar{

	public CXFExampleServiceWar() {
		super("CXFExampleService.war", "http://localhost:8080/CXFExampleService/personService",WarFilePathResolutionStrategy.BUILT_LOCALLY);
	}

	
	
	
}
