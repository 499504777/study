package com.mina.socket.server;

public class StartServer {
    public static void main(String[] args) {
        Server server = new Server(8088);
        server.start();
    }
}
