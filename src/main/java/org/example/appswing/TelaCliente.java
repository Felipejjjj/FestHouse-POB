package org.example.appswing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

import org.example.modelo.Cliente;
import org.example.requisito.Fachada;

public class TelaCliente {

    private JDialog frame;
    private JTable table;
    private DefaultTableModel model;

    private JTextField tfCpf, tfNome, tfLat, tfLon;
    private JLabel labelMensagem;

    public TelaCliente() {
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setTitle("Clientes");
        frame.setModal(true);
        frame.setSize(700, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 20, 640, 150);
        frame.add(scrollPane);

        table = new JTable();
        model = new DefaultTableModel(new Object[]{"CPF", "Nome", "Latitude", "Longitude"}, 0);
        table.setModel(model);
        scrollPane.setViewportView(table);

        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setBounds(20, 190, 80, 20);
        frame.add(lblCpf);

        tfCpf = new JTextField();
        tfCpf.setBounds(100, 190, 150, 20);
        frame.add(tfCpf);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 220, 80, 20);
        frame.add(lblNome);

        tfNome = new JTextField();
        tfNome.setBounds(100, 220, 150, 20);
        frame.add(tfNome);

        JLabel lblLat = new JLabel("Latitude:");
        lblLat.setBounds(300, 190, 80, 20);
        frame.add(lblLat);

        tfLat = new JTextField();
        tfLat.setBounds(380, 190, 100, 20);
        frame.add(tfLat);

        JLabel lblLon = new JLabel("Longitude:");
        lblLon.setBounds(300, 220, 80, 20);
        frame.add(lblLon);

        tfLon = new JTextField();
        tfLon.setBounds(380, 220, 100, 20);
        frame.add(tfLon);

        JButton btCriar = new JButton("Criar Cliente");
        btCriar.setBounds(520, 190, 140, 25);
        frame.add(btCriar);

        JButton btAtualizar = new JButton("Atualizar Lista");
        btAtualizar.setBounds(520, 220, 140, 25);
        frame.add(btAtualizar);

        labelMensagem = new JLabel("");
        labelMensagem.setBounds(20, 260, 400, 20);
        labelMensagem.setForeground(java.awt.Color.RED);
        frame.add(labelMensagem);

        frame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                listagem();
            }
        });

        btAtualizar.addActionListener(e -> listagem());

        btCriar.addActionListener(e -> criarCliente());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = table.getSelectedRow();
                if (i >= 0) {
                    tfCpf.setText(model.getValueAt(i, 0).toString());
                    tfNome.setText(model.getValueAt(i, 1).toString());
                    tfLat.setText(model.getValueAt(i, 2).toString());
                    tfLon.setText(model.getValueAt(i, 3).toString());
                }
            }
        });

        frame.setVisible(true);
    }

    private void listagem() {
        try {
            model.setRowCount(0);
            List<Cliente> lista = Fachada.listarClientes();

            for (Cliente c : lista) {
                model.addRow(new Object[]{
                        c.getCpf(),
                        c.getNome(),
                        c.getLocalizacao().getLatitude(),
                        c.getLocalizacao().getLongitude()
                });
            }

            labelMensagem.setText("");
        } catch (Exception e) {
            labelMensagem.setText(e.getMessage());
        }
    }

    private void criarCliente() {
        try {
            Fachada.criarCliente(
                    tfCpf.getText(),
                    tfNome.getText(),
                    Double.parseDouble(tfLat.getText()),
                    Double.parseDouble(tfLon.getText())
            );

            labelMensagem.setText("Cliente criado com sucesso!");
            listagem();
        } catch (Exception e) {
            labelMensagem.setText(e.getMessage());
        }
    }
}
