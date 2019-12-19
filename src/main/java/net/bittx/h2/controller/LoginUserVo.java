package net.bittx.h2.controller;


public class LoginUserVo {

    private int id;
    private String name;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
