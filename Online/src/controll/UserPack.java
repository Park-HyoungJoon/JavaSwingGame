package controll;

import java.io.Serializable;
import java.util.ArrayList;

import main.GameRoom;
import main.GameRoomFrame;
import main.GameUserPanel;
import main.RoomCreate;

@SuppressWarnings("serial")
public class UserPack implements Serializable{
    // serialVersionUID는 통신하는 자바 시스템간 동일해야 합니다.
    private static final long serialVersionUID = 1L;
	private String Uname;
	private String Rname;
	private String Start;
	public String getStart() {
		return Start;
	}
	public void setStart(String start) {
		Start = start;
	}
	public UserPack(String Uname,String Rname,String Start) {
		this.Uname = Uname;
		this.Rname = Rname;
		this.Start = Start;
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
}
