package com.flastmc.sync.purchase;

public class Purchase {

    private String owner;
    private String command;

    private int id;
    private int server_id;
    private int online;

    public Purchase(String owner, String command, int id, int server_id, int online){
        this.owner = owner;
        this.command = command;
        this.id = id;
        this.server_id = server_id;
        this.online = online;
    }

    public String getOwner() {
        return owner;
    }

    public String getCommand() {
        return command;
    }

    public int getId() {
        return id;
    }

    public int getServer_id() {
        return server_id;
    }

    public int getOnline() {
        return online;
    }

}
