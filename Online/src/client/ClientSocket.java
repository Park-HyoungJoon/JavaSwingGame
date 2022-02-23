package client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutorService;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import DB.GameDB;
import Timer.TimerTest;
//import controll.Message;
import controll.UpdatePack;
import controll.UserPack;
import main.Exclude;
import main.GameListPanel;
import main.GameRoom;
import main.GameRoomFrame;
import main.GameUserPanel;
import main.RoomCreate;
import server.ServerHandler;
import controll.Controller;
public class ClientSocket {
	Controller controller;
	  ExecutorService executorService; // 스레드풀인 ExecutorService 선언

  Socket socket;
private BufferedReader br;
private PrintWriter pw;
private ObjectInputStream ois;
private static int ReadyCount = 0;
public static int getReadyCount() {
	return ReadyCount;
}

public static void setReadyCount(int readyCount) {
	ReadyCount = readyCount;
}
List<GameUserPanel> user_pan = new Vector<>();
List<String> Gname_list = new Vector<>();
GameRoomFrame grf;

  public GameRoomFrame getGrf() {
	return grf;
}

public void setGrf(GameRoomFrame grf) {
	this.grf = grf;
}

public void startClient() {
    
    Thread thread = new Thread(()->{
      try {
        socket = new Socket(); // 소켓 생성
        socket.connect(new InetSocketAddress("localhost", 5000)); // 연결 요청
        // -> socket 생성 및 연결 요청
      } catch (IOException e) {
        System.out.println("서버 통신 안됨");
        e.printStackTrace();
      }	
    });
    
    thread.start();

  }

