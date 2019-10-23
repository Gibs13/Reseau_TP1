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

public class EchoClientServerListenerThread
        extends Thread {

    private BufferedReader clientReader;
    JTextArea output;

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
                output.append("\n" + line);
            }
        } catch (Exception e) {
            System.err.println("Error in EchoClientServerListenerThread:" + e);
        }
    }

	public void setOutput(JTextArea taAreaReceivedMessages) {
        output=taAreaReceivedMessages;
	}

}
