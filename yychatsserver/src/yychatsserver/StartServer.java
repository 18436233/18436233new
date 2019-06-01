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
		
		if(user.getuserMessageType().equals("USER_REGISTER")){
			boolean seekUserResult=YychatDbUtil.seekUser(userName);
			mess=new Message();
			mess.setSender("Server");
			mess.setReceiver(userName);
			if(seekUserResult){
				mess.setMessageType(Message.message_RegisterFailure);
				//System.out.println("注册失败 ");
			}else {
				YychatDbUtil.addUser(userName,passWord);
				mess.setMessageType(Message.message_RegisterSuccess);
				//System.out.println("注册成功 ");
			}
			sendMessage(s,mess);
			s.close();
		}
		
		
		if(user.getuserMessageType().equals("USER_LOGIN")){
		
		boolean loginSuccess=YychatDbUtil.loginValidate(userName, passWord);
		//
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
			System.out.println(userName+"的relation数据表中好友："+friendString);
		
		}else{
			mess.setMessageType(Message.message_LoginFailure);//
			
		}
		sendMessage(s,mess);
		
		//
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

	}
		} catch (IOException | ClassNotFoundException e) {
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
