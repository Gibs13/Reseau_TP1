/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;

import javax.swing.JTextField;

public class EchoServer {

	JTextField output;

	/**
	 * receives a request from client then sends an echo to the client
	 * 
	 * @param clientSocket the client socket
	 **/
	void doService(Socket clientSocket) {
		try {
			BufferedReader socIn = null;
			socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
			while (true) {
				String line = socIn.readLine();
				System.out.println(line);
				output.setText(output.getText() + "\n" + line);
				output.repaint();
				socOut.println("response");
			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}

	/**
	 * main method
	 * 
	 * @param EchoServer port
	 * 
	 **/
	public void start(String port, JTextField serverLogs) {
		ServerSocket listenSocket;
		output = serverLogs;
		try {
			listenSocket = new ServerSocket(Integer.parseInt(port)); // port
			System.out.println("server launched");
			while (true) {
				Socket clientSocket = listenSocket.accept();
				System.out.println("connexion from:" + clientSocket.getInetAddress());
				output.setText(output.getText() + "\n" + "connexion from:" + clientSocket.getInetAddress());
				output.repaint();
				doService(clientSocket);
			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}
}
