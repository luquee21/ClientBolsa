package com.proyecto.client;

import com.proyecto.gui.Gui;
import com.proyecto.utils.Utilities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {

    private final String host = "127.0.0.1";
    private final int port = 2121;
    private final Gui gui = new Gui();
    protected Socket sk;
    protected DataOutputStream dos;
    protected DataInputStream dis;
    private int id;

    @Override
    public void run() {
        try {
            sk = new Socket(host, port);
            dos = new DataOutputStream(sk.getOutputStream());
            dis = new DataInputStream(sk.getInputStream());
            while (!login()) {
            }
        } catch (IOException ex) {
        }
    }

    public boolean login() {
        boolean flag = false;
        String response = "";
        try {
            dos.writeUTF(gui.askLogin());
            response = dis.readUTF();
            if (response.equals("Login data OK")) {
                dos.writeUTF(gui.askPass());
                response = dis.readUTF();
                if (response.equals("Password data OK")) {
                    response = dis.readUTF();
                    if (response.equals("Login OK")) {
                        mainMenu(dis.readUTF());
                        flag = true;
                    } else {
                        System.out.println("Error in login...");
                    }
                } else {
                    System.out.println("Bad password...");
                }
            } else {
                System.out.println("Bad login...");
            }
        } catch (IOException e) {

        }
        return flag;
    }

    public void mainMenu(String name) {
        int option = 0;
        String response = "";
        while (option != 8) {
            response = "";
            option = gui.mainMenu(name);
            try {
                dos.writeUTF(Integer.toString(option));
                checkOption(option);
            } catch (IOException e) {
            }
        }
        disconnect();
    }

    public void checkOption(int op) {
        switch (op) {
            case 1:
                System.out.println("Mostrando datos de las empresas...");
                try {
                    System.out.println(dis.readUTF());
                } catch (IOException e) {
                    disconnect();
                }
                break;
            case 2:
                System.out.println("Mostrando datos de los clientes...");
                try {
                    System.out.println(dis.readUTF());
                } catch (IOException e) {
                    disconnect();
                }
                break;
            case 3:
                break;
            case 4:
                try {
                    System.out.println(dis.readUTF());
                    dos.writeUTF(Utilities.getString());
                    System.out.println("Mostrando todas las acciones de este cliente...");
                    System.out.println(dis.readUTF());
                } catch (IOException e) {
                    disconnect();
                }
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                System.out.println("Saliendo...");
                break;
            default:
                break;

        }
    }

    public void disconnect() {
        try {
            dis.close();
            dos.close();
            sk.close();
        } catch (IOException e) {
        }
    }

}
