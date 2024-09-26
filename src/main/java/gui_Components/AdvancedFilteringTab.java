package gui_Components;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import settings.Settings;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class AdvancedFilteringTab extends JPanel {
	private static final long serialVersionUID = 1L;
	private WeaponPanel wepsPanel;
	private DatePanel datePanel;
	JFormattedTextField formattedTextFieldMinComDist;
	JFormattedTextField formattedTextFieldMinSquadSize;
	JFormattedTextField formattedTextFieldMinTimeAlive;
	private JSeparator separator;
	private JSeparator separator_1;
	

	
	public AdvancedFilteringTab(Settings curSettings) {
		wepsPanel = new WeaponPanel();
		wepsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		datePanel = new DatePanel();
		datePanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		this.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("Min Dist. to Comm.:");
		lblNewLabel.setToolTipText("Minimum required distance to the commander of the squad.");
		
		JLabel lblNewLabel_1 = new JLabel("Min Squad Size:");
		lblNewLabel_1.setToolTipText("Minimum squad size.");
		
		JLabel lblNewLabel_12 = new JLabel("Start Date:");
		lblNewLabel_12.setToolTipText("Logs will be parsed if they were recorded on or after this date.");
		
		formattedTextFieldMinComDist = new JFormattedTextField();
		formattedTextFieldMinComDist.setValue(Integer.valueOf(0));
		formattedTextFieldMinComDist.setColumns(10);
		
		formattedTextFieldMinSquadSize = new JFormattedTextField();
		formattedTextFieldMinSquadSize.setValue(Integer.valueOf(0));
		formattedTextFieldMinSquadSize.setColumns(10);
		
		formattedTextFieldMinTimeAlive = new JFormattedTextField();
		formattedTextFieldMinTimeAlive.setToolTipText("enter an nteger between 0 and 100. ");
		formattedTextFieldMinTimeAlive.setValue(Integer.valueOf(0));
		formattedTextFieldMinTimeAlive.setColumns(10);
		
		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		
		separator_1 = new JSeparator();
		
		JLabel lblNewLabel_2 = new JLabel("Min % Time Alive:");
		lblNewLabel_2.setToolTipText("<html>Minimum percentage of time you were alive during the fight. <br>Enter an integer between 0 and 100. <br>**This is not always accurate, but should end up including, rather than excluding due to errors.</html>");
		
		
		
		GroupLayout gl_panelSettings = new GroupLayout(this);
		gl_panelSettings.setHorizontalGroup(
			gl_panelSettings.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelSettings.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelSettings.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panelSettings.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(formattedTextFieldMinSquadSize))
						.addGroup(gl_panelSettings.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(formattedTextFieldMinComDist, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelSettings.createSequentialGroup()
							.addComponent(lblNewLabel_2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(formattedTextFieldMinTimeAlive)))
					.addPreferredGap(ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelSettings.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelSettings.createParallelGroup(Alignment.LEADING, false)
							.addComponent(wepsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(datePanel, GroupLayout.PREFERRED_SIZE, 299, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelSettings.createSequentialGroup()
							.addGap(72)
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panelSettings.setVerticalGroup(
			gl_panelSettings.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSettings.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelSettings.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelSettings.createSequentialGroup()
							.addComponent(datePanel, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 7, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(wepsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 461, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelSettings.createSequentialGroup()
							.addGroup(gl_panelSettings.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel)
								.addComponent(formattedTextFieldMinComDist, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelSettings.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1)
								.addComponent(formattedTextFieldMinSquadSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelSettings.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_2)
								.addComponent(formattedTextFieldMinTimeAlive, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(11, Short.MAX_VALUE))
		);
		
		this.setLayout(gl_panelSettings);
	}
	
	public void updateFields(Settings s) {
		formattedTextFieldMinComDist.setValue(s.getMinComDistance());
		formattedTextFieldMinSquadSize.setValue(s.getMinSquadSize());
		formattedTextFieldMinTimeAlive.setValue(s.getMinPercentTimeAlive());
		wepsPanel.updateFields(s);
		datePanel.updateFields(s);
	}
	
	public void saveFields(Settings s) {
		s.setMinComDistance(Integer.parseInt(formattedTextFieldMinComDist.getText().replaceAll(",","")));
		s.setMinSquadSize(Integer.parseInt(formattedTextFieldMinSquadSize.getText().replaceAll(",","")));
		s.setMinPercentTimeAlive(Integer.parseInt(formattedTextFieldMinTimeAlive.getText().replaceAll(",","")));
		wepsPanel.saveFields(s);
		datePanel.saveFields(s);
	}
}
