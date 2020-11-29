package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int altezza = 0;
        int larghezza = 0;
        do {
            //input
            System.out.println("Inserisci l'altezza della palude:");
            altezza = sc.nextInt();
            System.out.println("Inserisci la larghezza della palude:");
            larghezza = sc.nextInt();

            //validazione dei dati inseriti
            while (!(altezza > 0 && larghezza > 0)) {
                System.out.println("Errore! - Altezza e larghezza devono essere maggiori di 0!");

                System.out.println("Inserisci l'altezza della palude:");
                altezza = sc.nextInt();
                System.out.println("Inserisci la larghezza della palude:");
                larghezza = sc.nextInt();
            }

            int[][] palude = generatePalude(altezza, larghezza);
            ArrayList<Integer> path = new ArrayList<>();
            printPaludeInt(palude);

            //scorro la prima colonna per trovare un attraversamento
            for (int i = 0; i < palude[0].length; i++) {
                ArrayList<Integer> shortestPath = new ArrayList<>();
                searchPath(palude, 0, i, path);
                if (path.size() > 1) {
                    break; //se è stato trovato un percorso, esco dal ciclo
                }
            }

            //output
            if (path.size() > 0) {
                printPath(path);
                printPaludeStr(drawPath(palude, path));
            } else {
                System.out.println("Non è stato possibile trovare un percorso per questa palude :(");
            }

            //reinizializzare il programma
            System.out.println("Riprovare? {s} {n}");
        } while (sc.next().equals("s"));

        System.out.println("Programma terminato!");
    }

    //metodo che genera la palude
    public static int[][] generatePalude(int larghezza, int altezza) {
        Random random = new Random();
        int[][] palude = new int[larghezza][altezza];
        double ones = ((double) larghezza * (double) altezza / 100) * 65; //quanti uno devo inserire (circa il 65%)

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

    //metodo ricorsivo che trova un percorso
    public static boolean searchPath(int[][] palude, int x, int y, ArrayList<Integer> path) {

        // validazione x e y per evitare un outofbound
        if (x < 0 || x >= palude[0].length || y < 0 || y >= palude.length) {
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

    //metodo che disegna il percorso con gli asterischi
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

    //stampa la palude formattata (INT)
    public static void printPaludeInt(int[][] palude) {
        System.out.println("Palude generata:");
        for (int i = 0; i < palude.length; i++) {
            for (int j = 0; j < palude[0].length; j++) {
                System.out.print(palude[i][j] + "  ");
            }
            System.out.println();
        }
    }

    //stampa la palude formattata (STRING)
    public static void printPaludeStr(String[][] palude) {
        System.out.println("\nSoluzione:");
        for (int i = 0; i < palude.length; i++) {
            for (int j = 0; j < palude[0].length; j++) {
                System.out.print(palude[i][j] + "  ");
            }
            System.out.println();
        }
    }

    //stampa il percorso trovato
    public static void printPath(ArrayList<Integer> path) {
        System.out.println("\nPercorso trovato (x;y):");

        //parto dal fondo dato che il percorso viene creato al contrario
        for (int i = path.size() - 1; i > 0; i -= 2) {
            System.out.print("(" + path.get(i - 1) + ";" + path.get(i) + ")" + (i == 1 ? "\n" : " --> "));
        }
    }
}
