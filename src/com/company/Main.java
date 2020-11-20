package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //feature branch
        do {
            System.out.println("Inserisci l'altezza della palude:");
            int altezza = sc.nextInt();
            System.out.println("Inserisci la larghezza della palude:");
            int larghezza = sc.nextInt();

            int[][] maze = generatePalude(altezza, larghezza);
            ArrayList<Integer> path = new ArrayList<>();
            printPaludeInt(maze);

            //pathfinding
            for (int i = 0; i < maze[0].length; i++) {
                path.clear();
                ArrayList<Integer> shortestPath = new ArrayList<>();
                searchPath(maze, 0, i, path);
                if (path.size() > 1) {
                    break;
                }
            }

            //output
            if (path.size() > 0) {
                printPath(path);
                printPaludeStr(drawPath(maze, path));
            } else {
                System.out.println("Non è stato possibile trovare un percorso per questa palude :(");
            }

            System.out.println("Riprovare? {s} {n}");
        } while (sc.next().equals("s"));

        System.out.println("Programma terminato!");
    }

    //LOGIC
    public static int[][] generatePalude(int larghezza, int altezza) {
        Random random = new Random();
        int[][] palude = new int[larghezza][altezza];
        double ones = ((double) larghezza * (double) altezza / 100) * 65; //quanti uno devo inserire (il 75%)

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

    public static boolean searchPath(int[][] palude, int x, int y, ArrayList<Integer> path) {

        if (x < 0 || x >= palude[0].length || y < 0 || y >= palude.length) {
            return false;
        }

        //arrivato alla fine della palude
        if (x == palude[0].length - 1 && palude[y][x] == 1) {
            path.add(x);
            path.add(y);
            return true;
        }

        //[y][x] è piu facile da visualizzare
        if (palude[y][x] == 1) {
            palude[y][x] = 2; //per marcarlo

            //destra
            int dx = 1;
            int dy = 0;
            if (searchPath(palude, x + dx, y + dy, path)) {
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

            //sotto
            dx = 0;
            dy = -1;
            if (searchPath(palude, x + dx, y + dy, path)) {
                path.add(x);
                path.add(y);
                return true;
            }

            //sopra
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

            //sinistra
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

    //OUTPUT
    public static void printPaludeInt(int[][] palude) {
        System.out.println("Palude generata:");
        for (int i = 0; i < palude.length; i++) {
            for (int j = 0; j < palude[0].length; j++) {
                System.out.print(palude[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public static void printPaludeStr(String[][] palude) {
        System.out.println("Soluzione:");
        for (int i = 0; i < palude.length; i++) {
            for (int j = 0; j < palude[0].length; j++) {
                System.out.print(palude[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public static void printPath(ArrayList<Integer> path) {
        //print path
        System.out.println("Percorso trovato (x;y):");
        for (int i = path.size() - 1; i > 0; i -= 2) {
            System.out.print("(" + path.get(i - 1) + ";" + path.get(i) + ")" + (i == 1 ? "\n" : " --> "));
        }
    }
}
