/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
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

        if (getTaskById(id) == null) {
            Task t = new Task(id, conf);
            for (Server s : serverList) {
                if (s.isAllocatable(t, conf.getConf().get(0))) {
                    s.getTaskList().add(t);
                    System.out.println("Task is allocated on server : " + s.getId());
                    return;
                }
            }
            System.out.println("Failed to allocate with normal allocation. Going for migration");
            //. First parameter to start with
            String param = conf.getConf().get(0);
            // Target server with maximum available value for parameter
            Server targetServer = getTargetServer(param);
            // Sorted List of tasks on target server
            ArrayList<Task> targetTaskList = getSortedTasksOnServer(targetServer, param);
//            System.out.println("Sorted tasks on target server");
//            for(Task tTask : targetTaskList){
//                System.out.println(tTask);
//            }
//            // Sorted list of all the tasks except from server
//            ArrayList<Task> allTaskList = sortTasks(getAllTaskList(targetServer), param);
//            System.out.println("All the tasks except from target");
//            for(Task tTask : allTaskList){
//                System.out.println(tTask);
//            }
            // Sorted List of servers except target
            ArrayList<Server> allServers = sortServers(getServerListExceptTarget(targetServer), param);

            // Starting to check for migration
            for (Task task : targetTaskList) {
                for (Server server : allServers) {
                    if (server.isAllocatable(task, param) && (targetServer.getAvailable(param) + task.getConfMap().get(param)) > t.getConfMap().get(param)) {
                        removeTask(task.getId());
                        server.getTaskList().add(task);
                        targetServer.getTaskList().add(t);
                        System.out.println("Task is allocated on server : "+targetServer.getId());
                        return;
                    }
                }
            }

        } else {
            System.out.println("Task with same ID already exists.");
        }
    }

    public ArrayList<Server> sortServers(ArrayList<Server> servers, String param) {
        Server[] sarray = new Server[1];
        sarray = servers.toArray(sarray);
        for (int i = 0; i < sarray.length - 1; i++) {
            for (int j = i + 1; j < sarray.length; j++) {
                if (sarray[i].getAvailable(param) < sarray[j].getAvailable(param)) {
                    Server temp = sarray[j];
                    sarray[j] = sarray[i];
                    sarray[i] = temp;
                }
            }
        }
        ArrayList<Server> list = new ArrayList<>();
        list.addAll(Arrays.asList(sarray));
        return list;
    }

    public ArrayList<Task> sortTasks(ArrayList<Task> tasks, String param) {
        Task[] tarray = new Task[1];
        tarray = tasks.toArray(tarray);
        for (int i = 0; i < tarray.length - 1; i++) {
            for (int j = i + 1; j < tarray.length; j++) {
                if (((int) tarray[i].getConfMap().get(param)) < ((int) tarray[j].getConfMap().get(param))) {
                    Task temp = tarray[j];
                    tarray[j] = tarray[i];
                    tarray[i] = temp;
                }
            }
        }
        ArrayList<Task> list = new ArrayList<>();
        list.addAll(Arrays.asList(tarray));
        return list;
    }

    public ArrayList<Task> getAllTaskList() {
        ArrayList<Task> list = new ArrayList<>();
        for (Server s : serverList) {
            list.addAll(s.getTaskList());
        }
        return list;
    }

    public ArrayList<Task> getAllTaskList(Server targetServer) {
        ArrayList<Task> list = new ArrayList<>();
        for (Server s : serverList) {
            if (s != targetServer) {
                list.addAll(s.getTaskList());
            }
        }
        return list;
    }

    public Server getTargetServer(String param) {
        Server server = serverList.get(0);
        for (Server s : serverList) {
            if (s.getAvailable(param) > server.getAvailable(param)) {
                server = s;
            }
        }
        System.out.println("Target Server is : " + server);
        return server;
    }

    public ArrayList<Task> getSortedTasksOnServer(Server s, String param) {
        return sortTasks(s.getTaskList(), param);
    }

    public ArrayList<Server> getServerListExceptTarget(Server target) {
        ArrayList<Server> list = new ArrayList<>();
        for (Server s : serverList) {
            if (s != target) {
                list.add(s);
            }
        }
        return list;
    }

    public void removeTask(int id) {
        Server s = getServerByTask(id);
        System.out.println(s);
        if (s == null) {
            System.out.println("Task doesn't exist");
        } else {
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
