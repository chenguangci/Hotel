package com.cgc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetCustomer {
	static Connection con = null;
	static Statement sql = null;
	static ResultSet ret = null;
	static PreparedStatement pres = null;
	private Object[][] dataAll_c = new Object[1000][5];
	public GetCustomer(){
		Conn conn = new Conn();
		con = conn.getConnection();
		try{
			sql = con.createStatement();
			ret = sql.executeQuery("select * from Customer");
			int i = 0;
			while(ret.next()){
				Object[] data = {ret.getString("customerId"),ret.getString("password"),ret.getString("type"),ret.getString("name"),ret.getString("tel")};
				dataAll_c[i] = data;
				i++;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				if(ret!=null){
				ret.close();
				ret = null;
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
	}
	public Object[][] getdataAll(){
		return dataAll_c;
	}
	public Boolean addCustomer(String[] values){
		try {
			Conn con1 = new Conn();
			con = con1.getConnection();
			pres =con.prepareStatement("insert into Customer(customerId,password,type,name,tel) values(?,?,?,?,?)");
			pres.setString(1, values[0]);
			pres.setString(2, values[1]);
			pres.setString(3, values[2]);
			pres.setString(4, values[3]);
			pres.setString(5, values[4]);
			pres.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}finally{
			try {
				if(con!=null){
					con.close();
					con = null;
				}
				if(pres!=null){
					pres.close();
					pres = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void delete_customer(String id){
		Conn conn2 = new Conn();
		con = conn2.getConnection();
		
		try {
			sql = con.createStatement();
			sql.executeUpdate("delete from Customer where customerId = '"+id+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public Boolean update_customer(String[] values,String id){
		Conn conn3 = new Conn();
		con = conn3.getConnection();	
		try {
			sql = con.createStatement();
			sql.executeUpdate("update Customer set tel = '"+values[4]+"' where customerId = '"+id+"'");
			sql.executeUpdate("update Customer set name = '"+values[3]+"' where customerId = '"+id+"'");
			sql.executeUpdate("update Customer set type = '"+values[2]+"' where customerId = '"+id+"'");
			if(!values[1].equals("")){
				sql.executeUpdate("update Customer set password = '"+values[1]+"' where customerId = '"+id+"'");  //ÃÜÂë²»Îª¿Õ
			}
			sql.executeUpdate("update Customer set customerId = '"+values[0]+"' where customerId = '"+id+"'");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
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
