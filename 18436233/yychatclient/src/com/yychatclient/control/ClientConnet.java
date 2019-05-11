package com.yychatclient.control;

import java.io.*;
import java.net.Socket;

import com.yychat.*;
import com.yychat.model.Message;
import com.yychat.model.User;

public class ClientConnet {
	
	public static Socket s;
	
	public ClientConnet() {
		try {
			s=new Socket("127.0.0.1",3456);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public Message loginValidate(User user){
		ObjectOutputStream oos;
		ObjectInputStream ois;
		Message mess=null;
		try {
			oos =new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(user);
			
			//
			ois=new ObjectInputStream(s.getInputStream());
			mess=(Message)ois.readObject();
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return mess;
		
	}
}