/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maxflow;

import java.util.LinkedList;

/**
 *
 * @author Rangita
 */
public class MaxFlow {
    boolean bfs(int rGraph[][], int s, int t, int parent[])
    {
        boolean visited[] = new boolean[8];
        for(int i=0; i<8; ++i)
            visited[i]=false;
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        parent[s]=-1;
         while (!queue.isEmpty())
        {
            int u = queue.poll();
 
            for (int v=0; v<8; v++)
            {
                if (visited[v]==false && rGraph[u][v] > 0)
                {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return (visited[t] == true);
    }
    int fordFulkerson(int graph[][], int s, int t)
    {
        int u, v;
        int newGraph[][] = new int[8][8];
        for (u = 0; u < 8; u++)
            for (v = 0; v < 8; v++)
                newGraph[u][v] = graph[u][v];
 
       int parent[] = new int[8];
       int mflow = 0; 
        while (bfs(newGraph, s, t, parent))
        {
            int pflow = Integer.MAX_VALUE;
            for (v=t; v!=s; v=parent[v]) 
            {
                u = parent[v];
                pflow = Math.min(pflow, newGraph[u][v]);
            }
            for (v=t; v != s; v=parent[v])
            {
                u = parent[v];
                newGraph[u][v] -= pflow;
                newGraph[v][u] += pflow;
            }
            mflow += pflow;
        }
        return mflow;
    }
    public static void main (String[] args) throws java.lang.Exception
    {
       int graph[][] =new int[][] { 
                                        {0, 10, 5, 15, 0, 0,0,0},
                                        {0, 0, 4, 0, 9, 15,0,0},
                                        {0, 0, 0, 4, 0, 8,0,0},
                                        {0, 0, 0, 0, 0, 0,30,0},
                                        {0, 0, 0, 0, 0, 15,0,10},
                                        {0, 0, 0, 0, 0, 0,15,10},
                                        {0, 0, 6, 0, 0, 0,0,10},
                                        {0, 0, 0, 0, 0, 0,15,10},
                                   };
        MaxFlow m = new MaxFlow();
        System.out.println("The maximum possible flow is " +
                           m.fordFulkerson(graph, 0, 7));
 
    }
    
}
