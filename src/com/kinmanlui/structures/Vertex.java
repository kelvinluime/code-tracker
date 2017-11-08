/**
 * Implements vertices in a unweighted graph, i.e., every edge is unweighted.
 * @author Kelvin Lui
 * @since 2017-9-5
 */

package com.kinmanlui.structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Vertex<T> {

    private T label;
    private ArrayList<Edge> edges;
    private ArrayList<Vertex<T>> predecessors;
    private boolean isVisited;

    public Vertex(T label) {
        this.label = label;
        edges = new ArrayList<>();
        predecessors = new ArrayList<>();
        isVisited = false;
    }

    public T getLabel() { return label; }

    public void visit() {
        isVisited = true;
    }

    public void unvisit() {
        isVisited = false;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public Iterator<Vertex<T>> getNeighborIterator() { return new NeighborIterator(); }

    public ArrayList<Vertex<T>> getPredecessors() {
        ArrayList<Vertex<T>> result = new ArrayList<>();
        result.addAll(predecessors);
        return result;
    }

    /**
     * Connects this vertex with another vertex as its neighbor. If the end vertex has the exactly same element,
     * it prevents from adin a new vertex.
     * @param endVertex The vertex to be connected to this vertex.
     * @return A boolean value which indicates whether the connection is successful.
     */
    public boolean connect(Vertex<T> endVertex) {
        if(this.equals(endVertex)) {
            return false;
        }

        endVertex.unvisit();
        boolean isDuplicate = false;
        for(Edge e : edges) {
            if(e.endVertex.equals(endVertex)) {
               isDuplicate = true;
            }
        }

        if(!isDuplicate) {
            edges.add(new Edge(endVertex));
            endVertex.predecessors.add(this);
        }

        return true;
    }

    public boolean hasNeighbor() { return !edges.isEmpty(); }

    /**
     * Gets an unvisited neighbor of this vertex. If there is no unvisited neighbor, it returns null.
     * @return The neighbor vertex that is unvisited.
     */
    public Vertex<T> getUnvisitedNeighbor() {
        for(Edge e : edges) {
            if(!e.endVertex.isVisited()) {
                return e.endVertex;
            }
        }
        return null;
    }

    private class Edge {

        private Vertex<T> endVertex;

        private Edge(Vertex<T> endVertex) {
            this.endVertex = endVertex;
            isVisited = false;
        }
    }

    public class NeighborIterator implements Iterator<Vertex<T>> {

        private Iterator<Edge> edgeIterator;

        private NeighborIterator() {
            edgeIterator = edges.listIterator();
        }

        @Override
        public boolean hasNext() {
            return edgeIterator.hasNext();
        }

        @Override
        public Vertex<T> next() {
            Vertex<T> nextNeighbor = null;
            if(edgeIterator.hasNext()) {
                Edge nextEdge = edgeIterator.next();
                nextNeighbor = nextEdge.endVertex;
            } else {
                throw new NoSuchElementException();
            }
            return nextNeighbor;
        }
    }
}
