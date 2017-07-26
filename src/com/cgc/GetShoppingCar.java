package com.cgc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetShoppingCar {
	static Connection con;  //连接数据库
	static Statement sql;  //用于发送SQL语句
	static ResultSet res;   
	private Object[][] shoppingcar = new Object[200][7];
	public GetShoppingCar(){
		Conn c = new Conn();
		con = c.getConnection();
		int i=0;
		try {
			sql  =con.createStatement();
			res = sql.executeQuery("select roomID,Rtype,Raddress,Rprice,live,Days from Shoppingcar a,Room b where a.roomID = b.roomID");
			while(res.next()){
				Object[] car = {res.getString("roomID"),res.getString("Rtype"),res.getString("Raddress"),res.getString("Rprice"),res.getString("live"),res.getInt("Days"),new Boolean(false)};	
				shoppingcar[i] = car;
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
	public Object[][] GetShoppingcar(){
		return shoppingcar;		
	}
}
