package principal;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;

public class VerTablas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	
	private OperacionesTablas ope = new OperacionesTablas();
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VerTablas frame = new VerTablas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	/**
	 * Create the frame.
	 */
	public VerTablas() {
		setTitle("MOSTRAR DATOS DE TABLAS");
	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 926, 503);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 250, 205));
		panel.setBounds(23, 28, 866, 87);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnVerProyectos = new JButton("Ver Proyectos");
		btnVerProyectos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ope.rellenarJTable("SELECT * FROM PROYECTOS", "PROYECTOS", table, scrollPane);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnVerProyectos.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnVerProyectos.setBounds(86, 30, 174, 36);
		panel.add(btnVerProyectos);
		
		JButton Verestudiantes = new JButton("Ver Estudiantes");
		Verestudiantes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ope.rellenarJTable("SELECT * FROM ESTUDIANTES", "ESTUDIANTES", table, scrollPane);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		Verestudiantes.setFont(new Font("Tahoma", Font.BOLD, 14));
		Verestudiantes.setBounds(346, 30, 174, 36);
		panel.add(Verestudiantes);
		
		JButton VerEntidades = new JButton("Ver Entidades");
		VerEntidades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ope.rellenarJTable("SELECT * FROM ENTIDADES", "ENTIDADES", table, scrollPane);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		VerEntidades.setFont(new Font("Tahoma", Font.BOLD, 14));
		VerEntidades.setBounds(606, 30, 174, 36);
		panel.add(VerEntidades);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(26, 143, 863, 313);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		scrollPane.setViewportView(table);
	}
}
