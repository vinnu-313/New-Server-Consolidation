/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sim.util.Config;
import sim.util.ServerManager;

/**
 *
 * @author vinayaka.p
 */
public class Simulator {

    private static Config conf = null;
    private static ServerManager manager = null;
    
    public static void main(String args[]) {
        DataInputStream din = new DataInputStream(System.in);
        while (true) {
            try {
                System.out.println("Select an operation");
                System.out.println("1 - Change Configuration Parameters");
                System.out.println("2 - Server operations");
                System.out.println("3 - Task operations");
                System.out.println("5 - Display environment status");
                System.out.println("6 - Exit ");

                int choice = Integer.parseInt(din.readLine());
                switch (choice) {
                    case 1:
                        System.out.println("1 - Add parameter");
                        System.out.println("2 - View parameters");
                        System.out.println("3 - Remove parameter");
                        choice = Integer.parseInt(din.readLine());
                        switch (choice) {
                            case 1:
                                if (conf == null) {
                                    conf = new Config();
                                }
                                System.out.print("Enter the configuration parameter : ");
                                conf.addConf(din.readLine());
                                break;
                            case 2:
                                if (conf == null || conf.getConf().isEmpty()) {
                                    System.out.println("No parameters yet.");
                                    break;
                                }
                                System.out.println(conf);
                                break;
                            case 3:
                                if (conf == null || conf.getConf().isEmpty()) {
                                    System.out.println("No parameters to remove from");
                                    break;
                                }
                                System.out.print("Enter the configuration parameter to remove : ");
                                conf.removeConf(din.readLine());
                                break;
                            default:
                                System.out.println("Invalid Choice, try again");
                                break;
                        }
                        break;
                    case 2:
                        System.out.println("1 - Add Server");
                        System.out.println("2 - View Servers");
                        System.out.println("3 - Remove Server");
                        choice = Integer.parseInt(din.readLine());
                        switch (choice) {
                            case 1:
                                if (manager == null) {
                                    manager = new ServerManager();
                                }
                                System.out.println("Enter the ID for the server : ");
                                manager.addServer(Integer.parseInt(din.readLine()), conf);
                                break;
                            case 2:
                                if (manager == null || manager.getServerList().isEmpty()) {
                                    System.out.println("No servers yet.");
                                    break;
                                }
                                System.out.println(manager);
                                break;
                            case 3:
                                if (manager == null || manager.getServerList().isEmpty()) {
                                    System.out.println("No servers to remove from");
                                    break;
                                }
                                System.out.print("Enter the Server ID to remove : ");
                                manager.removeServer(Integer.parseInt(din.readLine()));
                                break;
                            default:
                                System.out.println("Invalid Choice, try again");
                                break;
                        }
                        break;
                    case 6:
                        System.out.println("System will exit now.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid Choice, try again");
                }
            } catch (IOException ex) {
                System.out.println("Please enter valid data.");
                Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
