package gui_Components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class DataModel extends DefaultTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int CHECK_COL = 1;
	//original, one check mark active code is from https://stackoverflow.com/questions/7920068/using-setvalueat-to-recreate-mutually-exclusive-check-boxes/7920159#7920159
	private Boolean toggleState = false;
	
    public DataModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }
    public DataModel(List<String> list, Object[] columnNames) {
    	super(checkTableObject(list), columnNames);
    }
    
    public void toggleAll() {
    	for (int r = 0; r < getRowCount(); r++) {
    		super.setValueAt(!toggleState, r, CHECK_COL);
    	}
    	toggleState = !toggleState;
    }
    
    public List<String> getSelectedValues() {
    	List<String> list = new ArrayList<String>();
    	for (int r = 0; r < getRowCount(); r++) {
    		if ((Boolean)getValueAt(r, CHECK_COL))
    			list.add((String.valueOf(getValueAt(r,0))));
    	}
    	return list;
    }
    
	public static Object[][] checkTableObject(List<String> list) {
		Object[][] obj = new Object[list.size()][list.size()];
		for (int i = 0; i < list.size(); i++) {
			obj[i] = new Object[] {list.get(i), Boolean.FALSE};
		}
		return obj;
	}
    
    public void toggleGivenValues(List<String> list) {
    	if (list == null)
    		return;
    	for (int r = 0; r < getRowCount(); r++) {
    		if (list.contains(String.valueOf(getValueAt(r,0)))) 
    			super.setValueAt(true, r, CHECK_COL);
    		else
    			super.setValueAt(false, r, CHECK_COL);
    	}
    }
    
    //Is getColumnClass necessary? 
    @Override
    public Class<?> getColumnClass(int col) {
        if (col == CHECK_COL) {
            return getValueAt(0, CHECK_COL).getClass();
        }
        return super.getColumnClass(col);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == CHECK_COL;
    }
}
