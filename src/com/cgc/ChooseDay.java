package com.cgc;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class ChooseDay extends AbstractListModel<String>implements ComboBoxModel<String> {
	String selectItem = null;
	String[] day_all = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	String[] day = new String[31];
	public ChooseDay(String day1,String select_month,int select_item){
		int m = Integer.parseInt(select_month);
		m = month_day(m);
		int d = Integer.parseInt(day1)-1;
		if(select_item==0){          
			m = m-d;
			for(int i=0;i<m;i++){
				day[i] = day_all[d];
				d++;
			}
		}else if(select_item==1){
			for(int i = 0; i<m; i++){
				day[i] = day_all[i];
			}
		}else{
			for(int i = 0; i<=d; i++){
				day[i] = day_all[i];
				if(i==m-1)
					break;
			}
		}
	}
	@Override
	public String getElementAt(int index) {
		// TODO Auto-generated method stub
		return day[index];
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		int i = 0;
		while(day[i]!=null){
			i++;
			if(i==31)
				break;
		}
		return i;
	}

	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return selectItem;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		// TODO Auto-generated method stub
		selectItem = (String)anItem;
	}
	public int month_day(int m){
		if(m==1||m==3||m==5||m==7||m==8||m==10||m==12){
			return 31;
		}else if(m==2){
			return 28;
		}else{
			return 30;
		}
	}
}
