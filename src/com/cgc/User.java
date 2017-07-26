package com.cgc;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class User extends JFrame implements ActionListener{
	private ImageIcon background = new ImageIcon(getClass().getResource("user.jpg"));
	private String date_str;
	private JTabbedPane tab;
	private JCheckBox check = new JCheckBox();  //���ø�ѡ��;
	private JTable table,car_table,sole_table;
	private DefaultTableModel sole_model;
	private JButton exit,select_room;
	private JLabel err;
	private String select_month;
	private int select_item;
	private JComboBox<String> getDay;
	private JComboBox<String> getMonth;
	private JComboBox<String> day_number;
	private Date date = new Date();
	private FindRoom find;
	private MySole mySole;
	private String User_Id;
	private MyShoppingcar mycar;
	private String[] sole_header = {"������","�۸�","����ʱ��","��סʱ��","��ס����"};
	private Object[][] sole_data = new Object[200][5];
	//private JLabel room,price;
	
	@SuppressWarnings("serial")
	public User(String User_Id){
		this.User_Id = User_Id;
		Container c = getContentPane();
		setTitle("�𾴵�"+User_Id+"�û�");
		c.setLayout(null);
		JLabel j = new JLabel(background);
		j.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
		getLayeredPane().add(j,new Integer(Integer.MIN_VALUE));
		JPanel panel = (JPanel) getContentPane();
		panel.setOpaque(false);
		c.setLayout(null);
		 //**********************�˳���ť************
        exit = new JButton("�˳�");
        exit.setBounds(900, 10, 50, 20);
        exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new Action();
				setVisible(false);
			}
        	
        });
        c.add(exit);
		JPanel p1 = new JPanel();
		p1.setBounds(10, 10, 200, 50);
		p1.setOpaque(false);		
		date_str = String.format("%tF", date);
		JLabel jl_date = new JLabel("��ǰ���ڣ�"+date_str);
		jl_date.setFont(new Font("΢���ź�",Font.BOLD,18));
		jl_date.setForeground(Color.white);
		p1.add(jl_date);
		c.add(p1);
		//****************����ѡ�����***************************
		tab = new JTabbedPane(2); //����ѡ�
		tab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		table = new JTable();
		table.setRowHeight(30);
		table.getTableHeader().setReorderingAllowed(false);  //���������ƶ�
		table.getTableHeader().setResizingAllowed(false);  //�п�Ȳ��ɸ���
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane js = new JScrollPane(table);
		center(table);
		JPanel ptable = new JPanel();
		ptable.setLayout(null);
		js.setBounds(0, 0, 900, 360);
		ptable.add(js);
		JPanel in = new JPanel();
		in.setLayout(new FlowLayout(1,10,5));
		in.setBounds(0, 360, 900, 40);
		//***********************ѡ����ס���ڰ��**************
		JLabel choose_day = new JLabel("ѡ����ס����");
		in.add(choose_day);
		String month = String.format("%tm", date);
		getMonth = new JComboBox<>(new ChooseMonth(month));
		getMonth.setSelectedIndex(0);
		in.add(getMonth);
		JLabel mon = new JLabel("��");
		in.add(mon);
		String day = String.format("%te", date);
		
		getMonth.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				select_month = getMonth.getSelectedItem().toString();
				select_item = getMonth.getSelectedIndex();
				ChooseDay choose = new ChooseDay(day,select_month,select_item);
				getDay.setModel(choose);	
				getDay.setSelectedIndex(0);
			}
		});
		
		getDay = new JComboBox<>();
		select_month = getMonth.getSelectedItem().toString();
		select_item = getMonth.getSelectedIndex();
		ChooseDay choose = new ChooseDay(day,select_month,select_item);
		getDay.setModel(choose);	
		getDay.setSelectedIndex(0);
		in.add(getDay);	
		JLabel d = new JLabel("��");
		in.add(d);
		JLabel day_num = new JLabel("ѡ����ס����");
		in.add(day_num);
		day_number = new JComboBox<String>();
		day_number.addItem("1"); day_number.addItem("2"); day_number.addItem("3"); day_number.addItem("4"); 
		day_number.addItem("5"); day_number.addItem("6"); day_number.addItem("7");
		in.add(day_number);
		select_room = new JButton("��ѯ���з���");
		select_room.addActionListener(this);
		in.add(select_room);
		JButton sure = new JButton("���빺�ﳵ");
		sure.addActionListener(this);
		in.add(sure);
		ptable.add(in);
		tab.addTab("  ����Ԥ��  ", null,ptable,"����ѡ�����ķ���");
		c.add(tab);
		//*****************��ʼ�����***************
		String year_1 = String.format("%tY", date);
		String month_1 = getMonth.getSelectedItem().toString();
		String day_1 = getDay.getSelectedItem().toString();
		String day_num_1 = day_number.getSelectedItem().toString();
		find = new FindRoom(year_1,month_1,day_1,day_num_1);
		table.setModel(find);
		//table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(check));
		center(table);
		//******************************************************
		
		
		//***********************************************���ﳵ******************************************************
		JPanel Shopping = new JPanel();
		Shopping.setLayout(null);
		mycar = new MyShoppingcar(User_Id);
		car_table = new JTable(mycar);
		center(car_table);
		car_table.setRowHeight(30);
		car_table.getTableHeader().setReorderingAllowed(false);  //���������ƶ�
		car_table.getTableHeader().setResizingAllowed(false);  //�п�Ȳ��ɸ���
		car_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//car_table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(car_check));
		JScrollPane shopping_js = new JScrollPane(car_table);
		shopping_js.setBounds(0, 0, 900, 360);
		err = new JLabel("");
		JButton buy = new JButton("����");
		buy.addActionListener(this);
		JButton car_delete = new JButton("ɾ��");
		car_delete.addActionListener(this);
		JPanel car_in = new JPanel();
		car_in.setBounds(0, 360, 900, 40);
		car_in.setLayout(new FlowLayout(2,20,5));
		car_in.add(err); car_in.add(buy); car_in.add(car_delete);
		Shopping.add(shopping_js);  Shopping.add(car_in);
		tab.addTab(" �ҵĹ��ﳵ ", null,Shopping,"�����鿴�ҵĹ��ﳵ");
		
		
		
		//***************************************************�������**********************************************
		mySole = new MySole(User_Id);
		sole_data = mySole.getdata();
		sole_model = new DefaultTableModel(sole_data,sole_header){
			@Override
			public boolean isCellEditable(int row, int column){  //���ñ�񲻿ɱ༭
				return false;
			}
		};
		int i = 0;
		while(sole_model.getValueAt(i, 0)!=null)
			i++;
		sole_model.setRowCount(i);
		sole_table = new JTable(sole_model);	
		center(sole_table);
		sole_table.setRowHeight(30);
		sole_table.getTableHeader().setReorderingAllowed(false);  //���������ƶ�
		sole_table.getTableHeader().setResizingAllowed(false);  //�п�Ȳ��ɸ���
		sole_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane js_sole = new JScrollPane(sole_table);
		
		tab.addTab("  �ҵĶ���  ", null,js_sole,"�����鿴�ҵĶ���");
		//************************************************
		tab.setBounds(0, 100, 1000, 400);
		
		//****************************************
		
		
		setBounds(200,50,1000,620);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(3);
	}
	//���õ�Ԫ�����
	public void center(JTable table){
		DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
		cr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		for(int i = 0; i<table.getColumnCount()-1;i++){		
			TableColumn tc = table.getColumn(table.getColumnName(i));
			tc.setCellRenderer(cr);
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		GetRoom getRoom = new GetRoom();
		int i;
		String year = String.format("%tY", date);
		if(getMonth.getItemAt(0).toString().compareTo(getMonth.getItemAt(2))>0){    //ѡ������ڿ�������һ��
			if(getMonth.getSelectedItem().toString().compareTo("11")<0){  //ѡ����·�����һ��
				int y = Integer.parseInt(year)+1;     //����+1
				year = String.valueOf(y);
			}
		}
		String month = getMonth.getSelectedItem().toString();
		String day = getDay.getSelectedItem().toString();
		String day_num = day_number.getSelectedItem().toString();
		String live = year+"-"+month+"-"+day;
		switch(arg0.getActionCommand()){
		
		case "���빺�ﳵ":
			i = 0;
			for(i=0; find.getValueAt(i, 0)!=null ;i++){
				Object a = find.getValueAt(i, 5);
				String id = find.getValueAt(i, 0).toString();
				if(a.toString().equals("true")){
					getRoom.addcar(id, User_Id, live, day_num);
					String[] valuse = { find.getValueAt(i, 0).toString(), find.getValueAt(i, 1).toString(), find.getValueAt(i, 2).toString(), find.getValueAt(i, 3).toString(), live };
					int row = mycar.getRowCount()+1;
					mycar.setValueAt(valuse[0], row, 0);
					mycar.setValueAt(valuse[1], row, 1);
					mycar.setValueAt(valuse[2], row, 2);
					mycar.setValueAt(valuse[3], row, 3);
					mycar.setValueAt(valuse[4], row, 4);
					mycar = new MyShoppingcar(User_Id);
					car_table.setModel(mycar);
					center(car_table);
					find.setValueAt(false, i, 5);
				}					
			}
			break;
			
		case "��ѯ���з���":	
				find = new FindRoom(year,month,day,day_num);
				table.setModel(find);
				table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(check));
				center(table);
			break;
		case "ɾ��":
			err.setText("");
			getRoom = new GetRoom();
			i = 0;
			for(i=0; mycar.getValueAt(i, 0)!=null ;i++){
				Object a = mycar.getValueAt(i, 6);
				if(a.toString().equals("true")){
					Object[] val = {mycar.getValueAt(i, 0),User_Id,mycar.getValueAt(i, 4),mycar.getValueAt(i, 5)};
					getRoom.deletecar(val);
					mycar = new MyShoppingcar(User_Id);
					car_table.setModel(mycar);
					center(car_table);
				}					
			}
			break;
		case "����":
			err.setText("");
			getRoom = new GetRoom();		
			i = 0;
			boolean j = false;
			for(i=0; mycar.getValueAt(i, 0)!=null ;i++){
				Object a = mycar.getValueAt(i, 6);
				if(a.toString().equals("true")){
					Object[] val = {mycar.getValueAt(i, 0), User_Id, mycar.getValueAt(i, 4), mycar.getValueAt(i, 5)};
					String soleID = mycar.getValueAt(i, 0).toString()+User_Id+mycar.getValueAt(i, 4).toString()+mycar.getValueAt(i, 5).toString();   //�������	
					String today = String.format("%tF", date);
					String danjia = mycar.getValueAt(i, 3).toString();
					double money = Double.valueOf(danjia)*(int)mycar.getValueAt(i, 5);
					if(getRoom.check(val,soleID,today,money)){
						getRoom.deletecar(val);
						Object[] val2 = {val[0],money,today,val[2],val[3]};					
						sole_model.addRow(val2);						
					}else{
						j = true;
					}				
				}
			}
			if(j){
				err.setText("���ַ�������ѡ���ʱ�����ѱ�Ԥ������ɾ����Ԥ����������ѡ��");;
			}
			mycar = new MyShoppingcar(User_Id);
			car_table.setModel(mycar);
			center(car_table);
			break;
		default: break;
		}
	}
}
