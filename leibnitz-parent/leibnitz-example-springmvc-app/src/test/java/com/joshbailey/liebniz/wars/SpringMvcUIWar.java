package com.joshbailey.liebniz.wars;

import com.joshbailey.leibniz.wars.LeibnitzWar;
import com.joshbailey.leibniz.wars.LeibnitzWar.WarFilePathResolutionStrategy;

public class SpringMvcUIWar extends LeibnitzWar{

	public SpringMvcUIWar() {
		super("leibnitz-example-springmvc-app-0.0.1-SNAPSHOT.war", "http://localhost:8080/leibnitz-example-springmvc-app/",WarFilePathResolutionStrategy.BUILT_LOCALLY);
	}
	
}
