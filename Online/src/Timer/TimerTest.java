package Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controll.UpdatePack;
import main.GameRoom;

public class TimerTest extends Thread{
	static int oldTime;
	public static String timeBuffer="03:00:00";
	public static String getTimeBuffer() {
		return timeBuffer;
	}

	static int hour,min,sec;	
	@Override
	public void run() {
		try {
			stopwatch(1);
			while(!timeBuffer.equals("00:00:00")) {
			Thread.sleep(1000);
			stopwatch(0);
			System.out.format("Timer OFF! 경과시간 : [%s]%n",timeBuffer);
			GameRoom.jl.setText(timeBuffer);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Thread tr = new TimerTest();
		tr.start();
	}
	public static void stopwatch(int off) {
		if(off==1) {
			oldTime = (int)System.currentTimeMillis()/1000;
		}
		
		if(off==0) {
			secToHHMMSS((int)System.currentTimeMillis()/1000-oldTime);
		}
	}
	public static void secToHHMMSS(int secs) {
		System.out.println(secs);
		sec =60 -( secs%60);
		if(sec==60) sec=sec-60;
		min=2 - ((secs-1)/60%60);
		hour=secs/3600;
		timeBuffer = String.format("%02d:%02d:%02d", hour,min,sec);
	}
	
	public static void pause() {
		try {
			System.in.read();
		}catch (IOException e) {
			// TODO: handle exception
		}
	}
}
