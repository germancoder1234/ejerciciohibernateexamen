package com.hibernate.gui;

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
import java.awt.Color;

public class App {

    private JFrame frame;
    private JTable table;
    private JTextField textFieldCodigo;
    private JTextField textFieldNumeroPlanta;
    private JTextField textFieldNumeroPlazas;
    private JLabel lblAlertaPlazas;

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

    private void actualizarAlertaPlazas(int numeroPlazasLibres) {
        if (numeroPlazasLibres < 10 && numeroPlazasLibres > 0) {
            lblAlertaPlazas.setText("Quedan pocas plazas disponibles en esa planta");
            lblAlertaPlazas.setForeground(Color.RED);
        } else {
            lblAlertaPlazas.setText("");
        }
    }

    public App() {
        initialize();
    }
//funcion para cargar tabla mediante for each
    private void cargarTabla(PlazaDAO pDAO, DefaultTableModel modelo) {
        modelo.setRowCount(0);
        List<Plaza> plazas = pDAO.selectAllPlazas();
        for (Plaza p : plazas) {        
            modelo.addRow(new Object[]{p.getId(), p.getNumeroPlanta(), p.getNumeroPlazasLibres()});
        }
    }
    //misma funcion pero esta vez con for en vez de con un for each
/*private void cargarTabla(PlazaDAO pDAO, DefaultTableModel modelo) {
    modelo.setRowCount(0);
    List<Plaza> plazas = pDAO.selectAllPlazas();
    for (int i = 0; i < plazas.size(); i++) {
        modelo.addRow(new Object[]{
            plazas.get(i).getId(), 
            plazas.get(i).getNumeroPlanta(), 
            plazas.get(i).getNumeroPlazasLibres()
        });
    }
}*/
    private void initialize() {
        DefaultTableModel modelo;
        PlazaDAO pDAO = new PlazaDAO();
         
        frame = new JFrame();
        frame.setBounds(100, 100, 642, 652);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Numero de planta");
        modelo.addColumn("Numero de plazas libres");

        table = new JTable(modelo);
    
        cargarTabla(pDAO, modelo);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int indice = table.getSelectedRow();
                TableModel model = table.getModel();
                textFieldCodigo.setText(String.valueOf(model.getValueAt(indice, 0)));
                textFieldNumeroPlanta.setText(String.valueOf(model.getValueAt(indice, 1)));
                textFieldNumeroPlazas.setText(String.valueOf(model.getValueAt(indice, 2)));
                actualizarAlertaPlazas(Integer.parseInt(textFieldNumeroPlazas.getText()));
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(28, 33, 535, 215);
        frame.getContentPane().add(scrollPane);

        lblAlertaPlazas = new JLabel("");
        lblAlertaPlazas.setBounds(28, 10, 400, 20);
        lblAlertaPlazas.setForeground(Color.RED);
        frame.getContentPane().add(lblAlertaPlazas);

        textFieldCodigo = new JTextField();
        textFieldCodigo.setEditable(false);
        textFieldCodigo.setBounds(121, 299, 86, 20);
        frame.getContentPane().add(textFieldCodigo);
        textFieldCodigo.setColumns(10);

        textFieldNumeroPlanta = new JTextField();
        textFieldNumeroPlanta.setBounds(121, 331, 86, 20);
        frame.getContentPane().add(textFieldNumeroPlanta);
        textFieldNumeroPlanta.setColumns(10);

        textFieldNumeroPlazas = new JTextField();
        textFieldNumeroPlazas.setBounds(121, 363, 86, 20);
        frame.getContentPane().add(textFieldNumeroPlazas);
        textFieldNumeroPlazas.setColumns(10);

        JButton btnInsertar = new JButton("Insertar");
        btnInsertar.addActionListener(e -> {
            try {
                int numeroPlanta = Integer.parseInt(textFieldNumeroPlanta.getText());
                int numeroPlazasLibres = Integer.parseInt(textFieldNumeroPlazas.getText());
                Plaza p = new Plaza(numeroPlanta, numeroPlazasLibres);
                pDAO.insertPlaza(p);
                cargarTabla(pDAO, modelo);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Introduce numeros enteros.");
            }
        });
        btnInsertar.setBounds(28, 411, 103, 23);
        frame.getContentPane().add(btnInsertar);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(textFieldCodigo.getText());
                int numeroPlanta = Integer.parseInt(textFieldNumeroPlanta.getText());
                int numeroPlazasLibres = Integer.parseInt(textFieldNumeroPlazas.getText());
                Plaza p = pDAO.selectPlazaById(id);
                p.setNumeroPlanta(numeroPlanta);
                p.setNumeroPlazasLibres(numeroPlazasLibres);
                pDAO.updatePlaza(p);
                cargarTabla(pDAO, modelo);
                actualizarAlertaPlazas(numeroPlazasLibres);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Selecciona para actualizar.");
            }
        });
        btnActualizar.setBounds(159, 411, 127, 23);
        frame.getContentPane().add(btnActualizar);

        JButton btnBorrar = new JButton("Borrar");
        btnBorrar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(textFieldCodigo.getText());
                pDAO.deletePlaza(id);
                cargarTabla(pDAO, modelo);
                lblAlertaPlazas.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "selecciona para borrar.");
            }
        });
        btnBorrar.setBounds(309, 411, 103, 23);
        frame.getContentPane().add(btnBorrar);

        JLabel lblId = new JLabel("id");
        lblId.setBounds(16, 301, 46, 14);
        frame.getContentPane().add(lblId);

        JLabel lblNumeroPlaza = new JLabel("planta");
        lblNumeroPlaza.setBounds(16, 333, 46, 14);
        frame.getContentPane().add(lblNumeroPlaza);

        JLabel lblNumeroPlanta = new JLabel("plazas libres");
        lblNumeroPlanta.setBounds(16, 363, 100, 14);
        frame.getContentPane().add(lblNumeroPlanta);
        
        JButton btnAparcar = new JButton("Aparcar");
        btnAparcar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(textFieldCodigo.getText());
                    int numeroPlazasLibres = Integer.parseInt(textFieldNumeroPlazas.getText());
                    
                    if (numeroPlazasLibres <= 0) {
                        JOptionPane.showMessageDialog(frame, 
                            "No quedan plazas disponibles en esta planta", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    Plaza p = pDAO.selectPlazaById(id);
                    p.setNumeroPlazasLibres(numeroPlazasLibres - 1);
                    pDAO.updatePlaza(p);
                    cargarTabla(pDAO, modelo);
                    actualizarAlertaPlazas(p.getNumeroPlazasLibres());
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Selecciona una plaza válida.");
                }
            }
        });
        btnAparcar.setBounds(54, 503, 117, 25);
        frame.getContentPane().add(btnAparcar);
        
        JButton btnLiberar = new JButton("Liberar");
        btnLiberar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(textFieldCodigo.getText());
                    int numeroPlazasLibres = Integer.parseInt(textFieldNumeroPlazas.getText());
                    Plaza p = pDAO.selectPlazaById(id);
                    p.setNumeroPlazasLibres(numeroPlazasLibres + 1);
                    pDAO.updatePlaza(p);
                    cargarTabla(pDAO, modelo);
                    actualizarAlertaPlazas(p.getNumeroPlazasLibres());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Selecciona una plaza válida.");
                }
            }
        });
        btnLiberar.setBounds(258, 503, 117, 25);
        frame.getContentPane().add(btnLiberar);
    }
}
