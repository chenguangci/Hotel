package com.cgc;

import javax.swing.table.AbstractTableModel;

public class MyShoppingcar extends AbstractTableModel{
	String[] head = {"房间编号","类型","位置","价格/天","入住时间","入住天数","选择"};
	Class[] typeArray = { Object.class, Object.class, Object.class, Object.class, Object.class ,Object.class , Boolean.class};
	private Object[][] dataAll = new Object[200][8];
	private Object[][] data = new Object[200][8];
	private Object[][] Sole = new Object[1000][3];
	public MyShoppingcar(String ID){
		GetRoom getRoom = new GetRoom();
		data = getRoom.GetShoppingcar();
		int i = 0;
		int j = 0;
		while(data[i][0]!=null){
			if(data[i][7].toString().equals(ID)){
				dataAll[j] = data[i];
				j++;
			}
			i++;
		}
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return head.length;
	}

	@Override
	public int getRowCount() {            
		// TODO Auto-generated method stub
		int i = 0;
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
        return head[column];  
    } 
	@Override  
    public boolean isCellEditable(int rowIndex, int columnIndex) {  
        if(columnIndex==6)	
        	return true;
        else
        	return false;
    } 
	// 替换单元格的值  
    @Override  
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {  
        dataAll[rowIndex][columnIndex] = aValue;  
        fireTableCellUpdated(rowIndex, columnIndex);  
    } 
	public Class getColumnClass(int columnIndex) {  
        return typeArray[columnIndex];// 返回每一列的数据类型  
    }
	public Object[][] getdata(){
		return dataAll;
	}
}
