package main;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DB.GameDB;
import controll.Controller;
import controll.UpdatePack;
import controll.UserPack;

public class GameUserPanel extends JPanel implements ActionListener{
	public static int SAVEUSER =0;
	public static int BtnClick = 0;
	public static int getBtnClick() {
		return BtnClick;
	}

	private GameRoom room;

    /**
     * 방에 입장시킴
     * @param room  입장할 방
     */
//    public void enterRoom(GameRoom room) {
//		room.enterUser(this); // 룸에 입장시킨 후
//		this.room = room; // 유저가 속한 방을 룸으로 변경한다.(중요)
//	}
	public static int getSAVEUSER() {
		return SAVEUSER;
	}

	public static void setSAVEUSER(int sAVEUSER) {
		SAVEUSER = sAVEUSER;
	}

	public JButton ready;
	public JTextField Chat_jt;
	private String Rname;
	private JLabel uName;
	private String userName;
	private Controller controller;
	public String N_get() {
		String Nget = uName.getText();
		return Nget;
	}
	public GameUserPanel(String Uname,String Rname) {
		this.Rname=Rname;
		this.userName=Uname;
		setLayout(null);
		setBackground(Color.white);
		Chat_jt = new JTextField();
//		Chat_jt.setEditable(false);
//		Chat_jt.setVisible(false);
		Chat_jt.setBounds(0,0,160,40);
	ImageIcon icon = new ImageIcon("Img/lgMu.gif");
	Image img = icon.getImage();
	Image changeImg = img.getScaledInstance(50, 50, Image.SCALE_FAST);
	ImageIcon changeIcon = new ImageIcon(changeImg);
	JLabel imgjl = new JLabel(changeIcon);
	uName = new JLabel(Uname);
	ready = new JButton("ready");
	ready.addActionListener(this);
	imgjl.setBounds(5, 45, 75, 75);
	uName.setBounds(5, 120, 160, 20);
	ready.setBounds(5, 145, 160, 35);
	add(imgjl);
	add(uName);
	add(ready);
	add(Chat_jt);
	setVisible(true);
}
	@Override
	public void actionPerformed(ActionEvent e) {
		GameDB gd = new GameDB();
		int Rready = gd.getRready(Rname);
		Rready++;
		gd.upReady(Rname, Rready);
		String bClick = Integer.toString(Rready);
		controller = Controller.getInstance();
		for (int i = 0; i < controller.getGr_list().size(); i++) {
			if(controller.getGr_list().get(i).getRname().equals(Rname)) {
				UpdatePack upack = new UpdatePack(userName, Rname,bClick,"0");
				controller.clientSocket.DataUpdate(controller.getGr_list().get(i), upack,null);
			}
		}
	}
}
