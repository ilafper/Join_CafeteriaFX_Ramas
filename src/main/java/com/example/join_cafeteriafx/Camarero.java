package com.example.join_cafeteriafx;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.util.List;
import java.util.Random;

class Camarero extends Thread {
    private String nombre;
    private List<Cliente> clientes;
    private Random random = new Random();
    private static int atendidos = 0;
    private TextArea areaCamarero;

    public Camarero(String nombre, List<Cliente> clientes, TextArea areaCamarero) {
        this.nombre = nombre;
        this.clientes = clientes;
        this.areaCamarero = areaCamarero;
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
                String mensajeInicio = nombre + " empieza a preparar café para " + cliente.getNombre()
                        + " (tiempo preparación: " + tiempoPreparacion / 1000.0 + " s)\n";

                // Mostrar mensaje en la interfaz y consola
                System.out.print(mensajeInicio);
                Platform.runLater(() -> areaCamarero.appendText(mensajeInicio));

                try {
                    if (tiempoPreparacion > cliente.getTiempoEspera()) {
                        Thread.sleep(cliente.getTiempoEspera());
                        String mensaje = cliente.getNombre() + " se fue porque no recibió su café a tiempo\n";
                        System.out.print(mensaje);
                        Platform.runLater(() -> areaCamarero.appendText(mensaje));
                    } else {
                        Thread.sleep(tiempoPreparacion);
                        cliente.setAtendido(true);
                        atendidos++;
                        cliente.interrupt(); // Deja de esperar
                        String mensaje = nombre + " sirvió el café a " + cliente.getNombre()
                                + " en " + tiempoPreparacion / 1000.0 + " s\n";
                        System.out.print(mensaje);
                        Platform.runLater(() -> areaCamarero.appendText(mensaje));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        String fin = nombre + " ha terminado su turno.\n";
        System.out.print(fin);
        Platform.runLater(() -> areaCamarero.appendText(fin));
    }

    public static int getAtendidos() {
        return atendidos;
    }
}
