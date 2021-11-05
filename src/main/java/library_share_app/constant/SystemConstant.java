package library_share_app.constant;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SystemConstant {
	public static final String download ="D:\\StoringFile\\download\\";
	public static final String upload ="D:\\StoringFile\\upload\\";
	public static  ServerSocket server ;
	public static List<Socket> sockets =  new ArrayList<Socket>();
	public static Socket socket;
	public static Socket socket_client;


}
