package yychatsserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;

import com.yychat.model.Message;

public class ServerReceiverThread extends Thread{
	Socket s;
	String sender;
	ObjectInputStream ois;
	Message mess;
	ObjectOutputStream oos;
	public ServerReceiverThread(Socket s) {
		this.s=s;
		
	}
	
	public void run(){
		
		while(true){
		try {
			ois= new ObjectInputStream(s.getInputStream());
			mess=(Message)ois.readObject();//
				sender =mess.getSender();
			System.out.println(mess.getSender()+"对"+mess.getReceiver()+"说："+mess.getContent());
				
			if(mess.getMessageType().equals(Message.message_Common)){
				Socket s1=(Socket)StartServer.hmSocket.get(mess.getReceiver());
				senderMessage(s1,mess);//
		}
			//2
			if(mess.getMessageType().equals(Message.message_RequestOnlineFriend)){
				Set friendSet =StartServer.hmSocket.keySet();
				Iterator it=friendSet.iterator();
				String friendName;
				String friendString =" ";
				while(it.hasNext()){
					friendName=(String)it.next();
					if(!friendName.equals(mess.getSender()))
					friendString=friendName+" "+friendString;
					
				}
				System.out.println("全Socket s,Message mess部好友的名字："+friendString);
				mess.setContent(friendString);
				mess.setMessageType(Message.message_OnlineFriend);
				mess.setSender("Server");
				mess.setReceiver(sender);
				senderMessage(s,mess);
			}
			
		}
			catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			}
		}
		
	}

	private void senderMessage(Socket s,Message mess) throws IOException {
		oos =new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(mess);
	}
}
