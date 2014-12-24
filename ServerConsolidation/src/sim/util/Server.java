/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vinayaka
 */
public class Server {
    private int id;
    private HashMap<String, Object> confMap;
    private List<Task> taskList;
    public Server() {
    }

    public Server(int id, HashMap<String, Object> confMap) {
        this.id = id;
        this.confMap = confMap;
    }
    
    public Server(int id, Config conf){
        DataInputStream din = new DataInputStream(System.in);
        for (Iterator<String> iterator = conf.getConf().iterator(); iterator.hasNext();) {
            try {
                String next = iterator.next();
                System.out.print("Enter the value for "+next+" : ");
                confMap.put(next, Integer.parseInt(din.readLine()));
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
    
    public HashMap<String, Object> getConfMap() {
        return confMap;
    }

    public void setConfMap(HashMap<String, Object> confMap) {
        this.confMap = confMap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Server Details \n");
        sb.append("ID : ").append(id);
        for (String key : confMap.keySet()) {
            sb.append(key).append(" : ").append(confMap.get(key)).append("\n");
        }
        return sb.toString();
    }

    public boolean isStoppable(){
        return taskList.isEmpty();
    }
}
