package vista;

import javax.swing.SwingUtilities;


/* Punto de entrada de la aplicacion */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Ventana ventana = new Ventana();
            ventana.setVisible(true);
        });
    }
}
