package udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import interfaces.InterfaceClient;

public class EchoClientUDP {

    String username = "";
    MulticastSocket groupchat = null;
    InetAddress groupAddr = null;
    Integer groupPort = 6789;
    EchoClientListenerThread eclt = null;
    InterfaceClient output = null;

    public void start(String username) {

        try {

            groupAddr = InetAddress.getByName("228.5.6.7");
            groupchat = new MulticastSocket(groupPort);
            groupchat.joinGroup(groupAddr);
        } catch (Exception e) {
            System.out.println("Error in EchoClient : " + e);
            System.exit(1);
        }
        this.username = username;
        eclt = new EchoClientListenerThread(groupchat);
        eclt.start();
    }

    public void setOutput(InterfaceClient ic){
        output = ic;
        eclt.setOuput(ic);
    }

    public void sendMessage(String line) {
        line = line;
        DatagramPacket toSend = new DatagramPacket(line.getBytes(), line.length(), groupAddr, groupPort);

        // Send a multicast message to the group
        try {
            groupchat.send(toSend);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            groupchat.leaveGroup(groupAddr);
            eclt.exit();
            // System.exit(1);
        } catch (IOException ex) {
            Logger.getLogger(EchoClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
