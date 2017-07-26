package com.cgc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class ExitSole extends AbstractTableModel{
	String[] head = {"�������","�û��˺�","������","�µ�ʱ��","�۸�","��סʱ��","��ס����","ѡ��"};
	@SuppressWarnings("rawtypes")
	Class[] typeArray = { Object.class, Object.class, Object.class, Object.class, Object.class ,Object.class ,Object.class , Boolean.class};
	private Object[][] dataAll = new Object[200][8];
	static Connection con;  //�������ݿ�
	static Statement sql;  //���ڷ���SQL���
	static ResultSet res;   
	public ExitSole(){
		Conn c = new Conn();
		con = c.getConnection();
		int i=0;
		try {
			sql  =con.createStatement();
			res = sql.executeQuery("select * from Sole order by customerId");		
			while(res.next()){
				Object[] Sol = {res.getString("soleID"),res.getString("customerId"),res.getString("roomID"),res.getString("soledate"),res.getString("price"),res.getString("live"),res.getInt("Days"),false};
				dataAll[i] = Sol;
				i++;
			}
		} catch (SQLException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
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
        if(columnIndex==7)	
        	return true;
        else
        	return false;
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
	public Object[][] getdata(){
		return dataAll;
	}
	public void delete(String id){
		Conn conn = new Conn();
		con = conn.getConnection();
		try {
			sql = con.createStatement();
			sql.executeUpdate("delete from Sole where soleID = '"+id+"'");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}finally{
			try {
				if(con!=null){
					con.close();
					con = null;
				}
				if(sql!=null){
					sql.close();
					sql = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}

