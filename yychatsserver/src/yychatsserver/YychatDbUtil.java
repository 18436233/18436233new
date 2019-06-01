package yychatsserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.yychat.model.Message;
import com.yychat.model.User;

public class YychatDbUtil {
	public static final String MYSQLDRIVER ="com.mysql.jdbc.Driver";
	public static final String url="jdbc:mysql://127.0.0.1:3306/����chat";
	public static final String dbuser="root";
	public static final String dbpass = "";
	//1��������������
	public static void loadDriver(){
			try {
				Class.forName(MYSQLDRIVER);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
			//2����ȡ���ӵĶ���
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
	
	public static void addUser(String userName,String passWord){
		Connection conn =getConnection();
		String user_Login_Sql="insert into user (username,password,registertimestamp) values(?,?,?)";
		PreparedStatement ptmt=null;
		
		try {
			ptmt=conn.prepareStatement(user_Login_Sql);	
			ptmt.setString(1,userName);
			ptmt.setString(2,passWord);
			//java.util.Date date=new java.util.Date();
			Date date=new Date();
			java.sql.Timestamp timestamp=new java.sql.Timestamp(date.getTime());
			ptmt.setTimestamp(3, timestamp);
			//4��ִ�в�ѯ�����ؽ����
			int count=ptmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			closeDB(conn,ptmt);
		}
	}
		
	public static boolean seekUser(String userName) {
		boolean seekUserResult=false;
		Connection conn =getConnection();
		String user_Login_Sql="select * from user where username=?";
		PreparedStatement ptmt=null;
		ResultSet rs=null;
		
		try {
			ptmt=conn.prepareStatement(user_Login_Sql);	
			ptmt.setString(1,userName);
			
			//4��ִ�в�ѯ�����ؽ����
			rs =ptmt.executeQuery();
			//5�����ݽ�������ж��Ƿ��ܵ�¼
			seekUserResult =rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			closeDB(conn,ptmt,rs);
		}
		
		return seekUserResult;
	}
		
		public static boolean loginValidate(String userName,String passWord){
			boolean loginSuccess=false;
			Connection conn =getConnection();
			//3������PreparedStatement��������ִ��SQL��䣬��׼
			String user_Login_Sql="select * from user where username=? and password=?";
			PreparedStatement ptmt=null;
			ResultSet rs=null;
			
			try {
				ptmt=conn.prepareStatement(user_Login_Sql);	
				ptmt.setString(1,userName);
				ptmt.setString(2,passWord);
		
				//4��ִ�в�ѯ�����ؽ����
				rs =ptmt.executeQuery();
				//5�����ݽ�������ж��Ƿ��ܵ�¼
				loginSuccess =rs.next();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				closeDB(conn,ptmt,rs);
			}
			System.out.println("loginSuccessΪ��"+loginSuccess);
			return loginSuccess;
			}
		
		/*public static Message mess(Socket s) {
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(s.getInputStream());
				User user=(User)ois.readObject();
				String userName=user.getUserName();
			String passWord=user.getPassWord();
			boolean loginSuccess=YychatDbUtil.loginValidate(userName, passWord);
			
			mess=new Message();
			mess.setSender("Server");
			mess.setReceiver(user.getUserName());
			//if(user.getPassWord().equals("123456")){
				//
			if(loginSuccess){
				mess.setMessageType(Message.message_LoginSuccess);//
				String friendString=YychatDbUtil.getFriendString(userName);
				//
				mess.setContent(friendString);
				System.out.println(userName+"��relation���ݱ��к��ѣ�"+friendString);
			
			}else{
				mess.setMessageType(Message.message_LoginFailure);//
				
			}
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return mess(s);
		}*/

		
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
	public static void closeDB(Connection conn,PreparedStatement ptmt){
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
