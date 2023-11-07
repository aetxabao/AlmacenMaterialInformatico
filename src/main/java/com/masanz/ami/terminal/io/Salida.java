package com.masanz.ami.terminal.io;

import com.masanz.ami.io.CsvLoader;
import com.masanz.ami.model.Armario;
import com.masanz.ami.model.Articulo;
import com.masanz.ami.model.Celda;
import com.masanz.ami.enums.ETipoArticulo;

import java.util.List;
import java.util.Map;

public class Salida {

    public static void menuPrincipal() {
        System.out.println("=================================================================");
        System.out.println("=             Almacén de Material Informático (AMI)             =");
        System.out.println("=================================================================");
        System.out.println("\t1. Mostrar armario del almacén");                 //M_PR_MOSTRAR_ARMARIO
        System.out.println("\t2. Meter artículo en armario");                   //M_PR_METER_ARTICULO
        System.out.println("\t3. Consultar artículo");                          //M_PR_CONSULTAR_ARTICULO
        System.out.println("\t4. Consultar celda");                             //M_PR_CONSULTAR_CELDA
        System.out.println("\t5. Listados (menú)");                             //M_PR_LISTADOS
        System.out.println("\t6. Grabar estado");                               //M_PR_GRABAR_ESTADO
        System.out.println("\t7. Cargar datos");                                //M_PR_CARGAR_DATOS
        System.out.println("\t0. Terminar");                                    //M_PR_TERMINAR
        System.out.println("=================================================================");
    }

    public static void menuListados() {
        System.out.println("-----------------------------------------------------------------");
        System.out.println("-                 L i s t a d o s     ( A M I )                 -");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("\t\t1. Artículos por tipo y precio");               //M_LI_TIPO_PRECIO
        System.out.println("\t\t2. Artículos por tipo y fecha");                //M_LI_TIPO_FECHA
        System.out.println("\t\t3. Importer artículos por tipo");               //M_LI_IMPORTE_TIPO
        System.out.println("\t\t0. Terminar");                                  //M_LI_TERMINAR
        System.out.println("-----------------------------------------------------------------");
    }

    public static void mostrarOpciones(String[] opciones) {
        for (String opcion : opciones) {
            System.out.print(opcion + " ");
        }
        System.out.println();
    }

    public static void mostrarError(String error) {
        System.out.println("ERROR: " + error);
    }

    public static void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public static void mostrarArticulo(Articulo articulo, String posicion) {
        System.out.printf("%10s: %s\n", "Id", articulo.getId());
        System.out.printf("%10s: %s\n", "Tipo", articulo.getTipo());
        System.out.printf("%10s: %s\n", "Espacio", articulo.getEspacio());
        System.out.printf("%10s: %s\n", "Fecha", articulo.getFechaAdquisicion());
        System.out.printf("%10s: %.2f\n", "Precio", articulo.getPrecio());
        System.out.printf("%10s: %s\n", "Posición", posicion);
    }

