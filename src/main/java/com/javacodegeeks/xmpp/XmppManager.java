package com.javacodegeeks.xmpp;

import java.io.IOException;
import java.util.Scanner;

import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class XmppManager implements Runnable{

	XMPPTCPConnection connection;
	Scanner scan = new Scanner(System.in);
	private ChatManager chatManager;
	private Thread conn, send, rec;
	Chat chat;
	private StzListener stzListener;
	
	public static void main(String[] args) {
		XmppManager deneme = new XmppManager();
		deneme.connect();
		try{
			deneme.sendMessage();
		} catch(XMPPException e){
			e.printStackTrace();
		}
	}

	private void connect() {
		conn = new Thread(this);
		conn.start();
		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
				.setUsernameAndPassword("eclipse", "1").setServiceName("localhost").setHost("localhost").setPort(5222)
				.setSecurityMode(SecurityMode.disabled).allowEmptyOrNullUsernames().build();

		connection = new XMPPTCPConnection(config);
		
		stzListener = new StzListener();
		connection.addAsyncStanzaListener(stzListener, stzListener);
		
		chatManager = ChatManager.getInstanceFor(connection);
		
//		chatManager.addChatListener(new ChatManagerListener() {
//
//            public void chatCreated(Chat chat, boolean createdLocally) {
//                System.out.println("Created chat");
//                chat.addMessageListener(new ChatMessageListener() {
//
//                    public void processMessage(Chat chat, Message message) {
//                    	if (message.getType() == Type.chat) {
//                    		System.out.println(message.getBody());
//						}else {
//							System.out.println("CHAT DEGIL");
//							System.out.println(message.getType());
//						}
//                        
//                    }
//                });
//
//            }
//        });
		
		try {
			connection.connect();
		} catch (SmackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (connection.isConnected()) {
			System.out.println("connected");
		}
		try {
			connection.login();
		} catch (XMPPException e) {
			e.printStackTrace();
		} catch (SmackException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {		
		if (connection != null){
			conn = null;
			connection.removeAsyncStanzaListener(stzListener);
			connection.disconnect();
		}
	}

	public void sendMessage() throws XMPPException {
		send = new Thread(this);
		send.start();
		chat = chatManager.createChat("sparks@localhost");
		Message msg = new Message("sparks@localhost", Message.Type.chat);
		String s = "scan.nextLine()";
		msg.setBody(s);		
		
		
		if (connection != null) {
			try {
				chat.sendMessage(msg);
			} catch (NotConnectedException e) {
				e.printStackTrace();
			}
		}
		send = null;
	}
	public void run() {
		// TODO Auto-generated method stub
		
		Thread curr = Thread.currentThread();
		if(curr == conn)
			System.out.println("connect() is started to run");
		
		if(curr == send)
			System.out.println("sendMessage() is started to run");
		
		if(curr == rec)
			System.out.println("processMessage() is started to run");
		//int i = 0;
		while ( conn != null && Thread.currentThread() == conn) {
			try{
				Thread.sleep(50);
				//System.out.println(i);
				//i++;
		
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
		if(curr == conn)
			System.out.println("connect() is stopping");
		
		if(curr == send)
			System.out.println("sendMessage() is stopping");
		
		if(curr == rec)
			System.out.println("processMessage() is stopping");
	}
}