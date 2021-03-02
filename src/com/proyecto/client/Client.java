package com.proyecto.client;

import com.proyecto.gui.Gui;

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
                response = dis.readUTF();
            } catch (IOException e) {
            }
            if (!response.isEmpty()) {
                System.out.println(response);
            } else {
                if (option == 8) {
                    System.out.println("Saliendo.....");
                } else {
                    System.out.println("Error ocurried...");
                }
            }
        }
        disconnect();
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
