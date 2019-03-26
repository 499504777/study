package com.mina.socket.server;

import com.mina.socket.JframeForm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 描述：服务器端
 *
 * @author Zhang Shaowei
 */

public class Server {
    private ServerSocket serverSocket;
    //线程池
    private ExecutorService threadPool;
    //双缓冲队列，保存所有客户端发送到服务器的信息
    private BlockingQueue<String> msgQueue;
    //页面UI
    private JframeForm form;
    private Integer count = 0;
    /**
     * 创建一个集合，用于保存所有客户端的输出流
     * 多线程需要访问这个集合，所以它应该是安全的
     */
    private Vector<PrintWriter> allOut = new Vector<>();

    public Server(Integer port) {
        try {
            /**
             * 打开服务端Socket时要捕获异常（端口被其他程序占用）
             * ---->错误信息中会出现 JVM_BIND 的信息
             */
            serverSocket = new ServerSocket(port);

            //创建线程池
            threadPool = Executors.newCachedThreadPool();
            //threadPool = Executors.newFixedThreadPool(20);

            //初始化双缓冲队列
            msgQueue = new LinkedBlockingQueue<>();

            //初始化页面
            form = new JframeForm("服务器");

            //启动用于转发信息的线程
            SendInfoToClientHander hander = new SendInfoToClientHander();
            Thread thread = new Thread(hander);
            thread.start();

            form.hidden();
            form.hideConnection();
            form.getcountField().setText(count + "");
            form.setVisible(true);
            form.setContent("服务器启动了...");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //开始服务
    public void start() {
        /**
         * Socket accept()
         * 在8088端口进行等待，等待客户端的连接
         * 当一个客户端通过ip和端口连接上时，会返回这个客户端的Socket与其开始通信
         * 这是一个阻塞方法，若客户端一直不连接，该方法就一直不能执行完毕
         *
         */
        while (true) {
            try {
                form.setContent("等待客户端连接...");
                Socket socket = serverSocket.accept();
                count += 1;
                form.getcountField().setText(count + "");
                form.setContent("一个客户端连接了...");

                //当一个客户端连接口，将他的输出流放入共享集合
                allOut.add(
                        new PrintWriter(
                                new OutputStreamWriter(
                                        socket.getOutputStream(), "utf-8"
                                )
                        )
                );
                //启动一个线程，用于和刚连上的客户端交流
                Handler handler = new Handler(socket);

                //将并发执行的任务交给线程池，让线程池分配空线程来运行
                //线程池在运行完任务后会自动回收线程等待再次分配任务去执行
                threadPool.execute(handler);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @author ZhangShaowei
     */
    private class Handler implements Runnable {
        //当前线程要处理的客户端Socket
        private Socket client;

        //通过构造方法，将客户端的Socket传进来
        public Handler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                //1.通过刚刚连接的客户端的Socket获取输入流，用于读取客户端发送过来的信息
                InputStream in = client.getInputStream();
                //将字节输入流抓换成缓冲字符输入流，便于以行为单位读取字符串
                InputStreamReader ir = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(ir);

                String info = null;
                //循环读取客户端发送过来的信息
                while ((info = reader.readLine()) != null) {
                    form.setContent(info);
                    //将信息放入双缓冲队列，等待被转发
                    msgQueue.add(info);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 描述：该线程只有一个实列，用户读取双缓冲队列中的信息，并转发给所有客户端
     *
     * @author ZhangShaowei
     */
    private class SendInfoToClientHander implements Runnable {
        @Override
        public void run() {
            try {
                //1.循环读取双缓冲队列
                while (true) {
                    int size = msgQueue.size();
                    for (int i = 0; i < size; i++) {
                        //2.从队列中获取一条信息
                        String msg = msgQueue.poll();
                        //3转发给所有客户端
                        for (PrintWriter writer : allOut) {
                            writer.println(msg);
                            writer.flush();
                        }
                    }
                    Thread.sleep(500);//所有信息发送后停顿，降低消耗
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

