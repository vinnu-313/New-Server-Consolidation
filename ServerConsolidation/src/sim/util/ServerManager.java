/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vinayaka
 */
public class ServerManager {

    private List<Server> serverList;

    public ServerManager() {
        serverList = new ArrayList<>();
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

    public void addServer(int id, Config conf) {
        if (getServerById(id) == null) {
            serverList.add(new Server(id, conf));
        } else {
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

    public Server getServerById(int id) {
        if (serverList.isEmpty()) {
            return null;
        }
        for (Server server : serverList) {
            if (server.getId() == id) {
                return server;
            }
        }
        return null;
    }

    public Server getServerByTask(int id) {
        for (Server s : serverList) {
            for (Task task : s.getTaskList()) {
                if (task.getId() == id) {
                    return s;
                }
            }
        }
        return null;
    }

    public Server getServerByTask(Task t) {
        for (Server s : serverList) {
            if (s.getTaskList().contains(t)) {
                return s;
            }
        }
        return null;
    }

    public void removeServer(int id) {
        if (getServerById(id) == null) {
            System.out.println("Server with specified ID not found.");
        } else {
            if (getServerById(id).isStoppable()) {
                serverList.remove(getServerById(id));
            } else {
                System.out.println("There are currently tasks running on the server, hence can't be removed.");
            }
        }
    }

    public void addTask(int id, Config conf) throws IOException {
        
        if(getTaskById(id) == null ){
            Task t = new Task(id, conf);
            for(Server s : serverList){
                if(s.isAllocatable(t, conf.getConf().get(0))){
                    s.getTaskList().add(t);
                    System.out.println("Task is allocated on server : " +s.getId());
                    return;
                }
            }
            System.out.println("Failed to allocate with normal allocation. Going for migration");
            
        }
    }

    public void removeTask(int id) {
        Server s = getServerByTask(id);
        System.out.println(s);
        if (s == null) {
            System.out.println("Task doesn't exist");
        } else {
            System.out.println(getTaskById(id));
            s.getTaskList().remove(getTaskById(id));
        }
    }

    private Task getTaskById(int id) {
        for (Server s : serverList) {
            for (Task t : s.getTaskList()) {
                if (t.getId() == id) {
                    return t;
                }
            }
        }
        return null;
    }
}
