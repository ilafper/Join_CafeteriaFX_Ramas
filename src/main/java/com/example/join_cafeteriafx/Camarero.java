import javafx.application.Platform;

import java.util.Random;

class CamareroFX extends Thread {
    private String nombre;
    private List<Cliente> clientes;
    private CafeteriaController controller;
    private Random random = new Random();
    private static int atendidos = 0;

    public CamareroFX(String nombre, List<ClienteFX> clientes, CafeteriaController controller) {
        this.nombre = nombre;
        this.clientes = clientes;
        this.controller = controller;
    }

    @Override
    public void run() {
        for (ClienteFX cliente : clientes) {
            try {
                cliente.join(cliente.getTiempoEspera());
            } catch (InterruptedException e) { e.printStackTrace(); }

            if (!cliente.isAtendido()) {
                int tiempoPreparacion = 2000 + random.nextInt(3000);
                Platform.runLater(() ->
                        controller.log(nombre + " empieza a preparar café para " + cliente.getNombre()
                                + " (tiempo preparación: " + tiempoPreparacion/1000.0 + " s)")
                );
                try {
                    if (tiempoPreparacion > cliente.getTiempoEspera()) {
                        Thread.sleep(cliente.getTiempoEspera());
                        Platform.runLater(() ->
                                controller.log(cliente.getNombre() + " se fue porque no recibió su café a tiempo")
                        );
                    } else {
                        Thread.sleep(tiempoPreparacion);
                        cliente.setAtendido(true);
                        atendidos++;
                        cliente.interrupt();
                        Platform.runLater(() ->
                                controller.log(nombre + " sirvió el café a " + cliente.getNombre()
                                        + " en " + tiempoPreparacion/1000.0 + " s")
                        );
                        Platform.runLater(() ->
                                controller.updateContador(atendidos)
                        );
                    }
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
        Platform.runLater(() ->
                controller.log(nombre + " ha terminado su turno.")
        );
    }

    public static int getAtendidos() { return atendidos; }
}