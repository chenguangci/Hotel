package com.cgc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.watermark.SubstanceBubblesWatermark;

public class Action extends JFrame implements FocusListener{
	private ImageIcon background = new ImageIcon(getClass().getResource("Hotel.jpg"));
	private JLabel j;
	private JRadioButton admin,customer;
	private JTextField user;
	private JPasswordField password;
	private JButton login;
	static Connection con = null;  //�������ݿ�
	static Statement sql = null;  //���ڷ���SQL���
	static ResultSet res = null;
	private Object[][] getUser = new Object[200][3];
	private int user_num = 0;
	private JLabel err;
	
	public Action(){
		Container c = getContentPane();
		setLayout(null);
		//**************�������ݿ�*************
		Conn conn = new Conn();
		con = conn.getConnection();
		try {
			sql = con.createStatement();
			res = sql.executeQuery("select * from Customer");
			int i=0;
			while(res.next()){
				Object[] data = {res.getString("customerId"),res.getString("password"),res.getString("type")};
				getUser[i] = data;
				i++;
			}
			user_num = i; //��ȡ�û�����
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(res!=null){
				res.close();
				res = null;
				}
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
		//***************************���ñ���ͼƬ
		j = new JLabel(background);
		j.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
		getLayeredPane().add(j, new Integer(Integer.MIN_VALUE));		//ͼƬ���ڵײ�
		JPanel panel = (JPanel) getContentPane();		// ��崰��
        panel.setOpaque(false);
       
        //******************���õ�¼��
        JLabel ID = new JLabel("��ѡ�������ݣ�");
        ID.setFont(new Font("΢���ź�",Font.BOLD,16));
        ID.setForeground(Color.white);
        admin = new JRadioButton("����Ա",true);
        admin.setFont(new Font("΢���ź�",Font.BOLD,14));
        admin.setForeground(Color.white);
        admin.setOpaque(false); 
        customer = new JRadioButton("�ͻ�");
        customer.setFont(new Font("΢���ź�",Font.BOLD,14));
        customer.setForeground(Color.white);
        customer.setOpaque(false);
        ButtonGroup group = new ButtonGroup();
        group.add(admin);group.add(customer);
        
        JLabel user1 = new JLabel("�˺ţ�");
        user1.setForeground(Color.white);
        user1.setFont(new Font("΢���ź�",Font.BOLD,18));
		user = new JTextField("",11);
		user.setFont(user.getFont().deriveFont((float) 18.0));
		user.addFocusListener(this);
		JLabel password1 = new JLabel("���룺");
		password1.setForeground(Color.white);
		password1.setFont(new Font("΢���ź�",Font.BOLD,18));
		password  = new JPasswordField("",11);
		password.setFont(password.getFont().deriveFont((float) 18.0));
		password.addFocusListener(this);
		login = new JButton("��¼");	
		login.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				String id = user.getText().toString();
				String pass = password.getText().toString();
				Encryption en = new Encryption(pass);
				pass = en.getstr();
				Boolean isadmin;
				if(admin.isSelected()){
					isadmin = true;
				}else{
					isadmin = false;
				}
				int i;
				for(i = 0;i<user_num;i++){
					if(getUser[i][0].toString().equals(id)&&getUser[i][1].toString().equals(pass)){
						if(getUser[i][2].toString().equals("��ͨ�û�")&&isadmin == false){
							new User(id);
							setVisible(false);
						}else if(getUser[i][2].toString().equals("����Ա")&&isadmin == true){
							new Admin();
							setVisible(false);
						}
					}
				}
				if(i == user_num){
					err.setText("�˺��������ݴ�������������");
				}
					
			}
			
		});
		
		JPanel p = new JPanel();
		c.add(p);
		p.setBounds(250, 190, 280, 200);
		p.setLayout(new FlowLayout(1,10,20));
		p.setOpaque(false);
		p.add(ID); p.add(admin); p.add(customer);
		p.add(user1);  p.add(user);
		p.add(password1);  p.add(password);
		p.add(login,BorderLayout.SOUTH);	
		err = new JLabel("");
		err.setForeground(Color.yellow);
		err.setFont(new Font("΢���ź�",Font.BOLD,16));
		err.setBounds(250, 390, 250, 50);
		c.add(err);
		setBounds(200,50,775,580);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(3);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {  
            UIManager.setLookAndFeel(new SubstanceLookAndFeel());  
            JFrame.setDefaultLookAndFeelDecorated(true);            
            SubstanceLookAndFeel.setCurrentButtonShaper(new ClassicButtonShaper());  
            SubstanceLookAndFeel.setCurrentWatermark(new SubstanceBubblesWatermark());     
        } catch (Exception e) {  
            System.err.println("Something went wrong!");  
        }
		new Action();
	}
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		err.setText("");
	}
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

}
