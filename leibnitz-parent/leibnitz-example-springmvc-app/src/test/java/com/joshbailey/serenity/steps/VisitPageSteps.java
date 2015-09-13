package com.joshbailey.serenity.steps;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;

import com.joshbailey.leibnitz.cargo.CargoOrchestrator;
import com.joshbailey.springmvc.liebniz.servers.StandardServer;

public class VisitPageSteps {

	private StandardServer server;
	
	@BeforeStories
	public void before(){
		server = new StandardServer();
		CargoOrchestrator.startTomcat(server);
	}
	
	@AfterStories
	public void after(){
		CargoOrchestrator.stopTomcat(server);
	}
	
	@Given("I have opened then page")
	@Pending
	public void givenIHaveOpenedThenPage() {
	  // PENDING
	}

	@Then("the page is there")
	@Pending
	public void thenThePageIsThere() {
	  // PENDING
	}
	
}
