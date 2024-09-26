package components;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

public class DialogHelper {
	static final Logger logger = Logger.getLogger(DialogHelper.class); 
	

	public static Boolean confirmationWindow(String message) {
		int dialogResult = JOptionPane.showConfirmDialog(null, message);
		return dialogResult == JOptionPane.YES_OPTION;
	}
	
	public static Boolean runProgramErrorMessage(String message) {
		logger.warn("Tried to run program with required field(s) missing: " + message);
		JOptionPane.showMessageDialog(null,
			    "The settings are missing one or more required fields: \n" + message,
			    "Configuration error",
			    JOptionPane.ERROR_MESSAGE);
		return false;
	}
	
	public static void errorMessage(String title, String message) {
		logger.error("Error during runtime: " + message);
		JOptionPane.showMessageDialog(null,
			    "Error: " + message,
			    title,
			    JOptionPane.ERROR_MESSAGE);
	}
	
	public static Boolean errorTryAgainMessage(String title, String message) {
		int dialogResult = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
		return dialogResult == JOptionPane.YES_OPTION;
	}
}
