package lab;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GeneratingGraphs {
    static Random random = new Random();

    public static ArrayList<Edge> generateGraph(int vertexAmount, int edgeAmount) {

        ArrayList<Edge> edges = new ArrayList<Edge>();
        HashSet<String> existingEdges = new HashSet<>();

        for (int i = 1; i < vertexAmount; i++) {
            int weight = random.nextInt(10) + 1;
            int from = random.nextInt(i);
            edges.add(new Edge(from, i, weight));
            String key = Math.min(from, i) + "-" + Math.max(from, i);
            existingEdges.add(key);
        }

        while (edges.size() < edgeAmount) {
            int from = random.nextInt(vertexAmount);
            int to = random.nextInt(vertexAmount);
            if (from == to) {
                continue;
            }
            String key = Math.min(from, to) + "-" + Math.max(from, to);
            if (!existingEdges.contains(key)) {
                int weight = random.nextInt(10) + 1;
                edges.add(new Edge(from, to, weight));
                existingEdges.add(key);
            }
        }

        return edges;
    }

    public static void makeFile(ArrayList<Edge> edges, int vertexAmount, String filename, int number) throws IOException {
        FileWriter writer = new FileWriter(filename);
        writer.write(number + " " + vertexAmount + " " + edges.size() + "\n");
        for (Edge edge : edges) {
            writer.write(edge.getFrom() + " " + edge.getTo() + " " + edge.getWeight() + "\n");
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        int number = 1;
        for (int N = 100; N <= 10000; N=N+100) {
            int vertexAmount = N;
            int edgeAmount = vertexAmount * 20;
            ArrayList<Edge> edges = generateGraph(vertexAmount, edgeAmount);
            String filename = String.format("graphs\\graph_%03d.txt", number);
            makeFile(edges, vertexAmount, filename, number);
            number += 1;
        }
    }
}
