package br.com.rotafood.api.domain.entity.order;

public enum OrderStatus {
    CREATED,              
    CONFIRMED,            
    PREPARATION_STARTED,  
    READY_TO_PICKUP,       
    DISPATCHED,           
    COMPLETED,            
    CANCELED              
}