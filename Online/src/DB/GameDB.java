package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import client.ClientSocket;
import controll.Controller;

public class GameDB {  
private static Connection conn; //DB 커넥션 연결 객체
private static final String USERNAME = "root";//DBMS접속 시 아이디
private static final String PASSWORD = "vhtmxm55%%";//DBMS접속 시 비밀번호
private static final String URL = "jdbc:mysql://localhost:3306/game?serverTimezone=UTC";//DBMS접속할 db명
private Controller controller = Controller.getInstance();
private PreparedStatement pstmt;
private Statement stmt;
public void connect() {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		   conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		      } catch(Exception e) {
		    	  e.printStackTrace();
		 }
	}
public void disconnect() {
	try {
		if(stmt!=null)stmt.close();
		if(pstmt!=null)pstmt.close();
		if(conn!=null)conn.close();
	}catch (SQLException e) {
		e.printStackTrace();
	}
}
//유저 추가
public String insertUser() {
	connect();
	String sql = "Insert into User values(?,0,0,'')";
	String sql2 = "Select Uname from User";
	int value = 0;
	String value2 = null;
	int s2=0;
	try {
		pstmt = conn.prepareStatement(sql2);
		ResultSet rs = pstmt.executeQuery();
		Vector<String> v = new Vector<>();
		v.clear();
		int i=0;
		while(rs.next()) {
			v.add(rs.getString("Uname"));
			System.out.println(rs.getString("Uname"));
		i++;
		}
		for (int j = 0; j < i; j++) {
			String s = v.get(j);
			s2 = Integer.parseInt(s);
			if(value<s2) {
				if(s2!=(value+1)) {
					value=value+1;
					break;
				}
				else
				value=s2;
			}
		}
		if(value==s2)
		value++;
		
		v.clear();
		value2 = Integer.toString(value);
		PreparedStatement pstmt2 = conn.prepareStatement(sql);
		pstmt2.setString(1, value2);
		pstmt2.executeUpdate();
		controller.clientSocket.startClient();
	}catch (SQLException e) {
		e.printStackTrace();
	}
	
	return value2;
}


