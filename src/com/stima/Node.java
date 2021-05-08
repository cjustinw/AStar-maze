/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stima;

import java.awt.Point;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import java.util.ArrayList;

/**
 *
 * @author chris
 */
public class Node {
    private Point point;
    private double destDistance;
    private double rootDistance;
    private double cost;
    private ArrayList<Point> path;

    public Node(Point P, Point Pdest, Node prev, boolean isEuclidean) {
        point = P;
        path = new ArrayList<>();
        if(isEuclidean) {
            destDistance = calculateEuclideanDistance(point, Pdest);
            if(prev != null) {
                rootDistance = prev.getRootDistance() + calculateEuclideanDistance(point, prev.getPoint());
            }
        }
        else{
            destDistance = calculateManhattanDistance(point, Pdest);
            if(prev != null) {
                rootDistance = prev.getRootDistance() + calculateManhattanDistance(point, prev.getPoint());
            }
        }
        if(prev == null) {
            rootDistance = 0;
        }
        else{
            for(int i = 0; i < prev.getPath().size(); i++){
                path.add(prev.getPath().get(i));
            }
        }
        cost = rootDistance + destDistance;
        path.add(point);
    }
    
    public ArrayList<Point> getPath() {
        return path;
    }
    
    public Point getPoint() {
        return point;
    }
    
    public double getRootDistance() {
        return rootDistance;
    }
    
    public double getDestDistance() {
        return destDistance;
    }
    
    public double getCost() {
        return cost;
    }
    
    public double calculateEuclideanDistance(Point P1, Point P2) {
        return sqrt((P2.y - P1.y) * (P2.y - P1.y) + (P2.x - P1.x) * (P2.x - P1.x));
    }
    
    public double calculateManhattanDistance(Point P1, Point P2) {
        return abs((P2.y - P1.y)) + abs((P2.x - P1.x));
    }
}
