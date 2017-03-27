package com.mina.socket.client;


//启动客户端，通过控制台收发信息
public class StartClient {
    public static void main(String[] args) {
        Client client = new Client();//传入ip,端口,可用户名
        client.start();
    }
}
