package main;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ReImage {
	public static JLabel Reimg(String imgURL,int width,int height) {
		ImageIcon icon = new ImageIcon(imgURL);
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(width, height, Image.SCALE_FAST);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		JLabel imgjl = new JLabel(changeIcon);
		return imgjl;
	}
}
