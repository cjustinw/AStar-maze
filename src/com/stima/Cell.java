/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stima;

/**
 *
 * @author chris
 */
public class Cell {
    
    private CellType type;
    private Cellable object;
    
    public enum CellType {
        WALL,
        FLOOR
    }
    
    public Cell(CellType type) {
        this.type = type;
        object = null;
    }
    
    public CellType getType() {
        return type;
    }
    
    public void setType(CellType type) {
        this.type = type;
    }
    
    public Cellable getObject() {
        return object;
    }
    
    public void setObject(Cellable object) {
        this.object = object;
    }
}
