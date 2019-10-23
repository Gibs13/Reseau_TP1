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

import interfaces.InterfaceServer;

public class EchoServer extends Thread {

	InterfaceServer output;
	String port;

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
				output.addLog("\n" + line);
				socOut.println("réponse : j'ai bien reçu le message " + line);
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
	public void initial(String port, InterfaceServer serverLogs) {
		output = serverLogs;
		this.port = port;
	}

	public void run() {
		ServerSocket listenSocket;
		try {
			listenSocket = new ServerSocket(Integer.parseInt(port)); // port
			System.out.println("server launched");
			while (true) {
				Socket clientSocket = listenSocket.accept();
				System.out.println("connexion from: " + clientSocket.getInetAddress());
				output.addLog("\n" + "connexion from: " + clientSocket.getInetAddress());
				doService(clientSocket);
			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}
}
