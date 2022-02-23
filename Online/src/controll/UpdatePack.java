package controll;

import java.io.Serializable;
import java.util.ArrayList;

import DB.GameDB;
import main.GameRoom;
import main.GameRoomFrame;
import main.GameUserPanel;
import main.RoomCreate;

@SuppressWarnings("serial")
public class UpdatePack implements Serializable{
    // serialVersionUID는 통신하는 자바 시스템간 동일해야 합니다.
    private static final long serialVersionUID = 1L;
    private String readyCount;
	private String startCount;
	private String Uname;
	private String Rname;
	private String message;
	private String Rquest;

	public UpdatePack(String rc) {
		readyCount=rc;
	}

	public UpdatePack(String Uname,String Rname,String message) {
		GameDB db = new GameDB();
		String rQuest = db.RQuest();
		this.Uname=Uname;
		this.Rname=Rname;
		this.message = message;
		this.Rquest=rQuest;
	}
	public UpdatePack(String Uname,String Rname,String message,String rQuest,int a) {
		this.Uname=Uname;
		this.Rname=Rname;
		this.message = message;
		this.Rquest=rQuest;
	}
	public UpdatePack(String Uname,String Rname) {
		this.Uname=Uname;
		this.Rname=Rname;
	}
	public UpdatePack(String Uname,String Rname,String readyCount,String startCount) {
		this.Uname=Uname;
		this.Rname=Rname;
		this.readyCount = readyCount;
		this.startCount = startCount;
	}
    public String getReadyCount() {
		return readyCount;
	}
	public void setReadyCount(String readyCount) {
		this.readyCount = readyCount;
	}
	public String getStartCount() {
		return startCount;
	}
	public void setStartCount(String startCount) {
		this.startCount = startCount;
	}
	public String getUname() {
		return Uname;
	}
	public void setUname(String uname) {
		Uname = uname;
	}
	public String getRname() {
		return Rname;
	}
	public void setRname(String rname) {
		Rname = rname;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRquest() {
		return Rquest;
	}

	public void setRquest(String rquest) {
		Rquest = rquest;
	}

}
