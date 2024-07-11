package principal;

import java.awt.Color;
import java.sql.*;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class OperacionesTablas {

	private static Connection conexion = null;

	public OperacionesTablas() {
		try {
			 //Class.forName("oracle.jdbc.driver.OracleDriver");

			if (conexion == null)
				conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "PROYECTOS", "proyectos");

		} catch (SQLException e) {
			Error(e);
			e.printStackTrace();

		}

	}

	public static Connection getConexion() {

		try {
			// Class.forName("oracle.jdbc.driver.OracleDriver");

			if (conexion == null)
				conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "PROYECTOS", "proyectos");

		} catch (SQLException e) {
			Error(e);
			return null;

		}
		return conexion;

	}

	public static void Error(SQLException e) {
		System.out.println("HA OCURRIDO UNA EXCEPCI�N:");
		System.out.println("Mensaje:    " + e.getMessage());
		System.out.println("SQL estado: " + e.getSQLState());
		System.out.println("Cod error:  " + e.getErrorCode());
	}

	public static void llenarcomboproyecto(JComboBox comboProyecto) {

		String sql = "SELECT * FROM PROYECTOS";

		comboProyecto.removeAllItems(); // Limpio la lista
		comboProyecto.addItem("Selecciona Proyecto"); // Añado el elemento en blanco

		try {

			Statement sentencia = conexion.createStatement();
			ResultSet res = sentencia.executeQuery(sql);
			while (res.next()) {
				comboProyecto.addItem(res.getInt(1) + " - " + res.getString(2));
			}
			res.close();
			sentencia.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static boolean existeEstudiante(String nombre) {
		String sql = "SELECT * FROM ESTUDIANTES WHERE nombre = ? ";

		boolean valor = false;

		PreparedStatement sentencia;
		try {
			sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, nombre);
			ResultSet resul = sentencia.executeQuery();

			if (resul.next())
				valor = true;

			System.out.println("EXISTE ESTUDIANTE:" + valor);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return valor;
	}

	public static void ejercicio1Insertarestudiante(String nombre, String direccion, String tlf, String fecha,
			JTextArea textArea) {
		try {
			int err = 0;
			textArea.append("\n");
			// comprobar el nombre
			// if (conexion == null) conexion=getConexion();
			System.out.println("nombere " + nombre + "**");
			if (existeEstudiante(nombre)) {
				err++;
				textArea.append("\nYa existe el nombre de ese estudiante: " + nombre);
			}
			if (nombre.isEmpty() || direccion.isEmpty() || tlf.isEmpty()) {
				err++;
				textArea.append("\nComprueba los datos, no pueden estar vacíos. ");
			}

			// extraer el máximo
			String sql = "SELECT max(codestudiante)+1 FROM ESTUDIANTES";

			System.out.println("PASO");

			PreparedStatement sentencia = conexion.prepareStatement(sql);
			ResultSet resul = sentencia.executeQuery();
			resul.next();
			int codigo = resul.getInt(1);

			if (err == 0) {
				sql = "INSERT INTO ESTUDIANTES (CODESTUDIANTE, NOMBRE, DIRECCION, TLF,FECHAALTA ) VALUES ( ?, ?, ?, ? , SYSDATE)";

				PreparedStatement sentencia2 = conexion.prepareStatement(sql);

				sentencia2.setInt(1, codigo);
				sentencia2.setString(2, nombre);
				sentencia2.setString(3, direccion);
				sentencia2.setString(4, tlf);
				System.out.println("PASO 2");
				sentencia2.executeUpdate();
				textArea.append("\nRegistro INSERTADO....");
				JOptionPane.showMessageDialog(null, "SE HA INSERTADO UN NUEVO ESTUDIANTE", "<<INFORMACIÓN>>",
						JOptionPane.INFORMATION_MESSAGE);

			} else {
				JOptionPane.showMessageDialog(null, "HAY ERRORES NO SE HA PODIDO INSERTAR EL REGISTRO",
						"<<INFORMACIÓN>>", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			textArea.append("\nNO SE HA PODIDO INSERTAR EL REGISTRO....");
			textArea.append("\nHA OCURRIDO UNA EXCEPCION:");
			textArea.append("\nMensaje:    " + e.getMessage());
			textArea.append("\nSQL estado: " + e.getSQLState());
			textArea.append("\nCod error:  " + e.getErrorCode());
			e.printStackTrace();
		}

	}

	public static void ejercicio2listarproyecto(int codigo, JTextArea textArea) {

		try {
			String sql = "SELECT * FROM proyectos WHERE codigoproyecto = ? ";

			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, codigo);
			ResultSet resul = sentencia.executeQuery();

			int sw = 0;
			while (resul.next()) {
				if (sw == 0) {
					sw = 1;
					textArea.append("\nCOD-PROYECTO: " + resul.getInt(1) + "   NOMBRE: " + resul.getString(2));
					textArea.append("\nFECHA INICIO: " + resul.getDate(3) + ", FECHA FIN: " + resul.getDate(4));
					textArea.append("\nPRESUPUESTO: " + resul.getFloat(5) + ", EXTRAAPORTACIÓN: " + resul.getFloat(6));
					textArea.append(
							"\n---------------------------------------------------------------------------------------------------");

					patrocinadoresproyectos(codigo, textArea, resul.getFloat(5));
					estudiantesproyecto(codigo, textArea);
				}

			} // no hay datos
			resul.close();
			sentencia.close();

		} catch (SQLException e) {
			textArea.append("\nNO SE HA PODIDO MOSTAR EL PROYECTO....");
			textArea.append("\nHA OCURRIDO UNA EXCEPCION:");
			textArea.append("\nMensaje:    " + e.getMessage());
			textArea.append("\nSQL estado: " + e.getSQLState());
			textArea.append("\nCod error:  " + e.getErrorCode());
			e.printStackTrace();
		}

	}

	private static void estudiantesproyecto(int codigo, JTextArea textArea) {

		String sql = "SELECT ed.codestudiante, ed.nombre, ed.direccion, pa.codparticipacion, pa.tipoparticipacion, pa.numaportaciones , pa.numaportaciones * extraaportacion"
				+ " from estudiantes ed join  participa pa  on (ed.codestudiante = pa.codestudiante) "
				+ " join proyectos pr on  pr.codigoproyecto=pa.codigoproyecto " + " where pa.codigoproyecto = ? "
				+ " order by ed.codestudiante, pa.codparticipacion ";

		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, codigo);
			ResultSet resul = sentencia.executeQuery();

			int sw = 0;
			int nuapt = 0, total = 0;
			while (resul.next()) {
				if (sw == 0) {
					textArea.append("\nLISTA DE ESTUDIANTES QUE PARTICIPAN EN EL PROYECTO");
					textArea.append(
							"\nCód  Nombre                         Dirección                                CodPar Tipo aportación      NumApt TotAport ");
					textArea.append(
							"\n==== ============================== ======================================== ====== ==================== ====== ======== ");
					sw = 1;
				}

				String cad = String.format("%4s %-30s %-40s %6s %20s %6s %8s", resul.getInt(1), resul.getString(2),
						resul.getString(3), resul.getInt(4), resul.getString(5), resul.getInt(6), resul.getInt(7));
				nuapt = nuapt + resul.getInt(6);
				total = total + resul.getInt(7);

				// resul.getDouble(8),
				// resul.getString(9) );

				textArea.append("\n" + cad);

			}

			if (sw == 0) {
				textArea.append("\n\tNINGÚN ESTUDIANTE PERTENECE A ESTE PROYECTO.");
				textArea.append(
						"\n---------------------------------------------------------------------------------------------------");
			} else

			{
				textArea.append(
						"\n=================================== ======================================== ====== ==================== ====== ========");
				String cad = String.format("%n%-35s %-40s %6s %20s %6s %8s", "TOTALES", " ", " ", " ", nuapt, total);
				textArea.append(cad);
				textArea.append(
						"\n=================================== ======================================== ====== ==================== ====== ========");
				textArea.append("\n");

			}

			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			textArea.append("\nNO SE HA PODIDO CONSULTAR LOS ESTUDIANTES DEL PROYECTO....");
			textArea.append("\nHA OCURRIDO UNA EXCEPCION:");
			textArea.append("\nMensaje:    " + e.getMessage());
			textArea.append("\nSQL estado: " + e.getSQLState());
			textArea.append("\nCod error:  " + e.getErrorCode());
			e.printStackTrace();
		}

	}

	private static void patrocinadoresproyectos(int codigo, JTextArea textArea, float presupuesto) {
		String sql = "SELECT e.CODENTIDAD, e.DESCRIPCION, importeaportacion, fechaaportacion "
				+ " from entidades e join  patrocina pa  on (e.codentidad = pa.codentidad) "
				+ " join proyectos pr on  pr.codigoproyecto=pa.codigoproyecto" + " where pa.codigoproyecto = ?"
				+ " order by e.CODENTIDAD ";

		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, codigo);
			ResultSet resul = sentencia.executeQuery();

			int sw = 0;
			float total = 0;
			while (resul.next()) {
				if (sw == 0) {
					textArea.append("\nLISTA DE ENTIDADES QUE PATROCINA EL PROYECTO");
					textArea.append("\nCódigo  Descripción                    Importe aportación Fecha aportación");
					textArea.append("\n======= ============================== ================== ================");
					sw = 1;
				}

				String cad = String.format("%7s %-30s %18s %16s", resul.getInt(1), resul.getString(2),
						resul.getFloat(3), resul.getDate(4));
				// nuapt=nuapt+resul.getInt(6);
				total = total + resul.getFloat(3);

				// resul.getDouble(8),
				// resul.getString(9) );

				textArea.append("\n" + cad);

			}

			if (sw == 0) {
				textArea.append("\n\tNINGUNA ENTIDAD PATROCINA ESTE PROYECTO.");
				textArea.append(
						"\n---------------------------------------------------------------------------------------------------");
			} else

			{
				textArea.append("\n======= ============================== ================== ================");
				String cad = String.format("%n%-38s %18s %16s", "TOTAL APORTACIONES: ", total, " ");
				textArea.append(cad);
				float ptotal = presupuesto + total;
				cad = String.format("%n%-38s %18s %16s", "PRESUPUESTO TOTAL:", ptotal, " ");
				textArea.append(cad);
				textArea.append("\n----------------------------------------------------------------------------");
				textArea.append("\n");

			}

			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			textArea.append("\nNO SE HA PODIDO CONSULTAR LOS PATROCINADORES DEL PROYECTO....");
			textArea.append("\nHA OCURRIDO UNA EXCEPCION:");
			textArea.append("\nMensaje:    " + e.getMessage());
			textArea.append("\nSQL estado: " + e.getSQLState());
			textArea.append("\nCod error:  " + e.getErrorCode());
			e.printStackTrace();
		}

	}

	public static void ejercicio3crearcolumnas(JTextArea textArea) {
		insertarColumnas(textArea);
		try {
			Statement orden1 = conexion.createStatement();

			StringBuilder modificartabla = new StringBuilder();
			modificartabla.append("UPDATE PROYECTOS P ");
			modificartabla
					.append("SET numempre = (SELECT COUNT(*) FROM PATROCINA WHERE CODIGOPROYECTO=P.CODIGOPROYECTO), ");
			modificartabla.append(
					" importempre = (SELECT coalesce(sum(importeaportacion),0) FROM PATROCINA WHERE CODIGOPROYECTO=P.CODIGOPROYECTO ), ");
			modificartabla
					.append(" numalum = (SELECT COUNT(*) FROM PARTICIPA WHERE CODIGOPROYECTO=P.CODIGOPROYECTO) , ");
			modificartabla.append(
					" gastoalum = (SELECT coalesce(SUM(numaportaciones),0) * p.extraaportacion  FROM PARTICIPA WHERE CODIGOPROYECTO=P.CODIGOPROYECTO) , ");
			modificartabla.append(
					" gastorecursos = (SELECT coalesce(SUM(cantidad * pvp),0) FROM usa join recursos using(codrecurso ) WHERE CODIGOPROYECTO=P.CODIGOPROYECTO)");

			int filas = 0;
			// textArea.append("\n"+modificartabla.toString());
			filas = orden1.executeUpdate(modificartabla.toString());
			textArea.append("\n--------------------------------------------");
			textArea.append("\nTabla PROYECTOS actualizada. ");

			orden1.close();

			mostrarprotyectos(textArea);

		} catch (SQLException e) {
			textArea.append("\n--------------------------------------------");
			textArea.append("\nERROR AL ACTUALIZAR LAS COLUMNAS");
			e.printStackTrace();
		}

	}

	public static void mostrarprotyectos(JTextArea textArea) {

		try {
			textArea.append("\n--------------------------------------------");
			textArea.append("\nMOSTRANDO TABLA PROYECTOS");
			textArea.append("\n--------------------------------------------");
			String sql = "SELECT * FROM proyectos";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			ResultSet resul = sentencia.executeQuery();

			int sw = 0;
			String cad = String.format("%n%5s %-45s %10s %10s %10s %10s %10s %10s %10s %10s %10s  ", "COD", "NOMBRE",
					"FECHAINI", "FECHAFIN", "PRESUPUEST", "EXTRAAPORT", "NUMEMPRE", "IMPORTEEMP", "NUMALUM",
					"GASTOALUM", "GASTORECUR");
			textArea.append(cad);
			cad = String.format("%n%5s %-45s %10s %10s %10s %10s %10s %10s %10s %10s %10s  ", "-----", "---------------------------------------------",
					"----------", "----------", "----------", "----------", "----------", "----------", "----------",
					"----------", "----------");
			textArea.append(cad);
			
			while (resul.next()) {

				cad = String.format("%n%5s %-45s %10s %10s %10s %10s %10s %10s %10s %10s %10s", resul.getInt(1),resul.getString(2),
						resul.getDate(3), resul.getDate(4),resul.getFloat(5), resul.getFloat(6), resul.getInt(7), resul.getFloat(8), resul.getInt(9),
						resul.getFloat(10), resul.getFloat(11));
				textArea.append(cad);

			}
			cad = String.format("%n%5s %-45s %10s %10s %10s %10s %10s %10s %10s %10s %10s  ", "-----", "---------------------------------------------",
					"----------", "----------", "----------", "----------", "----------", "----------", "----------",
					"----------", "----------");
			textArea.append(cad+"\n");
			resul.close();
			sentencia.close();
		} // no hay datos

		catch (SQLException e) {
			textArea.append("\nNO SE HA PODIDO MOSTAR EL PROYECTO....");
			textArea.append("\nHA OCURRIDO UNA EXCEPCION:");
			textArea.append("\nMensaje:    " + e.getMessage());
			textArea.append("\nSQL estado: " + e.getSQLState());
			textArea.append("\nCod error:  " + e.getErrorCode());
			e.printStackTrace();
		}

	}

	private static void insertarColumnas(JTextArea textArea) {
		try {
			Statement orden1 = conexion.createStatement();
			/*
			 * - el número de empresas que patrocina el proyecto de tipo int, - el importe
			 * que aportan estas empresas al proyecto de tipo float (será la suma de las
			 * aportaciones que hacen las empresas/entidades a los proyectos), - el número
			 * de alumnos que participa en ese proyecto de tipo int, - el gasto que supone
			 * las aportaciones de los alumnos de tipo float (la suma del número de
			 * aportaciones por la EXTRAAPORTACIÓN del proyecto), y - el gasto de recursos
			 * del proyecto de tipo float (se calculará multiplicando la CANTIDAD del
			 * recurso que usa el proyecto por el PVP)
			 * 
			 */
			StringBuilder modificartabla = new StringBuilder();
			modificartabla.append("ALTER TABLE PROYECTOS ADD ( ");
			modificartabla.append(" numempre   int DEFAULT 0 NOT NULL , ");
			modificartabla.append(" importempre  float DEFAULT 0 NOT NULL, ");
			modificartabla.append(" numalum   int  DEFAULT 0 NOT NULL ,");
			modificartabla.append(" gastoalum  float DEFAULT 0 NOT NULL, ");
			modificartabla.append(" gastorecursos  float DEFAULT 0 NOT NULL ) ");

			int filas = 0;

			filas = orden1.executeUpdate(modificartabla.toString());
			textArea.append("\nCOLUMNAS añadidas a la tabla PROYECTOS");
			orden1.close();
		} catch (SQLException e) {
			textArea.append("\n--------------------------------------------");
			textArea.append("\nCOLUMNAS a añadir en la tabla ya EXISTEN....");

		}

	}

	public static void ejercicio4PDF(JTextArea textArea)  {
		String reportSource = "./informes/plantilla.jrxml";
		String reportHTML = "./informes/Informe.html";
		String reportPDF = "Informe.pdf";
		String reportXML = "./informes/Informe.xml";

		LocalDate hoy = LocalDate.now();

		String fecha = hoy.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		//select CODENTIDAD, DESCRIPCION, DIRECCION, COUNT(CODIGOPROYECTO) as NUMPROYECTOS, 
		//SUM(IMPORTEAPORTACION) as SUMAAPOR FROM ENTIDADES left JOIN PATROCINA USING (CODENTIDAD)
		//group by CODENTIDAD, DESCRIPCION, DIRECCION order by CODENTIDAD;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("titulo", "LISTADO DE EMPRESAS Y SUS APORTACIONES.");
		params.put("autor", "Tu nombre y apellidos");
		params.put("fecha", fecha);


		try {
			JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);

			JasperPrint MiInforme = JasperFillManager.fillReport(jasperReport, params, conexion);
			// Visualizar en pantalla
			JasperViewer.viewReport(MiInforme, false);
			// Convertir a HTML
			JasperExportManager.exportReportToHtmlFile(MiInforme, reportHTML);
			// Convertir a PDF
			JasperExportManager.exportReportToPdfFile(MiInforme, reportPDF);
			// Convertir a XML.
			JasperExportManager.exportReportToXmlFile(MiInforme, reportXML, false);

			File f = new File(reportPDF);
			textArea.append("\nINFORME PDF CREADO EN " + f.getAbsolutePath());

		} catch (JRException ex) {
			textArea.append("\n Error Jasper.");
			ex.printStackTrace();
		}

	}//

	
	
	public static void rellenarJTable(String consulta, String tabla, JTable table, JScrollPane scrollPane)
			throws SQLException {

		table = new JTable();
		Statement st = conexion.createStatement();

		System.out.println(consulta);

		String queryS = consulta;
		ResultSet rs = st.executeQuery(queryS);
		ResultSetMetaData rsmd = rs.getMetaData();

		int colums = rsmd.getColumnCount(); // numero de columnas
		String[] headers = new String[colums];
		for (int i = 1; i <= colums; i++)
			headers[i - 1] = rsmd.getColumnName(i).toUpperCase();

		rs = st.executeQuery("select count(*) from " + tabla);
		rs.next();
		int rows = rs.getInt(1);

		int countRow = 0;
		rs = st.executeQuery(queryS);
		Object[][] data = new Object[rows][colums];
		while (rs.next()) {
			for (int i = 0; i < colums; i++)
				data[countRow][i] = rs.getObject(i + 1);
			countRow++;
		}

		DefaultTableModel model = new DefaultTableModel(data, headers);
		model.setColumnIdentifiers(headers);

		table.setModel(model);
		Color c = new Color(255, 250, 205);
		table.setBackground(c);
		table.setForeground(Color.BLUE);
		table.setFont(new Font("Verdana", Font.PLAIN, 12));
		scrollPane.setViewportView(table);

		rs.close();
		st.close();

	}
}
