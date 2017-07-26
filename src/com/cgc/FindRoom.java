package com.cgc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

public class FindRoom extends AbstractTableModel{
	String[] head = {"������","����","λ��","�۸�/��","״̬","ѡ��"};
	@SuppressWarnings("rawtypes")
	Class[] typeArray = { Object.class, Object.class, Object.class, Object.class, Object.class , Boolean.class};
	Object[][] dataAll = new Object[200][6];
	Object[][] data = new Object[200][6];
	Object[][] Sole = new Object[1000][3];
	public FindRoom(String year,String month,String day,String day_num){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );		
		try {
			long new_date = sdf.parse(year+"-"+month+"-"+day).getTime();   //�ó�ѡ������ڸ�ʽ
			GetRoom room = new GetRoom();
			data = room.FreeRoom();
			Sole = room.getSole();
			int i = 0;			
			int k = 0;
			int number = Integer.parseInt(day_num);
			while(data[i][0]!=null){
				Boolean can = true;
				int j = 0;
				while(Sole[j][0]!=null){
					//System.out.println(Sole[j][0].toString());
					if(data[i][0].toString().equals(Sole[j][0].toString())){
						String old_d = Sole[j][1].toString();
						long old_date = sdf.parse(old_d).getTime();
						int old_day_num = (int) Sole[j][2];
						long two_day = (new_date - old_date) / (1000 * 60 * 60 * 24);	//���������ڵĲ�ֵ
						if((two_day>=0&&two_day<old_day_num)||(two_day<=0&&two_day>(-1*number))){      //������ѡ��������δ��Ԥ��
							can = false;
							break;
						}	
					}
					j++;					
				}	
				if(can){
					dataAll[k] = data[i];
					k++;
				}
				i++;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("���ڸ�ʽ����");
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
        if(columnIndex==5)	
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
}
