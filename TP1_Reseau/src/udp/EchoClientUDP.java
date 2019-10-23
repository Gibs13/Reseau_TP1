package udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoClientUDP {

    public static void main(String[] args) {

        String username = "";
        MulticastSocket groupchat = null;
        InetAddress groupAddr = null;
        Integer groupPort = 6789;

        try {

            groupAddr = InetAddress.getByName("228.5.6.7");
            groupchat = new MulticastSocket(groupPort);
            groupchat.joinGroup(groupAddr);
        } catch (Exception e) {
            System.out.println("Error in EchoClient : " + e);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Username ? ");

        try {
            username = stdIn.readLine();
            String connectionMessage = username + " joined the chat.";
            DatagramPacket toSend = new DatagramPacket(connectionMessage.getBytes(), connectionMessage.length(),
                    groupAddr, groupPort);
            groupchat.send(toSend);
        } catch (IOException ex) {
            Logger.getLogger(EchoClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

        EchoClientListenerThread eclt = new EchoClientListenerThread(groupchat);
        eclt.start();
        while (true) {
            String line;
            try {
                line = stdIn.readLine();
                if (line.equals(".")) {
                    break;
                }
                line = username + " : " + line;
                DatagramPacket toSend = new DatagramPacket(line.getBytes(), line.length(), groupAddr, groupPort);

                // Send a multicast message to the group
                groupchat.send(toSend);
            } catch (Exception e) {
                System.err.println("Error while sending message : s" + e);
            }
        }
        try {
            groupchat.leaveGroup(groupAddr);
            eclt.exit();
            // System.exit(1);
        } catch (IOException ex) {
            Logger.getLogger(EchoClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
