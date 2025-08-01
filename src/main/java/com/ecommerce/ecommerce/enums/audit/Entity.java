package com.ecommerce.ecommerce.enums.audit;

public enum Entity
{
    PRODUCT ("PRODUCT"),
    ORDER ("ORDER"),
    STOCK ("STOCK"),
    ROLE ("ROLE"),
    USER ("USER");

    private String entity;

    Entity(String entity) {
        this.entity = entity;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}
