package com.ecommerce.ecommerce.enums.audit;

public enum Action
{
    CREATE ("CREATE"),
    CHANGE_STATE ("CHANGE_STATE"),
    UPDATE ("UPDATE"),
    DELETE ("DELETE");

    private String action;

    Action(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
