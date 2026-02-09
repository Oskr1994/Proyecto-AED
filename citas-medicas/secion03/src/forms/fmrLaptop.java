package forms;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import classes.clsLaptop;

public class fmrLaptop extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTextArea textResultado;
	private JButton btnProcesar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fmrLaptop frame = new fmrLaptop();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public fmrLaptop() {
		setTitle("REGISTRO DE LAPTOP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1051, 606);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 29, 938, 388);
		contentPane.add(scrollPane);
		
		textResultado = new JTextArea();
		scrollPane.setViewportView(textResultado);
		
		btnProcesar = new JButton("Procecsar");
		btnProcesar.addActionListener(this);
		btnProcesar.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnProcesar.setBounds(31, 449, 139, 46);
		contentPane.add(btnProcesar);

	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnProcesar) {
			actionPerformedBtnProcesar(e);
		}
	}
	protected void actionPerformedBtnProcesar(ActionEvent e) {

	    textResultado.setText("");

	    clsLaptop lap = new clsLaptop(101, "Laptop Gamer", "HP", 3, 1800);

	    textResultado.append("=== DATOS INICIALES ===\n");
	    listado(lap);

	    lap.setPrecioventa(lap.getPrecioventa() * 1.15);
	    lap.setCantvendida(lap.getCantvendida() + 2);

	    textResultado.append("\n=== DATOS MODIFICADOS ===\n");
	    listado(lap);
	}

	private void listado(clsLaptop l) {

	    textResultado.append("Código: " + l.getCodigo() + "\n");
	    textResultado.append("Descripción: " + l.getDescripcion() + "\n");
	    textResultado.append("Marca: " + l.getMarca() + "\n");
	    textResultado.append("Cantidad Vendida: " + l.getCantvendida() + "\n");
	    textResultado.append("Precio Venta: " + l.getPrecioventa() + "\n");
	    textResultado.append("Monto Pagado: " + l.montoPagado() + "\n");
	    textResultado.append("Descuento: " + l.descuento() + "\n");
	    textResultado.append("Monto Neto: " + l.montoNeto() + "\n\n");
	}
}
