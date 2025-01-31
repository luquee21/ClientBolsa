package com.proyecto.client;

import com.proyecto.gui.Gui;
import com.proyecto.utils.Utilities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {

    private final String host = "172.16.16.229";
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
                try {
                    System.out.println("Mostrando clientes...");
                    System.out.println(dis.readUTF());
                    dos.writeUTF("OK");
                    System.out.println("Mostrando empresas...");
                    System.out.println(dis.readUTF());
                    dos.writeUTF("OK");
                    //id cliente
                    System.out.print(dis.readUTF());
                    dos.writeUTF(Utilities.getString());
                    //id empresa
                    System.out.print(dis.readUTF());
                    dos.writeUTF(Utilities.getString());
                    //n acciones
                    System.out.print(dis.readUTF());
                    dos.writeUTF(Utilities.getString());
                    //Respuesta
                    String response = dis.readUTF();
                    if (response.equals("NO AVAILABLE")) {
                        dos.writeUTF("OK");
                        System.out.print(dis.readUTF());
                        dos.writeUTF(Utilities.getString());
                        System.out.println(dis.readUTF());
                    } else if (response.equals("AVAILABLE")) {
                        System.out.println(dis.readUTF());
                    } else {
                        System.out.println(dis.readUTF());
                    }
                } catch (IOException e) {
                    disconnect();
                }
                break;
            case 4:
                try {
                    System.out.print(dis.readUTF());
                    dos.writeUTF(Utilities.getString());
                    System.out.println("Mostrando todas las acciones de este cliente...");
                    System.out.println(dis.readUTF());
                } catch (IOException e) {
                    disconnect();
                }
                break;
            case 5:
                try {
                    System.out.print(dis.readUTF());
                    dos.writeUTF(Utilities.getString());
                    System.out.println("Mostrando todas las acciones compradas a esta empresa...");
                    System.out.println(dis.readUTF());
                } catch (IOException e) {
                    disconnect();
                }
                break;
            case 6:
                try {
                    System.out.println("Mostrando empresas...");
                    System.out.println(dis.readUTF());
                    dos.writeUTF("OK");
                    System.out.print(dis.readUTF());
                    dos.writeUTF(Utilities.getString());
                    System.out.println(dis.readUTF());
                } catch (IOException e) {
                    disconnect();
                }
                break;
            case 7:
                try {
                    System.out.println("Mostrando clientes...");
                    System.out.println(dis.readUTF());
                    dos.writeUTF("OK");
                    System.out.print(dis.readUTF());
                    dos.writeUTF(Utilities.getString());
                    System.out.println(dis.readUTF());
                } catch (IOException e) {
                    disconnect();
                }
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
