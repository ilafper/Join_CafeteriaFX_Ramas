package com.example.join_cafeteriafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    private TextArea areaCamarero1;

    @FXML
    private TextArea areaCamarero2;

    @FXML
    private Label mensajeClientes;

    @FXML
    private Button btnEmpezar;

    @FXML
    protected void initialize() {
        // Configurar botón para iniciar simulación
        btnEmpezar.setOnAction(e -> onEmpezarClick());
    }

    private void onEmpezarClick() {
        btnEmpezar.setDisable(true);
        mensajeClientes.setText("Iniciando simulación...");

        // Crear listas de clientes
        List<Cliente> listaCamarero1 = new ArrayList<>();
        List<Cliente> listaCamarero2 = new ArrayList<>();

        listaCamarero1.add(new Cliente("Cliente1", 3000));
        listaCamarero1.add(new Cliente("Cliente2", 4000));
        listaCamarero1.add(new Cliente("Cliente3", 5000));
        listaCamarero1.add(new Cliente("Cliente4", 6000));

        listaCamarero2.add(new Cliente("Cliente5", 3000));
        listaCamarero2.add(new Cliente("Cliente6", 4000));
        listaCamarero2.add(new Cliente("Cliente7", 5000));
        listaCamarero2.add(new Cliente("Cliente8", 6000));

        int totalClientes = listaCamarero1.size() + listaCamarero2.size();

        // Iniciar hilos de clientes
        for (Cliente c : listaCamarero1) c.start();
        for (Cliente c : listaCamarero2) c.start();

        // Crear camareros
        Camarero camarero1 = new Camarero("Camarero1", listaCamarero1, areaCamarero1);
        Camarero camarero2 = new Camarero("Camarero2", listaCamarero2, areaCamarero2);

        camarero1.start();
        camarero2.start();

        // Hilo para actualizar el contador de clientes atendidos
        new Thread(() -> {
            while (camarero1.isAlive() || camarero2.isAlive()) {
                Platform.runLater(() -> {
                    mensajeClientes.setText(Camarero.getAtendidos() + " de " + totalClientes + " clientes atendidos");
                });
                try {
                    Thread.sleep(500); // actualiza cada medio segundo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Cuando los camareros terminen
            Platform.runLater(() -> {
                mensajeClientes.setText(Camarero.getAtendidos() + " de " + totalClientes);
                btnEmpezar.setDisable(false);
            });
        }).start();
    }
}
