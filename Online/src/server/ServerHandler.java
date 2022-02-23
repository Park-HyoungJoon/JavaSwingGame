package server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;

import com.google.gson.Gson;

import DB.GameDB;
//import controll.Message;
import controll.UpdatePack;
import controll.UserPack;
import main.GameRoomFrame;
import main.GameUserPanel;


public class ServerHandler {

  ExecutorService executorService; // 스레드풀인 ExecutorService 선언

  ServerSocket serverSocket; // 클라이언트 연결 수락

  List<Client> connections = new Vector<Client>(); // 연결되어있는 클라이언트들

  List<GameRoomFrame> gr_list = new Vector<>();
  
  public void startServer() {

    ExecutorService threadPool = new ThreadPoolExecutor(10, // 코어 스레드 개수
        100, // 최대 스레드 개수
        120L, // 놀고 있는 시간
        TimeUnit.SECONDS, // 놀고 있는 시간 단위
        new SynchronousQueue<Runnable>() // 작업 큐
    ); // 초기 스레드 개수 0개,
    executorService = threadPool;
    try {
      serverSocket = new ServerSocket();
      serverSocket.bind(new InetSocketAddress(5000));
      System.out.println("서버 연결 기다림");
      // -> serverSocket 생성 및 포트 바인딩
    } catch (Exception e) {
    	System.out.println("!");
      e.printStackTrace();
    }
    // 연결을 수락하는 코드
    Runnable runnable = new Runnable() { // 수락 작업 생성

      @Override
      public void run() {

        while (true) {
          try {
            Socket socket = serverSocket.accept(); // 클라이언트 연결 수락, client와 통신할 socket 리
            System.out.println("연결 수락: " + socket.getRemoteSocketAddress() + ": "
                + Thread.currentThread().getName());
            Client client = new Client(socket); // 클라이언트 객체에 저장.
            connections.add(client);
            System.out.println("연결 개수: " + connections.size());
          } catch (IOException e) {
            if (!serverSocket.isClosed()) { // serverSocket이 닫혀있지 않을 경우
              stopServer();
            }
            break;
          }
        }
      }
    };
    executorService.submit(runnable); // 스레드풀에서 처리.
  }

  public void stopServer() {

    try {
      Iterator<Client> iterator = connections.iterator(); // 모든 socket 닫기.
      while (iterator.hasNext()) {
        Client client = iterator.next();
        client.socket.close();
        iterator.remove();
      }
      if (serverSocket != null && !serverSocket.isClosed()) { // ServerSocket 닫기.
        serverSocket.close();
      }
      if (executorService != null && !executorService.isShutdown()) { // ExecutorService 종료.
        executorService.shutdown();
      }
    } catch (Exception e) {
    }
  }

  class Client {

    Socket socket;

    String userName;
    String Rname;

	private BufferedReader br;

	private PrintWriter pw;

    Client(Socket socket) {

      this.socket = socket;
      receive();
      
    }
    Client(String Rname){
    	this.Rname = Rname;
    }

