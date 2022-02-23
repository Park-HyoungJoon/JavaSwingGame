package main;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class UserProfileButton extends JButton{

  public UserProfileButton() {
    setFont(new Font("맑은 고딕", Font.BOLD, 18));
    setBackground(new Color(204,204,255));
    
    setHorizontalAlignment(SwingConstants.LEFT);
    setFocusPainted(false);
    
    Border emptyBorder = BorderFactory.createEmptyBorder();
    setBorder(emptyBorder);
    
  }
}

