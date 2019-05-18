package com.chatclient.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.*;

public class FriendList extends JFrame implements ActionListener,MouseListener{//��������
	public static HashMap hmFriendChat1=new HashMap<String,FriendChatClient1>();
	CardLayout cardLayout;//��Ƭ����
	
	JPanel myFriendPanel;
	JButton myFriendJButton;
	
	JScrollPane myFriendScrollPane,myStrangerScrollPane,myBlackScrollPane;
	JPanel myFriendListJPanel,myStrangerListJPanel,myBlackListJPanel;
	static final int FRIENDCOUNT=51;
	static final int STRANGER=21;
	static final int BLACK=11;
	JLabel[] myFriendJLabel=new JLabel[FRIENDCOUNT];//��������
	JLabel[] myStrangerJLabel=new JLabel[STRANGER];
	JLabel[] myBlackJLabel=new JLabel[BLACK];
	JPanel myStrangerBlackListJPanel;
	JButton myStrangerJButton,myBlackJButton;
	JButton blackListJButton;
	
	JPanel myStrangerPanel,myBlackPanel,myBlackPanel1;
	
	JPanel myFriendStrangerPanel;
	JButton myFriendJButton1,myFriendJButton2;
	JButton myStrangerJButton1,myStrangerJButton2;
	
	JButton blackListJButton1;
	String userName;
	
