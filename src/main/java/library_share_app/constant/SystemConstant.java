package library_share_app.constant;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import library_share_app.dto.UserDTO;

public class SystemConstant {
	public static final String download ="D:\\StoringFile\\download\\";
	public static final String download_server ="D:\\StoringFile\\downloadserver\\";
	public static final String upload ="D:\\StoringFile\\upload\\";
	public static  ServerSocket server ;
	public static List<Socket> sockets =  new ArrayList<Socket>();
	public static Socket socket;
	public static Socket socket_client;
	public static Long id_user_current;
	public static HashMap<UserDTO, Socket> list_user_active = new HashMap<UserDTO, Socket>();
	public static HashMap<UserDTO, Socket> list_socket_client = new HashMap<UserDTO, Socket>();


}
