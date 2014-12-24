/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vinayaka
 */
public class Server {

    private int id;
    private HashMap<String, Integer> confMap;
    private ArrayList<Task> taskList;

    public Server(int id) {
        this.id = id;
        confMap = new HashMap<>();
        taskList = new ArrayList<>();
    }

    public Server(int id, HashMap<String, Integer> confMap) {
        this.id = id;
        this.confMap = confMap;
    }

    public Server(int id, Config conf) {
        this.id = id;
        confMap = new HashMap<>();
        taskList = new ArrayList<>();
        DataInputStream din = new DataInputStream(System.in);
        for (Iterator<String> iterator = conf.getConf().iterator(); iterator.hasNext();) {
            try {
                String next = iterator.next();
                System.out.print("Enter the value for " + next + " : ");
                confMap.put(next, new Integer(din.readLine()));
            } catch (IOException ex) {
                System.out.println("Invalid Input");
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }


    public HashMap<String, Integer> getConfMap() {
        return confMap;
    }

    public void setConfMap(HashMap<String, Integer> confMap) {
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
        sb.append("ID : ").append(id).append("\n");
        for (String key : confMap.keySet()) {
            sb.append(key).append(" : ").append(confMap.get(key)).append("\n");
        }
        if (taskList.isEmpty()) {
            sb.append("Task List is empty\n");
        } else {
            sb.append("ID.\t");
            for(String key : confMap.keySet()){
                sb.append(key).append("\t");
            }
            sb.append("\n");
            for(Task task : taskList){
                sb.append(task.toString());
            }
        }
        return sb.toString();
    }

    public boolean isAllocatable(Task t, String param){
        return (getAvailable(param) > ((int)t.getConfMap().get(param)));
    }
    
    public boolean isStoppable() {
        return taskList.isEmpty();
    }
    
    public int getAvailable(String param){
        int capacity = (int)confMap.get(param), total = 0;
        for(Task task : taskList){
            total += (int)task.getConfMap().get(param);
        }
     return capacity - total;   
    }
}
