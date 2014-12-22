/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author vinayaka.p
 */
public class Config {

    private List<String> conf;

    // Constructors
    public Config() {
        conf = new ArrayList<>();
    }

    public Config(List<String> conf) {
        this.conf = conf;
    }

    // Getters and setters
    public List<String> getConf() {
        return conf;
    }

    public void setConf(List<String> conf) {
        this.conf = conf;
    }

    // Utility methods
    public void addConf(String param) {
        this.conf.add(param);
    }

    public void removeConf(String param) {
        this.conf.remove(param);
    }

    public void removeConf(int index) {
        this.conf.remove(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Config Params are \n");
        for (Iterator<String> iterator = conf.iterator(); iterator.hasNext();) {
            sb.append(iterator.next()).append('\n');
        }
        return sb.toString();
    }
}
