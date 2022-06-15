package edu.skku.cs.finalproject;

import java.io.Serializable;

public class Group implements Serializable {
    private String groupid;
    private String groupname;
    private String[] members;

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }

    public String getGroupname() {
        return groupname;
    }

    public String getGroupid() {
        return groupid;
    }

    public String[] getMembers() {
        return members;
    }
}
