package tcp;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import observer.Observable;
import observer.Observer;
import treemodel.ClientNode;
import treemodel.ServerNode;
import treeview.ServerFrame;

public class Server implements Runnable, Observable {
	private static Server instance;
	private ArrayList<PrintWriter> chats;
	private ArrayList<Socket> userSockets;
	private ArrayList<Observer> observers;
	private static ArrayList<String> userNames;
	private static ServerSocket server_socket;
	
	private Server() throws Exception {
		chats = new ArrayList<>();
		userNames = new ArrayList<>();
		observers = new ArrayList<>();
		userSockets = new ArrayList<>();
		server_socket = new ServerSocket(2020);

		System.out.println("Port 2020 is now opened.");
		
		Thread thread = new Thread(this);
		thread.start();
	}

	public void addClient(Socket socket, String name) {
		try {
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			chats.add(out);
			userSockets.add(socket);
			userNames.add(name);
			
			// Broadcasts that a new client has just joined to all clients that are already in chat room.
			broadcast(null, "Client: " + name + " has just joined the chat.");
			
			// Notifies ServerFrame that it needs to add one more client to the list.
			notify("Add: " + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeClient(String nickname) {
		try {
			if (!userNames.contains(nickname)) {
				return;
			}
			int index = userNames.indexOf(nickname);
			
			// Broadcasts that client has just left chat to all clients that are already in chat room.
			broadcast(null, "Client: " + nickname + " has left the chat.");
			chats.remove(index);
			userSockets.remove(index);
			userNames.remove(index);
			
			// Notifies ServerFrame that it needs to delete this client from the list.
			notify("Delete: " + nickname);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void broadcast(Object sender, Object message) {
		if (!(message instanceof String)) 
			return;
		
		if (sender != null && !(sender instanceof Socket)) 
			return;
		// If one of the clients just exited it will tell server "EXITING_NOW" and server will find 
		// that client in its list and call removeClient() function to delete it from its base.
		if (((String) message).equals("EXITING_NOW")) {
			for(int j = 0 ; j < userSockets.size() ; j++) {
				if ((Socket) sender == userSockets.get(j)) {
					removeClient(userNames.get(j));
					return;
				}
			}
			return;
		}
		// Updates ClientNode with new text client has written.
		if (sender != null) {
			ServerNode sn = (ServerNode) ServerFrame.getInstance().getTreeModel().getRoot();
			ClientNode cn = (ClientNode) sn.getChildByString(getUserNickname((String)message));
			cn.appendText((String)message);
		}
		// Loops through all connected clients and with their PrintWriters sends them what
		// other client has said, or if it was that client that sent it it changes the name to "<You>"
		int i = 0;
		for (PrintWriter user : chats) {
			if (sender == null) {
				user.println((String) message);
				continue;
			}
			if ((Socket) sender == userSockets.get(i)) {
				String tmp = "<";
				tmp += getUserNickname((String) message) + ">";
				String[] split = ((String) message).split(tmp);
				tmp = "<You>";
				tmp += split[1];
				user.println(tmp);
			} else {
				user.println((String) message);
			}
			i++;
		}
	}

	public static Server getInstance() {
		if (instance == null) {
			try {
				instance = new Server();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	public static boolean isStarted() {
		if (instance == null)
			return false;
		return true;
	}

	public void stop() {
		try {
			if (server_socket != null)
				server_socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getUserNickname(String nick) {
		String tmp = "";
		int i = 1;
		while (nick.charAt(i) != '>') {
			tmp += nick.charAt(i);
			i++;
		}
		return tmp;
	}

	public static boolean IsNameViable(String name) {
		return !userNames.contains(name);
	}

	@Override
	public void run() {
		try {
			while(true) {
				Socket incoming = server_socket.accept();
	            Thread t = new Thread(new ServerThread(incoming, this));
	            t.start();
			}
		} catch (Exception e) {
			// This Exception is thrown when application is closed since it was waiting for incoming socket.
			// e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		String ip = "";
		try {
			ip = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "";
		}
		if (ip.contains("/")) {
			String[] split = ip.split("/");
			ip = split[split.length - 1];
		}
		return ip;
	}

	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notify(Object o) {
		for (Observer ob : observers) {
			ob.update(o);
		}
	}
}
