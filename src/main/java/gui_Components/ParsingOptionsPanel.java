package gui_Components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;

import settings.Settings;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

/*
 * Panel containing the parsing selection options. (Selecting what stats, buffs, etc. you'd like to show in the output. 
 * Functionality requires you to run updateFields(Settings) and saveFields(Settings), 
 * in addition to calling updateProfessionWindow(Settings) to refresh the profession buffs when a new profession is selected.  
 */
public class ParsingOptionsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JCheckBox chckbxNewCheckBox;
	private static BuffWindow professionBuffWindow;
	private static BuffWindow newBuffsWindow;
	private static StatsWindow statWindow; 
	private static String curProf; //Used to detect when the profession Buff window should update.
	
	public ParsingOptionsPanel(Settings curSettings) {
		JPanel panelSettings = new JPanel();
		panelSettings.setVisible(true);
		curProf = curSettings.getProfession();
		
		//Check here, so swing will work.  
		if(curSettings != null) {
			professionBuffWindow = new BuffWindow(curSettings.getProfessionBuffsSettings());
			newBuffsWindow = new BuffWindow(curSettings.getBuffsList());
		}
		
		JButton btnOpenProfessionBuffsWindow = new JButton("Profession Buffs Settings");
		btnOpenProfessionBuffsWindow.setToolTipText("<html> Select buffs that are, typically, unique to specific professions to parse out. <br>Options available are effected by the profession selected in filtering. </html>");
		btnOpenProfessionBuffsWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(curProf != curSettings.getProfession()) 
					professionBuffWindow = new BuffWindow(curSettings.getProfessionBuffsSettings());
				professionBuffWindow.setWindowVisible();
			}
		});
		
		chckbxNewCheckBox = new JCheckBox("Effects Enabled");
		chckbxNewCheckBox.setToolTipText("Will show effects active on character, such as consumables, utilities, and more.");
		
		JButton btnNewButton_4 = new JButton("Buff Settings");
		btnNewButton_4.setToolTipText("Select what buffs/boons to parse out.");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newBuffsWindow.setWindowVisible();
			}
		});
		
		JButton btnNewButton = new JButton("Stat Settings");
		btnNewButton.setToolTipText("Select general statistics and details to parse out.");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statWindow.setWindowVisible();
			}
		});
		
		JLabel lblNewLabel = new JLabel("Output Options");
		
		GroupLayout gl_panelSettings = new GroupLayout(panelSettings);
		gl_panelSettings.setHorizontalGroup(
			gl_panelSettings.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSettings.createSequentialGroup()
					.addGap(222)
					.addGroup(gl_panelSettings.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelSettings.createSequentialGroup()
							.addGap(21)
							.addGroup(gl_panelSettings.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton)
								.addComponent(btnOpenProfessionBuffsWindow, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNewButton_4)
								.addComponent(chckbxNewCheckBox)))
						.addComponent(lblNewLabel))
					.addContainerGap(222, Short.MAX_VALUE))
		);
		gl_panelSettings.setVerticalGroup(
			gl_panelSettings.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSettings.createSequentialGroup()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnOpenProfessionBuffsWindow)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_4)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxNewCheckBox)
					.addContainerGap(298, Short.MAX_VALUE))
		);
		
		
		panelSettings.setLayout(gl_panelSettings);
		add(panelSettings);
	}
	
	public void updateProfessionWindow(Settings s) {
		professionBuffWindow = new BuffWindow(s.getProfessionBuffsSettings());
	}
	
	public void updateFields(Settings s) {
		chckbxNewCheckBox.setSelected(s.getEffectsEnabled());
		professionBuffWindow = new BuffWindow(s.getProfessionBuffsSettings());
		newBuffsWindow = new BuffWindow(s.getBuffsList());
		statWindow = new StatsWindow(s.getCategories());
	}
	
	public void saveFields(Settings s) {
		s.setEffectsEnabled(chckbxNewCheckBox.isSelected());
	}
}
