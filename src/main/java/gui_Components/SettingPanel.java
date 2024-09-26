package gui_Components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import components.FileHelper;
import settings.Settings;

public class SettingPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private static JLabel lblSourceJSONCount;
	private static JTextField textFieldSourceDirectory;
	private static JTextField textFieldOutputDirectory;
	private static JTextField textFieldOutputName;
	
	private static JTextField textFieldEIDirctory;
	private static JCheckBox chckbxUseEI;
	private static JCheckBox chckbxSaveJSON;
	private static JPanel panelEI;
	private String desiredFileExtension = ".json";
	private JCheckBox chckbxEmbedHTML;
	
	
	public SettingPanel() {
		this.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("Output Name");
		lblNewLabel.setToolTipText("This will be the name of the spreadsheet your program generates.");
		
		JButton btnNewButton_2 = new JButton("Output directory");
		btnNewButton_2.setToolTipText("Select the location you'd like to output the program results.");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					String fileName=FileHelper.selectDirectory("csv SpreadSheet", "csv");
					if (fileName == null)
						return;
					textFieldOutputDirectory.setText(fileName);
			}
		});
		
		JButton btnNewButton_1 = new JButton("Source Directory");
		btnNewButton_1.setToolTipText("Select the location of your log files.");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName;
				if (desiredFileExtension.equals(".json"))
					fileName = FileHelper.selectDirectory("JSON File", "json");
				else
					fileName = FileHelper.selectDirectory("zevtc File", "zevtc");
				if (fileName == null)
					return;
				File f = new File(fileName);
				textFieldSourceDirectory.setText(fileName);
				lblSourceJSONCount.setText("Files Found: "+FileHelper.getFileCount(f, desiredFileExtension));
			}
		});
		
		JButton btnNewButton_3 = new JButton("Elite Insights Directory");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				Path p = FileHelper.selectFile(null, "Select your Elite Insights application (.exe file)", "Application Files", "exe");
				if(p == null || !Files.exists(p))
					return;
				textFieldEIDirctory.setText(p.toString());
			}
		});
		
		chckbxUseEI = new JCheckBox("Parse from .zevtc***");
		chckbxUseEI.setToolTipText("<html>Parse .zevtc files, instead of JSON. <br>NOTE: This option requires some space in your selected output directory hard drive and the \"Elite Insights Directory\" to be set. </html>");
		chckbxUseEI.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
		            //Set file search to .zevtc, 
					panelEI.setVisible(true);
					desiredFileExtension = ".zevtc";
					updateSourceFileCount(textFieldSourceDirectory.getText());
		        } else {
		            //Set file search to JSON.
		        	desiredFileExtension = ".json";
					updateSourceFileCount(textFieldSourceDirectory.getText());
		        	panelEI.setVisible(false);
		        };
			}
		});
		
		textFieldSourceDirectory = new JTextField();
		textFieldSourceDirectory.setColumns(10);
		textFieldOutputDirectory = new JTextField();
		textFieldOutputDirectory.setColumns(10);
		textFieldOutputName = new JTextField();
		textFieldOutputName.setColumns(10);
		
		lblSourceJSONCount = new JLabel("Files Detected: 0");
		lblSourceJSONCount.setToolTipText("Number of applicable log files detected in the selected Source Directory.");
		
		JLabel lblNewLabel_10 = new JLabel("Program Options");
		panelEI = new JPanel();
		
		chckbxEmbedHTML = new JCheckBox("Embed HTML");
		chckbxEmbedHTML.setToolTipText("Embbed HTML files into the excel document. This will significantly increase the size of the file.");
		
		GroupLayout gl_panel = new GroupLayout(this);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(102, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(21)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(10)
											.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)))
									.addGap(7)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(textFieldOutputName, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(6)
											.addComponent(textFieldOutputDirectory, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE))))
								.addComponent(lblNewLabel_10)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
									.addGap(10)
									.addComponent(textFieldSourceDirectory, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
									.addGap(4)
									.addComponent(lblSourceJSONCount))
								.addComponent(panelEI, GroupLayout.PREFERRED_SIZE, 370, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(chckbxUseEI)
							.addGap(18)
							.addComponent(chckbxEmbedHTML)))
					.addGap(88))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxUseEI)
						.addComponent(chckbxEmbedHTML))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelEI, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_10)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_1)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(1)
							.addComponent(textFieldSourceDirectory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(4)
							.addComponent(lblSourceJSONCount)))
					.addGap(3)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnNewButton_2)
							.addGap(4)
							.addComponent(lblNewLabel))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(textFieldOutputDirectory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(textFieldOutputName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(73, Short.MAX_VALUE))
		);
		
		
		/*
		 * EI panel
		 */
		
		textFieldEIDirctory = new JTextField();
		textFieldEIDirctory.setColumns(10);
		
		chckbxSaveJSON = new JCheckBox("Save JSON & HTML");
		chckbxSaveJSON.setToolTipText("If selected, will keep the JSON files generated.");
		GroupLayout gl_panelEI = new GroupLayout(panelEI);
		gl_panelEI.setHorizontalGroup(
			gl_panelEI.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelEI.createSequentialGroup()
					.addGroup(gl_panelEI.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelEI.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxSaveJSON))
						.addGroup(gl_panelEI.createSequentialGroup()
							.addComponent(btnNewButton_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldEIDirctory, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(23, Short.MAX_VALUE))
		);
		gl_panelEI.setVerticalGroup(
			gl_panelEI.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelEI.createSequentialGroup()
					.addContainerGap()
					.addComponent(chckbxSaveJSON)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelEI.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_3)
						.addComponent(textFieldEIDirctory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelEI.setLayout(gl_panelEI);
		
		this.setLayout(gl_panel);
	}
	
	private void updateSourceFileCount(String fileName) {
		if(fileName!= null && !fileName.isBlank())
			lblSourceJSONCount.setText("Files Found: "+FileHelper.getFileCount(new File(fileName), desiredFileExtension));
		else
			lblSourceJSONCount.setText("Files Found: 0");
	}
	
	
	public void updateFields(Settings s) {
		textFieldSourceDirectory.setText(s.getInputFile());
		textFieldOutputDirectory.setText(s.getOutputFile());
		textFieldOutputName.setText(s.getOutputFileName());
		textFieldEIDirctory.setText(s.getEIAbsPath());
		chckbxUseEI.setSelected(s.getUseEI());
		panelEI.setVisible(s.getUseEI());
		chckbxSaveJSON.setSelected(s.getSaveJSON());
		chckbxEmbedHTML.setSelected(s.getEmbbedHTML());
		
		if(s.getUseEI())
			desiredFileExtension = ".zevtc";
		else
			desiredFileExtension = ".json";
		
		updateSourceFileCount(s.getInputFile());
	}
	
	public void saveFields(Settings s) {
		s.setInputFile(textFieldSourceDirectory.getText());
		s.setOutputFile(textFieldOutputDirectory.getText());
		s.setOutputFileName(textFieldOutputName.getText());
		s.setEIAbsPath(textFieldEIDirctory.getText());
		s.setUseEI(chckbxUseEI.isSelected());
		s.setSaveJSON(chckbxSaveJSON.isSelected());
		s.setEmbbedHTML(chckbxEmbedHTML.isSelected());;
	}
}
