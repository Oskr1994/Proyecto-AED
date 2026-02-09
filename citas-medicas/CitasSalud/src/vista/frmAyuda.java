package vista;

import javax.swing.*;

public class frmAyuda extends JFrame {

    public frmAyuda() {
        setTitle("Ayuda - Información del Proyecto");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JTextArea txt = new JTextArea();
        txt.setEditable(false);

        txt.setText(
            "SISTEMA DE CITAS MÉDICAS\n\n" +
            "Curso: Algoritmos y Estructura de Datos\n" +
            "Instituto: CIBERTEC\n\n" +
            "Integrantes:\n" +
            "- Bily Rodrigo Quinto Becerra\n" 
        );

        add(new JScrollPane(txt));
    }
}