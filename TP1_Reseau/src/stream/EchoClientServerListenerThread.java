/** *
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */
package stream;

import java.io.*;
import java.net.*;

import javax.swing.JTextArea;

import interfaces.InterfaceClient;

public class EchoClientServerListenerThread
        extends Thread {

    private BufferedReader clientReader;
    InterfaceClient output;

    EchoClientServerListenerThread(BufferedReader in) {
        clientReader = in;
    }

    /**
     * receives a request from client then sends an echo to the client
     *
     * @param clientSocket the client socket
     *
     */
    public void run() {
        try {
            while (true) {
                String line = clientReader.readLine();
                if (line == null) {
                    System.out.println("server disconnected");
                    break;
                }
                System.out.println("server : " + line);
                output.addMessage(line);
            }
        } catch (Exception e) {
            System.err.println("Error in EchoClientServerListenerThread:" + e);
        }
    }

	public void setOutput(InterfaceClient taAreaReceivedMessages) {
        output=taAreaReceivedMessages;
	}

}
