package com.masanz.ami.enums;

public enum EMenuMeterArticulo {

    M_AR_TERMINAR,//0
    M_AR_BOTTOM_RIGHT_TOP_LEFT,//1
    M_AR_TOP_LEFT_BOTTOM_RIGHT,//2
    M_AR_CELDA_ESPECIFICA;//3

    public static EMenuMeterArticulo desdeEntero(int x) {
        return values()[x];
    }

    public static int tamano(){
        return values().length;
    }
    
}