	//public FriendList(String userName){
	public FriendList(String userNames,String friendString){
		this.userName=userName;
		//��һ�ſ�Ƭ
		myFriendPanel=new JPanel(new BorderLayout());//�߽粼��
		//System.out.printin(myFriendPanel.getLayou());
		
		myFriendJButton=new JButton("�ҵĺ���");
		myFriendPanel.add(myFriendJButton,"North");
		
		//�в�
		String[] friendName= friendString.split(" ");
		int count=friendName.length;
		myFriendListJPanel=new JPanel(new GridLayout(count,1));
		for(int i=0;i<count;i++)
		{
			myFriendJLabel[i]=new JLabel(friendName[i]+"",new ImageIcon("images/YY0.gif"),JLabel.LEFT);//"1"
			//myFriendJLabel[i].setEnabled(false);
			//
			//if(Integer.parseInt(userName)==i)myFriendJLabel[i].setEnabled(true);
			
			myFriendJLabel[i].addMouseListener(this);
			myFriendListJPanel.add(myFriendJLabel[i]);
		}
		
		/*myFriendListJPanel=new JPanel(new GridLayout(FRIENDCOUNT-1,1));
		for(int i=1;i<FRIENDCOUNT;i++)
		{
			myFriendJLabel[i]=new JLabel(i+"",new ImageIcon("images/YY0.gif"),JLabel.LEFT);//"1"
			myFriendJLabel[i].setEnabled(false);
			//
			//if(Integer.parseInt(userName)==i)myFriendJLabel[i].setEnabled(true);
			
			myFriendJLabel[i].addMouseListener(this);
			myFriendListJPanel.add(myFriendJLabel[i]);
		}*/
		//myFriendJLabel[Integer.parseInt(userName)].setEnabled(true);
		//myFriendScrollPane=new JScrollPane();
		//myFriendScrollPane.add(myFriendListJPanel);
		myFriendScrollPane=new JScrollPane(myFriendListJPanel);
		myFriendPanel.add(myFriendScrollPane);
		
		myStrangerBlackListJPanel=new JPanel(new GridLayout(2,1));//���粼��
		myStrangerJButton=new JButton("�ҵ�İ����");
		//����¼�������
		myStrangerJButton.addActionListener(this);
		blackListJButton=new JButton("������");
		blackListJButton.addActionListener(this);
		myStrangerBlackListJPanel.add(myStrangerJButton);
		myStrangerBlackListJPanel.add(blackListJButton);
		myFriendPanel.add(myStrangerBlackListJPanel,"South");
		
		//��һ�ſ�Ƭ
		myStrangerPanel = new JPanel(new BorderLayout());
		myFriendStrangerPanel=new JPanel(new GridLayout(2,1));
		myFriendJButton1=new JButton("�ҵĺ���");//��Ӽ�����
		myFriendJButton1.addActionListener(this);
		myStrangerJButton1=new JButton("�ҵ�İ����");
		myFriendStrangerPanel.add(myFriendJButton1);
		myFriendStrangerPanel.add(myStrangerJButton1);
		myStrangerPanel.add(myFriendStrangerPanel,"North");
		myStrangerListJPanel=new JPanel(new GridLayout(STRANGER-1,1));
		for(int i=1;i<STRANGER;i++)
		{
			myStrangerJLabel[i]=new JLabel(i+"",new ImageIcon("images/YY1.gif"),JLabel.LEFT);//"2"
			myStrangerJLabel[i].addMouseListener(this);
			myStrangerListJPanel.add(myStrangerJLabel[i]);
		}
		myStrangerScrollPane=new JScrollPane(myStrangerListJPanel);
		myStrangerPanel.add(myStrangerScrollPane);
		blackListJButton1=new JButton("������");
		blackListJButton1.addActionListener(this);
		myStrangerPanel.add(blackListJButton1,"South");
		
		//����
		myBlackPanel = new JPanel(new BorderLayout());
		myBlackPanel1=new JPanel(new GridLayout(3,1));
		myFriendJButton2=new JButton("�ҵĺ���");//��Ӽ�����
		myFriendJButton1.addActionListener(this);
		myStrangerJButton2=new JButton("�ҵ�İ����");
		myStrangerJButton.addActionListener(this);
		myBlackJButton=new JButton("������");
		myBlackPanel1.add(myFriendJButton2);
		myBlackPanel1.add(myStrangerJButton2);
		myBlackPanel1.add(myBlackJButton);
		myBlackPanel.add(myBlackPanel1,"North");
		myBlackListJPanel=new JPanel(new GridLayout(BLACK-1,1));
		for(int i=1;i<BLACK;i++)
		{
			myBlackJLabel[i]=new JLabel(i+"",new ImageIcon("images/YY1.gif"),JLabel.LEFT);//"2"
			myBlackJLabel[i].addMouseListener(this);
			myBlackListJPanel.add(myBlackJLabel[i]);
		}	
		myBlackScrollPane=new JScrollPane(myBlackListJPanel);
		myBlackPanel.add(myBlackScrollPane);

		
		cardLayout=new CardLayout();
		this.setTitle(userName);
		this.setLayout(cardLayout);
		this.add(myFriendPanel,"1");
		this.add(myStrangerPanel,"2");
		this.add(myBlackPanel,"3");		
		this.setSize(250,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args){
		//FriendList friendList=new FriendList();
		
	}
	public void setEnableFriendIcon(String friendString){
		//
		String[] friendName=friendString.split(" ");
		int count=friendName.length;
		for(int i=1;i<count;i++){
			myFriendJLabel[Integer.parseInt(friendName[i])].setEnabled(true);//
		}
		
	
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent argo){
		if(argo.getSource()==myStrangerJButton){
			cardLayout.show(this.getContentPane(), "2");
		}
		if(argo.getSource()==myFriendJButton1){
			cardLayout.show(this.getContentPane(), "1");
		}
		if(argo.getSource()==blackListJButton){
			cardLayout.show(this.getContentPane(), "3");
		}
		if(argo.getSource()==blackListJButton1){
			cardLayout.show(this.getContentPane(), "3");
		}
		if(argo.getSource()==myStrangerJButton2){
			cardLayout.show(this.getContentPane(), "2");
		}
		if(argo.getSource()==myFriendJButton2){
			cardLayout.show(this.getContentPane(), "1");
		}
		
		
	}

	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getClickCount()==2){
			JLabel jlbl=(JLabel)arg0.getSource();
			String receiver =jlbl.getText();
			FriendChatClient1 FriendChatClient1=(FriendChatClient1)hmFriendChat1.get(userName+"to"+receiver);
			if(FriendChatClient1==null){
				FriendChatClient1=new FriendChatClient1(this.userName,receiver);
				hmFriendChat1.put(userName+"to"+receiver,FriendChatClient1);
		
			}else{
				FriendChatClient1.setVisible(true);
			}
			
		}
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		JLabel jLabel=(JLabel)arg0.getSource();
		jLabel.setForeground(Color.red);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		JLabel jLabel=(JLabel)arg0.getSource();
		jLabel.setForeground(Color.black);
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}