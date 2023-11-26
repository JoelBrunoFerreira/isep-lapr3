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

        Node currentNode = node;  // O(1)

        // Find the representative
        while (currentNode.getParent() != null) {  // O(h)
            currentNode = currentNode.getParent();  // O(1)
        }

        // "path compression" to make sure that next time we look for the representative of the node we have = O(1)
        // NOTE:
        // path compression means --> That we set the parent node of the current node to be the root node
        Node root = currentNode;  // O(1)
        currentNode = node;  // O(1)

        while (currentNode != root) {  // O(h)
            Node temp = currentNode.getParent();  // O(1)
            currentNode.setParent(root);  // O(1)
            currentNode = temp;  // O(1)
        }
        return root;
    }

    public void union(Node node1, Node node2) {

        Node root1 = find(node1);  // O(h)
        Node root2 = find(node2);  // O(h)

        if (root1 == root2) {  // O(1)
            return;
        }

        // attach the smaller tree to the root of the larger tree "union by height"
        if (root1.getHeight() < root2.getHeight()) {  // O(1)
            root1.setParent(root2);  // O(1)
        } else if (root2.getHeight() < root1.getHeight()) {  // O(1)
            root2.setParent(root1);  // O(1)
        } else {
            root2.setParent(root1);  // O(1)
            root1.setHeight(root1.getHeight() + 1);  // O(1)
        }
    }

    private void makeSets(List<Vertex> vertexList) {
        for (Vertex vertex: vertexList) {  // O(V)
            makeSet(vertex);
        }
    }

    private void makeSet(Vertex vertex) {
        Node node = new Node(0, null);  // O(1)
        vertex.setNode(node);  // O(1)
    }
}
