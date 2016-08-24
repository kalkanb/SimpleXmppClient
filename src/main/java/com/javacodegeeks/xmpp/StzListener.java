package com.javacodegeeks.xmpp;

import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.packet.Message;

public class StzListener implements StanzaListener, StanzaFilter{

	public boolean accept(Stanza stanza) {
		// TODO Auto-generated method stub
		return true;
	}

	public void processPacket(Stanza packet) throws NotConnectedException {
		//System.out.println("*******************************************************************");
		//System.out.println(packet.toXML());		
		//System.out.println("-------------------------------------------------------------------");

		if(packet instanceof Message){
			if(((Message) packet).getBody() == null){
				//System.out.println(((Message) packet).getFrom()  + " yazıyor...");
			}
			else{
				System.out.println(((Message) packet).getBody());
			}
		}
	}

}
