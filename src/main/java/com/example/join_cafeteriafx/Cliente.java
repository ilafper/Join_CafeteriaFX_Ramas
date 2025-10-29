import java.util.Random;
class Cliente extends Thread {
    private String nombre;
    private int tiempoEspera;
    private boolean atendido = false;

    public Cliente(String nombre, int tiempoEspera) {
        this.nombre = nombre;
        this.tiempoEspera = tiempoEspera;
    }

    public String getNombre() { return nombre; }
    public int getTiempoEspera() { return tiempoEspera; }
    public boolean isAtendido() { return atendido; }
    public void setAtendido(boolean atendido) { this.atendido = atendido; }

    @Override
    public void run() {
        try {
            // Simular tiempo de llegada aleatorio entre 0 y 2 segundos
            Random r = new Random();
            int llegada = r.nextInt(2000);
            Thread.sleep(llegada);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(nombre + " llega a la cafetería y espera " + tiempoEspera/1000.0 + " s por su café");

        try {
            Thread.sleep(tiempoEspera);
        } catch (InterruptedException e) {
            // Interrumpido significa que fue atendido
        }

        if (!atendido) {
            System.out.println(nombre + " se fue porque no recibió su café a tiempo");
        }
    }
}