    //서버로 패널 수 보내는 역할 
    public void receive() {
    Thread thread = new Thread(() ->{
  		  try {
  				  try {
  		  			  while(true) {
  					  br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  					  String gp = br.readLine();
  					  System.out.println("::::"+gp);
  			        	Gson gson = new Gson();
  			        	JSONObject json = gson.fromJson(gp, JSONObject.class);

  			        	List<Map<String,String>> list1 = (ArrayList<Map<String, String>>)json.get("list");
  			        	Map<String, String> map1 = list1.get(0);
  			        	if(gp.contains("readyCount") && !gp.contains("message")) {
  			        		UpdatePack up2 = new UpdatePack((String)map1.get("Uname"), (String)map1.get("Rname")
  			        				,(String)map1.get("readyCount"),
  			        				(String)map1.get("startCount"));
  			        	
//  			        		send3(up2);
  			        		for (Client client : connections) { // 모든 클라이언트에게 보냄
  		  		                if (client.userName != null) {
  		  		                	GameDB gd = new GameDB();
  		  		                	if(up2.getRname().equals(gd.SrchR(client.userName)))
  		  		                	  client.send3(up2);
  		  		                    System.out.println("client go send3");
  		  		             
  		  		                }
  			        		}
  			        	}
  			        	else if(!gp.contains("readyCount")&&!gp.contains("message")){
  			        	UserPack up2 = new UserPack((String)map1.get("Uname"), (String)map1.get("Rname"),(String)map1.get("Start"));
  			        	
  			        	userName= up2.getUname();
//  			        	send2(up2); // 본인한테 보
  		              for (Client client : connections) { // 모든 클라이언트에게 보냄
  		                if (client.userName != null) {
  		                	System.out.println((String)map1.get("Start"));
  		                	GameDB gd = new GameDB();
  		                	if(up2.getRname().equals(gd.SrchR(client.userName)))
  		                	  client.send2(up2);
  		                    System.out.println("얘가 실행돼야 하는데");
  		                }
  		              }
  				  }
  			        	else if(gp.contains("message")) {
  			        		System.out.println("실행 되는 건가요? message2입니다.");
  			        		UpdatePack up2 = new UpdatePack((String)map1.get("Uname"),(String)map1.get("Rname"),(String)map1.get("message"),(String)map1.get("Rquest"),1);
  			        		
  			        		userName = up2.getUname();
  			        		GameDB db = new GameDB();
  			        		String quest = db.RQuest();
  			        		for(Client client : connections) {
  			        			if (client.userName != null) {
  		  		                 	GameDB gd = new GameDB();
  		  		                	//현재 채팅을 친 유저와 client의 유저가 같은 경우만 전송
  		  		                	if(up2.getRname().equals(gd.SrchR(client.userName)))
  		  		                	  client.send3(up2);
  		  		                    System.out.println("얘가 실행돼야 하는데");
  		  		                }	
  			        		}
  			        	}
  		  			  }
  				  }
  		  			  catch (Exception e) {
  					  e.printStackTrace();
  		          }
  		  }catch (Exception e) {
  			  e.printStackTrace();
  		  }finally{
//			// 소켓 닫기 (연결 끊기)
			try {
			    if(socket != null) { socket.close(); }
			    if(br != null) { br.close(); }
			    if(pw != null) { pw.close(); }
			} catch (IOException e) {
			    System.out.println(e);
			}
		}
  	  });
  	  thread.start();
    }
    public void send2(UserPack up) {

	    Thread thread = new Thread(()->{
	    	try {
			pw = new PrintWriter(socket.getOutputStream());
			ArrayList<UserPack> list = new ArrayList<>();
				list.add(up);
			JSONObject jo = new JSONObject();
			jo.put("list", list);
			//VO 메시지 발송
			pw.println(new Gson().toJson(jo));
			pw.flush();
	    	} catch (IOException e) {
			    System.out.println(e);
			} finally {
//				// 소켓 닫기 (연결 끊기)
//				try {
//				    if(socket != null) { socket.close(); }
//				    if(br != null) { br.close(); }
//				    if(pw != null) { pw.close(); }
//				} catch (IOException e) {
//				    System.out.println(e);
//				}
			}    	
    });
    thread.start();
    }    

    public void send3(UpdatePack up) {

	    Thread thread3 = new Thread(()->{
	    	try {
			pw = new PrintWriter(socket.getOutputStream());
			ArrayList<UpdatePack> list =  new ArrayList<>();
				list.add(up);
			JSONObject jo = new JSONObject();
			jo.put("list", list);
			//VO 메시지 발송
			pw.println(new Gson().toJson(jo));
			pw.flush();
			System.out.println("pw push");
	    	} catch (IOException e) {
			    System.out.println(e);
			} finally {
			}    	
    });
    thread3.start();
    }    
    
    }
  }

