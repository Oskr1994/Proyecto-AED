package vista;

import javax.swing.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.Frame;

public class frmAyuda extends JDialog {

    public frmAyuda(Frame owner) {
        super(owner, "Ayuda - Información del Proyecto", true);
        getContentPane().setBackground(new Color(255, 255, 255));
        setSize(400, 300);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JTextArea txtrSistemaDeCitas = new JTextArea();
        txtrSistemaDeCitas.setText(
                "Curso: Algoritmos y Estructura de Datos\r\nInstituto: CIBERTEC\r\n\r\nIntegrantes:\r\n- Oscar Enrique Perez Alvarez\r\n- Bily Rodrigo Quinto Becerra\r\n- Ivan Bray Vasquez Cabanillas\r\n- Kevin Ronald Gradados Condori\r\n- Jose Wislly Zafra Alcantara");
        txtrSistemaDeCitas.setBounds(10, 67, 364, 194);
        getContentPane().add(txtrSistemaDeCitas);

        JLabel lblTitulo = new JLabel("SISTEMA DE CITAS MÉDICAS");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(78, 11, 199, 43);
        getContentPane().add(lblTitulo);
    }
}