//방 인원 수 search
public int SearchRC(String Rname) {
	connect();
	ResultSet rs = null;
	int rc = 0;
	String sql = "select RCount from room Where Rname ='"+Rname+"'";
	try {
		stmt=conn.createStatement();
		rs = stmt.executeQuery(sql);
		while(rs.next()) {
			System.out.println(rs.getInt(1));
			rc = rs.getInt(1);
		}
	}catch(SQLException e) {
		e.printStackTrace();
	}
	disconnect();
	return rc;
}
//방 인원 수 업데이트
public boolean HowRC(String RName, int RNum) {
	connect();
	String sql = "UPDATE room set Rcount = '"+RNum+"' WHERE Rname ='"+RName+"'";
	boolean kc = false;
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		kc=true;
	}
	catch (SQLException e) {
		e.printStackTrace();
	}
	disconnect();
	return kc;
}
// 방 삭제
public boolean DelRoom(String Rname) {
	connect();
	String sql = "delete from room where Rname = '"+Rname+"'";
	String sql2 = "Update user set Rname = null where Rname = '"+Rname+"'";
	boolean kc =false;
	try {
		pstmt = conn.prepareStatement(sql2);
		pstmt.executeUpdate();
		pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		kc=true;
	}catch (SQLException e) {
		e.printStackTrace();
	}
	disconnect();
	return kc;
}
//유저 정보창 ArrayList
public ArrayList<String> UserList(){
	connect();
	ArrayList<String> userList = new ArrayList<String>();
	userList.clear();
	String sql = "select Uname from User";
	try {
		pstmt=conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			userList.add(rs.getString("Uname"));
		}
	}catch(SQLException e) {
		e.printStackTrace();
	}
	disconnect();
	return userList;
}
public ArrayList<String> gameList(){
	connect();
	ArrayList<String> gameList = new ArrayList<>();
	gameList.clear();
	String sql = "select Rname from room";
	try {
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			gameList.add(rs.getString("Rname"));
			System.out.println(rs.getString("Rname"));
		}
	}catch (SQLException e) {
		e.printStackTrace();
	}
	return gameList;
}
//방 만들기 클릭 시 
public boolean insertRoom(String Rname,String F_user) {
	connect();
	String sql = "Insert into Room values(?,1,?,0)";
	boolean isInsert =false;
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, Rname);
		pstmt.setString(2, F_user);
		pstmt.executeUpdate();
		isInsert = true;
	}catch (SQLException e) {
		e.printStackTrace();
	}
	disconnect();
	return isInsert;
}
//랜덤 quest 출력
public String RQuest() {
	connect();
	String sql = "select question from Quest";
	String Rq =null;
	try {
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		Vector<String> v = new Vector<>();
		v.clear();
		int i=0;
		while(rs.next()) {
			v.add(rs.getString("question"));
			i++;
		}
		Random r = new Random();
	int Ran = r.nextInt(i);
	Rq = v.get(Ran);
	}catch(SQLException e) {
		e.printStackTrace();
	}
	disconnect();
	return Rq;
}
//방 업데이트
public boolean UpU_R(String user,String Rname) {
	connect();
	String sql = "Update user set Rname = '"+Rname+"' where Uname ='"+user+"'";
	boolean tk = false;
	try {
		pstmt= conn.prepareStatement(sql);
		int rs = pstmt.executeUpdate();
		tk = true;
	}catch (SQLException e) {
		e.printStackTrace();
	}
	disconnect();
	return tk;
}
//유저 삭제
public boolean DelUser(String Rname) {
	connect();
	String sql = "Delete From User where Uname = '"+Rname+"'";
	boolean isDel = false;
	try {
		pstmt = conn.prepareStatement(sql);
		int r = pstmt.executeUpdate();
		isDel=true;
	}catch (SQLException e) {
		e.printStackTrace();
	}
	return isDel;
	
}
//Win,Lose 검색
public String S_W_L(String Uname) {
	connect();
	String win=null;
	String lose=null;
	String w_l = null;
	String sql = "select Uwin from User where Uname = '"+Uname + "'";
	String sql2 = "select Ulose from User where Uname = '"+Uname + "'";
	try {
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			win = rs.getString(1);
		}
		PreparedStatement pstmt2 = conn.prepareStatement(sql2);
		ResultSet rs2 = pstmt2.executeQuery();
		while(rs2.next()) {
			lose=rs2.getString(1);
		}
		w_l = win+"/"+lose;
	}catch (SQLException e) {
		e.printStackTrace();
	}
	return w_l;
}
//방 들어갈시 user
public boolean InUser(String Uname,String Rname) {
	connect();
	String sql = "Update User set Rname='"+Rname+"' Where Uname = '"+Uname + "'";
	boolean ck = false;
	try {
		pstmt = conn.prepareStatement(sql);
		int rs = pstmt.executeUpdate();
		ck = true;
	}catch (SQLException e) {
		e.printStackTrace();
	}
	return ck;
}
public static ResultSet getResultSet(String sql) {
	try {
		Statement stmt = conn.createStatement();
		return stmt.executeQuery(sql);
	} catch (Exception e) {
		System.out.println(e);
		return null;
	}
}
public ArrayList<String> searchU_R(String Rname){
connect();
ArrayList<String> S_U = new ArrayList<>();
String sql = "select Uname from user Where Rname = '"+Rname+"'";
PreparedStatement pstmt = null;
ResultSet rs = null;
try {
	pstmt = GameDB.conn.prepareStatement(sql);
	rs = pstmt.executeQuery();
	while(rs.next()) {
		S_U.add(rs.getString("Uname"));
	}
}catch (SQLException e) {
	e.printStackTrace();
}
disconnect();
return S_U;
}

public String SrchR(String Uname) {
	connect();
	String Rname="";
	String sql = "select Rname from user where Uname='"+Uname+"'";
	try {
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
		Rname = rs.getString("Rname");}
	}catch (SQLException e) {
		e.printStackTrace();
	}
	return Rname;
}
//방 숫자
public int RoomCount() {
	connect();
	int count = 0;
	String sql = "select Rname from room";
	try {
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			count++;
		}
	}catch (SQLException e) {
		e.printStackTrace();
	}
	return count;
}
//방장 구하기
public String getFirstUser(String Rname) {
	connect();
	String RoomOner=null;
	String sql = "select FirstUser from room where Rname= '"+Rname+"'";
	try {
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			RoomOner = rs.getString("FirstUser");
		}
	}catch (SQLException e) {
		e.printStackTrace();
	}
	return RoomOner;
}
//ready 값 구하기
public int getRready(String Rname) {
	connect();
	int Rready = 0;
	String sql = "select Rready from room where Rname = '"+Rname+"'";
	try {
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			Rready = rs.getInt("Rready");
		}

	}catch (SQLException e) {
		e.printStackTrace();
	}
	return Rready;	
}
//ready 값 업데이트
public boolean upReady(String Rname,int Rready) {
	connect();
	String sql= "update room set Rready = '"+Rready+"' where Rname = '"+Rname+"'";
	boolean k = false;
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
	}catch (SQLException e) {
		e.printStackTrace();
	}
	return k;
}
}
