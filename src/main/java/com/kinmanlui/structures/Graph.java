/**
 * Implements the data structure of graph. It does not allow duplicate entries in the graph.
 * @author Kelvin Lui
 * @since 2017-9-5
 */
package com.kinmanlui.structures;

import java.util.*;

public class Graph<T> {

    public HashMap<T, Vertex<T>> vertices;
    private int edgeCount;

    public Graph() {
        vertices = new HashMap<>();
        edgeCount = 0;
    }

    /**
     * Adds a vertex into the graph. This new vertex does not have an edge to any vertex in the graph.
     * @param label The label of the new vertex to be added into the graph.
     * @return true if the addition is successful
     */
    public boolean addVertex(T label) {
        Vertex<T> result = vertices.put(label, new Vertex<>(label));
        return result != null;
    }

    /**
     * Determines whether the graph contains loop.
     * @return true if the graph contains loop(s)
     */
    public boolean containsLoop() {
        HashSet<Vertex<T>> set;
        resetVertices();
        for(Vertex<T> v : vertices.values()) {
            set = new HashSet<>();
            while(v.hasNeighbor()) {
                // TODO: 12/6/17 Test if it has cycle
            }
        }
        return false;
    }

    /**
     * Adds an edge between two vertices based on their labels.
     * @param begin The vertex that is the beginning of the edge.
     * @param end The vertex that is the end of the edge.
     * @return true if addition is successful
     */
    public boolean addEdge(T begin, T end) {
        Vertex<T> beginVertex = vertices.get(begin);
        Vertex<T> endVertex = vertices.get(end);

        if(beginVertex == null || endVertex == null) {
            return false;
        }

        beginVertex.connect(endVertex);
        edgeCount++;
        return true;
    }

    public boolean hasEdge(T begin, T end) {
        Vertex<T> beginVertex = vertices.get(begin);
        Vertex<T> endVertex = vertices.get(end);

        if(beginVertex == null || endVertex == null) {
            return false;
        }

        Iterator<Vertex<T>> vertexIterator = beginVertex.getNeighborIterator();

        while(vertexIterator.hasNext()) {
            if(vertexIterator.next().equals(endVertex)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the topological order of actions based on this graph. A topological order is not possible for a graph that
     * has a cycle.
     * @return The stack which contains the topological order from its top to bottom.
     */
    public Stack<T> getTopologicalOrder() {
        Stack<T> order = new Stack<>();
        resetVertices();

        for(int i = 0; i < vertices.values().size(); i++) {
            Vertex<T> next = null;
            for(Vertex<T> v : vertices.values())  {
                if(!v.isVisited() && v.getUnvisitedNeighbor() == null) {
                    next = v;
                }
            }
            if(next != null) {
                order.push(next.getLabel());
                next.visit();
            }
        }

        return order;
    }

    public Queue<T> getDFS(T begin, T end) {
        Vertex<T> beginVertex = vertices.get(begin);
        Vertex<T> endVertex = vertices.get(end);

        if(beginVertex == null || endVertex == null) {
            return null;
        }

        resetVertices();
        Queue<T> resultQueue = new ArrayDeque<>();
        Stack<Vertex<T>> vertexStack= new Stack<>();
        vertexStack.push(beginVertex);
        Vertex<T> current;
        Iterator<Vertex<T>> neighborIterator;
        while(!vertexStack.isEmpty()) {
            current = vertexStack.pop();
            resultQueue.add(current.getLabel());
            current.visit();
            if(current.getLabel().equals(end)) {
                break;
            }
            neighborIterator = current.getNeighborIterator();
            while(neighborIterator.hasNext()) {
                Vertex<T> nextNeighbor = neighborIterator.next();
                if(!nextNeighbor.isVisited()) {
                    vertexStack.push(nextNeighbor);
                }
            }
        }
        return resultQueue;
    }

    public Queue<T> getBFS(T begin, T end) {
        Vertex<T> beginVertex = vertices.get(begin);
        Vertex<T> endVertex = vertices.get(end);

        if(beginVertex == null || endVertex == null) {
            return null;
        }

        resetVertices();
        Queue<T> resultQueue = new ArrayDeque<>();
        Queue<Vertex<T>> vertexQueue = new ArrayDeque<>();
        vertexQueue.add(beginVertex);
        Vertex<T> current;
        Iterator<Vertex<T>> neighborIterator;
        while(!vertexQueue.isEmpty()) {
            current = vertexQueue.remove();
            resultQueue.add(current.getLabel());
            current.visit();
            if(current.getLabel().equals(end)) {
                break;
            }
            neighborIterator = current.getNeighborIterator();
            while(neighborIterator.hasNext()) {
                Vertex<T> nextNeighbor = neighborIterator.next();
                if(!nextNeighbor.isVisited()) {
                    vertexQueue.add(nextNeighbor);
                }
            }
        }
        return resultQueue;
    }

    public boolean isEmpty() { return vertices.isEmpty(); }

    public void clear() { vertices.clear(); }

    public int getNumberOfVertices() { return vertices.size(); }

    public int getNumberOfEdges() { return edgeCount; }

    /**
     * Reset all vertices to unvisited. It is used before traversing vertices in other methods.
     */
    private void resetVertices() {
        Iterator<Vertex<T>> vertexIterator = vertices.values().iterator();
        while(vertexIterator.hasNext()) {
            vertexIterator.next().unvisit();
        }
    }
}
