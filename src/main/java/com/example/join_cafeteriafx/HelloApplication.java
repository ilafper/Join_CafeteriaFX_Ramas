package com.example.join_cafeteriafx;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        ejecutarSimulacion();


        System.exit(0); // Cierra el proceso JavaFX cuando termina
    }


    private void ejecutarSimulacion() {
        try {
            // Crear listas de clientes
            List<Cliente> listaCamarero1 = new ArrayList<>();
            List<Cliente> listaCamarero2 = new ArrayList<>();

            listaCamarero1.add(new Cliente("Cliente1", 3000));
            listaCamarero1.add(new Cliente("Cliente2", 5000));
            listaCamarero1.add(new Cliente("Cliente3", 4000));
            listaCamarero1.add(new Cliente("Cliente4", 6000));

            listaCamarero2.add(new Cliente("Cliente5", 3000));
            listaCamarero2.add(new Cliente("Cliente6", 5000));
            listaCamarero2.add(new Cliente("Cliente7", 4000));
            listaCamarero2.add(new Cliente("Cliente8", 6000));


            for (Cliente c : listaCamarero1) c.start();
            for (Cliente c : listaCamarero2) c.start();


            Camarero camarero1 = new Camarero("Camarero1", listaCamarero1);
            Camarero camarero2 = new Camarero("Camarero2", listaCamarero2);


            camarero1.start();
            camarero2.start();

            // Esperar a que terminen
            camarero1.join();
            camarero2.join();

            // Resultado final
            int totalClientes = listaCamarero1.size() + listaCamarero2.size();
            System.out.println("Clientes atendidos: " + Camarero.getAtendidos() + " de " + totalClientes);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
