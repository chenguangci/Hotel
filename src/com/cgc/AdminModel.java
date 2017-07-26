package com.cgc;

import javax.swing.table.AbstractTableModel;

public class AdminModel extends AbstractTableModel{
	String[] header = {"������","�۸�","λ��","����","״̬"};
	Class[] typeArray = { Object.class, Object.class, Object.class, Object.class,Object.class};
	Object[][] dataAll = new Object[60][6];
	public AdminModel(){
		GetRoomadmin admin = new GetRoomadmin();
		dataAll = admin.getdata();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return header.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		int i;
		for(i=0;;i++){
			if(dataAll[i][0]==null)
				break;
		}
		return i;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return dataAll[arg0][arg1];
	}
	@Override  
    public String getColumnName(int column) {  
        return header[column];  
    } 
	// �滻��Ԫ���ֵ  
    @Override  
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {  
        dataAll[rowIndex][columnIndex] = aValue;  
        fireTableCellUpdated(rowIndex, columnIndex);  
    } 
	public Class getColumnClass(int columnIndex) {  
        return typeArray[columnIndex];// ����ÿһ�е���������  
    } 
}
