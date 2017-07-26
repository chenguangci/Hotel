package com.cgc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetRoomadmin {
	static Connection con = null;  //连接数据库
	static Statement sql = null;  //用于发送SQL语句
	static ResultSet res = null;   
	static PreparedStatement pres = null;
	private Object[][] dataAll = new Object[200][6];
	public GetRoomadmin(){
		Conn conn = new Conn();
		con = conn.getConnection();
		try {
			sql = con.createStatement();
			res = sql.executeQuery("select * from Room");
			int i=0;
			while(res.next()){
				Object[] data = {res.getString("roomID"),res.getString("Rprice"),res.getString("Raddress"),res.getString("Rtype"),res.getString("Rstate")};
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
	public Boolean add(String[] values){
		try {
			Conn con1 = new Conn();
			con = con1.getConnection();
			pres =con.prepareStatement("insert into Room(roomID,Rprice,Raddress,Rtype,Rstate) values(?,?,?,?,?)");
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
	public void delete(String id){
		Conn conn2 = new Conn();
		con = conn2.getConnection();
		
		try {
			sql = con.createStatement();
			sql.executeUpdate("delete from Room where roomID = '"+id+"'");
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
	public Boolean update(String[] values,String id){
		Conn conn3 = new Conn();
		con = conn3.getConnection();
		
		try {
			sql = con.createStatement();
			sql.executeUpdate("update Room set Rstate = '"+values[4]+"' where roomID = '"+id+"'");
			sql.executeUpdate("update Room set Rtype = '"+values[3]+"' where roomID = '"+id+"'");
			sql.executeUpdate("update Room set Raddress = '"+values[2]+"' where roomID = '"+id+"'");
			sql.executeUpdate("update Room set Rprice = '"+values[1]+"' where roomID = '"+id+"'");
			sql.executeUpdate("update Room set roomID = '"+values[0]+"' where roomID = '"+id+"'");
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
