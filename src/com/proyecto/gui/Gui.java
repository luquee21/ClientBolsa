package com.proyecto.gui;

import com.proyecto.utils.Utilities;

public class Gui {

    public int mainMenu(String name) {
        int option = 0;
        System.out.println("<----Bienvenido " + name + "---->");
        System.out.println("1.- Listar empresas");
        System.out.println("2.- Listar clientes");
        System.out.println("3.- Comprar acciones");
        System.out.println("4.- Acciones compradas por cliente");
        System.out.println("5.- Listado de accionistas de una empresa");
        System.out.println("6.- Eliminar empresa");
        System.out.println("7.- Eliminar cliente");
        System.out.println("8.- Salir");
        System.out.println("<----------------->");
        option = Utilities.getInt();
        return option;
    }

    public String askLogin() {
        System.out.print("Login: ");
        return Utilities.getString();
    }

    public String askPass() {
        System.out.print("Password: ");
        return Utilities.getString();
    }
}
