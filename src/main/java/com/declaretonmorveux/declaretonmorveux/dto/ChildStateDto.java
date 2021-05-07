package com.declaretonmorveux.declaretonmorveux.dto;

public class ChildStateDto {
    private long id;
    private boolean isSick;
    private boolean isContagious;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public boolean isSick() {
        return isSick;
    }
    public void setSick(boolean isSick) {
        this.isSick = isSick;
    }
    public boolean isContagious() {
        return isContagious;
    }
    public void setContagious(boolean isContagious) {
        this.isContagious = isContagious;
    }

    
}
