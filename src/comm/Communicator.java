/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * @author udara
 */
package comm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Communicator {                                                           // communicator class manage the comunication with the server

    public static final Communicator communicator = new Communicator();                //
    private static String serverIp;

    public static Communicator getInstance() {
        return communicator;
    }

    public void setServerIp(String IP) {                                               // set server ip at the biging of application
        this.serverIp = IP;
    }

    public void sendMessage(String msg) {                                              // call by interpretor to send message to server
        try {
            Socket soc = new Socket(serverIp, 6000);                                   // crate a socket connection to servers port 6000
            DataOutputStream output = new DataOutputStream(soc.getOutputStream());
            output.writeBytes(msg);                                                    // write the message to server port
            soc.close();                                                               // close socket conncetion
            output.close();
        } catch (UnknownHostException une) {
            System.out.println("Server: \n" + Communicator.class.getName() + "\n" + une + "\n");
            une.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Runtime Exception:" + e);
        }
    }

    public String getUpdate(int timeout) {                                            // get updates from the server
        String update;
        ServerSocket server = null;
        Socket connection = null;
        InputStreamReader reader = null;

        try {
            server = new ServerSocket(7000);                                         // create a socket connection to port 7000
            server.setSoTimeout(timeout);                                            // set the server socket timeout
            connection = server.accept();
            reader = new InputStreamReader(connection.getInputStream());
            BufferedReader input = new BufferedReader(reader);
            update = input.readLine();
            return update;

        } catch (IOException e) {
            return "Error while reading from the socket";
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (server != null) {
                    server.close();
                }
            } catch (IOException e) {
                System.err.println("could not close network socket");
            }
        }
    }

    public String getUpdate() {
        String update;
        ServerSocket server = null;
        Socket connection = null;
        InputStreamReader reader = null;
        try {
            server = new ServerSocket(7000);
            connection = server.accept();
            reader = new InputStreamReader(connection.getInputStream());
            BufferedReader input = new BufferedReader(reader);
            update = input.readLine();
            return update;

        } catch (IOException io) {
            return "Error while rading from socket";
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (server != null) {
                    server.close();
                }
            } catch (IOException io) {
                System.out.println("could not close network connection");
            }
        }


    }
}
