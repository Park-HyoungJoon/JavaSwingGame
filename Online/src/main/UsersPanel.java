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

import DB.GameDB;

@SuppressWarnings("serial")
public class UsersPanel extends JPanel {

  private ArrayList<String> friends; // 친구들 이름 저장

  private ArrayList<ImageIcon> friendIcons = new ArrayList<ImageIcon>(); // 친구들 프로필 이미지 저장

  public static ArrayList<JButton> friendButtons = new ArrayList<JButton>(); // 친구들 정보 버튼 저장

  private final int FRIEND_PROFILE_IMG_MAX = 8;

  private final int FRIEND_PROFILE_IMG_MIN = 1;

  public UsersPanel() {
	 GameDB gd = new GameDB();
	 ArrayList<String> userList = gd.UserList(); 
    int friendNum = userList.size();
    setLayout(new GridLayout(friendNum, 0));
    for (int index = 0; index < friendNum; index++) {
      UserProfileButton userprofileButton = new UserProfileButton();
      userprofileButton.setText(userList.get(index));
      add(userprofileButton);
      friendButtons.add(userprofileButton);
//      ActionListener action = new ActionListener() {
//
//          @Override
//          public void actionPerformed(ActionEvent e) {
//        	  
//          }
//	};
    }
//    for (int i = 0; i < friendNum; i++) {
//    	System.out.println("이 친구좀 보시게 왜 안되는가?");
//      friendButtons.get(i).putClientProperty("page", i);
//      friendButtons.get(i).addActionListener(new ActionListener() {
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//        }
//      });
//    }
  }
}
