package lab;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        int graphNumber = 1;
        double[] times = new double[100];
        int[] iterations = new int[100];
        for (int currentGraph = 0; currentGraph < 100; currentGraph++) {
            long[] timesForCurrentGraph = new long[10];
            long[] iterationsForCurrentGraph = new long[10];
            for (int runNumber = 0; runNumber < 10; runNumber++) {
                String filename = String.format("graphs\\graph_%03d.txt", graphNumber);
                ArrayList<ArrayList<Edge>> graph = readFile(filename);
                long start = System.nanoTime();
                int iteration = findMST(graph);
                long end = System.nanoTime();
                timesForCurrentGraph[runNumber] = end - start;
                iterationsForCurrentGraph[runNumber] = iteration;

            }
            double time = (double) ((sum(timesForCurrentGraph)) / 10);
            times[currentGraph] = time;
            int iteration = (int) ((sum(iterationsForCurrentGraph)) / 10);
            iterations[currentGraph] = iteration;
            graphNumber++;
        }
        makeFile(times, iterations, "result.txt");
    }

    public static int findMST(ArrayList<ArrayList<Edge>> graph) {
        int iterations = 0;
        if (graph == null || graph.isEmpty()) {
            return 0;
        }

        int vertexCount = graph.size();
        boolean[] inMST = new boolean[vertexCount];
        ArrayList<Edge> mstEdges = new ArrayList<Edge>(vertexCount - 1);
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<Edge>();

        inMST[0] = true;
        priorityQueue.addAll(graph.get(0));

        while (mstEdges.size() < vertexCount - 1 && !priorityQueue.isEmpty()) {
            Edge minEdge = priorityQueue.poll();
            iterations++;
            if (inMST[minEdge.getTo()]) {
                continue;
            }

            mstEdges.add(minEdge);
            inMST[minEdge.getTo()] = true;

            for (Edge edge : graph.get(minEdge.getTo())) {
                if (!inMST[edge.getTo()]) {
                    priorityQueue.add(edge);
                }
                iterations++;
            }
        }
        return iterations;
    }

    public static ArrayList<ArrayList<Edge>> readFile(String filename) throws IOException {
        Scanner scanner = new Scanner(new File(filename));
        int number = scanner.nextInt();
        int vertexAmount = scanner.nextInt();
        int edgeAmount = scanner.nextInt();

        ArrayList<ArrayList<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < vertexAmount; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edgeAmount; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            int weight = scanner.nextInt();

            graph.get(from).add(new Edge(from, to, weight));
            graph.get(to).add(new Edge(to, from, weight));
        }

        return graph;
    }

    public static void makeFile(double[] times, int[] iterations, String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        String str = String.format("%-10s %-25s %-10s%n", "Размер N", "Время выполнения, мс", "Количество итераций");
        writer.write(str);
        int N = 100;
        for (int x = 0; x < 100; x++) {
            str = String.format("%-10d %-25s %-10s%n", N, (""+ times[x] / 1_000_000).replace(".", ","), iterations[x]);
            writer.write(str);
            N = N + 100;
        }
        writer.close();
    }

    public static long sum(long[] massive) {
        long cnt = 0;
        for (int i = 0; i < massive.length; i++) {
            cnt += massive[i];
        }
        return cnt;
    }


}
