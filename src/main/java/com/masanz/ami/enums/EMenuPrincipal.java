package com.masanz.ami.enums;

public enum EMenuPrincipal {

    M_PR_TERMINAR,//0
    M_PR_MOSTRAR_ARMARIO, M_PR_METER_ARTICULO,  //1-2
    M_PR_CONSULTAR_ARTICULO, M_PR_CONSULTAR_CELDA, //3-4
    M_PR_LISTADOS, //5
    M_PR_GRABAR_ESTADO, M_PR_CARGAR_DATOS;//6-7

    public static EMenuPrincipal desdeEntero(int x) {
        return values()[x];
    }

    public static int tamano(){
        return values().length;
    }

}
