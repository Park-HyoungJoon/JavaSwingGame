package main;


import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.mysql.cj.x.protobuf.MysqlxNotice.Frame;

import DB.GameDB;
import controll.UserPack;
import controll.Controller;
import main.RoomCreate;
@SuppressWarnings("serial")
public class GameListPanel extends JPanel{
	public GameRoomFrame grf;
	Login login;
	Controller controller;

  private ArrayList<String> GRname = new ArrayList<>(); // 친구들 이름 저장

  public static ArrayList<JButton> friendButtons = new ArrayList<JButton>(); // 친구들 정보 버튼 저장

  public static ArrayList<JButton> getFriendButtons() {
	return friendButtons;
}
  private ArrayList<GameRoom> gameRoom = new ArrayList<GameRoom>();
public static void setFriendButtons(ArrayList<JButton> friendButtons) {
	GameListPanel.friendButtons = friendButtons;
}

private final int FRIEND_PROFILE_IMG_MAX = 8;

  private final int FRIEND_PROFILE_IMG_MIN = 1;

private int i;

private ArrayList gr_room;

  public GameListPanel(String Uname,Main m) {
		controller = Controller.getInstance();
      friendButtons.clear();
	  	  try {
	  		 gr_room = new ArrayList<>();
		 GameDB gd = new GameDB();
		 ArrayList<String> gameList = gd.gameList(); 
	    int friendNum = gameList.size();
	    setLayout(new GridLayout(friendNum, 0));
	    for (int index = 0; index < friendNum; index++) {
	      UserProfileButton userprofileButton = new UserProfileButton();
	      userprofileButton.setText(gameList.get(index)+"            "+gd.SearchRC(gameList.get(index))+"/4");
	      GRname.add(gameList.get(index));
	      GameRoom gr = new GameRoom(gameList.get(index));
	      gr_room.add(gr);
	      add(userprofileButton);
	      friendButtons.add(userprofileButton);	      
	    }
	    controller.setGr_list(gr_room);
	    for (i = 0; i < friendNum; i++) {
	    	System.out.println("이 친구좀 보시게 왜 안되는가?");
	      friendButtons.get(i).putClientProperty("page", i);
	      friendButtons.get(i).addActionListener(new ActionListener() {	
	        private GameRoom gr;

			@Override
	        public void actionPerformed(ActionEvent e) {
	            int idx = (Integer) ((JButton) e.getSource()).getClientProperty("page");
	            //유저창 Rname 업데이트
//	            gd.InUser(Uname, GRname.get(idx));
	            //방 인원수 증가
	            int RCount = gd.SearchRC(GRname.get(idx));
	            RCount++;
	            gd.HowRC(GRname.get(idx), RCount);
	            UserPack up = new UserPack(Uname, GRname.get(idx),"0");//유저이름 , 방이름,Start=0의 정보를 보냄.
	            ArrayList<GameRoom> gr_list = controller.getGr_list();
	            for (int i = 0; i < gr_list.size(); i++) {
	            	if(GRname.get(idx).equals(gr_list.get(i).getRname())) { 
	            		gd.InUser(Uname, GRname.get(idx));
	            		m.dispose();
	            		grf = new GameRoomFrame(gr_list.get(i), "f"); 	
					controller.clientSocket.DataUpdate(gr_list.get(i),null,up); //clientsocket의 DattaUpdate 함수에 현재 폼 정보와 객체 값을 전달
					}
				}
					
				
	            
	        }
	      });
	    }
	  }catch (Exception e) {
		  
	  }
	  }

}
