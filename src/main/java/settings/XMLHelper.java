package settings;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import components.Constants;

public class XMLHelper {

	//given the absolute path of the settings file, will load and run functions to update settings and fields. 
	public static Settings loadSettings(Path p) throws Exception{
		if (p == null)
			throw new Exception("Cannot load settings because the settings file name appears to be empty.");
	
		try {
			if(!Files.exists(p))
				throw new Exception("Cannot load settings because the settings file path could not be loaded.");
			ObjectMapper mapper = new XmlMapper(); 
			TypeReference<Settings> typeRef = new TypeReference<Settings>() {};
			Settings s = mapper.readValue(Files.newInputStream(p), typeRef);
			return s;
		} catch(FileNotFoundException e1) {
			e1.printStackTrace();
			throw new Exception("Failed find the settings file.");
		}
		catch (UnrecognizedPropertyException e1){
			throw new Exception("Unrecognized properties found or missing in the selected settings file.");
		} 
		catch(IOException e2) {
			throw new Exception("Failed to open, or close, the desired settings file.");
		}
		catch(InaccessibleObjectException e3) {
			throw new Exception("Tried to load in accessible object from settings... " + e3);
		}
		catch (Exception e4) {
			e4.printStackTrace();
			throw new Exception("Something went wrong while trying to load the selected settings file."); 
		}
	}
		
	/*
	 * Searches through settings file for most recently used settings file.
	 * If found, will load it. If not found, returns false and allows creation of new settings.  
	 */
	public static Path findLastSettings() throws Exception
	{
		try {
			Path p = Paths.get(Constants.GW2_APP_DATA_FILE + "\\Settings");
			if (!Files.exists(p)) {
				Files.createDirectory(p);
				System.out.println("Settings file doesn't exist.");
				return null;
			}
			DirectoryStream<Path> ds = Files.newDirectoryStream(p);
			PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.xml");
			FileTime lastModifiedTime = null;
			Path chosen = null;
			
			for(Path path : ds) {
				FileTime time = Files.readAttributes(path,BasicFileAttributes.class).lastModifiedTime();
				if(matcher.matches(path.getFileName()) && (lastModifiedTime == null || lastModifiedTime.compareTo(time) < 0)) {
					chosen = path;
					lastModifiedTime = time;
				}
			}

			return chosen;
		} catch(Exception e) {
			throw new Exception("Exception when searching for previous settings to load. " + e);
		}
	}
}
