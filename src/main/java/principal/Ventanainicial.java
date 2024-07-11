package principal;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class Ventanainicial extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField nombre;
	private JTextField direccion;
	private JTextField tlf;
	private JTextField fechaalta;
	private JComboBox comboProyecto;

	private static OperacionesTablas ope = new OperacionesTablas();
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventanainicial frame = new Ventanainicial();
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
	public Ventanainicial() {
		setTitle("OPERACIONES CON PROYECTOS");
		setBounds(100, 100, 999, 780);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("EJERCICIO 1. INSERTAR DATOS DE ESTUDIANTES:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(28, 52, 398, 27);
		getContentPane().add(lblNewLabel);

		nombre = new JTextField();
		nombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nombre.setBounds(43, 114, 191, 27);
		getContentPane().add(nombre);
		nombre.setColumns(10);

		direccion = new JTextField();
		direccion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		direccion.setColumns(10);
		direccion.setBounds(271, 114, 256, 27);
		getContentPane().add(direccion);

		tlf = new JTextField();
		tlf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tlf.setColumns(10);
		tlf.setBounds(550, 114, 129, 27);
		getContentPane().add(tlf);

		fechaalta = new JTextField();
		fechaalta.setHorizontalAlignment(SwingConstants.CENTER);
		fechaalta.setEditable(false);
		fechaalta.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fechaalta.setColumns(10);
		fechaalta.setBounds(707, 114, 129, 27);
		// asignar fecha de hoy
		// fechaalta
		Date fechaActual = new Date();
		// Crear un objeto SimpleDateFormat con el formato deseado
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		// Formatear la fecha como una cadena
		String fechaFormateada = formato.format(fechaActual);
		fechaalta.setText(fechaFormateada);
		getContentPane().add(fechaalta);

		JLabel lblNewLabel_1 = new JLabel("NOMBRE");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(61, 91, 88, 27);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("DIRECCION");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1.setBounds(271, 89, 88, 27);
		getContentPane().add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("TELÉFONO");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1_1.setBounds(550, 89, 88, 27);
		getContentPane().add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_1_2 = new JLabel("FECHA ALTA");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1_2.setBounds(707, 91, 88, 27);
		getContentPane().add(lblNewLabel_1_1_2);

		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(0, 128, 128));
		separator.setForeground(new Color(0, 0, 255));
		separator.setBounds(21, 152, 939, 2);
		getContentPane().add(separator);

		JButton insertarestudiante = new JButton("\nInsertar");
		insertarestudiante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				ope.ejercicio1Insertarestudiante(nombre.getText(), direccion.getText(), tlf.getText(),
						fechaalta.getText(), textArea);

			}
		});
		insertarestudiante.setFont(new Font("Tahoma", Font.BOLD, 13));
		insertarestudiante.setBounds(854, 76, 106, 62);
		getContentPane().add(insertarestudiante);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLUE);
		separator_1.setBackground(new Color(0, 128, 128));
		separator_1.setBounds(28, 218, 939, 2);
		getContentPane().add(separator_1);

		JLabel lblEjercicioListar = new JLabel("EJERCICIO 2. LISTAR DATOS DEL PROYECTO.");
		lblEjercicioListar.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEjercicioListar.setBounds(28, 174, 398, 27);
		getContentPane().add(lblEjercicioListar);

		comboProyecto = new JComboBox();
		comboProyecto.setBounds(403, 176, 289, 27);
		ope.llenarcomboproyecto(comboProyecto);
		getContentPane().add(comboProyecto);

		JButton verdatosproyecto = new JButton("Mostrar proyecto");
		verdatosproyecto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				int codigo;
				String datos = comboProyecto.getSelectedItem().toString();
				int posi = datos.indexOf("-");
				System.out.println(datos + " " + posi);
				if (posi >= 0) {
					String cod = datos.substring(0, posi).trim();
					System.out.println(datos + " " + posi + " " + cod + "**");
					codigo = Integer.parseInt(cod);
					ope.ejercicio2listarproyecto(codigo, textArea);
				}

				else {
					textArea.append("\n---------------------------");
					textArea.append("\nPROYECTO INEXISTENTE ");
					textArea.append("\n---------------------------");
				}

			}
		});
		verdatosproyecto.setFont(new Font("Tahoma", Font.BOLD, 13));
		verdatosproyecto.setBounds(733, 168, 189, 40);
		getContentPane().add(verdatosproyecto);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 463, 952, 274);
		getContentPane().add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		scrollPane.setViewportView(textArea);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setForeground(Color.BLUE);
		separator_1_1.setBackground(new Color(0, 128, 128));
		separator_1_1.setBounds(28, 280, 939, 2);
		getContentPane().add(separator_1_1);
		
		JLabel lblEjercicioAadir = new JLabel("EJERCICIO 3. AÑADIR COLUMNAS A LA TABLA PROYECTOS Y MOSTRAR LOS DATOS\r\n");
		lblEjercicioAadir.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEjercicioAadir.setBounds(28, 230, 651, 27);
		getContentPane().add(lblEjercicioAadir);
		
		JButton anadircolumnas = new JButton("Añadir columnas");
		anadircolumnas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				ope.ejercicio3crearcolumnas(textArea);
			}
		});
		anadircolumnas.setFont(new Font("Tahoma", Font.BOLD, 13));
		anadircolumnas.setBounds(733, 230, 189, 40);
		getContentPane().add(anadircolumnas);
		
		JLabel lblEjercicioGenerar = new JLabel("EJERCICIO 4. GENERAR PDF CON LA INFORMACIÓN DE EMPRESAS");
		lblEjercicioGenerar.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEjercicioGenerar.setBounds(28, 292, 554, 40);
		getContentPane().add(lblEjercicioGenerar);
		
		JButton btnGenerarPdf = new JButton("Generar pdf");
		btnGenerarPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				ope.ejercicio4PDF(textArea);
			}
		});
		btnGenerarPdf.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnGenerarPdf.setBounds(535, 292, 189, 40);
		getContentPane().add(btnGenerarPdf);
		
		JLabel lblTextreaParaMostrar = new JLabel("Text-Área para mostrar las informaciones");
		lblTextreaParaMostrar.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTextreaParaMostrar.setBounds(28, 426, 301, 40);
		getContentPane().add(lblTextreaParaMostrar);
		
		JSeparator separator_1_1_1 = new JSeparator();
		separator_1_1_1.setForeground(Color.BLUE);
		separator_1_1_1.setBackground(new Color(0, 128, 128));
		separator_1_1_1.setBounds(21, 342, 939, 2);
		getContentPane().add(separator_1_1_1);
		
		JSeparator separator_1_1_1_1 = new JSeparator();
		separator_1_1_1_1.setForeground(Color.BLUE);
		separator_1_1_1_1.setBackground(new Color(0, 128, 128));
		separator_1_1_1_1.setBounds(28, 414, 939, 2);
		getContentPane().add(separator_1_1_1_1);
		
		JButton btnEjercicioVer = new JButton("EJERCICIO 5. VER DATOS DE TABLAS");
		btnEjercicioVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VerTablas frame = new VerTablas();
				frame.setVisible(true);
			}
		});
		btnEjercicioVer.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnEjercicioVer.setBounds(28, 354, 376, 40);
		getContentPane().add(btnEjercicioVer);
		
		JLabel lblPrcticaRealizada = new JLabel("PRÁCTICA JDBC.");
		lblPrcticaRealizada.setForeground(Color.BLUE);
		lblPrcticaRealizada.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrcticaRealizada.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPrcticaRealizada.setBounds(179, 10, 616, 32);
		getContentPane().add(lblPrcticaRealizada);

	}
}
