package org.isep.Controllers;

import org.isep.Utilities.graph.Local;
import org.isep.Utilities.graph.matrix.MatrixGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Loader {
    /**
     * Obtém uma lista de vértices (locais/hubs) a partir de um ficheiro.
     *
     * @param file o ficheiro contendo dados dos vértices
     * @return uma lista de vértices
     */
    private static List<Local> getLocalList(String file) {
        List<Local> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            reader.readLine(); // Ignorar a linha do cabeçalho
            // Ler cada linha no ficheiro
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0];
                double lt = Double.parseDouble(data[1]);
                double lg = Double.parseDouble(data[2]);

                result.add(new Local(name, lt, lg));
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        Collections.sort(result); // Ordenar os vértices com base nos nomes, questão de teste, não necessário
        return result;
    }

    /**
     * Cria um grafo de matriz com base nos ficheiros de vértice e aresta.
     *
     * @param vertexFile ficheiro que contém dados dos vértices
     * @param edgeFile   ficheiro que contém dados das arestas
     * @param directed   verdadeiro se o grafo é direcionado, falso caso contrário
     * @return grafo de matriz
     */
    public static MatrixGraph<Local, Double> fillMatrixGraph(String vertexFile, String edgeFile, boolean directed) {
        List<Local> localList = getLocalList(vertexFile);
        MatrixGraph<Local, Double> matrixGraph = new MatrixGraph<>(directed);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(edgeFile));
            String line;
            reader.readLine(); // Ignorar a linha do cabeçalho
            // Ler cada linha no ficheiro
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Local vOrig = new Local(data[0]);
                Local vDest = new Local(data[1]);

                // Verificar se ambos os vértices de origem e destino estão na lista de vértices
                if (localList.contains(vOrig) && localList.contains(vDest)) {
                    vOrig = localList.get(localList.indexOf(vOrig));
                    vDest = localList.get(localList.indexOf(vDest));
                    double distanciaKM = (Double.parseDouble(data[2]) / 1000);
                    matrixGraph.addEdge(vOrig, vDest, distanciaKM);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return matrixGraph;
    }
}
