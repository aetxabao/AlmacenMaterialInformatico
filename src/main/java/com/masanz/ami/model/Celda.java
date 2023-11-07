package com.masanz.ami.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * La clase Celda implementa la interfaz Iterable que permite recorrer
 * todos los elementos artículos que haya en ella.
 * Una celda dispone de un espacio que apriori en el armario del examen será 4.
 * Una celda consta de una lista de artículos.
 * El sumatorio del espacio de los artículos de la celda no puede exceder
 * el espacio del que consta la propia celda.
 */
public class Celda implements Iterable<Articulo> {

    private final int espacio;

    private List<Articulo> lista;

    public Celda(int espacio) {
        this.espacio = espacio;
        lista = new ArrayList<>();
    }

    public List<Articulo> getLista() {
        return lista;
    }

    public void setLista(List<Articulo> lista) {
        this.lista = lista;
    }

    public void meter(Articulo articulo) {
        lista.add(articulo);
    }

    /**
     * Calcula el espacio libre de la celda.
     */
    public int getEspacioLibre(){
        int n = 0;
        for (Articulo a : lista) {
            n += a.getEspacio();
        }
        return espacio - n;
    }

    /**
     * Calcula el espacio ocupado de la celda.
     */
    public int getEspacioOcupado(){
        int n = 0;
        for (Articulo a : lista) {
            n += a.getEspacio();
        }
        return n;
    }

    /**
     * Si está el artículo en la celda.
     */
    public boolean estaArticulo(Articulo articulo){
        for (Articulo a : lista) {
            if (a.equals(articulo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Si está el artículo con ese id en la celda.
     */
    public boolean existeIdArticulo(String id) {
        for (Articulo a : lista) {
            if (id.equals(a.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve el artículo con ese id en la celda o null.
     */
    public Articulo getArticulo(String id) {
        for (Articulo a : lista) {
            if (id.equals(a.getId())) {
                return a;
            }
        }
        return null;
    }

    @Override
    public Iterator<Articulo> iterator() {
        return lista.iterator();
    }

}
