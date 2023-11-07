package com.masanz.ami.enums;

import java.util.ArrayList;
import java.util.List;

public enum ETipoArticulo {
    NINGUNO,
    MONITOR,
    IMPRESORA,
    CPU,
    FAX,
    SCANNER;

    private final int[] espacios =        {0,1,4,1,3,4};

    public int getEspacio() {
        return espacios[this.ordinal()];
    }

    public static boolean existeTipo(String tipo) {
        for (ETipoArticulo value : values()) {
            if (value.toString().equals(tipo)) {
                return true;
            }
        }
        return false;
    }

    public static String[] getArrayTipos(){
        List<String> lista = new ArrayList<>();
        for (ETipoArticulo value : values()) {
            lista.add(value.toString());
        }
        lista.remove(0);
        return lista.toArray(new String[0]);
    }

}
