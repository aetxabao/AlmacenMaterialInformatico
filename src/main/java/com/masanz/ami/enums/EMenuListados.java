package com.masanz.ami.enums;

public enum EMenuListados {

        M_LI_TERMINAR,//0
        M_LI_TIPO_PRECIO,//1
        M_LI_TIPO_FECHA,//2
        M_LI_IMPORTE_TIPO;//3

        public static EMenuListados desdeEntero(int x) {
            return values()[x];
        }

        public static int tamano(){
            return values().length;
        }

    }
