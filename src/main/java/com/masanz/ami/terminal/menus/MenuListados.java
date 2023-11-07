package com.masanz.ami.terminal.menus;

import com.masanz.ami.managers.GestorAlmacen;
import com.masanz.ami.model.Articulo;
import com.masanz.ami.enums.ETipoArticulo;
import com.masanz.ami.enums.EMenuListados;
import com.masanz.ami.enums.EOrden;
import com.masanz.ami.terminal.io.Entrada;
import com.masanz.ami.terminal.io.Salida;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuListados {

    private GestorAlmacen gestorAlmacen;

    public MenuListados(GestorAlmacen gestorAlmacen) {
        this.gestorAlmacen = gestorAlmacen;
    }

    public void run() {
        Salida.menuListados();
        int x = Entrada.leerEntero("Opción", 0, EMenuListados.tamano()-1);
        EMenuListados opc =  EMenuListados.desdeEntero(x);
        while (opc != EMenuListados.M_LI_TERMINAR) {
            switch (opc) {
                case M_LI_TIPO_PRECIO:
                    articulosPorTipoPrecio();
                    break;
                case M_LI_TIPO_FECHA:
                    articulosPorTipoFecha();
                    break;
                case M_LI_IMPORTE_TIPO:
                    importePorTipos();
                    break;
                default:
            }
            Salida.menuListados();
            x = Entrada.leerEntero("Opción", 0, EMenuListados.tamano()-1);
            opc =  EMenuListados.desdeEntero(x);
        }
    }

    private void articulosPorTipoPrecio() {
        String orden = Entrada.leerString("Por defecto orden Ascendente 'A' o descendente 'D'");
        Map<ETipoArticulo, List<Articulo>> mapa = gestorAlmacen.articulosPorTipoPrecio(EOrden.desdeString(orden));
        Salida.mostrarArticulosTipo(mapa);
    }

    private void articulosPorTipoFecha() {
        String orden = Entrada.leerString("Por defecto orden Ascendente 'A' o descendente 'D'");
        Map<ETipoArticulo, List<Articulo>> mapa = gestorAlmacen.articulosPorTipoFecha(EOrden.desdeString(orden));
        Salida.mostrarArticulosTipo(mapa);
    }

    private void importePorTipos() {
        Map<ETipoArticulo, List<Articulo>> mapa = gestorAlmacen.articulosPorTipoFecha(EOrden.INDIFERENTE);
        Map<ETipoArticulo, Double> mapaTipoImporte = sumatorioPreciosPorTipo(mapa);
        Salida.mostrarTiposImportes(mapaTipoImporte);
    }

    private  Map<ETipoArticulo, Double> sumatorioPreciosPorTipo(Map<ETipoArticulo, List<Articulo>> mapa) {
        Map<ETipoArticulo, Double> mapaTipoImporte = new HashMap<>();
        for (ETipoArticulo tipo : mapa.keySet()) {
            double importe = 0;
            List<Articulo> lista = mapa.get(tipo);
            for (Articulo articulo : lista) {
                importe += articulo.getPrecio();
            }
            if (importe > 0){
                mapaTipoImporte.put(tipo, importe);
            }
        }
        return  mapaTipoImporte;
    }

    public static void main(String[] args) throws Exception {
        GestorAlmacen gestorAlmacen = new GestorAlmacen();
        gestorAlmacen.loadCsv("backup/ami_2023-10-29_122343.csv");
        new MenuListados(gestorAlmacen).run();
    }

}