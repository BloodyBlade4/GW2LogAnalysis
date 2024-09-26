package gui_Components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import settings.SettingsBuff;

public class BuffWindow {
	JFrame frame = new JFrame();
	private Map<String, DataModel> dataModelMap = new HashMap<String, DataModel>();
	private DataModel dataModel;
	private static final String[] tableChecksColumns = {"Categories", "Active"};
	private JButton btnNewButton;
	private JTabbedPane tabbedPane;
	private JButton btnNewButton_4;
	private JCheckBox chckbxPhaseDuration;
	private JCheckBox chckbxActivePhaseDuration;
	private JCheckBox chckbxUptime;
	private JCheckBox chckbxGenerationSelf;
	private JCheckBox chckbxGenerationGroup;
	private JCheckBox chckbxGenerationSquad;
	private SettingsBuff curBuff;
	private JPanel panel;
	
	public void setWindowVisible() {
		frame.setVisible(true);
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public BuffWindow(SettingsBuff buff) {
		frame.setBounds(100, 100, 555, 447);
		this.curBuff = buff;

		btnNewButton = new JButton("(un)Select All");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					dataModel.toggleAll();
			}
		});
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		panel = new JPanel();
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnNewButton)
							.addGap(268))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(22)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 499, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
					.addGap(34)
					.addComponent(btnNewButton)
					.addContainerGap())
		);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		constructPhasesWindow();
		
		
		dataModel = generateTabbedPane(buff);
		setAllCheckBox(buff);
		
		frame.getContentPane().setLayout(groupLayout);
		
		//Save all fields upon exiting the window. 
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	    		buff.updateActiveByNames(dataModel.getSelectedValues());
		    }
		});
		
	}
	
	public BuffWindow(List<SettingsBuff> buffs) {
		frame.setBounds(100, 100, 576, 447);

		btnNewButton = new JButton("(un)Select All");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (DataModel dm : dataModelMap.values())
					dm.toggleAll();
			}
		});
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		btnNewButton_4 = new JButton("(un)Select Current Tab");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    int index = tabbedPane.getSelectedIndex();
			    dataModelMap.get(buffs.get(index).getDisplayName()).toggleAll();
			}
		});
		
		panel = new JPanel();
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnNewButton)
							.addGap(29)
							.addComponent(btnNewButton_4, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
							.addGap(239))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(22)
							.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(22)
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
					.addGap(34)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_4))
					.addContainerGap())
		);
		constructPhasesWindow();

    	for(SettingsBuff buff : buffs) {
    		dataModelMap.put(buff.getDisplayName(), generateTabbedPane(buff));
    	}
    	curBuff= buffs.get(0);
    	setAllCheckBox(curBuff);
    	
		frame.getContentPane().setLayout(groupLayout);
		
		//Save all fields upon exiting the window. 
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	for(SettingsBuff buff : buffs) {
		    		buff.updateActiveByNames(dataModelMap.get(buff.getDisplayName()).getSelectedValues());
		    	}
		    }
		});
		
		//Updates the phases checkbox's according to the selected categories settings. 
		ChangeListener changeListener = new ChangeListener() {
		  public void stateChanged(ChangeEvent changeEvent) {
			savePhases();
			JTabbedPane selectedTab = (JTabbedPane) changeEvent.getSource();
		    int index = selectedTab.getSelectedIndex();
		    
		    curBuff= buffs.get(index);
		    setAllCheckBox(curBuff);
		    
		  }
		};
		tabbedPane.addChangeListener(changeListener);
	}
	
	//Creates the gui element that holds the "phases" check marks. 
	private void constructPhasesWindow() {
		JPanel panel_top = new JPanel();
		panel_top.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		chckbxPhaseDuration = new JCheckBox("Phase Duration");
		panel_top.add(chckbxPhaseDuration);
		
		chckbxActivePhaseDuration = new JCheckBox("Active Phase Duration");
		panel_top.add(chckbxActivePhaseDuration);
		chckbxActivePhaseDuration.setToolTipText("Removed dead time and dc time.");
		
		JPanel panel_bottom = new JPanel();
		panel_bottom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		chckbxUptime = new JCheckBox("Uptime");
		panel_bottom.add(chckbxUptime);
		
		chckbxGenerationSelf = new JCheckBox("Generation Self");
		panel_bottom.add(chckbxGenerationSelf);
		
		chckbxGenerationGroup = new JCheckBox("Generation Group");
		panel_bottom.add(chckbxGenerationGroup);
		chckbxGenerationGroup.setToolTipText("Self excluded.");
		
		chckbxGenerationSquad = new JCheckBox("Generation Squad");
		panel_bottom.add(chckbxGenerationSquad);
		chckbxGenerationSquad.setToolTipText("Self excluded.");
		
		panel.add(panel_top);
		panel.add(panel_bottom);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	}
	
	
	public SettingsBuff getwindowBuff() {
		return this.curBuff;
	}
	
	private void setAllCheckBox(SettingsBuff buff) {
		if (buff.getPhaseDuration() == null)
	    	chckbxPhaseDuration.setVisible(false);
	    else {
	    	chckbxPhaseDuration.setVisible(true); 
	    	chckbxPhaseDuration.setSelected(buff.getPhaseDuration());
	    }
	    
	    if (buff.getPhaseActiveDuration() == null)
	    	chckbxActivePhaseDuration.setVisible(false);
	    else {
	    	chckbxActivePhaseDuration.setVisible(true); 
	    	chckbxActivePhaseDuration.setSelected(buff.getPhaseActiveDuration());
	    }
	    
	    if (buff.getUptime() == null)
	    	chckbxUptime.setVisible(false);
	    else {
	    	chckbxUptime.setVisible(true); 
	    	chckbxUptime.setSelected(buff.getUptime());
	    }
	    
	    if (buff.getGenerationSelf() == null)
	    	chckbxGenerationSelf.setVisible(false);
	    else {
	    	chckbxGenerationSelf.setVisible(true); 
	    	chckbxGenerationSelf.setSelected(buff.getGenerationSelf());
	    }
	    
	    if (buff.getGenerationGroup() == null)
	    	chckbxGenerationGroup.setVisible(false);
	    else {
	    	chckbxGenerationGroup.setVisible(true); 
	    	chckbxGenerationGroup.setSelected(buff.getGenerationGroup());
	    }
	    
	    if (buff.getGenerationSquad() == null)
	    	chckbxGenerationSquad.setVisible(false);
	    else {
	    	chckbxGenerationSquad.setVisible(true); 
	    	chckbxGenerationSquad.setSelected(buff.getGenerationSquad());
	    }
	}
	
	private DataModel generateTabbedPane(SettingsBuff buff) {
		JPanel pan = new JPanel();
		tabbedPane.addTab(buff.getDisplayName(), null, pan, null);
		
		DataModel dm = new DataModel(buff.retrieveBuffNamesList(), tableChecksColumns);
		JTable table = new JTable(dm);
		JScrollPane catScrollPane = new JScrollPane();
		
		GroupLayout gl = new GroupLayout(pan);
		gl.setHorizontalGroup(
			gl.createParallelGroup(Alignment.LEADING)
				.addComponent(catScrollPane, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		gl.setVerticalGroup(
			gl.createParallelGroup(Alignment.LEADING)
				.addComponent(catScrollPane, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		gl.setAutoCreateContainerGaps(true);
		gl.setAutoCreateGaps(true);
		pan.setLayout(gl);
		
		catScrollPane.setLayout(new ScrollPaneLayout());
		catScrollPane.setViewportView(table);
		catScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		catScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		catScrollPane.setSize(new Dimension(25,25));
		pan.setSize(new Dimension(25,25));
		
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSize(new Dimension(25,25));
		
		
		dm.toggleGivenValues(buff.retrieveActiveBuffNamesList());
		return dm;
	}
	
	private void savePhases() {
		if (curBuff == null)
			return;
		if(curBuff.getPhaseDuration() != null)
			curBuff.setPhaseDuration(chckbxPhaseDuration.isSelected());
		if(curBuff.getPhaseActiveDuration() != null)
			curBuff.setPhaseActiveDuration(chckbxActivePhaseDuration.isSelected());
		if(curBuff.getUptime() != null)
			curBuff.setUptime(chckbxUptime.isSelected());
		if(curBuff.getGenerationSelf() != null)
			curBuff.setGenerationSelf(chckbxGenerationSelf.isSelected());
		if(curBuff.getGenerationGroup() != null)
			curBuff.setGenerationGroup(chckbxGenerationGroup.isSelected());
		if(curBuff.getGenerationSquad() != null)
			curBuff.setGenerationSquad(chckbxGenerationSquad.isSelected());
	}
}
