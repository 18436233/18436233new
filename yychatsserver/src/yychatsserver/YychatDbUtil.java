package yychatsserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class YychatDbUtil {
	public static final String MYSQLDRIVER ="com.mysql.jdbc.Driver";
	public static final String url="jdbc:mysql://127.0.0.1:3306/嘤嘤chat";
	public static final String dbuser="root";
	public static final String dbpass = "";
	//1、加载驱动程序
	public static void loadDriver(){
			try {
				Class.forName(MYSQLDRIVER);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
			//2、获取连接的对象
		public static Connection getConnection(){
		loadDriver();
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(url,dbuser,dbpass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
			
		public static boolean loginValidate(String userName,String passWord){
			boolean loginSuccess=false;
			Connection conn =getConnection();
			//3、创建PreparedStatement对象，用来执行SQL语句，标准
			String user_Login_Sql="select * from user where username=? and password=?";
			PreparedStatement ptmt=null;
			ResultSet rs=null;
			
			try {
				ptmt=conn.prepareStatement(user_Login_Sql);	
				ptmt.setString(1,userName);
				ptmt.setString(2,passWord);
		
				//4、执行查询，返回结果集
				rs =ptmt.executeQuery();
				//5、根据结果集来判断是否能登录
				loginSuccess =rs.next();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				closeDB(conn,ptmt,rs);
			}
			System.out.println("loginSuccess为："+loginSuccess);
			return loginSuccess;
			}
		
	public static String getFriendString(String userName) {
		Connection conn =getConnection();
		PreparedStatement ptmt=null;
		ResultSet rs=null;
		String friendString="";
		String friend_Relation_Sql="select slaveuser from relation where majoruser=? and relationtype='1'";
		try {
			ptmt=conn.prepareStatement(friend_Relation_Sql);
			ptmt.setString(1,userName);
		rs=ptmt.executeQuery();
		while(rs.next()){
			friendString=friendString+rs.getString("slaveuser")+" ";
						}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
				closeDB(conn,ptmt,rs);
			}
		return friendString;
		
	}
		public static void closeDB(Connection conn,PreparedStatement ptmt,ResultSet rs){
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ptmt!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
}
