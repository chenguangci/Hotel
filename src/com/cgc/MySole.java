package com.cgc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySole {
	private Object[][] dataAll = new Object[200][5];
	static Connection con = null;  //连接数据库
	static Statement sql = null;  //用于发送SQL语句
	static ResultSet res = null;   
	static PreparedStatement pres = null;
	public MySole(String Id){
		Conn conn = new Conn();
		con = conn.getConnection();
		try {
			sql = con.createStatement();
			res = sql.executeQuery("select * from Sole where customerId = '"+Id+"'");
			int i=0;
			while(res.next()){
				Object[] data = {res.getString("roomID"),res.getString("price"),res.getString("soledate"),res.getString("live"),res.getString("Days")};
				dataAll[i] = data;
				i++;
			}
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
	}
	public Object[][] getdata(){
		return dataAll;
	}
}
