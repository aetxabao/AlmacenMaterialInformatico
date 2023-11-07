package com.masanz.ami.exceptions;

public class ExcepcionAmi extends Exception {

    private String msg;

    public ExcepcionAmi(String msg){
        this.msg = msg;
    }

    public String toString()
    {
        return msg;
    }
}
