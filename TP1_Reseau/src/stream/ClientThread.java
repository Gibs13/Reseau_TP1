/** *
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */
package stream;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ClientThread
        extends Thread {

    public static ArrayList<PrintStream> listeClientSockets = new ArrayList<PrintStream>();
    private static final String historyPath = "./ressource/chatHistory.txt";

    private Socket clientSocket;
    private int clientSocketIndex;
    private static final Boolean historyAsFile = true;
    private static ArrayList<String> localHistory = new ArrayList<String>();

    ClientThread(Socket s) {
        this.clientSocket = s;
        clientSocketIndex = addSocket(s);
    }

    public static synchronized int addSocket(Socket s) {
        try {
            PrintStream socOut = new PrintStream(s.getOutputStream());
            listeClientSockets.add(socOut);//TODO : remplacer une place null
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
        return listeClientSockets.size() - 1;
    }

    public static synchronized void removeSocket(int socketIndexToRemove) {
        listeClientSockets.set(socketIndexToRemove, null);
    }

    public static synchronized void sendMessage(String message) {
        try {
            for (int i = 0; i < listeClientSockets.size(); i++) {
                if (listeClientSockets.get(i) != null) {
                    PrintStream socOut = listeClientSockets.get(i);
                    socOut.println(message);
                }
            }
            if (historyAsFile) {
                Writer writer = null;
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(historyPath, true), "utf-8"));
                writer.write(message + "\n");
                writer.close();
            } else {
                localHistory.add(message);
                System.out.println("local history size : " + localHistory.size());
            }

        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

    /**
     * receives a request from client then sends an echo to the client
     *
     * @param clientSocket the client socket
     *
     */
    public void run() {
        try {
            BufferedReader socIn = null;
            socIn = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintStream socOut = listeClientSockets.get(clientSocketIndex);
            if (historyAsFile) {
                for (String temp : Files.readAllLines(Paths.get(historyPath))) {
                    socOut.println(temp);
                }
            } else {
                for (String temp : localHistory) {
                    socOut.println(temp);
                }
            }
            while (true) {
                String line = socIn.readLine();
                if (line.equals(".")) {
                    removeSocket(clientSocketIndex);
                    break;
                }
                sendMessage(line);
                //System.out.println("client " + listeClientSockets.get(clientSocketIndex).getInetAddress().toString() + " : " + socIn.readLine());
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

}
