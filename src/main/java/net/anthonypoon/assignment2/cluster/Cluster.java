/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anthonypoon.assignment2.cluster;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ypoon
 */
public class Cluster {
    private String name;
    private Node center;
    private List<Node> memberArray = new ArrayList(); 
    public Cluster(String name, Node initalNode) {
        this.name = name;
        this.center = initalNode;
    }
    
    public double getDistance(Node dataNode) {
        return center.distance(dataNode);
    }
    public String toString() {
        String str = "";
        for (Node dataNode : memberArray) {
            str = str + dataNode.toString() + "\t";
        }
        return name + "\tCentroid = " + center.toString() + "\tMembers = " + str.trim();
    }
    
    public List<Node> getMemberList() {
        return memberArray;
    }
    
    public void addMember(Node dataPoint) {
        dataPoint.setCluster(this);
        memberArray.add(dataPoint);
    }
    
    public String getName() {
        return name;
    }
    
    public Node getCenter() {
        return center;
    }

    public boolean contains(Node node) {
        return memberArray.contains(node);
    }
    
    public void removeMember(Node node) {
        memberArray.remove(node);
    }
    
    public void clearMember() {
        for (int i = 0; i < memberArray.size(); i ++) {
            memberArray.get(i).clearCluster();
        }
        memberArray = new ArrayList();
    }
    
    public void recenter() {
        if (memberArray.size() != 0 ) {
            double x = 0.0;
            double y = 0.0;
            int n = 0;
            for (Node node : memberArray) {
                n ++;
                x = x + node.getX();
                y = y + node.getY();
            }
            this.center = new Node ("Point", x / n, y / n);
        }
    }
    
    public void setCenter(Node node) {
        //node.setCluster(this);
        this.center = node;
    }
}
