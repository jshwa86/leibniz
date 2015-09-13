package com.joshbailey.liebniz.servers;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.codehaus.cargo.container.configuration.LocalConfiguration;

import com.joshbailey.leibnitz.servers.LeibnitzServer;
import com.joshbailey.leibnitz.wars.LeibnitzWar;
import com.joshbailey.liebniz.wars.CXFExampleServiceWar;

public class JoshServer extends LeibnitzServer{

	private static final Collection<LeibnitzWar> wars;
	static{
		wars = new LinkedList<LeibnitzWar>();
		wars.add(new CXFExampleServiceWar());
	}
	
	public JoshServer() {
		super(wars);
	}

	@Override
	public LocalConfiguration getConfig(String homeDir) {
		LocalConfiguration lclConfig = prepareConfiguration(homeDir,Arrays.asList(new String[]{"-Dspring.profiles.active=josh"}));
		return lclConfig;
	}

}
