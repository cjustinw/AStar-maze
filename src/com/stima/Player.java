/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stima;

import java.awt.Point;

/**
 *
 * @author chris
 */
public class Player implements Cellable{
    
    private Point position;
     private String imagePath;
    private String imageDownPath;
    private String imageDown1Path;
    private String imageDown2Path;
    private String imageUp1Path;
    private String imageUp2Path;
    private String imageLeft1Path;
    private String imageLeft2Path;
    private String imageRight1Path;
    private String imageRight2Path;
    
    public Player(Point P) {
        position = new Point(P);
        imageDownPath = "res/sprites/player/down1.png";
        imageDown1Path = "res/sprites/player/down2.png";
        imageDown2Path = "res/sprites/player/down3.png";
        imageUp1Path = "res/sprites/player/up2.png";
        imageUp2Path = "res/sprites/player/up2.png";
        imageLeft1Path = "res/sprites/player/left2.png";
        imageLeft2Path = "res/sprites/player/left3.png";
        imageRight1Path = "res/sprites/player/right2.png";
        imageRight2Path = "res/sprites/player/right3.png";
        imagePath = imageLeft1Path;
    }
    
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point P) {
        position = new Point(P);
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }
    
    public void setImagePath(String S, int n) {
        if (S.equals("U")) {
            if (n == 1) {
                imagePath = "res/sprites/player/up2.png";
            } else {
                imagePath = "res/sprites/player/up3.png";
            }
        } else if (S.equals("D")) {
            if (n == 1) {
                imagePath = "res/sprites/player/down2.png";
            } else {
                imagePath = "res/sprites/player/down3.png";
            }
        } else if (S.equals("L")) {
            if (n == 1) {
                imagePath = "res/sprites/player/left2.png";
            } else {
                imagePath = "res/sprites/player/left3.png";
            }
        } else if (S.equals("R")) {
            if (n == 1) {
                imagePath = "res/sprites/player/right2.png";
            } else {
                imagePath = "res/sprites/player/right3.png";
            }
        }
    }
}
