package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import DB.GameDB;
import client.ClientSocket;
import controll.Controller;
import controll.UserPack;

public class RoomCreate extends JFrame implements ActionListener {
	private JButton yes;
	private JButton no;
	private JTextField Rname;
	private String Uname;
	private Controller controller;
	private GameRoom gr;
	public GameRoomFrame grf;
	public GameRoomFrame getGrf() {
		return grf;
	}
	public void setGrf(GameRoomFrame grf) {
		this.grf = grf;
	}
	public GameRoom getGr() {
		return gr;
	}
	public void setGr(GameRoom gr) {
		this.gr = gr;
	}
	public JTextField getRname() {
		return Rname;
	}
	public void setRname(JTextField rname) {
		Rname = rname;
	}
	
	public RoomCreate(String Uname) {
		controller = Controller.getInstance();
		this.Uname=Uname;
		setTitle("RoomCreate");
		setSize(300, 150);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		Container c = getContentPane();
		c.setBackground(Color.white);
		setLayout(new GridLayout(0,1));
		JPanel jp1 = new JPanel();
		jp1.setBackground(Color.white);
		jp1.setBorder(new EmptyBorder(0, 15, 15, 10));
		JPanel jp2 = new JPanel();
		jp1.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel jl = new JLabel("방의 이름을 입력해주세요.");
		Rname = new JTextField(20);
		jp2.setLayout(new FlowLayout());
		jp2.setBackground(Color.white);
		yes = new JButton("확인");
		yes.addActionListener(this);
		no = new JButton("취소");
		no.addActionListener(this);
		jp1.add(jl);
		jp1.add(Rname);
		jp2.add(yes);
		jp2.add(no);
		add(jp1);
		add(jp2);
		setVisible(true);
	}
	public static void main(String[] args) {
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==yes) {
			//Main의 버튼List에 버튼을 추가;
			GameDB gd = new GameDB();
			gd.insertRoom(Rname.getText(),Uname);
			gd.UpU_R(Uname, Rname.getText());
			GameUserPanel gp = new GameUserPanel(Uname,Rname.getText());
//			GameRoom.user_pan.add(gp);
			gp.setSAVEUSER(1);
			gr = new GameRoom(Rname.getText());
			grf = new GameRoomFrame(gr, "d");
			controller.clientSocket.setGrf(grf);
			controller.setGrf(grf);
			UserPack up = new UserPack(Uname, Rname.getText(),"0");
			ArrayList<GameRoom> gr_list = controller.getGr_list();
			gr_list.add(gr);
			controller.setGr_list(gr_list);
			controller.clientSocket.DataUpdate(gr,null,up);
			Main.dump=1;
			dispose();
		}
		if(obj==no) {
			dispose();
			new Main(Uname);
		}
		
	}
}
