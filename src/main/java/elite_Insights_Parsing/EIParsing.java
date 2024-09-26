package elite_Insights_Parsing;

import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;

import org.apache.log4j.Logger;

/*
 * Used to parse .zevtc files through Elite Insights to generate JSON/HTML files, which this program uses. 
 * Elite Insights repository: https://github.com/baaron4/GW2-Elite-Insights-Parser
 * 
 * "If you would like to have your logs parsed without the GUI pass the file location of each evtc file as a string. 
 * Call: "GuildWars2EliteInsights.exe -c [config path] [logs]" "
 * 
 * Errors handled here will not end the program nor warn the user directly. 
 * The EI file will already have been verified as a file, and analysis will only try to read json files that exist. 
 */

public class EIParsing {
	static final Logger logger = Logger.getLogger(EIParsing.class); 
	
	//Prepares the list<String for the command line parameters. 
	public static void runEIBatch(List<String> batch, String EILoc, String confFile) {    	
        List<String> params = new ArrayList<>();
        params.add(EILoc); //Elite Insights location
        params.add("-p"); //Disables cmd lines from opening.
        params.add("-c"); //Enables sending config file
        params.add(confFile); //the config file path.
        //add the .zevtc files one by one. 
        for(int i = 0; i < batch.size(); i++)  {
        	if(batch.get(i) instanceof String)
        		params.add(batch.get(i));
        	else
        		System.out.println("What type is the file?");
        }
        runEIParsing(params);
	}
	
	//Runs the command line parameters based on a list of strings. 
	public static void runEIParsing(List<String> params) {
		try {
			//create and start a new process
			ProcessBuilder pb = new ProcessBuilder(params);
			pb.redirectErrorStream(true);
			Process p = pb.start();
			
			//read any incoming information from the process. 
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null)
				logger.info(line);
			
			//wait until process is finished. 
			int result = p.waitFor();
            if (result != 0) {
            	System.out.println("The elite insights parsing process failed with status " + result);
                logger.error("The Elite Insights parsing process failed with status " + result);
            }
		} catch (Exception e) {
			logger.error("Exception occured in the Elite Insights parsing thread. " + e);
        	e.printStackTrace();
		}
	}
	
	//Creates a directory, with a time stamp for unique name. Returns absolute path to new directory.
	public static Path createDirectory(String loc) {
		DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        System.out.println(timeStampPattern.format(java.time.LocalDateTime.now()));
		
		String dirName = loc + "\\GW2LAGeneratedJSON" + timeStampPattern.format(java.time.LocalDateTime.now()).toString();
		Path dir = Paths.get(dirName);
		try {
			if(!Files.exists(dir))
				Files.createDirectory(dir);
			return dir;
		} catch (Exception e) {
			logger.error("Tried to create a directory for EI, " + dir.toAbsolutePath().toString() + ". " + e);
			logger.info("Trying to manage the EI directory...");
			//TODO: Is there a way to handle this exception besides trying again?
			if(Files.exists(dir))
				return dir;
		}

	    return null;
	}
}
