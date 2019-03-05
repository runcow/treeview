package com.runcow.treeview;

/**
 * Created by runcow on 2019/3/5.
 */

public class OrgBean {
    private int id;
    private String name;
    private boolean isProvince;

    public OrgBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isProvince() {
        return isProvince;
    }

    public void setProvince(boolean province) {
        isProvince = province;
    }
}
