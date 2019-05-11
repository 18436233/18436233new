package yychatsserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.yychat.model.Message;
import com.yychat.model.User;

public class StartServer implements sendMessage {
	public static HashMap hmSocket=new HashMap<String,Socket>();
	Socket s;
	ServerSocket ss;
	String userName;
	String passWord;
	Message mess;
	public StartServer() {
		
		
		try {//
			ss=new ServerSocket(3456);
			System.out.println("服务器已经启动，监听3456端口");
		
		while(true){

			s=ss.accept();//
			System.out.println("连接成功:"+s);
		//
		ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
		User user=(User)ois.readObject();
		this.userName=user.getUserName();
		this.passWord=user.getPassWord();
		System.out.println(userName);
		System.out.println(passWord);
		
		//从数据库中实现用户的登录验证
		//1、加载驱动程序
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("已经加载了数据库驱动！");
		//2、获取连接的对象
		String url="jdbc:mysql://127.0.0.1:3306/嘤嘤chat";
		String dbuser="root";
		String dbpass = "";
		Connection conn= DriverManager.getConnection(url,dbuser,dbpass);
		System.out.println("2");
		//3、创建PreparedStatement对象，用来执行SQL语句，标准
		String user_Login_Sql="select * from user where username=? and password=?";
		PreparedStatement ptmt=conn.prepareStatement(user_Login_Sql);
		ptmt.setString(1,userName);
		ptmt.setString(2,passWord);
		
		//4、执行查询，返回结果集
		ResultSet rs =ptmt.executeQuery();
		System.out.println("3");
		//5、根据结果集来判断是否能登录
		boolean loginSuccess =rs.next();
		
		
		
		
		//
		mess=new Message();
		mess.setSender("Server");
		mess.setReceiver(user.getUserName());
		//if(user.getPassWord().equals("123456")){
			//
		if(loginSuccess){
			mess.setMessageType(Message.message_LoginSuccess);//
			
			//
			String friend_Relation_Sql="select slaveuser from relation where majoruser=? and relationtype='1'";
			ptmt=conn.prepareStatement(friend_Relation_Sql);
			ptmt.setString(1,userName);
			rs=ptmt.executeQuery();
			String friendString="";
			while(rs.next()){
				//rs.getString(1);
				friendString=friendString+rs.getString("slaveuser")+" ";
			}
			mess.setContent(friendString);
			System.out.println(userName+"的relation数据表中好友："+friendString);
		
		}else{
			mess.setMessageType(Message.message_LoginFailure);//
			
		}
		sendMessage(s,mess);
		
		//
		//if(user.getPassWord().equals("123456")){
		if(loginSuccess){
			//2.1
			mess.setMessageType(Message.message_NewOnlineFriend);
			mess.setSender("Server");
			mess.setContent(userName);
			//拿到已经在线用户的全部名字
			Set onlineFriendSet =hmSocket.keySet();
			Iterator it=onlineFriendSet.iterator();
			String friendName;
			while(it.hasNext()){
				friendName=(String)it.next();
				mess.setReceiver(friendName);
				//向friendName
				Socket s1=(Socket)hmSocket.get(friendName);
				sendMessage(s1,mess);
			}
			
			
			
			hmSocket.put(userName, s);
			new ServerReceiverThread(s).start();
			
		
			}
		}

		
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessage(Socket s,Message mess) throws IOException {
		ObjectOutputStream oos =new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(mess);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
