package com.masanz.ami.terminal;

import com.masanz.ami.managers.GestorAlmacen;
import com.masanz.ami.terminal.menus.MenuPrincipal;

public class Main {

    public static void main(String[] args) {
        new MenuPrincipal(new GestorAlmacen()).run();
    }

}
