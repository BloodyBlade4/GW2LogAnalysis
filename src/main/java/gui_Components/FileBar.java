package gui_Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FileBar extends JPanel{
	private static final long serialVersionUID = 1L;
	public static enum FileState {
		WAITING, PARSING_ZEVTC, READING_JSON, DONE, FAILED, ERROR, DNE
	}
	private static String FileStateToString(FileState s) {
		switch (s) {
		case WAITING:
			return "Waiting";
		case PARSING_ZEVTC:
			return "Parsing the zevtc file.";
		case READING_JSON:
			return "Reading JSON file.";
		case DONE:
			return "Done.";
		case ERROR:
			return "Error, check log file for details.";
		case DNE: 
			return "Error, cannot find file.";
		default:
			return "No enum for this case: " + s.toString();
					
		}
	}
	
	private String filePath = null;
	private JLabel lblFileState;
	private JLabel lblFileName;
	
	public FileBar(String newFilePath){
		this.filePath = newFilePath;
		
		this.setBorder(BorderFactory.createLineBorder(Color.black));

		setLayout(new GridLayout(0, 1, 0, 0));
		
		lblFileName = new JLabel(new File(newFilePath).getName());
		lblFileName.setAlignmentY(Component.LEFT_ALIGNMENT);
		add(lblFileName);
		
		lblFileState = new JLabel("Waiting...");
		lblFileState.setAlignmentY(Component.CENTER_ALIGNMENT);
		add(lblFileState);
	}
	
	public void setState(FileState FS) {
		lblFileState.setText(FileStateToString(FS));
	}
	public void finishedFile(String newLabel) {
		this.filePath = null;
		lblFileState.setText(newLabel);
	}
	public void setFilePath(String f) {
		this.filePath = f;
	}
	public String getFilePath() {
		return this.filePath;
	}
	public String generateFileName() {
		return (new File(this.filePath)).getName();
	}
}
