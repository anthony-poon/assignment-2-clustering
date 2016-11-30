/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anthonypoon.assignment2.cluster;

import java.awt.geom.Point2D;

/**
 *
 * @author ypoon
 */
public class Node extends Point2D.Double{
    private String name;
    private Cluster cluster;
    public Node(String name, double x, double y) {
        super(x, y);
        this.name = name;
    }

    public String toString() {
        return name + ": " + "[" + String.format("%.2f", x) +"," + String.format("%.2f", y) +"]";
    }
    
    public String getName() {
        return name;
    }
    
    public void setCluster(Cluster cluster) {
        // Remove himself from old cluster
        if (this.cluster != null) {
            this.cluster.removeMember(this);
        }
        // Set input cluster as new cluster
        this.cluster = cluster;
    }

    public double distance(Node node) {
        return this.distance(node.getX(), node.getY());
    }
    
    public void clearCluster() {
        this.cluster = null;
    }
}
