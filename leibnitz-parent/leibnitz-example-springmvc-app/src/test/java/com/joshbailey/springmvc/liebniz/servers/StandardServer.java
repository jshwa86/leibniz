package com.joshbailey.springmvc.liebniz.servers;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.codehaus.cargo.container.configuration.LocalConfiguration;

import com.joshbailey.leibnitz.servers.LeibnitzServer;
import com.joshbailey.leibnitz.wars.LeibnitzWar;
import com.joshbailey.liebniz.wars.GreeterServiceWar;
import com.joshbailey.liebniz.wars.SpringMvcUIWar;

public class StandardServer extends LeibnitzServer{

	private static final Collection<LeibnitzWar> wars;
	static{
		wars = new LinkedList<LeibnitzWar>();
		wars.add(new GreeterServiceWar());
		wars.add(new SpringMvcUIWar());
	}
	
	public StandardServer() {
		super(wars);
	}
	
	@Override
	public LocalConfiguration getConfig(String homeDir) {
		LocalConfiguration lclConfig = prepareConfiguration(homeDir,Arrays.asList(new String[]{}));
		return lclConfig;
	}

}
