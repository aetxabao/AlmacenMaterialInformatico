package com.masanz.ami.model;


import java.util.Objects;

/**
 * La clase Posicion implementa la interfaz Comparable que permite comparar posiciones.
 */
public class Posicion implements Comparable<Posicion> {

    private final static String letras="ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /** La fila va de 0 a la constante FILAS - 1*/
    private char fila;
    /** La columna va de 0 a la constante COLUMNAS - 1*/
    private int columna;

    /**
     * Crea un objeto con valor por defecto A0,
     * que representa una posición en el armario,
     * fila A y columna 0
     */
    public Posicion() {
        this.fila = letras.charAt(0);
        this.columna = 0;
    }

    /**
     * Crea un objeto que representa una posición en el armario
     * @param fila un valor de la A (posición 0) a la letra que corresponda a FILAS - 1, ambos incluidos
     * @param columna un valor de 0 a la constante COLUMNAS - 1, ambos incluidos
     */
    public Posicion(char fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    /**
     * Crea un objeto que representa una posición en el armario
     * @param fila un valor de 0 a la constante FILAS - 1, ambos incluidos
     * @param columna un valor de 0 a la constante COLUMNAS - 1, ambos incluidos
     */
    public Posicion(int fila, int columna) {
        this.fila = letras.charAt(fila);
        this.columna = columna;
    }

    /**
     * Crea un objeto que representa una posición en el armario
     * @param posicion cadena que representa una posición en el armario, siendo A1 la fila 0 y la columna 0
     */
    public Posicion(String posicion) {
        this.fila = posicion.charAt(0);
        this.columna = Integer.parseInt("" + posicion.charAt(1)) - 1;
    }

    // region getters and setters
    public char getFila() {
        return fila;
    }

    public int getFilaNumber() {
        return filaToInt(fila);
    }

    public void setFila(char fila) {
        this.fila = fila;
    }

    public void setFilaNumber(int fila) {
        this.fila = filaToChar(fila);
    }

    public void setFilaColumnaNumbers(int fila, int columna) {
        setFilaNumber(fila);
        setColumna(columna);
    }

    public static String getLetras() {
        return letras;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    // endregion

    private int filaToInt(char fila){
        return letras.indexOf(fila);
    }

    private char filaToChar(int fila){
        return letras.charAt(fila);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Posicion posicion = (Posicion) o;
        return fila == posicion.fila && columna == posicion.columna;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fila, columna);
    }

    @Override
    public String toString() {
        return "" + fila + (columna+1);
    }

    @Override
    public int compareTo(Posicion o) {
        return this.hashCode() - o.hashCode();
    }

}
