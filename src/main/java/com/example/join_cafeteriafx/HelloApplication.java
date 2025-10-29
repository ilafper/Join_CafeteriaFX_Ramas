package com.example.join_cafeteriafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simulación Cafetería");

        // Botón para iniciar la simulación
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> iniciarSimulacion());

        VBox root = new VBox(10, startButton);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Scene scene = new Scene(root, 300, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void iniciarSimulacion() {
        List<Cliente> listaCamarero1 = new ArrayList<>();
        List<Cliente> listaCamarero2 = new ArrayList<>();

        // Clientes
        listaCamarero1.add(new Cliente("Cliente1", 3000));
        listaCamarero1.add(new Cliente("Cliente2", 5000));
        listaCamarero1.add(new Cliente("Cliente3", 4000));
        listaCamarero1.add(new Cliente("Cliente4", 6000));

        listaCamarero2.add(new Cliente("Cliente5", 3000));
        listaCamarero2.add(new Cliente("Cliente6", 5000));
        listaCamarero2.add(new Cliente("Cliente7", 4000));
        listaCamarero2.add(new Cliente("Cliente8", 6000));

        // Iniciar hilos de clientes
        for (Cliente c : listaCamarero1) c.start();
        for (Cliente c : listaCamarero2) c.start();

        // Crear camareros
        Camarero camarero1 = new Camarero("Camarero1", listaCamarero1);
        Camarero camarero2 = new Camarero("Camarero2", listaCamarero2);

        camarero1.start();
        camarero2.start();

        // Esperar a que terminen los camareros
        new Thread(() -> {
            try {
                camarero1.join();
                camarero2.join();

                int totalClientes = listaCamarero1.size() + listaCamarero2.size();
                System.out.println("Clientes atendidos: " + Camarero.getAtendidos() + " de " + totalClientes);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