    public static void mostrarArticulosCelda(Articulo[] articulos, int espaciosCelda) {
        int cnt = 0;
        StringBuilder sbId = new StringBuilder("|");
        StringBuilder sbTipo = new StringBuilder("|");
        StringBuilder sbFecha = new StringBuilder("|");
        StringBuilder sbPrecio = new StringBuilder("|");
        for (Articulo articulo : articulos) {
            if (articulo!=null) {
                int n = articulo.getEspacio();
                cnt += n;
                sbId.append(" ".repeat(1+(int)Math.round((n-1.0)*12/2)+(int)Math.ceil((n-1.0)/2)))
                        .append(String.format("%-10s",articulo.getId()))
                        .append(" ".repeat(1+(int)Math.round((n-1.0)*12/2)+(int)Math.floor((n-1.0)/2)))
                        .append("|");
                sbTipo.append(" ".repeat(1+(int)Math.round((n-1.0)*12/2)+(int)Math.ceil((n-1.0)/2)))
                        .append(String.format("%-10s",articulo.getTipo()))
                        .append(" ".repeat(1+(int)Math.round((n-1.0)*12/2)+(int)Math.floor((n-1.0)/2)))
                        .append("|");
                sbFecha.append(" ".repeat(1+(int)Math.round((n-1.0)*12/2)+(int)Math.ceil((n-1.0)/2)))
                        .append(String.format("%-10s",articulo.getFechaAdquisicion()))
                        .append(" ".repeat(1+(int)Math.round((n-1.0)*12/2)+(int)Math.floor((n-1.0)/2)))
                        .append("|");
                sbPrecio.append(" ".repeat(1+(int)Math.round((n-1.0)*12/2)+(int)Math.ceil((n-1.0)/2)))
                        .append(String.format("%-10.2f",articulo.getPrecio()))
                        .append(" ".repeat(1+(int)Math.round((n-1.0)*12/2)+(int)Math.floor((n-1.0)/2)))
                        .append("|");
            }
        }
        for (int i = cnt; i < espaciosCelda; i++) {
            sbId.append(" ".repeat(12)).append("|");
            sbTipo.append(" ".repeat(12)).append("|");
            sbFecha.append(" ".repeat(12)).append("|");
            sbPrecio.append(" ".repeat(12)).append("|");
        }
        System.out.println("-".repeat(espaciosCelda*13+1));
        System.out.println(sbId);
        System.out.println(sbTipo);
        System.out.println(sbFecha);
        System.out.println(sbPrecio);
        System.out.println("-".repeat(espaciosCelda*13+1));
    }

    public static void tablaArmario(Armario armario) {
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int cols = armario.getColumnas();
        int rows = armario.getFilas();
        // cabecera
        System.out.printf("%2s |", "");
        for(int i=1; i<=cols; i++){
            System.out.print(" ".repeat(2*10-1));
            System.out.printf("%2d", i);
            System.out.print(" ".repeat(2*10-2));
            System.out.print("|");
        }
        System.out.println();
        System.out.println("-".repeat(4 + 4*10*cols));
        // filas
        for(int i=0; i<rows; i++){
            System.out.printf("%2c |", abc.charAt(i));
            for(int j=0; j<cols; j++){
                Celda celda = armario.getCelda(i, j);
                int espacioLibre = celda.getEspacioLibre();
                if(espacioLibre == 4) {
                    System.out.print(" ".repeat(4*10 - 1));
                    System.out.print("|");
                }else {
                    List<Articulo> lista = celda.getLista();
                    for (Articulo articulo : lista) {
                        int espacio = articulo.getEspacio();
                        System.out.printf("_%s%7s%s_|",
                                "_".repeat(5*(espacio-1)),
                                articulo.getId(),
                                "_".repeat(5*(espacio-1)));
                    }
                    for (int k = 0; k < espacioLibre; k++) {
                        System.out.print(" ".repeat(9));
                        System.out.print("|");
                    }
                }
            }
            System.out.println();
            System.out.println("-".repeat(4 + 4*10*cols));
        }
    }


    private static String getArticuloEnUnaLinea(Articulo articulo) {
        return  " ".repeat(12) + String.format("%-12s", articulo.getId()) +
                String.format("%-12s", articulo.getFechaAdquisicion()) +
                String.format("%12.2f", articulo.getPrecio());
    }

    public static void mostrarArticulosTipo(Map<ETipoArticulo, List<Articulo>> mapa) {
        for (ETipoArticulo tipo : mapa.keySet()) {
            List<Articulo> lista = mapa.get(tipo);
            if (lista.isEmpty()) {
                continue;
            }
            System.out.println(tipo);
            for (Articulo articulo : lista) {
                System.out.println(getArticuloEnUnaLinea(articulo));
            }
        }
    }

    public static void mostrarTiposImportes(Map<ETipoArticulo, Double> mapa) {
        double total = 0.0;
        for (Map.Entry<ETipoArticulo, Double> entry : mapa.entrySet()) {
            total += entry.getValue();
            System.out.printf("%-12s%12.2f\n", entry.getKey(), entry.getValue());
        }
        System.out.println("-".repeat(24));
        System.out.printf("%-12s%12.2f\n", "Total", total);
    }

    public static void main(String[] args) throws Exception {
        Armario armario = new Armario();
        CsvLoader csvLoader = new CsvLoader("backup/ami_2023-06-05_193120.csv");
        csvLoader.load(armario);
        tablaArmario(armario);
        menuPrincipal();
    }

}
