package com.cgc;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class ChooseMonth extends AbstractListModel<String>implements ComboBoxModel<String> {
	String selectItem = null;
	String[] month = {"01","02","03","04","05","06","07","08","09","10","11","12"};
	String[] month2 = new String[3];
	String month1;
	public ChooseMonth(String month1){
		this.month1 = month1;
		int m = Integer.parseInt(month1)-1;
		for(int i =0; i<3; i++){
			month2[i] = month[m];
			m = (m+1)%12;
		}
	}
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return month2.length;
	}

	@Override
	public String getElementAt(int index) {
		// TODO Auto-generated method stub
		return month2[index];
	}

	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return selectItem;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		// TODO Auto-generated method stub
		selectItem = (String) anItem;
	}

}
