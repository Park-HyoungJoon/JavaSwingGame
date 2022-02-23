package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.plaf.FontUIResource;


import DB.GameDB;
import Timer.TimerTest;
import client.ClientSocket;
import controll.Controller;
import controll.UpdatePack;
import controll.UserPack;


@SuppressWarnings("serial")
public class GameRoom extends JPanel implements ActionListener, KeyListener {
	GameListPanel glp;
	RoomCreate Rcreate;
	static Controller controller;
	private int id;
	private List UserList;
	private GameUserPanel roomUser;
	private static String Rname;
public String getRname() {
		return Rname;
	}
	private JButton exitBt;
	public JButton S_BT;
	private JTextField user_chat;
	private JTextField WordExam;
	public static JLabel jl;
	public static JPanel P_panel;
	private static GameUserPanel[] gup = new GameUserPanel[4];
	public static JPanel getP_panel() {
		return P_panel;
	}

	public static void setP_panel(JPanel p_panel) {
		P_panel = p_panel;
	}    
	public GameRoom(String Rname) {
		this.Rname = Rname;
//		setTitle("Game");
//		setSize(700, 800);
//		setDefaultCloseOperation(3);
//		setLocationRelativeTo(null);
		setLayout(null);
		JPanel MainPanel = new JPanel();
		MainPanel.setLayout(new BorderLayout());
		JPanel TopPanel = new JPanel();
		TopPanel.setPreferredSize(new Dimension(700, 150));
		TopPanel.setBackground(Color.white);
		TopPanel.setLayout(null);
		WordExam = new JTextField("핫윙");
		WordExam.setBounds(80, 30, 500, 100);
		WordExam.setFont(new FontUIResource("나눔", Font.BOLD, 15));
		WordExam.setEditable(false);
		exitBt = new JButton("나가기");
		exitBt.addActionListener(this);
		exitBt.setBounds(600,10,80,20);
		TopPanel.add(WordExam);
		TopPanel.add(exitBt);
		//중간 패널
		JPanel CenPanel = new JPanel();
		CenPanel.setPreferredSize(new Dimension(700,350));
		CenPanel.setBackground(Color.white);
		CenPanel.setLayout(new FlowLayout(FlowLayout.CENTER,250,10));
		jl = new JLabel(TimerTest.getTimeBuffer());
		jl.setFont(new Font("나눔", Font.BOLD, 50));
		jl.setForeground(Color.PINK);
		ImageIcon icon = new ImageIcon("Img/lgMu.gif");
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(100, 100, Image.SCALE_FAST);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		JLabel imgjl = new JLabel(changeIcon);
		S_BT =  new JButton("start");
		S_BT.addActionListener(this);
		S_BT.setPreferredSize(new DimensionUIResource(200, 70));
		S_BT.setEnabled(false);
		CenPanel.add(jl);
		CenPanel.add(imgjl);
		CenPanel.add(S_BT);
//		Cen_HighPanel.add(jl);
//		Cen_HighPanel.add(imgjl);
//		Cen_HighPanel.add(S_BT);
//		CenPanel.add(Cen_HighPanel);
//		CenPanel.add(Cen_BelowPanel);
		
		JPanel BotPanel = new JPanel();
		BotPanel.setPreferredSize(new Dimension(700, 300));
		BotPanel.setLayout(null);
		
		
		P_panel = new JPanel();
//		P_panel.setBounds(0, 0, 700, 150);
		P_panel.setBounds(0, 0, 700, 180);
		P_panel.setLayout(new GridLayout(1,4));

		JPanel chatPanel = new JPanel();
		chatPanel.setLayout(null);
		user_chat = new JTextField();
		user_chat.setBounds(40, 10, 600, 40);
		user_chat.addKeyListener(this);
		chatPanel.add(user_chat);
		P_panel.setBackground(Color.pink);
		chatPanel.setBounds(0,180,700,50);
		BotPanel.add(P_panel);
		BotPanel.add(chatPanel);
		
		MainPanel.add(TopPanel,BorderLayout.NORTH);
		MainPanel.add(CenPanel,BorderLayout.CENTER);
		MainPanel.add(BotPanel,BorderLayout.SOUTH);
		MainPanel.setBounds(0, 0, 700, 800);
		add(MainPanel);
//		this.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				GameDB gd = new GameDB();
//				int RC = gd.SearchRC(Rname);
//				if(RC==1) {
//					gd.DelRoom(Rname);
//					dispose();
//				}
//				else
//					RC--;
//				gd.HowRC(Rname, RC);
//				dispose();
//			}
//		});
		setVisible(true);
	}
	public static void main(String[] args) {
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		controller =Controller.getInstance();
		Object obj = e.getSource();
		if(obj==exitBt) {
			GameDB gd = new GameDB();
			int RC = gd.SearchRC(Rname);
			if(RC==1) {
				gd.DelRoom(Rname);
			}
			else
				RC--;
			gd.HowRC(Rname, RC);
		}
		if(obj==S_BT) {
			UserPack up2 = new UserPack(controller.username, Rname,"1");
			controller.clientSocket.DataUpdate(this,null, up2);
	        	
//			Thread tr = new TimerTest();
//			tr.start();
		}
	}
	public static void addP_Panel(String Uname,String Rname) {
		GameDB db = new GameDB();
		P_panel.removeAll(); // 기존에 있던 유저 정보 삭제
		ArrayList<String> U = db.searchU_R(Rname);//방에 있는 사용자들의 이름을 갖는 ArrayList 
		for (int i = 0; i < U.size(); i++) {
			 gup[i]  = new GameUserPanel(U.get(i), Rname); //방에 있는 사용자 이름과 방 이름을 갖고있는 Panel 생성 
			if(!gup[i].N_get().equals(Uname)) { //따른 사용자라면
				gup[i].ready.setEnabled(false); //레디버튼 사용 못하게 닫음
			}
			controller = Controller.getInstance();
			int count = controller.getRdyCount();
			count++;
			controller.setRdyCount(count); 
			P_panel.add(gup[i]); // 각 사용자를 P_panel에 추가
			
		}
		P_panel.repaint();
		P_panel.revalidate();	
	}
	@SuppressWarnings("unlikely-arg-type")
	public void chat(UpdatePack up) {
		GameDB db = new GameDB();
		if(!jl.getText().equals("03:00:00")) {
		if(Rname.equals(getRname())) {
			for(int i=0; i<gup.length; i++) {
			if(up.getUname().equals(gup[i].N_get())) {
				gup[i].Chat_jt.setText(up.getMessage());
				gup[i].Chat_jt.setVisible(true);
				if(up.getMessage().equals(WordExam.getText())) {
					WordExam.setText(up.getRquest());
					System.out.println("메시지 변경");
				}
			}
			}
		}
	}
		else {
			if(Rname.equals(getRname())) {
				for(int i=0; i<gup.length; i++) {
				if(up.getUname().equals(gup[i].N_get())) {
					gup[i].Chat_jt.setText(up.getMessage());
//					gup[i].Chat_jt.setVisible(true);
					
				}
				}
			}	
		}
		P_panel.repaint();
		P_panel.revalidate();
	}
	public void LookReady(String Uname,GameRoom gr,UpdatePack up) {
		GameDB gd = new GameDB();
		String r = gd.SrchR(Uname);
		System.out.println(r + "얘는 되나요 ? ? " + up.getRname());
		//사용자랑 버튼눌린 방이 같은지 확인
		if(r.equals(up.getRname())) {
			System.out.println("방 같은지 확인");
			//방 ready눌린 수랑 방인원 수랑 같은지 확인
			System.out.println(gd.getRready(up.getRname())+"ready 수"+gd.RoomCount());
		if(gd.getRready(up.getRname())==gd.SearchRC(up.getRname())) {
			System.out.println("ready수 확인");
			//해당 유저가 방장이라면
			if(Uname.equals(gd.getFirstUser(up.getRname()))) {
				System.out.println("왜 실행ㅇ 안될까????");
				gr.S_BT.setEnabled(true);
				
				int reset = 0;
				gd.upReady(Uname, reset);
				gr.repaint();
				gr.revalidate();
			}
		}
		else {
			
		}
		}
		System.out.println("사용자 방 이름 : " + up.getRname() + " , 게임 룸 자체 방 이름  :" + Rname);
		ArrayList<String> U = gd.searchU_R(up.getRname());
		for (int i = 0; i < U.size(); i++) {
			
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int d = e.getKeyCode();
		if(d==13||d==10) {
			System.out.println(user_chat.getText());
			UpdatePack up2 = new UpdatePack(controller.username, Rname,user_chat.getText());
			controller.clientSocket.DataUpdate(this, up2,null);
			user_chat.setText("");
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
