package com.cgc;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GetRoom {
	static Connection con;  //连接数据库
	static Statement sql;  //用于发送SQL语句
	static ResultSet res;   
	static PreparedStatement pres = null;
	private Object[][] Sole = new Object[200][3];
	private Object[][] free_room = new Object[200][6];
	private Object[][] shoppingcar = new Object[200][8];
	public GetRoom(){
		Conn c = new Conn();
		con = c.getConnection();
		int i=0;
		try {
			sql  =con.createStatement();
			res = sql.executeQuery("select * from Room");
			while(res.next()){
				Object[] free = {res.getString("roomID"),res.getString("Rtype"),res.getString("Raddress"),res.getString("Rprice"),"空房间",new Boolean(false)};
				free_room[i] = free;
				i++;				
			}
			res = sql.executeQuery("select * from Sole");
			i = 0;
			while(res.next()){
				Object[] Sol = {res.getString("roomID"),res.getString("live"),res.getInt("Days"),res.getString("customerId")};
				Sole[i] = Sol;	
				i++;
			}
			i = 0;
			res = sql.executeQuery("select a.roomID,Rtype,Raddress,Rprice,live,Days,a.customerId from Shoppingcar a,Room b where a.roomID = b.roomID");
			while(res.next()){
				Object[] car = {res.getString("roomID"),res.getString("Rtype"),res.getString("Raddress"),res.getString("Rprice"),res.getString("live"),res.getInt("Days"),new Boolean(false),res.getString("customerId")};	
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
	public Object[][] getSole(){
		return Sole;
	}
	public Object[][] FreeRoom(){
		return free_room;		
	}
	public Object[][] GetShoppingcar(){
		return shoppingcar;		
	}
	public void addcar(String roomID,String customId,String live,String Days){
		Conn conn = new Conn();
		con = conn.getConnection();
		try {
			pres =con.prepareStatement("insert into Shoppingcar(roomID,customerId,live,Days) values(?,?,?,?)");
			pres.setString(1, roomID);
			pres.setString(2, customId);
			pres.setString(3, live);
			pres.setInt(4, Integer.parseInt(Days));
			pres.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
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
	public void deletecar(Object[] val){
		Conn conn = new Conn();
		con = conn.getConnection();
		try {
			sql = con.createStatement();
			sql.executeUpdate("delete from Shoppingcar where roomID = '"+val[0].toString()+"' and customerId = '"+val[1].toString() +"' and live = '"+val[2].toString() +"' and Days = "+(int)val[3]);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	public Boolean check(Object[] data,String soleID,String today,double money){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		Boolean can = true;	
		try {
			long new_date = sdf.parse(data[2].toString()).getTime();   //得出选择的日期格式
			GetRoom room = new GetRoom();
			Sole = room.getSole();       //获取订单情况
			int j = 0;
			int number = (int)data[3];				
			while(Sole[j][0]!=null){
				if(data[0].toString().equals(Sole[j][0])){
					String old_d = Sole[j][1].toString();
					long old_date = sdf.parse(old_d).getTime();
					int old_day_num = (int) Sole[j][2];
					long two_day = (new_date - old_date) / (1000 * 60 * 60 * 24);	//求两个日期的差值
					if((two_day>=0&&two_day<old_day_num)||(two_day<=0&&two_day>(-1*number))){      //房间在选择日期内被预订
						can = false;
						break;
					}
				}				
				j++;
			}
			}catch (ParseException e) {
			// TODO Auto-generated catch block
			}
		if(can){
			Conn conn = new Conn();
			con = conn.getConnection();
			try {
				pres =con.prepareStatement("insert into Sole(soleID,customerId,roomID,soledate,price,state,sNumber,live,Days) values(?,?,?,?,?,?,?,?,?)");
				pres.setString(1, soleID);
				pres.setString(2, data[1].toString());
				pres.setString(3, data[0].toString());
				pres.setString(4, today);
				pres.setDouble(5, money);
				pres.setString(6, "Y");
				pres.setInt(7, 1);
				pres.setString(8, data[2].toString());
				pres.setString(9, data[3].toString());
				pres.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
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
		return can;
	}
}
