package org.isep.Utilities.graph;

import java.util.List;

public class DisjointSet {

    // Constructor
    // -------------------------------------
    public DisjointSet(List<Vertex> vertexList) {
        makeSets(vertexList);
    }


    // Methods
    // --------------------------------------
    public Node find(Node node) {  // --> To find the representative (root node) for Node 'node'

        Node currentNode = node;

        // Find the representative
        while (currentNode.getParent() != null) {
            currentNode = currentNode.getParent();
        }

        // "path compression" to make sure that next time we look for the representative of the node we have = O(1)
        // NOTE:
        // path compression means --> That we set the parent node of the current node to be the root node
        Node root = currentNode;
        currentNode = node;

        while (currentNode != root) {
            Node temp = currentNode.getParent();
            currentNode.setParent(root);
            currentNode = temp;
        }
        return root;
    }

    public void union(Node node1, Node node2) {

        Node root1 = find(node1);
        Node root2 = find(node2);

        if (root1 == root2) {
            return;
        }

        // attach the smaller tree to the root of the larger tree "union by height"
        if (root1.getHeight() < root2.getHeight()) {
            root1.setParent(root2);
        } else if (root2.getHeight() < root1.getHeight()) {
            root2.setParent(root1);
        } else {
            root2.setParent(root1);
            root1.setHeight(root1.getHeight() + 1);
        }
    }

    private void makeSets(List<Vertex> vertexList) {
        for (Vertex vertex: vertexList) {
            makeSet(vertex);
        }
    }

    private void makeSet(Vertex vertex) {
        Node node = new Node(0, null);
        vertex.setNode(node);
    }
}
