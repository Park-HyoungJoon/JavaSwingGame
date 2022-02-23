package main;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import main.Login;
import DB.GameDB;


@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener {
	private Login login;
	private JScrollPane scroller;
	private JButton addRoom,ReBtn;
	private JPanel jp1,jp2;
	private GameDB gd = new GameDB();
	private String Uname;
	public static int dump = 0;
	public Main(String Uname) {
		this.Uname=Uname;
		setTitle("Main");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setLayout(null);
		Container c =getContentPane();
		c.setBackground(Color.white);
		jp1 = new JPanel();
		jp1.setLayout(null);
		jp1.setBounds(0, 0, 300, 350);
		GameListPanel glp = new GameListPanel(Uname,this);
		scroller = new JScrollPane(glp);
	    scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    scroller.setBounds(0, 0, 287, 350);
	    jp1.add(scroller);
	    
		jp2 = new JPanel();
		jp2.setLayout(null);
		jp2.setBounds(300, 0, 200, 350);
		JPanel jp3 = new JPanel();
		jp3.setLayout(new GridLayout(1,0));
		jp3.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
		jp3.setBounds(300, 350, 200, 150);
		JPan1 jpan1 = new JPan1();
		JPan2 jpan2 = new JPan2();
		jp3.add(jpan1);
		jp3.add(jpan2);
		addRoom = new JButton("방만들기");
		addRoom.setBounds(210, 350, 90, 20);
		addRoom.addActionListener(this);
		ReBtn = new JButton("새로고침");
		ReBtn.setBounds(120,350,90,20);
		ReBtn.addActionListener(this);
		UsersPanel fp = new UsersPanel();
		scroller = new JScrollPane(fp);
	    scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    scroller.setBounds(0, 0, 187, 350);
	    jp2.add(scroller);
	    this.addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		gd.DelUser(Uname);
	    		System.exit(0);
	    	}
		});
//	    jp1.add(scroller);
		add(jp1);
		add(jp2);
		add(jp3);
		add(addRoom);
		add(ReBtn);
		setVisible(true);
		
	}
	public static void main(String[] args) {
//		new Main();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==addRoom) {
			dispose();
		new RoomCreate(Uname);
		}if(obj==ReBtn) {
			dispose();
//			login.Main = new Main(Uname);
			new Main(Uname);
			System.out.println(Uname);
			if(dump==1) {
				dispose();
			}
		}
	}
	public class JPan1 extends JPanel{
		public JPan1() {
			setBackground(Color.white);
			setLayout(new FlowLayout());
			JLabel jlimg = ReImage.Reimg("img/lgMu.gif", 100, 100);
			add(jlimg);
		}
	}
	public class JPan2 extends JPanel{
		public JPan2() {
			setBackground(Color.white);
			setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 30));
			setLayout(new FlowLayout());
			JLabel jl = new JLabel("닉네임 : "+Uname);
			jl.setFont(new Font("나눔", Font.BOLD, 15));
			jl.setForeground(new Color(204,204,255));
			String w_l = gd.S_W_L(Uname);
			JLabel jl2 = new JLabel("승/패 : "+w_l);
			jl2.setFont(new Font("나눔", Font.BOLD, 15));
			jl2.setForeground(new Color(204,204,255));
			add(jl);
			add(jl2);
		}
	}
}
