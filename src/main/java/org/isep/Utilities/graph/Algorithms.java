package org.isep.Utilities.graph;

import org.isep.Utilities.graph.map.MapGraph;
import org.isep.Utilities.graph.matrix.MatrixGraph;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 *
 * @author DEI-ISEP
 *
 */
public class Algorithms {

    /** Performs breadth-first search of a Graph starting in a vertex
     *
     * @param g Graph instance
     * @param vert vertex that will be the source of the search
     * @return a LinkedList with the vertices of breadth-first search
     */
    public static <V, E> LinkedList<V> BreadthFirstSearch(Graph<V, E> g, V vert) {

        if (!g.validVertex(vert))
            return null;

        LinkedList<V> qbfs = new LinkedList<>();
        LinkedList<V> qaux = new LinkedList<>();
        boolean[] visited = new boolean[g.numVertices()];

        qbfs.add(vert);
        qaux.add(vert);
        int key = g.key(vert);
        visited[key] = true;

        while(!qaux.isEmpty()){
            qaux.remove(vert);
            for (V vAdj: g.adjVertices(vert)) {
                if(visited[g.key(vert)] == false){
                    qbfs.add(vAdj);
                    qaux.add(vAdj);
                    visited[g.key(vert)] = true;
                }
            }
        }
        return qbfs;
    }

    /** Performs depth-first search starting in a vertex
     *
     * @param g Graph instance
     * @param vOrig vertex of graph g that will be the source of the search
     * @param visited set of previously visited vertices
     * @param qdfs return LinkedList with vertices of depth-first search
     */
    private static <V, E> void DepthFirstSearch(Graph<V, E> g, V vOrig, boolean[] visited, LinkedList<V> qdfs) {

        if(visited[g.key(vOrig)]){
            return;
        }

        qdfs.add(vOrig);
        visited[g.key(vOrig)] = true;

        for(V vAdj : g.adjVertices(vOrig)){
            DepthFirstSearch(g, vAdj, visited, qdfs);
        }
    }

