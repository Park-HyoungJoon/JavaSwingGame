package server;

import server.ServerHandler;

public class ServerLaunch {

  public static void main(String[] args) {
    
    ServerHandler serverHandler = new ServerHandler();
    serverHandler.startServer();

  }
 
}