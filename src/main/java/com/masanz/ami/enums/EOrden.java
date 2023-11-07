package com.masanz.ami.enums;

public enum EOrden {

    DESCENDENTE(-1),//-1
    INDIFERENTE(0),//0
    ASCENDENTE(1);//1

    private int orden;
    EOrden(int orden) {
        this.orden = orden;
    }

    public static EOrden desdeEntero(int v) {
        if (v == 0) return INDIFERENTE;
        if (v < 0) return DESCENDENTE;
        return ASCENDENTE;
    }

    public static EOrden desdeString(String valor) {
        if (valor.toUpperCase().startsWith("I")) return INDIFERENTE;
        if (valor.toUpperCase().startsWith("D")) return DESCENDENTE;
        return ASCENDENTE;
    }

}
