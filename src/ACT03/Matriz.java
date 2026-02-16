package Act03;

import java.io.*;
import java.util.Scanner;

// Clase que representa una matriz de enteros

public class Matriz {
    private int[][] datos;
    private int filas;
    private int columnas;

    // Constructor que crea una matriz vacía
    
    public Matriz(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.datos = new int[filas][columnas];
    }

    // Constructor que crea una matriz desde un array bidimensional
    
    public Matriz(int[][] datos) {
        this.filas = datos.length;
        this.columnas = datos[0].length;
        this.datos = datos;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getElemento(int fila, int columna) {
        return datos[fila][columna];
    }

    public void setElemento(int fila, int columna, int valor) {
        datos[fila][columna] = valor;
    }

    // Lee los valores de la matriz desde consola
    
    public void leerDesdeConsola(Scanner scanner, String nombre) {
        System.out.println("\n=== Introducir valores para " + nombre + " ===");
        System.out.println("Dimensiones: " + filas + "x" + columnas);
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print("Elemento [" + i + "][" + j + "]: ");
                datos[i][j] = scanner.nextInt();
            }
        }
    }

    // Lee los valores de la matriz desde un archivo
    // Formato: cada línea contiene los valores de una fila separados por espacios
     
    public void leerDesdeArchivo(String nombreArchivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
        String linea;
        int fila = 0;

        while ((linea = br.readLine()) != null && fila < filas) {
            String[] valores = linea.trim().split("\\s+");
            for (int col = 0; col < columnas && col < valores.length; col++) {
                datos[fila][col] = Integer.parseInt(valores[col]);
            }
            fila++;
        }
        br.close();
    }

    // Guarda la matriz en un archivo
    // Formato: cada línea contiene los valores de una fila separados por espacios
     
    public void guardarEnArchivo(String nombreArchivo) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo));
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                bw.write(datos[i][j] + "");
                if (j < columnas - 1) {
                    bw.write(" ");
                }
            }
            bw.newLine();
        }
        bw.close();
    }

    // Muestra la matriz por consola de forma formateada
     
    public void mostrar() {
        // Calcular el ancho máximo necesario
        int maxAncho = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                int ancho = String.valueOf(datos[i][j]).length();
                if (ancho > maxAncho) {
                    maxAncho = ancho;
                }
            }
        }
        maxAncho += 2; // Espacio adicional

        // Mostrar matriz
        for (int i = 0; i < filas; i++) {
            System.out.print("| ");
            for (int j = 0; j < columnas; j++) {
                System.out.printf("%" + maxAncho + "d", datos[i][j]);
            }
            System.out.println(" |");
        }
    }
}
