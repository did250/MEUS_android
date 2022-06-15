package edu.skku.cs.finalproject;

import java.io.Serializable;

public class Person implements Serializable {
    private String id;
    private String pw;
    private String name;
    private String friends[];
    private String groups[];
    private String schedules[][];
    private String friendrequest[];
    private String grouprequest[];

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String[] getFriends() {
        return friends;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    public void setFriends(String[] friends) {
        this.friends = friends;
    }

    public String[][] getSchedules() {
        return schedules;
    }

    public void setSchedules(String[][] schedules) {
        this.schedules = schedules;
    }

    public void setFriendrequest(String[] friendrequest) {
        this.friendrequest = friendrequest;
    }

    public void setGrouprequest(String[] grouprequest) {
        this.grouprequest = grouprequest;
    }

    public String[] getFriendrequest() {
        return friendrequest;
    }

    public String[] getGrouprequest() {
        return grouprequest;
    }
}

