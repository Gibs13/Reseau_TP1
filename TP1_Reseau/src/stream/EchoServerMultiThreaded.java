/** *
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */
package stream;

import java.io.*;
import java.net.*;

public class EchoServerMultiThreaded {

    /**
     * main method
     *
     * @param EchoServer port
     *
     *
     */
    public void start(String port) {
        ServerSocket listenSocket;
        try {
            listenSocket = new ServerSocket(Integer.parseInt(port)); //port
            System.out.println("Server ready...");
            while (true) {
                Socket clientSocket = listenSocket.accept(); //timer entre attente connexion et lecture var globale
                System.out.println("Connexion from:" + clientSocket.getInetAddress());
                ClientThread ct = new ClientThread(clientSocket);
                ct.start();
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}
