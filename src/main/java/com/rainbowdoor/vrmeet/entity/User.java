package com.rainbowdoor.vrmeet.entity;

public class User {
    private int uid;
    private String name;
    private int rid;
    private String rname;

    public int getUid()
    {
        return uid;
    }

    public String getName()
    {
        return name;
    }

    public int getRid()
    {
        return rid;
    }

    public String getRname()
    {
        return rname;
    }

    public void setUid(int _uid)
    {
        uid = _uid;
    }

    public void setName(String _name)
    {
        name = _name;
    }

    public void setRid(int _rid)
    {
        rid = _rid;
    }

    public void setRname(String _rname)
    {
        rname = _rname;
    }

}