    /** Performs depth-first search starting in a vertex
     *
     * @param g Graph instance
     * @param vert vertex of graph g that will be the source of the search

     * @return a LinkedList with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vert) {

        if (!g.validVertex(vert))
            return null;

        boolean[] visited = new boolean[g.numVertices()];
        LinkedList<V> qbfs = new LinkedList<>();
        DepthFirstSearch(g, vert, visited, qbfs);

        return qbfs;
    }

    /** Returns all paths from vOrig to vDest
     *
     * @param g       Graph instance
     * @param vOrig   Vertex that will be the source of the path
     * @param vDest   Vertex that will be the end of the path
     * @param visited set of discovered vertices
     * @param path    stack with vertices of the current path (the path is in reverse order)
     * @param paths   ArrayList with all the paths (in correct order)
     */
    private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
                                        LinkedList<V> path, ArrayList<LinkedList<V>> paths) {
        path.push(vOrig);
        visited[g.key(vOrig)] = true;

        for (V vAdj : g.adjVertices(vOrig)) {
            if (vAdj == vDest) {
                path.push(vDest);
                paths.add(path);
                path.pop();
            } else {
                if (!visited[g.key(vAdj)]) {
                    allPaths(g, vAdj, vDest, visited, path, paths);
                }
            }
            path.pop();
        }
    }

    /** Returns all paths from vOrig to vDest
     *
     * @param g     Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @return paths ArrayList with all paths from vOrig to vDest
     */
    public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {

        LinkedList<V> path = new LinkedList<>();
        ArrayList<LinkedList<V>> paths = new ArrayList<>();
        boolean[] visited = new boolean[g.numVertices()];

        allPaths(g, vOrig, vDest, visited, path, paths);

        return paths;
    }

    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with non-negative edge weights
     * This implementation uses Dijkstra's algorithm
     *
     * @param g        Graph instance
     * @param vOrig    Vertex that will be the source of the path
     * @param visited  set of previously visited vertices
     * @param pathKeys minimum path vertices keys
     * @param dist     minimum distances
     */
    private static <V, E> void shortestPathDijkstra(Graph<V, E> g, V vOrig,
                                                    Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                                    boolean[] visited, V [] pathKeys, E [] dist) {

        Iterable<V> verticesIterator = g.vertices();

        for (V v : verticesIterator) {
            int index = g.key(v);
            visited[index] = false;
            pathKeys[index] = null;
            dist[index] = null;
        }

        dist[g.key(vOrig)] = zero;

        int vOrigAux = g.key(vOrig);

        while (vOrigAux != -1) {
            visited[vOrigAux] = true;
            vOrig = g.vertex(vOrigAux);
            verticesIterator = g.adjVertices(vOrig);

            for (V vertex : verticesIterator) {
                Edge<V, E> edge = g.edge(vOrig, vertex);
                int aux = g.key(vertex);

                if (!visited[aux] && (dist[aux]) == null || ce.compare(dist[aux], sum.apply(dist[g.key(vOrig)], edge.getWeight())) > 0) {
                    dist[aux] = sum.apply(dist[g.key(vOrig)], edge.getWeight());
                    pathKeys[aux] = vOrig;
                }
            }

            E minDist = null;
            int indice = -1;

            for (int i = 0; i < dist.length; i++) {
                if (!visited[i] && (dist[i] != null) && ((minDist == null) || ce.compare(dist[i], minDist) > 0)) {
                    indice = i;
                    minDist = dist[i];
                }
            }

            vOrigAux = indice;
        }

    }

   
    /** Shortest-path between two vertices
     *
     * @param g graph
     * @param vOrig origin vertex
     * @param vDest destination vertex
     * @param ce comparator between elements of type E
     * @param sum sum two elements of type E
     * @param zero neutral element of the sum in elements of type E
     * @param shortPath returns the vertices which make the shortest path
     * @return if vertices exist in the graph and are connected, true, false otherwise
     */
    public static <V, E> E shortestPath(Graph<V, E> g, V vOrig, V vDest,
                                        Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                        LinkedList<V> shortPath) {

        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) {
            return null;
        }

        if (vOrig == vDest) {
            shortPath.push(vOrig);
            return zero;
        }

        shortPath.clear();
        boolean[] visited = new boolean[g.numVertices()];

        @SuppressWarnings("unchecked")
        E[] dist = (E[]) new Object[g.numVertices()];
        @SuppressWarnings("unchecked")
        V[] pathKeys = (V[]) new Object[g.numVertices()];

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

        getPath(g, vOrig, vDest, pathKeys, shortPath);

        return shortPath.isEmpty() ? null : dist[g.key(vDest)];
    }

    /** Shortest-path between a vertex and all other vertices
     *
     * @param g graph
     * @param vOrig start vertex
     * @param ce comparator between elements of type E
     * @param sum sum two elements of type E
     * @param zero neutral element of the sum in elements of type E
     * @param paths returns all the minimum paths
     * @param dists returns the corresponding minimum distances
     * @return if vOrig exists in the graph true, false otherwise
     */
    public static <V, E> boolean shortestPaths(Graph<V, E> g, V vOrig,
                                               Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                               ArrayList<LinkedList<V>> paths, ArrayList<E> dists) {

        if (!g.validVertex(vOrig)) {
            return false;
        }

        int numVerts = g.numVertices();
        boolean[] visited = new boolean[g.numVertices()];
        @SuppressWarnings("unchecked")
        E[] dist = (E[]) new Object[g.numVertices()];
        @SuppressWarnings("unchecked")
        V[] pathKeys = (V[]) new Object[g.numVertices()];

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

        dists.clear();
        paths.clear();

        for (int i = 0; i < numVerts; i++) {
            paths.add(null);
            dists.add(null);
        }

        for (V vertDest : g.vertices()) {
            int i = g.key(vertDest);
            if (dist[i] != null) {
                LinkedList<V> shortPath = new LinkedList<>();
                getPath(g, vOrig, vertDest, pathKeys, shortPath);
                paths.set(i, shortPath);
                dists.set(i, dist[i]);
            }
        }

        return true;
    }

    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf
     * The path is constructed from the end to the beginning
     *
     * @param g        Graph instance
     * @param vOrig    information of the Vertex origin
     * @param vDest    information of the Vertex destination
     * @param pathKeys minimum path vertices keys
     * @param path     stack with the minimum path (correct order)
     */
    private static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest,
                                       V [] pathKeys, LinkedList<V> path) {

        if (g.adjVertices(vDest).isEmpty()) {
            return;
        }

        if (vDest != null) {
            path.addFirst(vDest);

            if (g.key(vOrig) != g.key(vDest)) {
                int index = g.key(vDest);
                vDest = pathKeys[index];
                getPath(g, vOrig, vDest, pathKeys, path);
            }
        }
    }

    /** Calculates the minimum distance graph using Floyd-Warshall
     * 
     * @param g initial graph
     * @param ce comparator between elements of type E
     * @param sum sum two elements of type E
     * @return the minimum distance graph
     */
    public static <V,E> MatrixGraph<V,E> minDistGraph(Graph <V,E> g, Comparator<E> ce, BinaryOperator<E> sum) {
        
        throw new UnsupportedOperationException("Not supported yet.");
    }



    public static <V, E> Graph<V, E> kruskalAlgorithm(Graph<V, E> g) {
        Graph<V, E> mst = new MatrixGraph<>(false);
        List<Edge<V, E>> lstEdges = new ArrayList<>(g.edges());
        LinkedList<V> connectedVertexes;

        for (V vertice : g.vertices()) {
            mst.addVertex(vertice);
        }

        lstEdges.sort(new Comparator<Edge<V, E>>() {
            @Override
            public int compare(Edge<V, E> o1, Edge<V, E> o2) {
                return Double.compare((Double) o1.getWeight(), (Double) o2.getWeight());
            }
        });

        for (Edge e : lstEdges) {
            V vertexOrigin = (V) e.getVOrig();
            V vertexDestination = (V) e.getVDest();
            connectedVertexes = DepthFirstSearch(mst, vertexOrigin);
            if (!connectedVertexes.contains(vertexDestination)) {
                mst.addEdge(vertexOrigin, vertexDestination, (E) e.getWeight());
            }
        }
        return mst;
    }

}