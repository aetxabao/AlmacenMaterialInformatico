package com.masanz.ami.io;

import com.masanz.ami.exceptions.ExcepcionAmi;
import com.masanz.ami.model.Armario;
import com.masanz.ami.model.Articulo;
import com.masanz.ami.model.Posicion;
import com.masanz.ami.enums.ETipoArticulo;

import java.io.*;
import java.time.LocalDate;

public class CsvLoader{

    public static final String AVERIADO = "#";
    public static final String SPLITTER = ";";
    private final String csvPath;

    public CsvLoader(String csvPath) {
        this.csvPath = csvPath;
    }

    /**
     * Carga en el armario pasado como parámetro el fichero designado por el atributo csvPath.
     * Lee línea a línea el fichero para dependiendo del tipo de línea hacer el procesamiento.
     * B2;MONITOR;MON0001;1;2023-06-05;120.0
     *      Corresponde a un monitor con id MON0001 que ocupa 1 espacio adquirido el 2023-06-05 por
     *      120 Euros en la celda B2.
     * @param armario Referencia a una instancia de armario en el que cargar la información
     * @throws Exception de tipo RuntimeException o ExcepcionAmi
     * Al cargar un fichero CSV si se produce una excepción en la conversión de tipos o porque el orden
     * de los parámetros no es correcto la excepción de tipo RuntimeException es capturada por el programa
     * para informar al usuario con una alerta.
     * También hay que hacer ciertas comprobaciones, para que si no se cumplen se lance una excepción del
     * tipo ExcepcionAmi que será atrapada y mostrada para informar al usuario.
     * - Si no hay espacio en la celda para un artículo.
     *          No hay espacio en la celda D4
     *          para ubicar el artículo SCA0001
     */
    public void load(Armario armario) throws Exception {
        File file = new File(csvPath);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
            String[] a = line.split(SPLITTER);
//            D4;SCANNER;SCA0002;4;2023-06-05;300.0;
//            E1;SCANNER;SCA0001;4;2023-06-05;300.0;
//            E2;FAX;FAX0001;3;2023-06-05;650.0;
//            E3;IMPRESORA;IMP0001;4;2023-06-05;800.0;
//            E4;MONITOR;MON0001;1;2023-06-05;120.0;
//            E4;MONITOR;MON0002;1;2023-06-05;120.0;
//            E4;CPU;CPU0001;1;2023-06-05;800.0;
            ETipoArticulo tipo = ETipoArticulo.valueOf(a[1]);
            Posicion posicion = new Posicion(a[0]);
            String id = a[2];
            int espacio = Integer.parseInt(a[3]);
            LocalDate fechaAdquisicion = LocalDate.parse(a[4]);
            double precio = Double.parseDouble(a[5]);
            Articulo articulo = new Articulo(id, tipo, espacio, fechaAdquisicion, precio);
            if (armario.getEspacioLibre(posicion) < espacio) {
                throw new ExcepcionAmi("No hay espacio en la celda " + posicion + "\n" +
                        "para ubicar el artículo " + id );
            }
            armario.meter(posicion, articulo);
        }
    }
}
