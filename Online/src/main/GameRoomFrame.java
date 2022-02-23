package main;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import DB.GameDB;
import controll.Controller;

@SuppressWarnings("serial")
public class GameRoomFrame extends JFrame{
    Controller controller = Controller.getInstance();

	  private String frameName;

	  public GameRoomFrame(GameRoom gr, String frameName) {

	    
	    this.frameName = frameName;

	    setTitle(controller.username + "의 Chatting");
	    setLayout(new GridLayout(0,1));
	    setDefaultCloseOperation(3);
	    setLocationRelativeTo(null);
		setSize(700,800);
		getContentPane().add(gr);
	    setResizable(false);
	    setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				GameDB gd = new GameDB();
				int RC = gd.SearchRC(gr.getRname());
				if(RC==1) {
					gd.DelRoom(gr.getRname());		
					dispose();
					new Main(controller.username);
				}
				else
					RC--;
				gd.HowRC(gr.getRname(), RC);
				gd.DelRoom(gr.getRname());
				dispose();
				new Main(controller.username);
			}
		});
	  }

	  public String getFrameName() {

	    return frameName;
	  }

	  public void setFrameName(String frameName) {

	    this.frameName = frameName;
	  }

}
