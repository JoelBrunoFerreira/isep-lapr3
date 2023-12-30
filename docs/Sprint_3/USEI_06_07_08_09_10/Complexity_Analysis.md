# Análise de Complexidade Algoritmica
>V é o número de vértices e E é o número de arestas no grafo

## USEI06
### Methods Used | Time Complexity:
>findAllRoutes:
>>Complexidade: O(V + E)
>>>Isso ocorre porque o DFS visita cada vértice e cada aresta exatamente uma vez.

>findAllRoutesDFS:
>>Complexidade: O(V + E)
>>Pelo mesmo motivo mencionado acima.

>findLocalByName:
>>Complexidade: O(V)
>>>O método percorre todos os vértices para encontrar o local pelo nome.

>calculateTime:
>>Complexidade: O(1)

>getDistances:
>>Complexidade: O(V)
>>>O método percorre a lista de vértices na rota, e para cada vértice, procura pela aresta adjacente no grafo.



## USEI07
### Methods Used | Time Complexity:
> getNextHubToVisit() | O(n * (V + E) + n  log n)

> computePath() | O(n)

> getPathWithMaxHubs() | O(n * (V + E) + n log n)


## USEI08
### Methods Used | Time Complexity:



## USEI09
### Methods Used | Time Complexity:
> getSpecificHub() | O(n log n)

> sortDistancesByMinPaths() | O(n log n)

> GetDistancesList() | O(V * (V + E) * log V)

> getConnectedComponents() | O(V * (V + E))

> getNStrategicClusters | O((V + E) + V * (V + E) * log V)

> getClustersAndSpecificHub | O((V + E) + V * (V + E) * log V)


## USEI10
### Methods Used | Time Complexity:
> getResidualCapacity() | O(1)

> addResidualFlowTo() | O(1)

> addEdge() | O(1) 

> hasAugmentingPath() | O(V + E)

> FordFulkerson | O(V + E) * E

