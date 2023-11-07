package com.masanz.ami.managers;

import com.masanz.ami.enums.ETipoArticulo;
import com.masanz.ami.io.*;
import com.masanz.ami.model.*;
import com.masanz.ami.model.Armario;
import com.masanz.ami.model.Posicion;
import com.masanz.ami.enums.EOrden;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class GestorAlmacen
{
    private static final int ESPACIOS_CELDA = 4;
    private Armario armario;

    public GestorAlmacen(){
        this.armario = new Armario();
    }

    public Armario getArmario(){
        return armario;
    }

    public int getEspaciosCeldas() {
        return ESPACIOS_CELDA;
    }

    public int getFilas(){
        return armario.getFilas();
    }

    public int getColumnas(){
        return armario.getColumnas();
    }

    public boolean meterArticulo(Articulo articulo) {
        Posicion posicion = armario.buscarPosicionConEspacio(articulo.getEspacio());
        // si no hay sitio, no hay nada que hacer
        if (posicion == null) {
            return false;
        }
        // si hay sitio meterlo
        else{
            // meter articulo en armario
            armario.meter(posicion, articulo);
        }
        return true;
    }

    public Posicion getPosicionArticulo(Articulo articulo) {
        return armario.getPosicionArticulo(articulo);
    }

    public List<Articulo> getArticulos(int fila, int columna) {
        return armario.getArticulos(fila, columna);
    }

    public int getOcupacionCelda(Posicion posicion) {
        return armario.getOcupacionCelda(posicion);
    }

    public boolean existeIdArticulo(String id) {
        return armario.existeIdArticulo(id);
    }

    public String saveCsv() throws Exception {
        CsvSaver csvSaver = new CsvSaver();
        return csvSaver.save(armario);
    }

    public void loadCsv(String path) throws Exception {
        Armario armarioCargado = new Armario();
        CsvLoader csvLoader = new CsvLoader(path);
        csvLoader.load(armarioCargado);
        //si no lanza excepión
        armario = armarioCargado;
    }

    public Articulo getArticulo(String id) {
        return armario.getArticulo(id);
    }

    public Map<ETipoArticulo, List<Articulo>> articulosPorTipoPrecio(EOrden orden) {
        return armario.articulosPorTipo(Comparator.comparingDouble(Articulo::getPrecio), orden);
    }
    public Map<ETipoArticulo, List<Articulo>> articulosPorTipoFecha(EOrden orden) {
        return armario.articulosPorTipo(Comparator.comparing(Articulo::getFechaAdquisicion), orden);
    }


}
