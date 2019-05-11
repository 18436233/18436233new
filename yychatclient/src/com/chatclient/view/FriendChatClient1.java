package com.chatclient.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;

import com.yychat.model.Message;
import com.yychatclient.control.ClientConnet;

public class FriendChatClient1 extends JFrame implements ActionListener{

	//Timer t;
	//JLabel jl;
	private JTextField time;
    SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	//
	JScrollPane jsp;
	JTextArea jta;

	
	//
	JPanel jp,jp1;
	JTextField jtf;
	JButton jb;
	
	String sender;
	String receiver;
	
	public FriendChatClient1(String sender, String receiver){
		this.sender=sender;
		this.receiver=receiver;
		
		/*jp1=new JPanel();  
	    t=new Timer(1000,this);  
	    t.start();  
	    jl=new JLabel("当前时间"+Calendar.getInstance().getTime().toLocaleString());  
	    jp1.add(jl); */
        setBounds(100, 100, 200, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        time = new JTextField();
        time.setEditable(false);
        this.add(time,"North");
        time.addActionListener(new TimeActionListener());
        setVisible(true);

        
		
		jta = new JTextArea();
		jta.setEditable(false);
		jsp =new JScrollPane(jta);
		this.add(jsp,"Center");
		
		
		jp =new JPanel();
		jtf =new JTextField(15);
		jtf.setForeground(Color.red);
		jb=new JButton("发送");
		jb.addActionListener(this);
		jp.add(jtf);
		jp.add(jb);
		
		//this.add(jp1,"North");
		
		this.add(jp,"South");
		
		
		this.setSize(350,240);
		this.setTitle(sender+"正在和"+receiver+"聊天");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	
	}
	
	public static void main(String[] args) {
		//FriendChatClient FriendChatClient=new FriendChatClient();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==jb) {
			jta.append(jtf.getText()+"\r\n");
			//

			Message mess = new Message();
			mess.setSender(sender);
			mess.setReceiver(receiver);
			mess.setContent(jtf.getText());
			mess.setMessageType(Message.message_Common);
			ObjectOutputStream oos;
			try {
				Socket s=(Socket)ClientConnet.hmSocket.get(sender);
				oos = new ObjectOutputStream(s.getOutputStream());
				oos.writeObject(mess);
				//
			}//t
			catch (IOException e) {
				e.printStackTrace();
			
			}//c
			
		}//if

	}//pu
	public void appendJta(String showMessage) {
		jta.append(showMessage+"\r\n");
		
	}//1
	class TimeActionListener implements ActionListener{
        public TimeActionListener(){
            javax.swing.Timer t=new javax.swing.Timer(1000,this);
            t.start();
        }//pu

	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		time.setText(myfmt.format(new java.util.Date()).toString());
		
	}//pu


	}//cl
}
