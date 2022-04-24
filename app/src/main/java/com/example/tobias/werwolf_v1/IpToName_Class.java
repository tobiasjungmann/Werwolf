package com.example.tobias.werwolf_v1;

public class IpToName_Class {


    private String ip;
    private String name;

    public IpToName_Class(String ip, String name)
    {

        this.ip=ip;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


}




