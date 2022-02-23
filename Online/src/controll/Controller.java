package controll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import DB.GameDB;
import client.ClientSocket;
import main.GameListPanel;
import main.GameRoom;
import main.GameRoomFrame;
import main.GameUserPanel;
import main.RoomCreate;
public class Controller {

	private static Controller singleton = new Controller();

	public String username = null;
	private static int rdyCount = 0;
	public static int getRdyCount() {
		return rdyCount;
	}

	public static void setRdyCount(int rdyCount) {
		Controller.rdyCount = rdyCount;
	}

	private GameRoomFrame grf;
	public GameRoomFrame getGrf() {
		return grf;
	}

	public void setGrf(GameRoomFrame grf) {
		this.grf = grf;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String useremail = null;

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public ClientSocket clientSocket;
	GameDB GameDB;

	private Controller() {

		clientSocket = new ClientSocket();

		GameDB = new GameDB();

	}

	private static ArrayList<GameRoom> gr_list = new ArrayList<>();

	public  ArrayList<GameRoom> getGr_list() {
		return gr_list;
	}

	public void setGr_list(ArrayList<GameRoom> gr_list) {
		Controller.gr_list = gr_list;
	}

	public ArrayList<GameUserPanel> user_pan = new ArrayList<>();

	public ArrayList<GameUserPanel> getUser_pan() {
		return user_pan;
	}

	public void setUser_pan(ArrayList<GameUserPanel> user_pan) {
		this.user_pan = user_pan;
	}

	public static Controller getInstance() {

		return singleton;
	}
	private ArrayList<String> Rname = new ArrayList<>();

	public ArrayList<String> getRname() {
		return Rname;
	}

	public void setRname(ArrayList<String> rname) {
		Rname = rname;
	}
	}
