/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
package stream;

import java.io.*;
import java.net.*;

import javax.swing.JTextArea;

import interfaces.InterfaceClient;

public class EchoClient {

  /**
   * main method accepts a connection, receives a message from client then sends
   * an echo to the client
   **/

  private static Socket echoSocket = null;
  private static PrintStream socOut = null;
  private static BufferedReader stdIn = null;
  private static BufferedReader socIn = null;
  private static EchoClientServerListenerThread ecslt = null;
  InterfaceClient output = null;

  public void run(String ip, String port) throws IOException {

    // creation socket ==> connexion
    connect(ip, port);

    String line;
    while (true) {
      line = stdIn.readLine();
      sendMessage(line);
      if (line.equals("reconnect"))
        connect(ip, port);
      if (line.equals("."))
        break;

    }

  }

  public void sendMessage(String line) {
    output.addMessage(line);
    socOut.println(line);
  }

  public void close() throws IOException {
    ecslt.interrupt(); // marche pas?
    socOut.close();
    socIn.close();
    stdIn.close();
    echoSocket.close();
  }

  public void connect(String ip, String port) {
    try {
      echoSocket = new Socket(ip, new Integer(port).intValue());
      socIn = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
      socOut = new PrintStream(echoSocket.getOutputStream());
      stdIn = new BufferedReader(new InputStreamReader(System.in));
      ecslt = new EchoClientServerListenerThread(socIn);
      ecslt.start();
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host:" + ip);
      System.exit(1);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for " + "the connection to:" + ip);
      System.exit(1);
    } catch (Exception e) {
      System.err.println("Thread Exception : " + e);
      System.exit(1);
    }
  }

  public void setOutput(InterfaceClient taAreaReceivedMessages) {
    this.output=taAreaReceivedMessages;
    ecslt.setOutput(output);
  }
}
