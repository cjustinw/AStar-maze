/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stima;

import com.stima.Cell.CellType;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author chris
 */
public class Map {
    private static int WIDTH = 17;
    private static int HEIGHT = 17;
    private static String path = "res/map.txt";
    
    private Cell[][] map;
    
    public Map() throws FileNotFoundException {
        map = new Cell[WIDTH][HEIGHT];
        File file = new File(path);
        Scanner sc = new Scanner(file);
            
        for(int i = 0; i < WIDTH; i++){
            String str = sc.next();
                
            for(int j = 0; j < HEIGHT; j++) {
                switch (str.charAt(j)) {
                    case 'o' -> map[i][j] = new Cell(CellType.WALL);
                    case '-' -> map[i][j] = new Cell(CellType.FLOOR);
                    default -> {
                        
                    }
                }
            }
        }
    }
    
    public Cell at(int x, int y) {
        return map[y][x];
    }

    public Cell at(Point P) {
        return this.at(P.x, P.y);
    }
    
    public boolean isPositionValid(Point P) {
        if((P.x >= 0) && (P.x < WIDTH) && (P.y >= 0) && (P.y < HEIGHT)){
            if(at(P).getType().equals(CellType.FLOOR)){
                return true;
            }
        }
        return false;
    }
//    
//    public void print() {
//        for(int i = 0; i < WIDTH; i++) {
//            for(int j = 0; j < WID)
//        }
//    }
//    
}
