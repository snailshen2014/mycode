package com.elec.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

import com.elec.domain.ElecEntity;
import com.elec.utils.CompareObject;

/*
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
//        try {
//            //1.创建客户端Socket，指定服务器地址和端口
//            Socket socket=new Socket("123.206.44.250", 10000);
//            //2.获取输出流，向服务器端发送信息
//            OutputStream os=socket.getOutputStream();//字节输出流
//            PrintWriter pw=new PrintWriter(os);//将输出流包装为打印流
//            pw.write("用户名：whf;密码：789");
//            pw.flush();
//            socket.shutdownOutput();//关闭输出流
//            //3.获取输入流，并读取服务器端的响应信息
//            InputStream is=socket.getInputStream();
//            BufferedReader br=new BufferedReader(new InputStreamReader(is));
//            String info=null;
//            while((info=br.readLine())!=null){
//                System.out.println("我是客户端，服务器说："+info);
//            }
//            //4.关闭资源
//            br.close();
//            is.close();
//            pw.close();
//            os.close();
//            socket.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    	
    	//test compare two object
    	ElecEntity ent1 = new ElecEntity("20","20","30","40");
    	ElecEntity ent2 = new ElecEntity("20","20","30","50");
    	try {
			Map<String,String> result = CompareObject.compare(ent1, ent2);
			if (result.isEmpty()) {
				System.out.println("two object equals");
			}
			for(Map.Entry<String, String> en : result.entrySet()) {
				System.out.println(en.getKey() + ":" + en.getValue());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
