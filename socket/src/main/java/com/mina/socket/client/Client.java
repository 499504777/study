package com.mina.socket.client;

import com.mina.socket.JframeForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * 描述：客户端
 *
 * @author Zhang Shaowei
 */
public class Client {
    private Socket socket;
    private String realName;
    private String ip;
    private String port;
    private JframeForm form;

    public Client() {
        form = new JframeForm("客户端");
    }


    public void start() {
        form.setTitle("客户端");
        form.hiddenCount();
        form.setContent("请先连接服务器......");
        form.setVisible(true);
        form.getConButton().addActionListener(e -> {
            ip = form.getIp();
            port = form.getPort();
            realName = form.getUserName();
            if ("".equals(ip) || ip == null) {
                form.setContent("请输入ip地址！");
                return;
            }
            if ("".equals(port) || port == null) {
                form.setContent("请输入连接端口！");
                return;
            }
            if ("".equals(realName) || realName == null) {
                form.setContent("请输入用户名！");
                return;
            }

            /**
             * 客户端Socket，用于连接服务端
             * 构造方法参数1：服务器ip地址
             * 构造方法参数2：服务端程序申请的端口号（8088）
             * 连接本机地址：localhost
             * 本机ip:控制台 ipconfig
             *
             * 一旦创建Socket的实例时就会自动根据给定的地址和端口连接服务器了
             */
            try {
                socket = new Socket(ip, Integer.parseInt(port));
                doJob();
                form.setContent("成功连接服务器！可以开始聊天！");
                form.hideConnection();
            } catch (Exception e1) {
                e1.printStackTrace();
                form.setContent("连接服务器失败，请检查输入参数");
            }
        });

    }

    private void doJob() {
        try {
            //连接建立好后，启动一个线程。用于读取服务端发送过来的信息，并输出到控制台
            getServerInfoHandler handler = new getServerInfoHandler(socket);
            Thread thread = new Thread(handler);
            thread.start();

            //通过Socket获取输出流，通过该输出流写出的内容就发送到服务端去
            final PrintWriter writer = new PrintWriter(
                    socket.getOutputStream()
            );

            //按钮绑定事件，获取输入框内容，发送到服务器
            form.getJbutton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String message = form.getSendMessage();
                    String msg = realName + ":" + message;
                    writer.println(msg);
                    writer.flush();
                    form.getTextField().setText("");
                }
            });
            //监听输入框回车事件
            form.getTextField().addActionListener(e -> {
                String message = form.getSendMessage();
                String msg = realName + ":" + message;
                writer.println(msg);
                writer.flush();
                form.getTextField().setText("");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class getServerInfoHandler implements Runnable {
        private Socket socket;

        public getServerInfoHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                //通过Socket获取输入流，获取到的输入流就是服务端发送过来的信息
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream(), "utf-8"
                        )
                );
                String info = null;
                while ((info = reader.readLine()) != null) {
                    form.setContent(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
