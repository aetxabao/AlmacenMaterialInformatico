package com.masanz.ami.io;

import com.masanz.ami.model.*;
import com.masanz.ami.model.Armario;
import com.masanz.ami.model.Celda;
import com.masanz.ami.model.Posicion;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Iterator;

public class CsvSaver {

    public static final String FOLDER = "backup";
    public static final String PREFIX = "ami";
    public static final char SEPARATOR = '_';
    public static final String EXTENSION = ".csv";
    public static final char SPLITTER = ';';
    public static final char EOL = '\n';

    /**
     * Guarda la referencia al objeto armario pasado como parámetro en el fichero CSV.
     * Los ficheros deben ser guardados en la carpeta backup del proyecto con el siguiente patrón:
     *      ami_2023-06-05_193120.csv
     * Crea el directorio si es necesario por código.
     * Escribe el texto generado por el método encode en el fichero.
     */
    public String save(Armario armario) throws Exception  {
        Files.createDirectories(Path.of(FOLDER));
        String filePath = getFilePath();
        File file = new File(filePath);
        FileWriter fw = new FileWriter(file, true);
        fw.write(encode(armario));
        fw.close();
        return filePath;
    }

    /**
     * Devuelve el path absoluto con el nombre del patrón del fichero indicado al final.
     *      ami_2023-06-05_193120.csv
     */
    private String getFilePath() {
        File file = new File(FOLDER);
        String filePath = file.getAbsolutePath();
        filePath += File.separatorChar + PREFIX + SEPARATOR;
        String timestamp = LocalDateTime.now().toString().replace('T',SEPARATOR);
        timestamp = timestamp.replaceAll(":", "");
        timestamp = timestamp.substring(0, timestamp.indexOf("."));
        filePath += timestamp;
        filePath += EXTENSION;
        return filePath;
    }

    /**
     * Codifica el armario al formato CSV recorriendo todas las celdas y concatenando
     * el resultado del método transformLugarCeldaToCsv
     */
    public static String encode(Armario armario) {
        StringBuilder sb = new StringBuilder();
        for (int fila = 0; fila < armario.getFilas(); fila++) {
            for (int columna = 0; columna < armario.getColumnas(); columna++) {
                Posicion posicion = new Posicion(fila, columna);
                Celda celda = armario.getCelda(fila,columna);
                sb.append(transformLugarCeldaToCsv(posicion.toString(), celda));
            }
        }
        return sb.toString();
    }

    /**
     * Transforma la celda que hay en una posición a formato CSV
     * recorriendo todos los almacenables de la celda y dependiendo de si son
     * una instancia de Caja o de NoApilable llamando a su método transformador
     * transformLugarCajaToCsv o transformLugarArticuloToCsv.
     */
    private static String transformLugarCeldaToCsv(String lugar, Celda celda) {
        StringBuilder sb = new StringBuilder();
        Iterator<Articulo> it = celda.iterator();
        while (it.hasNext()) {
            Articulo a = it.next();
            sb.append(transformLugarArticuloToCsv(lugar, a));
        }
        return sb.toString();
    }

    /**
    * Transforma un artículo que hay en una celda a formato CSV.
    * B2;MONITOR;MON0001;1;2023-06-05;120.0
    *      Corresponde a un monitor con id MON0001 que ocupa 1 espacio adquirido el 2023-06-05 por 120 Euros en la celda B2.
    */
    private static String transformLugarArticuloToCsv(String posicion, Articulo a) {
        return posicion + SPLITTER +
                a.getTipo() + SPLITTER +
                a.getId() + SPLITTER +
                a.getEspacio() + SPLITTER +
                a.getFechaAdquisicion() + SPLITTER +
                a.getPrecio() + SPLITTER +
                EOL;
    }

}
