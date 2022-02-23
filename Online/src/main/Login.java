package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import DB.GameDB;
import Timer.TimerTest;
import client.ClientSocket;
import controll.Controller;

public class Login extends JFrame implements ActionListener {
	static Timer.TimerTest time = new TimerTest();
	private RoundedButton rb;
	String imgOri = "Img/lgMu.gif";
	public Main Main;
	public Login() {
		setTitle("로그인 화면");
		
		setSize(500, 500);
		setDefaultCloseOperation(3);
		setLocationRelativeTo(null);
		setLayout(null);
		JPanel jp1 = new JPanel();
		jp1.setBounds(0, 0, 500, 300);
		
		jp1.setBackground(Color.white);
		jp1.setLayout(null);
		JLabel cen_jl = new JLabel("끄투 온라인");
		
	
		ImageIcon icon = new ImageIcon("Img/helloMu.gif");
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(100, 100, Image.SCALE_FAST);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		JLabel imgjl = ReImage.Reimg("Img/helloMu.gif",100,100);
		imgjl.setBounds(159, 10, 120, 120);
		cen_jl.setForeground(Color.PINK);
		
//		cen_jl.setBorder(new EmptyBorder(150, 0, 0, 0));
		cen_jl.setFont(new Font("나눔", Font.BOLD, 50));
		cen_jl.setBounds(110, 100, 300, 100);
		jp1.add(cen_jl);
		jp1.add(imgjl);
		JPanel jp2 = new JPanel();
		jp2.setLayout(new FlowLayout(FlowLayout.CENTER));
		jp2.setBounds(0, 300, 500, 200);
		jp2.setBackground(Color.white);
		rb = new RoundedButton("비회원 로그인");
		rb.setPreferredSize(new Dimension(300,100));
		rb.addActionListener(this);
		jp2.add(rb);
		add(jp1);
		add(jp2);
		setVisible(true);
		
	}
	public void timeset() {
		
		
	}
	public static void main(String[] args) {
//		 javax.swing.SwingUtilities.invokeLater(new Runnable()
//         {public void run(){Login();}});
		new Login(); 
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==rb) {
			GameDB gb = new GameDB();
			String uName = gb.insertUser();
			Controller controller = Controller.getInstance();
			controller.setUsername(uName);
			dispose();
			Main = new Main(uName);
		}
	}

}
