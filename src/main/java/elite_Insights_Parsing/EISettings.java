package elite_Insights_Parsing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import components.Constants;
import settings.Settings;


/*
 * Generates the .conf file that Elite Insights runs with. 
 * Based on settings, however there are many default options. 
 * 
 * 
 * TODO: 
 * 	--Optimization could be done. It takes a long time to create a new file like this. 
 * 		---Originally tried using Properties(), but properties doesn't allow ':' colons to be used in fields, which messed up file location for EI. 
 * 	--There should be a way for users to customize what Elite insights generates. Perhaps even be able to drag in their own .conf file? 
 */

public class EISettings {
	private static HashMap<String, String> getConfMap(Settings s, String outLoc) {
		return new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

		{
			put("AddDuration","True");
			put("AddPoVProf","False");
			put("Anonymous","False");
			put("ApplicationTraces","False");
			put("AutoAdd","False");
			put("AutoAddPath","");
			put("AutoDiscordBatch","False");
			put("AutoParse","False");
			put("CompressRaw","False");
			put("ComputeDamageModifiers","False");
			put("CustomTooShort", Integer.toString(s.getMinDuration()));
			put("DPSReportUserToken","");
			put("DetailledWvW","True");
			put("HtmlCompressJson","False");
			put("HtmlExternalScripts","False");
			put("HtmlExternalScriptsCdn","");
			put("HtmlExternalScriptsPath","");
			put("IndentJSON","True");
			put("IndentXML","False");
			put("LightTheme","False");
			put("MultiThreaded","False");
			put("OutLocation", outLoc.replace("\\", "/"));
			put("Outdated","False");
			put("ParseCombatReplay","False");
			put("ParseMultipleLogs","True");
			put("ParsePhases","True");
			put("PopulateHourLimit","0");
			put("RawTimelineArrays","True");
			put("SaveAtOut","False");
			put("SaveOutCSV","False");
			put("SaveOutHTML",s.getEmbbedHTML().toString());
			put("SaveOutJSON","True");
			put("SaveOutTrace","True");
			put("SaveOutXML","False");
			put("SendEmbedToWebhook","False");
			put("SendSimpleMessageToWebhook","False");
			put("SkipFailedTries","True");
			put("UploadToDPSReports","False");
			put("UploadToRaidar","False");
			put("WebhookURL","");
		}};
	}
	
	
	public static String generateEIConf(Settings s, String JSONDirectory) throws IOException {
		// create new BufferedWriter for the output file
        File file = new File(Constants.GW2_APP_DATA_FILE + "\\EISettings.conf");
        BufferedWriter bf = new BufferedWriter(new FileWriter(file));
		  
		// iterate map entries
		for (Map.Entry<String, String> entry :
			getConfMap(s, JSONDirectory).entrySet()) {
			
			bf.write(entry.getKey() + "= " + entry.getValue());
			bf.newLine();
		}
		//Close writer
		bf.flush();
		bf.close();

		return file.getAbsolutePath();
	}
}
