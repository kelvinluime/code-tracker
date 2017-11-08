package com.kinmanlui.code;

import com.kinmanlui.structures.Graph;

import java.util.Stack;

public class GraphTest implements Runnable {

    @Override
    public void run() {
        Graph<String> projectGraph = new Graph<>();

        // Here are some classes that I will take at university
        projectGraph.addVertex("CAT 125");
        projectGraph.addVertex("Practicum");
        projectGraph.addVertex("CSE 15L");
        projectGraph.addVertex("CSE 21");
        projectGraph.addVertex("CSE 103");
        projectGraph.addVertex("CSE 100");
        projectGraph.addVertex("CSE 101");
        projectGraph.addVertex("CSE 105");
        projectGraph.addVertex("CSE 110");
        projectGraph.addVertex("CSE 140");
        projectGraph.addVertex("CSE 140L");
        projectGraph.addVertex("CSE 141");
        projectGraph.addVertex("CSE 141L");
        projectGraph.addVertex("CSE 120");
        projectGraph.addVertex("CSE 132A");
        projectGraph.addVertex("CSE 107");
        projectGraph.addVertex("CSE 150");
        projectGraph.addVertex("CSE 130");
        projectGraph.addVertex("CSE 131");
        projectGraph.addVertex("CSE 158");
        projectGraph.addVertex("COGS 118A");
        projectGraph.addVertex("COGS 118B");
        projectGraph.addVertex("COGS 188");
        projectGraph.addVertex("Math 20E");
        projectGraph.addVertex("Math 180A");

        // Dependencies based on prerequisites
        projectGraph.addEdge("CSE 15L", "CSE 100");
        projectGraph.addEdge("CSE 21", "CSE 100");
        projectGraph.addEdge("CSE 15L", "CSE 105");
        projectGraph.addEdge("CSE 21", "CSE 105");
        projectGraph.addEdge("CSE 140", "CSE 141");
        projectGraph.addEdge("CSE 140", "CSE 140L");
        projectGraph.addEdge("CSE 141", "CSE 141L");
        projectGraph.addEdge("CSE 100", "CSE 132A");
        projectGraph.addEdge("CSE 21", "CSE 107");
        projectGraph.addEdge("CSE 101", "CSE 107");
        projectGraph.addEdge("CSE 105", "CSE 107");
        projectGraph.addEdge("CSE 100", "CSE 150");
        projectGraph.addEdge("CSE 100", "CSE 131");
        projectGraph.addEdge("CSE 105", "CSE 131");
        projectGraph.addEdge("CSE 110", "CSE 131");
        projectGraph.addEdge("CSE 130", "CSE 131");
        projectGraph.addEdge("CSE 100", "CSE 130");
        projectGraph.addEdge("CSE 105", "CSE 130");
        projectGraph.addEdge("Math 20E", "COGS 118A");
        projectGraph.addEdge("Math 180A", "COGS 118A");
        projectGraph.addEdge("Math 20E", "COGS 118B");
        projectGraph.addEdge("Math 180A", "COGS 118B");
        projectGraph.addEdge("COGS 118A", "COGS 188");
        projectGraph.addEdge("COGS 118A", "COGS 118B");
        projectGraph.addEdge("COGS 118B", "COGS 188");

        Stack<String> stack = projectGraph.getTopologicalOrder();
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}
