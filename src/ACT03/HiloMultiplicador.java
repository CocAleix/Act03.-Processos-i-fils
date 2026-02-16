package Act03;

// Clase que representa un hilo que calcula un elemento de la matriz resultante
// de la multiplicación de dos matrices

public class HiloMultiplicador extends Thread {
    private Matriz matriz1;
    private Matriz matriz2;
    private int fila;
    private int columna;
    private int resultado;

    public HiloMultiplicador(Matriz matriz1, Matriz matriz2, int fila, int columna) {
        this.matriz1 = matriz1;
        this.matriz2 = matriz2;
        this.fila = fila;
        this.columna = columna;
        this.resultado = 0;
    }

    // Método run que ejecuta el cálculo del elemento
    // C[fila][columna] = suma de (A[fila][k] * B[k][columna]) para todo k
     
    @Override
    public void run() {
        resultado = 0;
        
        // Multiplicar fila de matriz1 por columna de matriz2
        for (int k = 0; k < matriz1.getColumnas(); k++) {
            resultado += matriz1.getElemento(fila, k) * matriz2.getElemento(k, columna);
        }
    }

    // Obtiene el resultado calculado por este hilo
    
    public int getResultado() {
        return resultado;
    }

    // Obtiene la fila del elemento calculado
    
    public int getFila() {
        return fila;
    }

    // Obtiene la columna del elemento calculado
    
    public int getColumna() {
        return columna;
    }
}