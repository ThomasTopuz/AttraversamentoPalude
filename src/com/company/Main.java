package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int height = 0;
        int width = 0;
        do {
            // input
            System.out.println("Insert the swamp's height.");
            height = sc.nextInt();
            System.out.println("Insert the swap's width.");
            width = sc.nextInt();

            // data validation
            while (!(height > 0 && width > 0)) {
                System.out.println("ERROR! - width and height must be greater than 1.");

                System.out.println("Insert the swamp's height.");
                height = sc.nextInt();
                System.out.println("Insert the swap's width.");
                width = sc.nextInt();
            }

            int[][] palude = generateSwamp(height, width);
            ArrayList<Integer> path = new ArrayList<>();
            printSwampInt(palude);

            // traverse the fist column to search a path
            for (int i = 0; i < palude[0].length; i++) {
                ArrayList<Integer> shortestPath = new ArrayList<>();
                searchPath(palude, 0, i, path);
                if (path.size() > 1) {
                    break; //if path is found
                }
            }

            // output
            if (path.size() > 0) {
                printPath(path);
                printSwampStr(drawPath(palude, path));
            } else {
                System.out.println("No path found for the generated swamp :(");
            }

            // try again
            System.out.println("Try again? {y} {n}");
        } while (sc.next().equals("y"));

        System.out.println("Process ended!");
    }

    // randomly generate the binary swamp
    public static int[][] generateSwamp(int larghezza, int altezza) {
        Random random = new Random();
        int[][] palude = new int[larghezza][altezza];
        double ones = ((double) larghezza * (double) altezza / 100) * 65; //ones percentile (65%)

        for (int i = 0; i < ones; ) {
            int x = random.nextInt(altezza);
            int y = random.nextInt(larghezza);
            if (palude[y][x] == 0) {
                palude[y][x] = 1;
                i++;
            }
        }
        return palude;
    }

    // recursive DFS implementation for finding a path from a point to another
    public static boolean searchPath(int[][] palude, int x, int y, ArrayList<Integer> path) {

        // validazione x e y per evitare un outofbound
        if (x < 0 || x >= palude[0].length || y < 0 || y >= palude.length) {
            	System.out.print(2);
		return false;
        }

        //arrivato alla fine della palude
        if (x == palude[0].length - 1 && palude[y][x] == 1) {
            path.add(x);
            path.add(y);
            return true;
        }

        //[y][x] è piu facile da visualizzare in fase di sviluppo
        if (palude[y][x] == 1) {
            palude[y][x] = 2; //per marcarlo

            //dx = distanza orizzontale dy = distanza verticale

            //a destra
            int dx = 1;
            int dy = 0;
            if (searchPath(palude, x + dx, y + dy, path)) { //ricorsione
                path.add(x);
                path.add(y);
                return true;
            }

            //in alto a destra
            dx = 1;
            dy = 1;
            if (searchPath(palude, x + dx, y + dy, path)) {
                path.add(x);
                path.add(y);
                return true;
            }

            //in basso a destra
            dx = 1;
            dy = -1;
            if (searchPath(palude, x + dx, y + dy, path)) {
                path.add(x);
                path.add(y);
                return true;
            }

            //in basso
            dx = 0;
            dy = -1;
            if (searchPath(palude, x + dx, y + dy, path)) {
                path.add(x);
                path.add(y);
                return true;
            }

            //in alto
            dx = 0;
            dy = 1;
            if (searchPath(palude, x + dx, y + dy, path)) {
                path.add(x);
                path.add(y);
                return true;
            }

            //in basso a sinistra
            dx = -1;
            dy = -1;
            if (searchPath(palude, x + dx, y + dy, path)) {
                path.add(x);
                path.add(y);
                return true;
            }

            //in alto a sinistra
            dx = -1;
            dy = 1;
            if (searchPath(palude, x + dx, y + dy, path)) {
                path.add(x);
                path.add(y);
                return true;
            }

            //a sinistra
            dx = -1;
            dy = 0;
            if (searchPath(palude, x + dx, y + dy, path)) {
                path.add(x);
                path.add(y);
                return true;
            }
        }
        return false;
    }

    // draw the found path in the swamp using "*"
    public static String[][] drawPath(int[][] palude, ArrayList<Integer> path) {
        for (int i = 0; i < path.size(); i += 2) {
            palude[path.get(i + 1)][path.get(i)] = 3;
        }
        //to string
        String[][] strMaze = new String[palude.length][palude[0].length];
        for (int i = 0; i < palude.length; i++) {
            for (int j = 0; j < palude[i].length; j++) {
                if (palude[i][j] == 3) {
                    strMaze[i][j] = "*";
                } else if (palude[i][j] == 2) {
                    strMaze[i][j] = "1";
                } else {
                    strMaze[i][j] = Integer.toString(palude[i][j]);
                }
            }
        }
        return strMaze;
    }

    // print the formatted swamp (INT)
    public static void printSwampInt(int[][] palude) {
        System.out.println("Generated swamp:");
        for (int i = 0; i < palude.length; i++) {
            for (int j = 0; j < palude[0].length; j++) {
                System.out.print(palude[i][j] + "  ");
            }
            System.out.println();
        }
    }

    // print the formatted swamp(STRING)
    public static void printSwampStr(String[][] palude) {
        System.out.println("\nSolution:");
        for (int i = 0; i < palude.length; i++) {
            for (int j = 0; j < palude[0].length; j++) {
                System.out.print(palude[i][j] + "  ");
            }
            System.out.println();
        }
    }

    // print the path found (x,y)->(x1,y1)...
    public static void printPath(ArrayList<Integer> path) {
        System.out.println("\nPath: (x;y):");

        // start from end because DFS implementation populate the path array from the end
        for (int i = path.size() - 1; i > 0; i -= 2) {
            System.out.print("(" + path.get(i - 1) + ";" + path.get(i) + ")" + (i == 1 ? "\n" : " --> "));
        }
    }
}
