package com.joshbailey.leibniz.servers;

import java.util.Collection;

import org.codehaus.cargo.container.configuration.LocalConfiguration;
import org.codehaus.cargo.container.property.GeneralPropertySet;
import org.codehaus.cargo.container.tomcat.Tomcat7xInstalledLocalContainer;
import org.codehaus.cargo.container.tomcat.Tomcat7xStandaloneLocalConfiguration;
import org.codehaus.cargo.util.log.SimpleLogger;

import com.joshbailey.leibniz.wars.LeibnitzWar;

public abstract class LeibnitzServer {

	private Collection<LeibnitzWar> wars;
	
	public LeibnitzServer(Collection<LeibnitzWar> wars) {
		this.wars = wars;
	}

	public abstract LocalConfiguration getConfig(String homeDir);

	private Tomcat7xInstalledLocalContainer associatedContainer;

	public Tomcat7xInstalledLocalContainer getAssociatedContainer() {
		return associatedContainer;
	}

	public void setAssociatedContainer(Tomcat7xInstalledLocalContainer associatedContainer) {
		this.associatedContainer = associatedContainer;
	}

	protected LocalConfiguration prepareConfiguration(String homeDir, Collection<String> args) {
		
		LocalConfiguration lclConfig = new Tomcat7xStandaloneLocalConfiguration(homeDir);
		lclConfig.setLogger(new SimpleLogger());
		for(LeibnitzWar war : wars){
			lclConfig.addDeployable(war.buildDeployable());			
		}
		setJvmArgs(lclConfig, args);
		return lclConfig;
	}

	protected void setJvmArgs(LocalConfiguration configuration, Collection<String> args) {
		StringBuilder jvmArgs = new StringBuilder();
		if (null != System.getProperty("jacoco.agent.arg")) {
			jvmArgs.append(System.getProperty("jacoco.agent.arg")).append(" ");
		}
		for (String arg : args) {
			jvmArgs.append(arg); // TODO This most likely does not work when
									// there are multiple args.
		}
		configuration.setProperty(GeneralPropertySet.JVMARGS, jvmArgs.toString());
	}

	@Override
	public int hashCode() {
		return this.getClass().getCanonicalName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() == obj.getClass()) {
			return true;
		}
		return false;
	}

}
