package com.cgc;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Admin extends JFrame implements ActionListener,FocusListener{
	private Object[][] dataAll = new Object[200][6];
	private Object[][] dataAll_customer = new Object[200][6];
	private JTextField rid,rprice,raddress;
	private JComboBox<String> rtype,rstate;
	private JTable table,table_customer,table_sole;
	private JLabel err,err2;
	private DefaultTableModel dmodel,model_customer;
	private String[] header = {"������","�۸�","λ��","����"};
	private String[] header_customer = {"�û����","�û����루�Ѽ��ܣ�","�û�����","�û�����","��ϵ��ʽ"};
	private String id_room,id_customer;
	private JButton exit;
	private int room_row = 0,customer_row = 0;
	private ExitSole exitSole;
	@SuppressWarnings("serial")
	public Admin(){
		//*******************************
		setTitle("����Ա����");
		Container c = getContentPane();
		JPanel p1 = new JPanel();
		p1.setLayout(null);
		 //**********************�˳���ť************
        exit = new JButton("�˳�");
        exit.setBounds(700, 0, 50, 20);
        exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new Action();
				setVisible(false);
			}
        	
        });
        c.add(exit);
		JTabbedPane tab = new JTabbedPane();
		//*********************������Ϣ**********************
		GetRoomadmin admin = new GetRoomadmin();
		dataAll = admin.getdata();
		dmodel = new DefaultTableModel(dataAll,header){
			@Override
			public boolean isCellEditable(int row, int column){  //���ñ�񲻿ɱ༭
				return false;
			}
		};
		while(dmodel.getValueAt(room_row, 0)!=null){        //��ȡ������
			room_row++;
		}
		dmodel.setRowCount(room_row);
		table = new JTable(dmodel);	
		center(table);
		table.setRowHeight(40);	
		table.getTableHeader().setReorderingAllowed(false);  //���������ƶ�
		table.getTableHeader().setResizingAllowed(false);  //�п�Ȳ��ɸ���
		JScrollPane js = new JScrollPane(table);
		js.setBounds(0, 0, 770, 400);
		p1.add(js);
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(1,30,10));
		JLabel id = new JLabel("������");  rid = new JTextField("",5);
		rid.addFocusListener(this);
		JLabel price = new JLabel("�۸�"); rprice = new JTextField("",5);
		JLabel address = new JLabel("����λ�ã�"); raddress = new JTextField("",10);
		JLabel type = new JLabel("�������ͣ�");  rtype = new JComboBox<String>(); 
		rtype.addItem("���˼�"); rtype.addItem("˫�˼�"); rtype.addItem("˫�׼�"); rtype.addItem("������");rtype.setSelectedItem(null);
		JLabel state = new JLabel("����״̬��"); rstate = new JComboBox<String>();
		rstate.addItem("N"); rstate.addItem("Y");rstate.setSelectedItem(null);
		p2.add(id);p2.add(rid);p2.add(price);p2.add(rprice);p2.add(address);p2.add(raddress);p2.add(type);p2.add(rtype);//p2.add(state);p2.add(rstate);
		JButton add = new JButton("��ӷ���"); p2.add(add); add.addActionListener(this);
		JButton change = new JButton("�޸ķ���"); p2.add(change); change.addActionListener(this);
		JButton delete = new JButton("ɾ������"); p2.add(delete); delete.addActionListener(this);
		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(1,400,0));
		err = new JLabel("");  err.setForeground(Color.red);
		p3.add(err);
		p2.add(p3);
		p2.setBounds(0, 400, 770, 100);
		p1.add(p2);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int row = table.getSelectedRow();
				if(dmodel.getValueAt(row, 0)!=null){
					Object oid = dmodel.getValueAt(row, 0);
					rid.setText(oid.toString());
					id_room = oid.toString();  //����ѡ�б��ĳ��ʱ�ķ����
					Object opr = dmodel.getValueAt(row, 1);
					rprice.setText(opr.toString());
					Object oadd = dmodel.getValueAt(row, 2);
					raddress.setText(oadd.toString());
					rtype.setSelectedItem(null);
					rstate.setSelectedItem(null);
				}
			}
		});
		tab.addTab("������Ϣ", null,p1,"�޸ķ�����Ϣ");
		//*****************************************************
		//****************�û���Ϣ****************************
		GetCustomer getCustomer = new GetCustomer();
		dataAll_customer = getCustomer.getdataAll();
		model_customer = new DefaultTableModel(dataAll_customer,header_customer){
			@Override
			public boolean isCellEditable(int row, int column){  //���ñ�񲻿ɱ༭
				return false;
			}
		};
		while(model_customer.getValueAt(customer_row, 0)!=null){
			customer_row++;
		}
		model_customer.setRowCount(customer_row);
		table_customer = new JTable(model_customer);
		center(table_customer);
		table_customer.setRowHeight(40);
		table_customer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane js2 = new JScrollPane(table_customer);
		js2.setBounds(0, 0, 770, 400);
		JPanel panel_edit = new JPanel();
		panel_edit.setBounds(0, 400, 770, 100);
		panel_edit.setLayout(new FlowLayout(1,10,10));
		JButton add_customer = new JButton("����û�");  
		add_customer.addActionListener(this);
		panel_edit.add(add_customer);
		JButton change_customer = new JButton("�޸��û�");  
		change_customer.addActionListener(this);
		panel_edit.add(change_customer);
		JButton delete_customer = new JButton("ɾ���û�");  
		delete_customer.addActionListener(this);
		panel_edit.add(delete_customer);
		err2 = new JLabel(" ");  err2.setForeground(Color.red);
		JPanel perr = new JPanel();
		perr.setLayout(new FlowLayout(1,400,0));
		perr.add(err2);
		panel_edit.add(perr);
		JPanel p_customer = new JPanel();
		p_customer.setLayout(null);
		p_customer.add(js2);
		p_customer.add(panel_edit);
		tab.addTab("�û���Ϣ", null,p_customer,"�޸��û���Ϣ");
		//*****************************************************
		//********************************�����༭*********************
		JPanel Sole = new JPanel();	
		Sole.setLayout(null);
		exitSole = new ExitSole();
		table_sole = new JTable(exitSole);
		center(table_sole);
		table_sole.setRowHeight(30);
		table_sole.getTableHeader().setReorderingAllowed(false);  //���������ƶ�
		table_sole.getTableHeader().setResizingAllowed(false);  //�п�Ȳ��ɸ���
		table_sole.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane js_sole = new JScrollPane(table_sole);
		js_sole.setBounds(0, 0, 770, 450);
		JButton delete_sole = new JButton("ɾ������");
		delete_sole.addActionListener(this);
		JPanel panel_sole = new JPanel();
		panel_sole.setLayout(new FlowLayout(2,40,10));
		panel_sole.setBounds(0, 450, 770, 50);
		panel_sole.add(delete_sole);
		Sole.add(js_sole);  Sole.add(panel_sole);
		tab.addTab("��������", null,Sole,"�����༭");
		c.add(tab);
		setBounds(200,50,785,580);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(3);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int select = table.getSelectedRow();
		String[] values = {rid.getText(),rprice.getText(),raddress.getText(),(String) rtype.getSelectedItem(),"N"};
		GetRoomadmin getroom = new GetRoomadmin();
		int select2 = table_customer.getSelectedRow();
		String[] valuse2 = null;
		if(select2!=-1){
			id_customer = table_customer.getValueAt(select2, 0).toString();
			valuse2 = new String[]{table_customer.getValueAt(select2, 0).toString(), table_customer.getValueAt(select2, 2).toString(), table_customer.getValueAt(select2, 3).toString(), table_customer.getValueAt(select2, 4).toString()};
		}
		GetCustomer getCustomer = new GetCustomer();
		switch(e.getActionCommand()){
		case "��ӷ���":
			err.setText("");
			if(!getroom.add(values)){
				err.setText("���ʧ�ܣ������Ƿ������ظ���ȱ�ٱ�Ҫ��Ϣ������������");
			}else{
				dmodel.addRow(values);
			}			
			break;
		case "�޸ķ���":
			err.setText("");
			if(select!=-1&&values[0]!=null){
				if(!getroom.update(values, id_room)){
					err.setText("�޸���Ϣ�����������޸�");
				}else{
					dmodel.setValueAt(values[0], select, 0);
					dmodel.setValueAt(values[1], select, 1);
					dmodel.setValueAt(values[2], select, 2);
					dmodel.setValueAt(values[3], select, 3);
				}
			}
			break;
		case "ɾ������":
			if(select!=-1){
				dmodel.removeRow(select);
				getroom.delete(values[0].toString());
			}
			break;
		case "����û�":
			err2.setText("");
			new Information_Customer(this,-1);
			break;
		case "�޸��û�":
			err2.setText("");
			if(select2!=-1){			
				Information_Customer in = new Information_Customer(this,select2);
				in.getValuse(valuse2);
			}
			break;
		case "ɾ���û�":
			if(select2!=-1){
				String value = model_customer.getValueAt(select2, 0).toString();
				getCustomer.delete_customer(value);
				model_customer.removeRow(select2);
			}
			break;
		case "ɾ������":
			int i = 0;
			for(i=0;exitSole.getValueAt(i, 0)!=null;i++){
				if((boolean) exitSole.getValueAt(i, 7)){
					String soleID = exitSole.getValueAt(i, 0).toString();
					exitSole.delete(soleID);
				}
			}
			exitSole = new ExitSole();
			table_sole.setModel(exitSole);
			center(table_sole);
			break;
		default:break;	
		}
		rid.setText(""); 
		rprice.setText("");
		raddress.setText("");
		rtype.setSelectedItem(null);
		rstate.setSelectedItem(null);
	}
	public void getInformation(String[] information){
		GetCustomer getCustomer = new GetCustomer();
		if(!getCustomer.addCustomer(information)){
			err2.setText("����û�ʧ�ܣ����������");
		}else{
			model_customer.addRow(information);
		}
	}
	public void changeInformation(String[] information,int row){
		GetCustomer getCustomer = new GetCustomer();
		if(!getCustomer.update_customer(information, id_customer)){

			err2.setText("�޸��û���Ϣʧ�ܣ��������޸�");
		}else{
			model_customer.setValueAt(information[0], row, 0);
			model_customer.setValueAt(information[1], row, 1);
			model_customer.setValueAt(information[2], row, 2);
			model_customer.setValueAt(information[3], row, 3);
			model_customer.setValueAt(information[4], row, 4);
		}
	}
	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		err.setText("");
	}
	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void center(JTable table){
		DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
		cr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		for(int i = 0; i<table.getColumnCount()-1;i++){		
			TableColumn tc = table.getColumn(table.getColumnName(i));
			tc.setCellRenderer(cr);
		}
	}
}
@SuppressWarnings("serial")
class Information_Customer extends JFrame{
	private String[] information;
	private String pass;
	private JLabel id,password,password2,type,name,tel;
	private JTextField id_jtf,name_jtf,tel_jtf;
	private JPasswordField password_jtf,password_jtf2;
	private JComboBox<String> type_box;
	private JButton sure;
	public Information_Customer(Admin me,int choose){
		Container c = getContentPane();
		c.setLayout(new FlowLayout(1,10,10));
		id = new JLabel("�û��˺ţ�");
		id_jtf = new JTextField("",10);
		password = new JLabel("�û����룺");
		password_jtf = new JPasswordField("",10);
		password2 = new JLabel("ȷ�����룺");
		password_jtf2 = new JPasswordField("",10);
		type = new JLabel("�û����ͣ�");
		type_box = new JComboBox<>();
		type_box.addItem("��ͨ�û�"); type_box.addItem("����Ա");
		name = new JLabel("�û�������");
		name_jtf = new JTextField("",10);
		tel = new JLabel("��ϵ��ʽ��");
		tel_jtf = new JTextField("",10);
		sure = new JButton("ȷ��");
		sure.addActionListener(new ActionListener(){

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if((password_jtf.getText().equals(password_jtf2.getText())&&password_jtf.getText()!=null)||choose!=-1){
					pass = password_jtf.getText();
					Encryption en = new Encryption(pass);
					pass = en.getstr();
					information = new String[]{id_jtf.getText(),pass,type_box.getSelectedItem().toString(),name_jtf.getText(),tel_jtf.getText()};
					if(choose==-1){
						me.getInformation(information);
					}else{
						me.changeInformation(information, choose);
					}
					setVisible(false);
				}
			}
			 
		});
		JButton clean = new JButton("����");
		clean.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				id_jtf.setText("");
				password_jtf.setText("");
				password_jtf2.setText("");
				type_box.setSelectedIndex(0);
				name_jtf.setText("");
				tel_jtf.setText("");
			}
			
		});
		c.add(id);  c.add(id_jtf);
		c.add(password);  c.add(password_jtf);
		c.add(password2);  c.add(password_jtf2);
		c.add(type);  c.add(type_box);
		c.add(name);  c.add(name_jtf);
		c.add(tel);  c.add(tel_jtf);
		c.add(sure);  c.add(clean);
		setResizable(false);
		setBounds(350, 180, 230, 300);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	public String[] getInformation(){
		return information;
	}
	public void getValuse(String[] valuse){
		id_jtf.setText(valuse[0]);
		if(valuse[2].equals("��ͨ�û�"))
			type_box.setSelectedIndex(0);
		else
			type_box.setSelectedIndex(1);
		name_jtf.setText(valuse[2]);
		tel_jtf.setText(valuse[3]);
	}
	
}