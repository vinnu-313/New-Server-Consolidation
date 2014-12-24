/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author vinayaka
 */
class Task {

    private int id;
    private HashMap<String, Integer> confMap;

    public Task(int id, Config conf) throws IOException {
        DataInputStream din = new DataInputStream(System.in);
        this.id = id;
        confMap = new HashMap<>();
        for (String key : conf.getConf()) {
            System.out.print("Enter the value for " + key + " : ");
            confMap.put(key, new Integer(din.readLine()));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashMap<String, Integer> getConfMap() {
        return confMap;
    }

    public void setConfMap(HashMap<String, Integer> confMap) {
        this.confMap = confMap;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getId()).append("\t");
        for (String key : getConfMap().keySet()) {
            sb.append(getConfMap().get(key)).append("\t");
        }
        sb.append("\n");
        
        return sb.toString();
    }

}
