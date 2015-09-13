package com.joshbailey.leibnitz.cargo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.cargo.container.ContainerException;
import org.codehaus.cargo.container.installer.Installer;
import org.codehaus.cargo.container.installer.ZipURLInstaller;
import org.codehaus.cargo.container.property.GeneralPropertySet;
import org.codehaus.cargo.container.tomcat.Tomcat7xInstalledLocalContainer;
import org.codehaus.cargo.container.tomcat.TomcatPropertySet;
import org.codehaus.cargo.util.log.SimpleLogger;

import com.joshbailey.leibnitz.workingdirectory.WorkingDirectoryHelper;
import com.joshbailey.leibniz.servers.LeibnitzServer;

public class CargoOrchestrator {

	//TODO: This may not need to be stored in a map at all
	private static enum PortInUseExceptionHandler{

		//Port number 8205 (defined with the property cargo.rmi.port) is in use. Please free it on the system or set it to a different port in the container configuration.
		RMI_PORT_IN_USE("Port number (\\d+) \\(defined with the property cargo.rmi.port\\) is in use\\. .*"
						,GeneralPropertySet.RMI_PORT),
		
		//Port number 8080 (defined with the property cargo.servlet.port) is in use. Please free it on the system or set it to a different port in the container configuration.
		HTTP_PORT_IN_USE("Port number (\\d+) \\(defined with the property cargo.servlet.port\\) is in use\\. .*"
						,"cargo.servlet.port"),
		
		//Port number 8009 (defined with the property cargo.tomcat.ajp.port) is in use. Please free it on the system or set it to a different port in the container configuration.
		AJP_PORT_IN_USE("Port number (\\d+) \\(defined with the property cargo.tomcat.ajp.port\\) is in use\\. .*"
					   ,TomcatPropertySet.AJP_PORT);
		
		private final String regex;
		private final Pattern pattern;
		private final String containerPortProperty;
		
		private PortInUseExceptionHandler(String regex
										 ,String containerPortProperty) {
			this.regex = regex;
			this.pattern = Pattern.compile(regex);
			this.containerPortProperty = containerPortProperty;
		}
		
		public boolean matches(String message){
			return message.matches(regex);
		}

		public void handle(String message, Tomcat7xInstalledLocalContainer lclContainer){
			Matcher m = pattern.matcher(message);
			m.find();
			int alreadyInUsePort = Integer.parseInt(m.group(1));
			System.out.println("This port was already in use: "+alreadyInUsePort);
			lclContainer.getConfiguration().setProperty(containerPortProperty, Integer.toString(alreadyInUsePort+1));
		}
		
	}
	
	

	//The container is already started. If you wish to restart a running container, please use the restart method instead.
	
	private static void install(LeibnitzServer config) throws MalformedURLException {
		System.out.println("jacoco.agent.arg: "+System.getProperty("jacoco.agent.arg"));
		
		//http://archive.apache.org/dist/tomcat/tomcat-7/v7.0.62/bin/apache-tomcat-7.0.62.tar.gz
		Installer installer = new ZipURLInstaller(new URL("file:\\c:\\dev\\Tomcat-zips\\apache-tomcat-7.0.62.tar.gz"), 
														  filePathForServer(config)+"\\download", 
														  filePathForServer(config)+"\\extract");
		installer.setLogger(new SimpleLogger());
		System.out.println(">>> INSTALL:");
		installer.install();
		System.out.println(">>> INSTALLED.");
		
		System.out.println("Home: "+installer.getHome());
	}
	
	private static String filePathForServer(LeibnitzServer server){
		return WorkingDirectoryHelper.workingDirectoryFilePath()+"\\"+server.getClass().getCanonicalName();
	}
	
	public static synchronized void startTomcat(LeibnitzServer config){
		try { //TODO Figure a way to remove this ugly try/catch block
			install(config);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(config.getAssociatedContainer() == null){
			Tomcat7xInstalledLocalContainer lclContainer = new Tomcat7xInstalledLocalContainer(config.getConfig(filePathForServer(config)+"\\config"));
			lclContainer.setHome(filePathForServer(config)+"\\extract\\apache-tomcat-7.0.62\\apache-tomcat-7.0.62");
			lclContainer.setLogger(new SimpleLogger());
			lclContainer.setOutput(filePathForServer(config)+"\\log.txt");
			System.out.println(">>> START:");
			boolean shouldTryAgain = false;
			do{
				try{
					lclContainer.start();
				}catch(ContainerException containerException){
					shouldTryAgain = handleContainerException(containerException,lclContainer);
				}
			}while(shouldTryAgain);
			System.out.println(">>> STARTED.");
			config.setAssociatedContainer(lclContainer);
		}
	}
	
	private static boolean handleContainerException(ContainerException containerException, Tomcat7xInstalledLocalContainer lclContainer){
		System.out.println("Here is the message: "+containerException.getMessage());
		System.out.println("Here is the cause: "+containerException.getCause().getMessage());
		String message = containerException.getMessage();
		for(PortInUseExceptionHandler portInUseExceptionHandler : PortInUseExceptionHandler.values()){
			if(portInUseExceptionHandler.matches(message)){
				portInUseExceptionHandler.handle(message, lclContainer);
				return true;
			}
		}
		return false;
	}

	public static synchronized void stopTomcat(LeibnitzServer config){
		//TODO This probably needs to be synchronized
		if(config.getAssociatedContainer() != null){
			System.out.println(">>> STOP:");
			config.getAssociatedContainer().stop();
			System.out.println(">>> STOPPED.");
			config.setAssociatedContainer(null);
		}
	}
	
}
