package com.wks.newIO;


import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;


public class Client {
    public static void main(String[] args) throws IOException {
        Socket client = null;
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        try {
            client = new Socket();
            client.connect(new InetSocketAddress("localhost",8686));
            printWriter = new PrintWriter(client.getOutputStream(),true);
            printWriter.println("hello");
            printWriter.println("hello2");
            printWriter.println("hello3");

            printWriter.flush();

            int i=1;
            while(  i>0){
                i=client.getInputStream().read();
                System.out.print(new Character((char)i));
            }

            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}