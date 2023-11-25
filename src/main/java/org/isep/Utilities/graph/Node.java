package org.isep.Utilities.graph;

public class Node {
    private int height;  // --> It will represent the number of nodes bellow the current node
    private Node parent;


    // Constructor
    // ---------------------------------
    public Node(int height, Node parent) {
        this.height = height;
        this.parent = parent;
    }


    // Getters
    // -----------------------------------
    public int getHeight() {
        return height;
    }

    public Node getParent() {
        return parent;
    }


    // Setters
    // ----------------------------------
    public void setHeight(int height) {
        this.height = height;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
