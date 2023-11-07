package com.masanz.ami.terminal.menus;

import com.masanz.ami.enums.ETipoArticulo;
import com.masanz.ami.managers.GestorAlmacen;
import com.masanz.ami.model.Articulo;
import com.masanz.ami.model.Posicion;
import com.masanz.ami.enums.EMenuPrincipal;
import com.masanz.ami.terminal.io.Entrada;
import com.masanz.ami.terminal.io.Salida;

import java.time.LocalDate;
import java.util.List;

public class MenuPrincipal {

    private GestorAlmacen gestorAlmacen;

    public MenuPrincipal(GestorAlmacen gestorAlmacen) {
        this.gestorAlmacen = gestorAlmacen;
        cargaInicialDatos();
    }

    private void cargaInicialDatos(){
    }

    public void run() {
        Salida.menuPrincipal();
        int x = Entrada.leerEntero("Opción", 0, EMenuPrincipal.tamano()-1);
        EMenuPrincipal opc =  EMenuPrincipal.desdeEntero(x);
        while (opc != EMenuPrincipal.M_PR_TERMINAR) {
            switch (opc) {
                case M_PR_MOSTRAR_ARMARIO:
                    mostrarArmario();
                    break;
                case M_PR_METER_ARTICULO:
                    meterArticulo();
                    break;
                case M_PR_CONSULTAR_ARTICULO:
                    consultarArticulo();
                    break;
                case M_PR_CONSULTAR_CELDA:
                    consultarCelda();
                    break;
                case M_PR_LISTADOS:
                    menuListados();
                    break;
                case M_PR_GRABAR_ESTADO:
                    grabarEstado();
                    break;
                case M_PR_CARGAR_DATOS:
                    cargarDatos();
                    break;
                default:
            }
            Salida.menuPrincipal();
            x = Entrada.leerEntero("Opción", 0, EMenuPrincipal.tamano()-1);
            opc =  EMenuPrincipal.desdeEntero(x);
        }
    }

    private void mostrarArmario() {
        Salida.tablaArmario(gestorAlmacen.getArmario());
    }

    private void meterArticulo() {
        String id = Entrada.leerString("Id del artículo (7 caracteres), ej. MON0001");
        if (id.trim().length() != 7) {
            Salida.mostrarError("El id del artículo debe tener 7 caracteres");
            return;
        }
        if (gestorAlmacen.existeIdArticulo(id)) {
            Salida.mostrarError("Ya existe un artículo con ese id");
            return;
        }
        Salida.mostrarOpciones(ETipoArticulo.getArrayTipos());
        String tipo = Entrada.leerString("Tipo de artículo");
        if (!ETipoArticulo.existeTipo(tipo.toUpperCase())) {
            Salida.mostrarError("Tipo de artículo no válido");
            return;
        }
        ETipoArticulo tipoArticulo = ETipoArticulo.valueOf(tipo.toUpperCase());

        int espacio = Entrada.leerEntero("Espacio que ocupa, ej. " + tipoArticulo.getEspacio(), 1, 4);
        String fecha = Entrada.leerString("Fecha de adquisición (aaaa-mm-dd), ej. 2021-06-05");
        LocalDate fechaAdquisicion;
        try {
            fechaAdquisicion = LocalDate.parse(fecha);
        } catch (Exception e) {
            Salida.mostrarError("Fecha no válida");
            return;
        }
        double precio = Entrada.leerDecimalPositivo("Precio de adquisición, ej. 120.0");
        Articulo articulo = new Articulo(id, tipoArticulo, espacio, fechaAdquisicion, precio);

        if (!gestorAlmacen.meterArticulo(articulo)) {
            Salida.mostrarError("No hay espacio en el armario para ese artículo");
        }else {
            Salida.mostrarMensaje("Artículo " + articulo + " metido en el armario");
        }

    }

    private void consultarArticulo() {
        String id = Entrada.leerString("Id del artículo (7 caracteres), ej. IMP0001");
        Posicion posicion = gestorAlmacen.getPosicionArticulo(new Articulo(id));
        if (posicion == null) {
            Salida.mostrarError("No existe ningún artículo con ese id");
        }else{
            Articulo articulo = gestorAlmacen.getArticulo(id);
            String pos = posicion.toString();
            Salida.mostrarArticulo(articulo, pos);
        }
    }

    private void consultarCelda() {
        String pos = Entrada.leerString("Celda del armario (2 caracteres), ej. A1").toUpperCase() ;
        if (pos.trim().length() != 2  || !Character.isLetter(pos.charAt(0)) || !Character.isDigit(pos.charAt(1))
            || Posicion.getLetras().indexOf(pos.charAt(0)) < 0 )  {
            Salida.mostrarError("Posición no válida");
        }
        Posicion posicion = new Posicion(pos.toUpperCase());
        if (posicion.getFilaNumber() < 0 || posicion.getFilaNumber() >= gestorAlmacen.getArmario().getFilas() ||
                posicion.getColumna() < 0 || posicion.getColumna() >= gestorAlmacen.getArmario().getColumnas()) {
            Salida.mostrarError("Posición no válida");
        }
        List<Articulo> listArticulos = gestorAlmacen.getArticulos(posicion.getFilaNumber(), posicion.getColumna());
        Articulo[] articulos = listArticulos.toArray(new Articulo[4]);
        Salida.mostrarArticulosCelda(articulos, gestorAlmacen.getEspaciosCeldas());
    }

    private void menuListados() {
        MenuListados menuListados = new MenuListados(gestorAlmacen);
        menuListados.run();
    }

    private void grabarEstado()  {
        try {
            String filePath =gestorAlmacen.saveCsv();
            Salida.mostrarMensaje("Estado del armario grabado correctamente en:\n" + filePath);
        } catch (Exception e) {
            Salida.mostrarError("ERROR: " + "No se ha podido grabar el estado del armario");
        }
    }

    private void cargarDatos() {
        String pathFicheroCsv = Entrada.leerString("Introduce el nombre del fichero, ej. backup/ami_2023-06-05_193120.csv ");
        try {
            gestorAlmacen.loadCsv(pathFicheroCsv);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return;
        }
        System.out.println("Datos cargados correctamente.");
    }

    public static void main(String[] args) throws Exception {
        GestorAlmacen gestorAlmacen = new GestorAlmacen();
//        gestorAlmacen.loadCsv("backup/ami_2023-06-05_193120.csv");
        gestorAlmacen.loadCsv("backup/ami_2023-10-28_100712.csv");
        new MenuPrincipal(gestorAlmacen).run();
    }

}
