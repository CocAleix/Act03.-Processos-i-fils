package Act03;

import java.io.IOException;
import java.util.Scanner;

// Clase principal que gestiona la multiplicación de matrices usando hilos

public class Main {
    
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  MULTIPLICADOR DE MATRICES CON HILOS");
        System.out.println("=================================================\n");

        // Solicitar dimensiones por consola
        int[] dimensiones = solicitarDimensiones();
        
        int filas1 = dimensiones[0];
        int cols1 = dimensiones[1];
        int filas2 = dimensiones[2];
        int cols2 = dimensiones[3];

        boolean continuar = true;

        while (continuar) {
            try {
                // Crear las matrices
                Matriz matriz1 = new Matriz(filas1, cols1);
                Matriz matriz2 = new Matriz(filas2, cols2);

                // Cargar datos en las matrices
                cargarMatrices(matriz1, matriz2);

                // Mostrar matrices de entrada
                System.out.println("\n=== MATRIZ 1 ===");
                matriz1.mostrar();
                System.out.println("\n=== MATRIZ 2 ===");
                matriz2.mostrar();

                // Multiplicar matrices usando hilos
                Matriz resultado = multiplicarMatrices(matriz1, matriz2);

                // Mostrar resultado
                System.out.println("\n=== MATRIZ RESULTADO ===");
                resultado.mostrar();

                // Preguntar si desea guardar el resultado
                guardarResultado(resultado);

                // Preguntar si desea continuar
                continuar = preguntarContinuar();
                
                // Si continúa, puede cambiar dimensiones
                if (continuar) {
                    System.out.print("\n¿Desea usar las mismas dimensiones? (s/n): ");
                    String resp = scanner.next();
                    scanner.nextLine();
                    
                    if (!resp.equalsIgnoreCase("s")) {
                        dimensiones = solicitarDimensiones();
                        filas1 = dimensiones[0];
                        cols1 = dimensiones[1];
                        filas2 = dimensiones[2];
                        cols2 = dimensiones[3];
                    }
                }

            } catch (Exception e) {
                System.err.println("Error durante la ejecución: " + e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println("\n¡Gracias por usar el multiplicador de matrices!");
        scanner.close();
    }

    // Solicita las dimensiones de las matrices por consola
    
    private static int[] solicitarDimensiones() {
        int filas1, cols1, filas2, cols2;
        
        while (true) {
            try {
                System.out.println("\n--- Dimensiones de las Matrices ---");
                
                System.out.print("Número de filas de la Matriz 1 (1-20): ");
                filas1 = scanner.nextInt();
                
                System.out.print("Número de columnas de la Matriz 1 (1-20): ");
                cols1 = scanner.nextInt();
                
                System.out.print("Número de filas de la Matriz 2 (1-20): ");
                filas2 = scanner.nextInt();
                
                System.out.print("Número de columnas de la Matriz 2 (1-20): ");
                cols2 = scanner.nextInt();
                
                scanner.nextLine(); // Limpiar buffer

                // Validar que estén entre 1 y 20
                if (filas1 < 1 || filas1 > 20 || cols1 < 1 || cols1 > 20 ||
                    filas2 < 1 || filas2 > 20 || cols2 < 1 || cols2 > 20) {
                    System.err.println("\nERROR: Las dimensiones deben estar entre 1 y 20.");
                    System.out.println("Por favor, inténtelo de nuevo.\n");
                    continue;
                }

                // Validar que columnas de matriz1 = filas de matriz2
                if (cols1 != filas2) {
                    System.err.println("\nERROR: El número de columnas de la primera matriz (" + cols1 + 
                                       ") debe ser igual al número de filas de la segunda matriz (" + filas2 + ").");
                    System.out.println("Por favor, inténtelo de nuevo.\n");
                    continue;
                }

                System.out.println("\nDimensiones validadas correctamente:");
                System.out.println("  Matriz 1: " + filas1 + "x" + cols1);
                System.out.println("  Matriz 2: " + filas2 + "x" + cols2);
                System.out.println("  Resultado: " + filas1 + "x" + cols2);

                return new int[]{filas1, cols1, filas2, cols2};

            } catch (Exception e) {
                System.err.println("\nERROR: Debe introducir números enteros válidos.");
                System.out.println("Por favor, inténtelo de nuevo.\n");
                scanner.nextLine(); // Limpiar buffer en caso de error
            }
        }
    }

    // Carga los datos en las matrices (desde consola o archivo)
    
    private static void cargarMatrices(Matriz matriz1, Matriz matriz2) throws IOException {
        System.out.println("\n¿Cómo desea introducir las matrices?");
        System.out.println("1. Desde consola (introducir valores manualmente)");
        System.out.println("2. Desde archivos de texto");
        System.out.print("Seleccione una opción (1 o 2): ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        if (opcion == 1) {
            // Leer desde consola
            matriz1.leerDesdeConsola(scanner, "Matriz 1");
            matriz2.leerDesdeConsola(scanner, "Matriz 2");
        } else if (opcion == 2) {
            // Leer desde archivos
            System.out.print("Nombre del archivo para Matriz 1: ");
            String archivo1 = scanner.nextLine();
            matriz1.leerDesdeArchivo(archivo1);
            System.out.println("Matriz 1 cargada desde " + archivo1);

            System.out.print("Nombre del archivo para Matriz 2: ");
            String archivo2 = scanner.nextLine();
            matriz2.leerDesdeArchivo(archivo2);
            System.out.println("Matriz 2 cargada desde " + archivo2);
        } else {
            System.out.println("Opción no válida. Se usará entrada por consola.");
            matriz1.leerDesdeConsola(scanner, "Matriz 1");
            matriz2.leerDesdeConsola(scanner, "Matriz 2");
        }
    }

    // Multiplica dos matrices usando hilos
    // Cada elemento de la matriz resultado es calculado por un hilo diferente
     
    private static Matriz multiplicarMatrices(Matriz matriz1, Matriz matriz2) throws InterruptedException {
        int filasResultado = matriz1.getFilas();
        int columnasResultado = matriz2.getColumnas();
        
        Matriz resultado = new Matriz(filasResultado, columnasResultado);
        
        // Crear array de hilos
        int totalHilos = filasResultado * columnasResultado;
        HiloMultiplicador[] hilos = new HiloMultiplicador[totalHilos];
        
        System.out.println("\nCreando " + totalHilos + " hilos para calcular la matriz resultado...");
        
        // Crear y arrancar todos los hilos
        int indiceHilo = 0;
        for (int i = 0; i < filasResultado; i++) {
            for (int j = 0; j < columnasResultado; j++) {
                hilos[indiceHilo] = new HiloMultiplicador(matriz1, matriz2, i, j);
                hilos[indiceHilo].start();
                indiceHilo++;
            }
        }
        
        System.out.println("Esperando a que todos los hilos terminen...");
        
        // Esperar a que todos los hilos terminen y recoger resultados
        for (int i = 0; i < totalHilos; i++) {
            hilos[i].join(); // Esperar a que el hilo termine
            
            // Recoger el resultado y colocarlo en la matriz resultado
            int fila = hilos[i].getFila();
            int columna = hilos[i].getColumna();
            int valor = hilos[i].getResultado();
            resultado.setElemento(fila, columna, valor);
        }
        
        System.out.println("¡Todos los hilos han terminado!");
        
        return resultado;
    }

    // Pregunta al usuario si desea guardar el resultado
    
    private static void guardarResultado(Matriz resultado) {
        System.out.print("\n¿Desea guardar la matriz resultado en un archivo? (s/n): ");
        String respuesta = scanner.next();
        scanner.nextLine(); // Limpiar buffer

        if (respuesta.equalsIgnoreCase("s")) {
            System.out.print("Nombre del archivo de salida: ");
            String nombreArchivo = scanner.nextLine();
            try {
                resultado.guardarEnArchivo(nombreArchivo);
                System.out.println("Matriz guardada exitosamente en " + nombreArchivo);
            } catch (IOException e) {
                System.err.println("Error al guardar el archivo: " + e.getMessage());
            }
        }
    }

    // Pregunta al usuario si desea realizar otra multiplicación
    
    private static boolean preguntarContinuar() {
        System.out.print("\n¿Desea realizar otra multiplicación? (s/n): ");
        String respuesta = scanner.next();
        scanner.nextLine(); // Limpiar buffer
        
        return respuesta.equalsIgnoreCase("s");
    }
}