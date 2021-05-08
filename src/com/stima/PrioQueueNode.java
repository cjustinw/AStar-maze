/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stima;

import java.awt.Point;
import static java.lang.Math.round;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author chris
 */
public class PrioQueueNode {
    private ArrayList<Node> queue;
    
    public PrioQueueNode() {
        queue = new ArrayList<>();
    }
    
    public void enqueue(Node e) {
        if(queue.isEmpty()){
            queue.add(e);
            return;
        }
        for(int i = queue.size()-1; i >= -1; i--) {
            if(i == -1){
                queue.add(0, e);
                return;
            }
            if(queue.get(i).getCost() < e.getCost()) {
                queue.add(i+1, e);
                return;
            }
        }
    }
    
    public Node dequeue() {
        Node node = queue.get(0);
        queue.remove(0);
        return node;
    }
}
