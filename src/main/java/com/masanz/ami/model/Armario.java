package com.masanz.ami.model;

import com.masanz.ami.enums.EOrden;
import com.masanz.ami.enums.ETipoArticulo;

import java.util.*;


public class Armario {

    public static final int FILAS = 5;
    public static final int COLUMNAS = 4;
    public static final int ESPACIOS_X_CELDA = 4;
    private final Celda[][] celdas;

    /**
     * Instanciación de las celdas con 4 espacios.
      */
    public Armario() {
        celdas = new Celda[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                celdas[i][j] = new Celda(ESPACIOS_X_CELDA);
            }
        }
    }

    public int getFilas(){
        return celdas.length;
    }

    public int getColumnas(){
        return celdas[0].length;
    }

    public Celda getCelda(int fila, int columna) {
        return celdas[fila][columna];
    }

    public int getEspacioLibre(Posicion posicion){
        return celdas[posicion.getFilaNumber()][posicion.getColumna()].getEspacioLibre();
    }

    public int getOcupacionCelda(Posicion posicion) {
        Celda celda = celdas[posicion.getFilaNumber()][posicion.getColumna()];
        return celda.getEspacioOcupado();
    }

    public List<Articulo> getArticulos(int fila, int columna) {
        return celdas[fila][columna].getLista();
    }

    /**
     * Búsqueda de una celda con espacio siguiendo el orden de inserción definido en el enunciado.
     * El programa ubicará los artículos no apilables en las celdas de derecha a izquierda
     * y de abajo a arriba. Para los artículos apilables el orden de búsqueda es el mismo,
     * pero primero se intenta meter el artículo en una caja del mismo tipo y espacio y con capacidad.
     * Dentro de una celda no importa el orden. Si todas las cajas de ese tipo y espacio resultan estar
     * llenas entonces se crearía una caja nueva de ese tipo y espacio que se intentaría ubicar en el
     * primer hueco que haya en las celdas de derecha a izquierda y de abajo a arriba.
     */
    public Posicion buscarPosicionConEspacio(int espacio) {
        for (int i = getFilas() - 1; i >= 0; i--) {
            for (int j = getColumnas() - 1; j >= 0; j--) {
                if (celdas[i][j].getEspacioLibre() >= espacio) {
                    return new Posicion(i,j);
                }
            }
        }
        return null;
    }

    public void meter(Posicion posicion, Articulo articulo) {
        celdas[posicion.getFilaNumber()][posicion.getColumna()].meter(articulo);
    }

    /**
     * Devuelve la posición en la que se encuentra el artículo o null.
     */
    public Posicion getPosicionArticulo(Articulo articulo) {
        for (int i = getFilas() - 1; i >= 0; i--) {
            for (int j = getColumnas() - 1; j >= 0; j--) {
                if (celdas[i][j].estaArticulo(articulo)) {
                    return new Posicion(i,j);
                }
            }
        }
        return null;
    }

    /**
     * Si el artículo con ese id existe en el armario.
     */
    public boolean existeIdArticulo(String id) {
        for (int i = getFilas() - 1; i >= 0; i--) {
            for (int j = getColumnas() - 1; j >= 0; j--) {
                if (celdas[i][j].existeIdArticulo(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Devuelve el artículo del armario con ese id o null.
     */
    public Articulo getArticulo(String id) {
        Articulo articulo;
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                articulo = celdas[i][j].getArticulo(id);
                if (articulo != null) {
                    return articulo;
                }
            }
        }
        return null;
    }


    public Map<ETipoArticulo, List<Articulo>> articulosPorTipo(java.util.Comparator<Articulo> comparator, EOrden orden) {
        TreeMap<ETipoArticulo, List<Articulo>> mapa = new TreeMap<>();
        for (ETipoArticulo tipo : ETipoArticulo.values()) {
            mapa.put(tipo, new ArrayList<>());
        }
        for (int i = 0; i < getFilas(); i++) {
            for (int j = 0; j < getColumnas(); j++) {
                List<Articulo> listaArticulosCelda = celdas[i][j].getLista();
                for (Articulo articulo : listaArticulosCelda) {
                    ETipoArticulo tipo = articulo.getTipo();
                    List<Articulo> listaTipo = mapa.get(tipo);
                    listaTipo.add(articulo);
                }
            }
        }
        if (orden != EOrden.INDIFERENTE){
            for (ETipoArticulo tipo : ETipoArticulo.values()) {
                List<Articulo> listaArticulos = mapa.get(tipo);
                listaArticulos.sort(comparator);
                if (orden == EOrden.DESCENDENTE) {
                    Collections.reverse(listaArticulos);
                }
            }
        }
        return mapa;
    }

}
