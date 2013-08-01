/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comm;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author udara
 */
public class Coordinator extends Thread {                                          // create game cordinator extending from thread

    private boolean jSuccess;                                                      // join success message
    private String jErrorMsg;
    public static final Coordinator codinat = new Coordinator();
    private boolean cancel;                                                        // cancel join to set either join or not
    private String serverIp;
    public boolean start = false;                                                  // set start

    public Coordinator() {
        jErrorMsg = null;
        jSuccess = false;
        cancel = false;
        this.setName("game coordinator");
    }

    public static Coordinator getInstance() {
        return codinat;
    }

    public void run() {                                                            // Run method to run the thread body
        Communicator com = new Communicator();
        Interpretor i = Interpretor.getInstance();
        System.out.println("requesting to join...");
        while (!jSuccess) {
            System.out.print(jSuccess);
            System.out.println("Requesting from Server at: " + serverIp);
            i.rejoin();                                                           // request to join game
            i.inputMessage(com.getUpdate(1100));                                  // get the upadte crating a 1100 millsecond socket connection
            if (jErrorMsg != null) {
                System.out.println(jErrorMsg);
                cancel = true;
            }

            if (!jSuccess) {
                System.out.println("Join unsucessful...! retry");
            }
            if (cancel) {
                System.out.println("connection aborted");
            }
        }
        if (jSuccess) {
            System.out.println("Suceesfully join");
            while (true) {
                i.inputMessage(com.getUpdate());
                try {
                    Thread.sleep(200);

                } catch (InterruptedException ex) {
                }
            }
        } else {
            System.out.println("Joining process unsuccessful \n do u want to retry [yes]/[no]");
            Scanner scanner = new Scanner(System.in);
            String ans = scanner.next();
            if (ans.startsWith("Y")) {
                cancel = false;
            }
        }
    }

    public void setServerIp(String ip)                                            //set server ip
    {
        this.serverIp=ip;
    }


    public void setJoinErrorMsg(String msg) {                                     //
        jErrorMsg = msg;
    }
    

    public void setJoinSucess(boolean su) {
        System.out.println("Code comes here*******************");
        jSuccess = su;
    }
}
