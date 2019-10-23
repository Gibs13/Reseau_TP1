/** *
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */
package udp;

//import java.io.*;
import java.net.*;

import interfaces.InterfaceClient;

public class EchoClientListenerThread
        extends Thread {

    private static Boolean running = true;
    private MulticastSocket multicast;
    InterfaceClient output = null;

    public EchoClientListenerThread(MulticastSocket s) {
        multicast = s;
    }

    /**
     * receives a request from client then sends an echo to the client
     *
     * @param clientSocket the client socket
     *
     */
    public void exit() {
        running = false;
    }

    public void run() {
        while (running) {
            try {
                byte[] buf2 = new byte[1000];
                DatagramPacket recv = new DatagramPacket(buf2, buf2.length);
// Receive a datagram packet response 
                multicast.setSoTimeout(1000);
                multicast.receive(recv);
                System.out.println(new String(buf2));
                output.addMessage(new String(buf2));

            } catch (SocketTimeoutException e) {
                
            } catch (Exception e) {
                System.err.println("Error in EchoClientServerListenerThread:" + e);
                break;
            } 
        }
        System.out.println("Disconnected");
        output.addMessage("Disconnected");
    }

	public void setOuput(InterfaceClient ic) {
        output = ic;
	}

}