  public void stopClient() {

    try {
      if (socket != null && !socket.isClosed()) {
        socket.close();
      }
    } catch (IOException e) {
    }
  }
  public void DataUpdate(GameRoom gr, UpdatePack up,UserPack user_pack) {
	  if(user_pack==null) {//정보가 UpdatePack 객체에 있는 정보로 전달받은 건지, UserPack 정보로 전달받은 건지 구별
	  controller = Controller.getInstance();
	  GameDB gd = new GameDB();
	  String rQuest = gd.RQuest();
	  Thread thread = new Thread(() -> {
		  try {
		  int count = gd.RoomCount();
		  //상대방에게 전송할 수 있는 스크림 핸들러를 얻는다.
		  pw = new PrintWriter(socket.getOutputStream());
		  ArrayList<UpdatePack> upack = new ArrayList<>();
		  upack.add(up);
		  JSONObject jo = new JSONObject();
		  jo.put("list",upack); //list라는 key에 upack(객체)을 value로 넣음
		  System.out.println(jo.get("list"));
		  pw.println(new Gson().toJson(jo));
		  pw.flush(); //pw에 println으로 저장해놓은 값을 server로 밀어넣는 느낌
		  try {
			  while(true) {
				  //socket.getInputSream으로 들어온 값을 Reader기를 통해 읽어 BufferedReader 즉, 버퍼단위로 저장
			  br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			  //버퍼 단위로 저장되어 있는 값을 라인 단위로 String 변수에 저장
			  String gp = br.readLine();
			  System.out.println(gp+"여기로 받아지는건 아니지 그치?..");
			  if(gp.contains("readyCount")&&!gp.contains("message")) {
			  Gson gson = new Gson();
			  //Gson 형식으로 바꿔 JSONObject에 저장
	        	JSONObject json = gson.fromJson(gp, JSONObject.class);
	        	//list껍데기를 벋겨냄
	        	List<Map<String,String>> list1 = (ArrayList<Map<String,String>>)json.get("list");
	        	Map<String,String> map1 = list1.get(0);
	        	//map1 key , value
	        	UpdatePack up2 = new UpdatePack((String)map1.get("Uname"), (String)map1.get("Rname"), 
	        			(String)map1.get("readyCount"), (String)map1.get("StartCount"));
	        	gr.LookReady(controller.username,gr,up2);///바꾸고 싶은 내용을 가진 각 객체를 각 클라이언트의 함수로 전달,
			  }
			  else if(!gp.contains("readyCount")&&!gp.contains("message")) {
				  System.out.println("야!!!! 왜!!!!! ");
		  System.out.println(gp);
		  Exclude ex = new Exclude();
		  Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addDeserializationExclusionStrategy(ex).create();
		 // Gson gson = new GsonBuilder().generateNonExecutableJson().create();
		//  Gson gson = new Gson();
		  System.out.println("실행됨");
        	JSONObject json = gson.fromJson(gp, JSONObject.class);
        	  System.out.println("실행됨2");
		        	List<Map<String,String>> list1 = (ArrayList<Map<String,String>>)json.get("list");
        	System.out.println("1111" + list1.get(0).toString());
        	Map<String,String> map1 = list1.get(0);
        	System.out.println(map1.get("Start")+"startstartstart");
        	if(map1.get("Start").equals("1")) {
        		GameDB db = new GameDB();
        		System.out.println(map1.get("Rname") + " 선긋기" + db.SrchR(controller.username));
        		Thread tr = new TimerTest();
        		pw.flush();
        		tr.start();
        		System.out.println("얘로 넘어가야 넘어가지는겅미");}
			  }
			  else if(gp.contains("message")) {
				  System.out.println(gp);
				  System.out.println("메시지 실행중..");
				  Exclude ex = new Exclude();
				  Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addDeserializationExclusionStrategy(ex).create();
				  JSONObject json = gson.fromJson(gp, JSONObject.class);
			      	List<Map<String,String>> list1 = (ArrayList<Map<String,String>>)json.get("list");
			    	Map<String,String> map1 = list1.get(0);
			    	UpdatePack up4 = new UpdatePack((String)map1.get("Uname"),(String)map1.get("Rname"), (String)map1.get("message"),(String)map1.get("Rquest"),1);
				    System.out.println((String)map1.get("Rquest")+"////////보내지는 퀘스트!!!!!!!!!");
			    	gr.chat(up4);
			  }
			  }
		  }catch (Exception e) {
			  e.printStackTrace();
		  }
		  }catch (Exception e) {
			  e.printStackTrace();
		  }
	  });
	  thread.start();
  }
	  else {

			controller = Controller.getInstance();
			GameDB db = new GameDB();
			
			    Thread thread2 = new Thread(()->{
				  try {
					  pw = new PrintWriter(socket.getOutputStream());
					  ArrayList<UserPack> list = new ArrayList<>();
					  list.add(user_pack);
					  JSONObject jo = new JSONObject();
					  jo.put("list", list);
					  pw.println(new Gson().toJson(jo));
					  pw.flush();
					  System.out.println("여기까지는 됨??");
					  while(true) {					  
						  try {	
								  br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
								  System.out.println("1번과 2번 실행 ");
								  String gp = br.readLine(); //클라 -> 서버 -> 클라 과정을 거쳐 객체 정보가
								  //각 클라이언트에게 넘어옴
								  System.out.println(gp);
								  if(!gp.contains("message")) { //{"list":[{"Uname":"2","Rname":"gd","Start":"0"}]} 이 문장에서 message key가 있는지 없는지 구별

							  System.out.println(gp);
							  Exclude ex = new Exclude();
							  Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addDeserializationExclusionStrategy(ex).create();
							 // Gson gson = new GsonBuilder().generateNonExecutableJson().create();
							//  Gson gson = new Gson();
							  System.out.println("실행됨");
					        	JSONObject json = gson.fromJson(gp, JSONObject.class);
					        	  System.out.println("실행됨2");
							        	List<Map<String,String>> list1 = (ArrayList<Map<String,String>>)json.get("list");
					        	System.out.println("1111" + list1.get(0).toString());
					        	Map<String,String> map1 = list1.get(0);
					        	System.out.println(map1.get("Start")+"startstartstart");
					        	if(map1.get("Start").equals("1")) {
					        		System.out.println(map1.get("Rname") + " 선긋기" + db.SrchR(controller.username));
					        		Thread tr = new TimerTest();
					        		pw.flush();
					        		tr.start();
					        		System.out.println("얘로 넘어가야 넘어가지는겅미");
					        	}
					        	else {
					        	GameUserPanel gp2 = new GameUserPanel(map1.get("Uname"),map1.get("Rname"));
					        	ArrayList<String> U_R  = db.searchU_R(map1.get("Rname"));
					        	
					        	ArrayList<GameUserPanel> user_pan = controller.getUser_pan();
					        	user_pan.add(gp2);
					        	
					        	controller.setUser_pan(user_pan);
					        	ArrayList<GameRoom> gr_list = controller.getGr_list();
					        	gr.addP_Panel(controller.username,map1.get("Rname"));// 현재 클라이언트 즉, 사용자의 이름과 , 들어갈 방 이름을 전달
					        
					        	}
						  }
								  else if(gp.contains("message")) {
									  System.out.println(gp);
									  System.out.println("메시지 실행중..");
									  Exclude ex = new Exclude();
									  Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addDeserializationExclusionStrategy(ex).create();
									  JSONObject json = gson.fromJson(gp, JSONObject.class);
								      	List<Map<String,String>> list1 = (ArrayList<Map<String,String>>)json.get("list");
								    	Map<String,String> map1 = list1.get(0);
								    	UpdatePack up2 = new UpdatePack((String)map1.get("Uname"),(String)map1.get("Rname"),(String)map1.get("message"),(String)map1.get("Rquest"),1);
			  			        		gr.chat(up2);
								  }  
							  }
							  
							  catch (Exception e) {
							  e.printStackTrace();
						  }
					  }
					  
				  }catch (Exception e) {
					  e.printStackTrace();
				  }
			    });
			    thread2.start();
		        
	  }
  }
}
