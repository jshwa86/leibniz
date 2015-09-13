package com.joshbailey.liebniz.main;

import com.joshbailey.leibnitz.cargo.CargoOrchestrator;
import com.joshbailey.leibnitz.servers.LeibnitzServer;
import com.joshbailey.liebniz.servers.JoshServer;
import com.joshbailey.liebniz.servers.StandardServer;

public class Demo {

	public static void main(String args[]){
		LeibnitzServer standard = new StandardServer();
		LeibnitzServer josh = new JoshServer();
		
		System.out.println("** Starting standard... **");
		CargoOrchestrator.startTomcat(standard);
		System.out.println("** Starting josh... **");
		CargoOrchestrator.startTomcat(josh);
		System.out.println("** Stop standard... **");
		CargoOrchestrator.stopTomcat(standard);
		System.out.println("** Stop standard... **");
		CargoOrchestrator.stopTomcat(josh);
	}
	
}
