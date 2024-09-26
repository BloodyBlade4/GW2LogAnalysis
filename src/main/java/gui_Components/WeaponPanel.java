package gui_Components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import components.Constants;
import settings.Settings;

/*
 * A panel containing the weapon selection filtering options. 
 * All you need to do to make this function is call updateFields(Settings s) and saveFields(Settings s) . 
 */
public class WeaponPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JComboBox<String> comboBoxWepSet1MainHand;
	private static JComboBox<String> comboBoxWepSet1Offhand;
	private static JComboBox<String> comboBoxWepSet2MainHand;
	private static JComboBox<String> comboBoxWepSet2Offhand;
	private static JCheckBox chckbxWepCheckStrict;
	
	public WeaponPanel() {
		this.setVisible(true);
		this.setBounds(100, 100, 267, 176);
		
		
		JLabel lblNewLabel_11 = new JLabel("Weapons");
		
		chckbxWepCheckStrict = new JCheckBox("Strict Wep Filter");
		chckbxWepCheckStrict.setToolTipText("<html>Ignore any file that has \"Unknown\" in your weapon field. <br>ArcDPS can sometimes not register a weapon for various reasons, such as never swapping weapons.</html>");
		JLabel lblNewLabel_7 = new JLabel("MainHand");
		comboBoxWepSet1MainHand = new JComboBox<String>(new DefaultComboBoxModel<String>(Constants.WEAPONS.toArray(String[]::new)));
		JLabel lblNewLabel_9 = new JLabel("MainHand");
		
		comboBoxWepSet2MainHand = new JComboBox<String>(new DefaultComboBoxModel<String>(Constants.WEAPONS.toArray(String[]::new)));
		JLabel lblNewLabel_8 = new JLabel("Offhand");
		comboBoxWepSet1Offhand = new JComboBox<String>(new DefaultComboBoxModel<String>(Constants.WEAPONS.toArray(String[]::new)));
		JLabel lblNewLabel_10 = new JLabel("Offhand");
		comboBoxWepSet2Offhand = new JComboBox<String>(new DefaultComboBoxModel<String>(Constants.WEAPONS.toArray(String[]::new)));
		
		JButton btnSwapWeaponSets = new JButton("Swap Weapon Sets");
		btnSwapWeaponSets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = String.valueOf(comboBoxWepSet1MainHand.getSelectedItem());
				comboBoxWepSet1MainHand.setSelectedItem(String.valueOf(comboBoxWepSet2MainHand.getSelectedItem()));
				comboBoxWepSet2MainHand.setSelectedItem(temp);
				
				temp = String.valueOf(comboBoxWepSet1Offhand.getSelectedItem());
				comboBoxWepSet1Offhand.setSelectedItem(comboBoxWepSet2Offhand.getSelectedItem());
				comboBoxWepSet2Offhand.setSelectedItem(temp);
				
			}
		});
		btnSwapWeaponSets.setToolTipText("Swap weapon set selections. The order of the sets does matter.");
		GroupLayout gl_weaponPanel = new GroupLayout(this);
		gl_weaponPanel.setHorizontalGroup(
			gl_weaponPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_weaponPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_weaponPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_weaponPanel.createSequentialGroup()
							.addGap(70)
							.addComponent(btnSwapWeaponSets))
						.addGroup(gl_weaponPanel.createSequentialGroup()
							.addGroup(gl_weaponPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_7)
								.addComponent(lblNewLabel_9)
								.addGroup(gl_weaponPanel.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(comboBoxWepSet1MainHand, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(comboBoxWepSet2MainHand, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_weaponPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_8)
								.addComponent(lblNewLabel_10)
								.addGroup(gl_weaponPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(comboBoxWepSet2Offhand, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
									.addComponent(comboBoxWepSet1Offhand, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
									.addComponent(chckbxWepCheckStrict, Alignment.TRAILING))))
						.addComponent(lblNewLabel_11))
					.addContainerGap(36, Short.MAX_VALUE))
		);
		gl_weaponPanel.setVerticalGroup(
			gl_weaponPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_weaponPanel.createSequentialGroup()
					.addGroup(gl_weaponPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxWepCheckStrict)
						.addComponent(lblNewLabel_11))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_weaponPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_weaponPanel.createSequentialGroup()
							.addComponent(lblNewLabel_7)
							.addGap(6)
							.addComponent(comboBoxWepSet1MainHand, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblNewLabel_9)
							.addGap(6)
							.addComponent(comboBoxWepSet2MainHand, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_weaponPanel.createSequentialGroup()
							.addComponent(lblNewLabel_8)
							.addGap(6)
							.addComponent(comboBoxWepSet1Offhand, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblNewLabel_10)
							.addGap(6)
							.addComponent(comboBoxWepSet2Offhand, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(15)
					.addComponent(btnSwapWeaponSets)
					.addContainerGap(23, Short.MAX_VALUE))
		);
		this.setLayout(gl_weaponPanel);
	}
	
	public void updateFields(Settings s) {
		comboBoxWepSet1MainHand.setSelectedItem(s.getWepSet1MainHand());
		comboBoxWepSet2Offhand.setSelectedItem(s.getWepSet2Offhand());
		comboBoxWepSet2MainHand.setSelectedItem(s.getWepSet2MainHand());
		comboBoxWepSet2Offhand.setSelectedItem(s.getWepSet2Offhand());
		chckbxWepCheckStrict.setSelected(s.getWepFilterStrict());
	}
	
	public void saveFields(Settings s) {
		s.setWepSet1MainHand(String.valueOf(comboBoxWepSet1MainHand.getSelectedItem()));
		s.setWepSet1Offhand(String.valueOf(comboBoxWepSet1Offhand.getSelectedItem()));
		s.setWepSet2MainHand(String.valueOf(comboBoxWepSet2MainHand.getSelectedItem()));
		s.setWepSet2Offhand(String.valueOf(comboBoxWepSet2Offhand.getSelectedItem()));
		s.setWepFilterStrict(chckbxWepCheckStrict.isSelected());
	}
}
