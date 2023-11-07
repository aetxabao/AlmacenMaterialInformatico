package com.masanz.ami.model;

import com.masanz.ami.enums.ETipoArticulo;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Tiene tres tipos de constructores: sin parámetros, sólo id, todos los atributos como parámetros.
 * Tiene los getters y setters correspondientes a todos los atributos.
 * Los métodos toString, hashCode e equals utilizan id
 * para devolver la representación, hash y comparar objetos.
 */
public class Articulo {

    private String id;
    private ETipoArticulo tipo;
    private int espacio;
    private LocalDate fechaAdquisicion;
    private double precio;

    public Articulo() {
        this("", ETipoArticulo.NINGUNO, 0, LocalDate.now(), 0);
    }

    public Articulo(String id) {
        this();
        this.id = id;
    }

    public Articulo(String id, ETipoArticulo tipo, int espacio, LocalDate fechaAdquisicion, double precio) {
        this.id = id;
        this.tipo = tipo;
        this.espacio = espacio;
        this.fechaAdquisicion = fechaAdquisicion;
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Articulo articulo = (Articulo) o;
        return Objects.equals(id, articulo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }

    // region getters y setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ETipoArticulo getTipo() {
        return tipo;
    }

    public void setTipo(ETipoArticulo tipo) {
        this.tipo = tipo;
    }
    
    public int getEspacio() {
        return espacio;
    }

    public void setEspacio(int espacio) {
        this.espacio = espacio;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // endregion

}
