/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anthonypoon.assignment2.cluster;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

/**
 *
 * @author ypoon
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("i", "init", true, "Comma sperated list of inital cluster");
        options.addOption("a", "action", true, "action to do");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        double[] x = {1.0, 1.0, 2.0, 2.0, 5.0, 5.0, 6.0, 6.0, 6.0, 7.0, 7.0, 9.0, 10.0, 10.0, 11.0};
        double[] y = {1.0, 2.0, 0.0, 3.0, 10.0, 12.0, 2.0, 4.0, 10.0, 2.0, 5.0, 9.0, 7.0, 9.0, 8.0};
        List<Node> coorArray = new ArrayList();
        for (int i = 0; i < x.length; i++) {
            coorArray.add(new Node("A" + String.valueOf(i+1), x[i], y[i]));
        }
        for (Node node : coorArray) {
            System.out.println(String.format("%.2f", coorArray.get(0).distance(node)));
        }
        List<Cluster> clusterArray = new ArrayList();
        int index = 0;
        for (String str : cmd.getOptionValue("init").split(",")) {
            Cluster cluster = new Cluster("Cluster " + index, new Node("A"+str, x[Integer.valueOf(str) - 1], y[Integer.valueOf(str) - 1]));
            clusterArray.add(cluster);            
            index ++;
        }
        if (cmd.hasOption("action")) {
            switch (cmd.getOptionValue("action")) {
                case "kmean":                    
                    getKMean(coorArray, clusterArray);
                    break;
                case "pam":
                    getPAM(coorArray, clusterArray);
                    break;
            }
        } else {
                throw new Exception("Missing action parameter");
        }
        
        
        
    }
    private static void getKMean(List<Node> coorArray, List<Cluster> clusterArray) {       
        boolean hasChanged = false;
        int iternation = 0;
        System.out.println("Initial Clustering");
        for (Node dataPoint : coorArray) {
            Cluster bestFit = null;
            for (Cluster cluster : clusterArray) {
                if (bestFit == null || cluster.getDistance(dataPoint) < bestFit.getDistance(dataPoint)) {
                    bestFit = cluster;
                }
            }
            if (!bestFit.contains(dataPoint)) {
                bestFit.addMember(dataPoint);
            }
        }
        for (Cluster cluster : clusterArray) {
            System.out.println(cluster);
        }
        do {   
            hasChanged = false;
            iternation++;
            for (Cluster cluster : clusterArray) {
                cluster.recenter();
            }
            for (Node dataPoint : coorArray) {
                Cluster bestFit = null;
                for (Cluster cluster : clusterArray) {
                    if (bestFit == null || cluster.getDistance(dataPoint) < bestFit.getDistance(dataPoint)) {
                        bestFit = cluster;
                    }
                }
                if (!bestFit.contains(dataPoint)) {
                    bestFit.addMember(dataPoint);
                    hasChanged = true;
                }
            }           
            System.out.println("Iteration " + iternation);
            for (Cluster cluster : clusterArray) {
                System.out.println(cluster);
            }

        } while (hasChanged);
    }
    
    private static void getPAM(List<Node> coorArray, List<Cluster> clusterArray) {
        int iteration = 0;
        boolean swapped = false;
        do {            
            swapped = false;
            for (Cluster cluster : clusterArray) {
                cluster.clearMember();
            }
            for (Node dataPoint : coorArray) {
                Cluster bestFit = null;
                for (Cluster cluster : clusterArray) {
                    if (bestFit == null || cluster.getDistance(dataPoint) < bestFit.getDistance(dataPoint)) {
                        bestFit = cluster;
                    }
                }
                if (!bestFit.contains(dataPoint)) {
                    bestFit.addMember(dataPoint);
                }
            }
            if (iteration == 0) {
                System.out.println("Inital cluster");
            } else {
                System.out.println("Iteration: " + iteration);
            }
            iteration ++;
            for (Cluster cluster : clusterArray) {
                System.out.println(cluster);
            }
            double sumDistance = getAllCost(clusterArray);
            for (Cluster cluster : clusterArray) {
                Node oldCenter = cluster.getCenter();
                for (Node node : coorArray) {
                    if (node != cluster.getCenter() && !swapped) {
                        cluster.setCenter(node);
                        double sumSwappedDistance = getAllCost(clusterArray);
                        if (sumSwappedDistance < sumDistance)  {                            
                            swapped = true;
                        } else {
                            cluster.setCenter(oldCenter);
                        }
                        
                    }
                }
            }
        } while (swapped);
        
        System.out.println("Final");
        for (Cluster cluster : clusterArray) {
            System.out.println(cluster);
        }
    }
    
    private static double getAllCost(List<Cluster> clusterArray) {
        double sumDistance = 0.0;
        for (Cluster cluster : clusterArray) {                
            for (Node node : cluster.getMemberList()) {
                sumDistance = sumDistance + cluster.getDistance(node);
            }
        }
        return sumDistance;
    }
}
