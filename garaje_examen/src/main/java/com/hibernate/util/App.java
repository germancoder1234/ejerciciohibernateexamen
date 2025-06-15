
package com.hibernate.util;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import com.hibernate.dao.PlazaDAO;
import com.hibernate.model.Plaza;

public class App {

	private JFrame frame;
	private JTable table;
	private JTextField textFieldCodigo;
	private JTextField textFieldNumeroPlanta;
	private JTextField textFieldNumeroPlazas;
	private DefaultTableModel modelo;
	private PlazaDAO pDAO = new PlazaDAO();

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				App window = new App();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public App() {
		initialize();
		cargarTabla();
	}

	private void cargarTabla() {
		modelo.setRowCount(0);
		List<Plaza> plazas = pDAO.selectAllPlazas();
		for (Plaza p : plazas) {        
			modelo.addRow(new Object[]{p.getId(), p.getNumeroPlanta(), p.getNumeroPlazasLibres()});
		}
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 633, 601);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		modelo = new DefaultTableModel();
		modelo.addColumn("Id");
		modelo.addColumn("Numero de planta");
		modelo.addColumn("Numero de plazas libres");

		table = new JTable();
		table.setModel(modelo);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indice = table.getSelectedRow();
				TableModel model = table.getModel();
				textFieldCodigo.setText(String.valueOf(model.getValueAt(indice, 0)));
				textFieldNumeroPlanta.setText(String.valueOf(model.getValueAt(indice, 1)));
				textFieldNumeroPlazas.setText(String.valueOf(model.getValueAt(indice, 2)));
			}
		});
		table.setBounds(28, 33, 535, 215);
		frame.getContentPane().add(table);

		textFieldCodigo = new JTextField();
		textFieldCodigo.setBounds(80, 299, 86, 20);
		frame.getContentPane().add(textFieldCodigo);
		textFieldCodigo.setColumns(10);

		textFieldNumeroPlanta = new JTextField();
		textFieldNumeroPlanta.setBounds(236, 299, 86, 20);
		frame.getContentPane().add(textFieldNumeroPlanta);
		textFieldNumeroPlanta.setColumns(10);

		textFieldNumeroPlazas = new JTextField();
		textFieldNumeroPlazas.setBounds(390, 299, 86, 20);
		frame.getContentPane().add(textFieldNumeroPlazas);
		textFieldNumeroPlazas.setColumns(10);

		JButton btnInsertar = new JButton("Insertar");
		btnInsertar.addActionListener(e -> {
			try {
				int numeroPlanta = Integer.parseInt(textFieldNumeroPlanta.getText());
				int numeroPlazasLibres = Integer.parseInt(textFieldNumeroPlazas.getText());
				Plaza p = new Plaza(numeroPlanta, numeroPlazasLibres);
				pDAO.insertPlaza(p);
				cargarTabla();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "Introduce valores numéricos válidos.");
			}
		});
		btnInsertar.setBounds(80, 390, 103, 23);
		frame.getContentPane().add(btnInsertar);

		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(textFieldCodigo.getText());
				int numeroPlanta = Integer.parseInt(textFieldNumeroPlanta.getText());
				int numeroPlazasLibres = Integer.parseInt(textFieldNumeroPlazas.getText());
				Plaza p = new Plaza(numeroPlanta, numeroPlazasLibres);
				p.setId(id);
				pDAO.updatePlaza(p);
				cargarTabla();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "Selecciona un registro válido para actualizar.");
			}
		});
		btnActualizar.setBounds(236, 390, 104, 23);
		frame.getContentPane().add(btnActualizar);

		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(textFieldCodigo.getText());
				pDAO.deletePlaza(id);
				cargarTabla();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "Selecciona un registro válido para borrar.");
			}
		});
		btnBorrar.setBounds(390, 390, 103, 23);
		frame.getContentPane().add(btnBorrar);

		JLabel lblId = new JLabel("id");
		lblId.setBounds(104, 274, 46, 14);
		frame.getContentPane().add(lblId);

		JLabel lblNumeroPlaza = new JLabel("planta");
		lblNumeroPlaza.setBounds(251, 274, 46, 14);
		frame.getContentPane().add(lblNumeroPlaza);

		JLabel lblNumeroPlanta = new JLabel("plazas libres");
		lblNumeroPlanta.setBounds(390, 274, 100, 14);
		frame.getContentPane().add(lblNumeroPlanta);
	}
}

