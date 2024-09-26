package gui_Components;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.lgooddatepicker.components.DatePicker;

import settings.Settings;

public class DatePanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private static JCheckBox chckbxDatesByLog;
	private DatePicker pickerFromDate;
	private DatePicker pickerToDate;
	
	
	public DatePanel() {
		this.setVisible(true);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel panel_2 = new JPanel();
		add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Date Filters");
		lblNewLabel.setBounds(10, 5, 106, 14);
		panel_2.add(lblNewLabel);
		
		
		chckbxDatesByLog = new JCheckBox("Dates by log (Not rec.)");
		chckbxDatesByLog.setToolTipText("<html>(Not recommended) <br>By default dates are checked via the \"last modified\" timestamp on your PC. <br>"
				+ "By checking this option, date will be checked inside of each log.<br>"
				+ "This can significaly slow performance, depending on use-case, but can be useful if you've modified those files after generation.</html>");
		chckbxDatesByLog.setBounds(97, 1, 150, 18);
		panel_2.add(chckbxDatesByLog);
		
		//From/Start date.
		JPanel panel = new JPanel();
		add(panel);
		
		JLabel lblNewLabel_2 = new JLabel("Start Date:");
		lblNewLabel_2.setToolTipText("Parse log on or after this date.");
		panel.add(lblNewLabel_2);
		
		pickerFromDate = new DatePicker();
		pickerFromDate.setBounds(100, 100, 100,100);
		panel.add(pickerFromDate);
		
		//To date
		JPanel panel_1 = new JPanel();
		add(panel_1);
		
		JLabel lblNewLabel_1 = new JLabel("End Date:");
		lblNewLabel_1.setToolTipText("Parse logs on or before this date.");
		panel_1.add(lblNewLabel_1);
		
		pickerToDate = new DatePicker();
		pickerToDate.setBounds(100, 100, 100,100);
		panel_1.add(pickerToDate);
	}
	
	public void updateFields(Settings s) {
		chckbxDatesByLog.setSelected(s.getDatesByLog());
		
		pickerFromDate.setDate(s.getFromDate());
		pickerToDate.setDate(s.getToDate());
	}
	
	public void saveFields(Settings s) {
		s.setDatesByLog(chckbxDatesByLog.isSelected());
		s.setFromDate(pickerFromDate.getDate());
		s.setToDate(pickerToDate.getDate());
	}
}
