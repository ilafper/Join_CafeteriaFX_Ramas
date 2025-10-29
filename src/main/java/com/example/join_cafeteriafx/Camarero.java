package com.example.join_cafeteriafx;

import java.util.List;
import java.util.Random;

public class Camarero extends Thread {
    private String nombre;
    private List<Cliente> clientes;
    private Random random = new Random();
    private static int atendidos = 0;

    public Camarero(String nombre, List<Cliente> clientes) {
        this.nombre = nombre;
        this.clientes = clientes;
    }

    @Override
    public void run() {
        for (Cliente cliente : clientes) {
            try {
                cliente.join(cliente.getTiempoEspera());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!cliente.isAtendido()) {
                int tiempoPreparacion = 2000 + random.nextInt(3000);
                System.out.println(nombre + " prepara café para " + cliente.getNombre());
                try {
                    if (tiempoPreparacion > cliente.getTiempoEspera()) {
                        Thread.sleep(cliente.getTiempoEspera());
                        System.out.println(cliente.getNombre() + " se fue sin café");
                    } else {
                        Thread.sleep(tiempoPreparacion);
                        cliente.setAtendido(true);
                        atendidos++;
                        cliente.interrupt();
                        System.out.println(nombre + " sirvió café a " + cliente.getNombre());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(nombre + " terminó su turno");
    }

    public static int getAtendidos() {
        return atendidos;
    }
}
