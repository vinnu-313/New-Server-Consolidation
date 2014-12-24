/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.util;

import java.util.List;

/**
 *
 * @author vinayaka
 */
public class ServerManager {

    private List<Server> serverList;
    public ServerManager() {
    }

    public ServerManager(List<Server> serverList) {
        this.serverList = serverList;
    }

    public List<Server> getServerList() {
        return serverList;
    }

    public void setServerList(List<Server> serverList) {
        this.serverList = serverList;
    }
  
    
    public void addServer(int id, Config conf){
        if(getServerById(id) == null){
            serverList.add(new Server(id, conf));
        }else{
            System.out.println("Server with given ID already exists. Try with different ID");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("List of Servers\n");
        for (Server server : serverList) {
            sb.append(server.toString());
        }
        return sb.toString();
    }
    
    public Server getServerById(int id){
        for (Server server : serverList) {
            if(server.getId() == id){
                return server;
            }
        }
        return null;
    }

    public void removeServer(int id) {
        if(getServerById(id) == null){
            System.out.println("Server with specified ID not found.");
        }else{
            if(getServerById(id).isStoppable()){
                serverList.remove(getServerById(id));
            }else{
                System.out.println("There are currently tasks running on the server, hence can't be removed.");
            }
        }
    }
    
}
