package ChatServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;

import yychatsserver.StartServer;

public class ChatServer  extends JFrame implements ActionListener{
		JButton jb1;
		JButton jb2;
		JPanel jp;
	public ChatServer() {
		jb1=new JButton("启动服务器");
		jb1.addActionListener(this);
		jb2=new JButton("停止服务器");
		jp =new JPanel();
		jp.add(jb1);
		jp.add(jb2);
		this.add(jp);
		
		this.setSize(240,240);
		this.setTitle("YYChat服务器");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		
		
		
	}

	public static void main(String[] args) {
		ChatServer ChatServer=new ChatServer();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new StartServer();
		
	}

}
