/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stima;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author chris
 */
public class GamePanel extends JPanel implements ActionListener{
    
    //Game Config
    private static final int WIDTH = 544;
    private static final int HEIGHT = 544;
    private static final int UNIT_SIZE = 32;
    private static final int DELAY = 75;
    
    private BufferedImage sprite = null;
    
    private Timer timer;
    
    private Map map;
    private Player player;
    private ArrayList<Point> path;
    
    private boolean running = false;
    private String direction = " ";
    private String prevMove = " ";
    private String currentMove = " ";
    private int moveCounter = 1;
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        try{
            map = new Map();
        } catch(Exception e) {}
        player = new Player(new Point(16,8));
        map.at(player.getPosition()).setObject(player);
        path = null;
        startGame();
    }
    
    private void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        renderMap(g);
    }
    
    private void renderMap(Graphics g) {
        for(int i = 0; i < WIDTH/UNIT_SIZE; i++) {
            for(int j = 0; j < HEIGHT/UNIT_SIZE; j++) {
                switch(map.at(i,j).getType()) {
                    case WALL  -> g.setColor(Color.GRAY);
                    case FLOOR -> g.setColor(Color.WHITE);
                    default -> {
                      
                    }
                }
                if(path != null){
                    for(int k = 0; k< path.size(); k++) {
                        if(path.get(k).x == i && path.get(k).y == j){
                            g.setColor(Color.YELLOW);
                        }
                    }
                }
                g.fillRect(i*UNIT_SIZE, j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                try {
                    if(map.at(i, j).getObject() != null) {
                        sprite = ImageIO.read(new File(map.at(i, j).getObject().getImagePath()));
                    }
                } catch(IOException e) {
                    
                }
                if(sprite != null) {
                    Image img = sprite.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
                    g.drawImage(img, i*UNIT_SIZE, j*UNIT_SIZE, this);
                    sprite = null;
                }
                g.setColor(Color.GRAY);
                g.drawLine(0, j*UNIT_SIZE, WIDTH, j*UNIT_SIZE);
            }
            g.setColor(Color.GRAY);
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, HEIGHT);
        }
    }
    
    public void playerMovement(String direction) {
        int xtmp = player.getPosition().x;
        int ytmp = player.getPosition().y;
        boolean move = true;
        if(!prevMove.equals(currentMove)){
            moveCounter = 1;
        }
        prevMove = currentMove;
        player.setImagePath(direction, moveCounter%2);
        switch(direction){
            case "U" -> {
                ytmp = ytmp - 1;
                currentMove = direction;
                moveCounter++;
            }
            case "D" -> {
                ytmp = ytmp + 1;
                currentMove = direction;
                moveCounter++;
            }
            case "L" -> {
                xtmp = xtmp - 1;
                currentMove = direction;
                moveCounter++;
            }
            case "R" -> {
                xtmp = xtmp + 1;
                currentMove = direction;
                moveCounter++;
            }
            case " " -> move = false;
        }
        
        if(map.isPositionValid(new Point(xtmp, ytmp))){
            map.at(player.getPosition()).setObject(null);
            player.setPosition(new Point(xtmp, ytmp));
            map.at(player.getPosition()).setObject(player);
        }
    }
    
    public ArrayList<Point> AStarAlgorithm(Point Psrc, Point Pdest, boolean isEuclidean){
        PrioQueueNode pq = new PrioQueueNode();
        Node head = new Node(Psrc, Pdest, null, isEuclidean);
        while(head.getDestDistance() > 0){
            Point W = new Point(head.getPoint().x - 1, head.getPoint().y);
            Point E = new Point(head.getPoint().x + 1, head.getPoint().y);
            Point N = new Point(head.getPoint().x, head.getPoint().y - 1);
            Point S = new Point(head.getPoint().x, head.getPoint().y + 1);
            Boolean canEnqueueW = true;
            Boolean canEnqueueE = true;
            Boolean canEnqueueN = true;
            Boolean canEnqueueS = true;
            
            for(int i = 0; i < head.getPath().size(); i++){
                if(head.getPath().get(i).y == W.y && head.getPath().get(i).x == W.x){
                    canEnqueueW = false;
                }
                if(head.getPath().get(i).y == E.y && head.getPath().get(i).x == E.x){
                    canEnqueueE = false;
                }
                if(head.getPath().get(i).y == N.y && head.getPath().get(i).x == N.x){
                    canEnqueueN = false;
                }
                if(head.getPath().get(i).y == S.y && head.getPath().get(i).x == S.x){
                    canEnqueueS = false;
                }
            }
            
            if(map.isPositionValid(W) && canEnqueueW){
                pq.enqueue(new Node(W, Pdest, head, isEuclidean));
            }
            if(map.isPositionValid(E) && canEnqueueE){
                pq.enqueue(new Node(E, Pdest, head, isEuclidean));
            }
            if(map.isPositionValid(N) && canEnqueueN){
                pq.enqueue(new Node(N, Pdest, head, isEuclidean));
            }
            if(map.isPositionValid(S) && canEnqueueS){
                pq.enqueue(new Node(S, Pdest, head, isEuclidean));
            }
            head = pq.dequeue();
        }
        return head.getPath();
    }
    
    public void searchPath(String dest) {
        Point Pdest;
        if(dest.equals("Gate 1")){
            Pdest = new Point(8,16);
        }
        else if(dest.equals("Gate 2")){
            Pdest = new Point(0,8);
        }
        else if(dest.equals("Gate 3")){
            Pdest = new Point(8,0);
        }
        else{
            Pdest = new Point(16,8);
        }
        path = AStarAlgorithm(player.getPosition(), Pdest, true);
    }
    
    public void autoMove() {
        if(!path.isEmpty()){
            if(path.get(0).x == player.getPosition().x && path.get(0).y == player.getPosition().y){
                direction = " ";
            }
            else if(path.get(0).x == player.getPosition().x-1 && path.get(0).y == player.getPosition().y){
                direction = "L";
            }
            else if(path.get(0).x == player.getPosition().x+1 && path.get(0).y == player.getPosition().y){
                direction = "R";
            }
            else if(path.get(0).x == player.getPosition().x && path.get(0).y == player.getPosition().y-1){
                direction = "U";
            }
            else if(path.get(0).x == player.getPosition().x && path.get(0).y == player.getPosition().y+1){
                direction = "D";
            }
            path.remove(0);
        }
        else{
            path = null;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            if(path != null){
                autoMove();
            }
            playerMovement(direction);
            direction = " ";
        }
        repaint();
    }
    
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()) {
                case KeyEvent.VK_A -> {
                    if(!direction.equals("R")) {
                        direction = "L";
                    }
                }
                case KeyEvent.VK_D -> {
                    if(!direction.equals("L")) {
                        direction = "R";
                    }
                }
                case KeyEvent.VK_W -> {
                    if(!direction.equals("D")) {
                        direction = "U";
                    }
                }
                case KeyEvent.VK_S -> {
                    if(!direction.equals("U")) {
                        direction = "D";
                    }
                }
            }
        }
    }
}
