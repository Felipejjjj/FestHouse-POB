package org.example.appswing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.example.requisito.Fachada;
import org.example.modelo.Evento;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaEvento {

    private JDialog frame;
    private JTable table;
    private DefaultTableModel model;

    private JTextField tfData, tfNomeEvento, tfCpfCliente;
    private JLabel labelMensagem;

    // LABEL PARA IMAGEM OU TEXTO
    private JLabel lblImagemEvento;

    public TelaEvento() {
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("Eventos");
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 20, 640, 150);
        frame.add(scrollPane);

        table = new JTable();
        model = new DefaultTableModel(new Object[]{"Data", "Nome", "CPF Cliente"}, 0);
        table.setModel(model);
        scrollPane.setViewportView(table);

        JLabel lblData = new JLabel("Data:");
        lblData.setBounds(20, 190, 80, 20);
        frame.add(lblData);

        tfData = new JTextField();
        tfData.setBounds(100, 190, 140, 20);
        frame.add(tfData);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 220, 80, 20);
        frame.add(lblNome);

        tfNomeEvento = new JTextField();
        tfNomeEvento.setBounds(100, 220, 140, 20);
        frame.add(tfNomeEvento);

        JLabel lblCpfCliente = new JLabel("CPF Cliente:");
        lblCpfCliente.setBounds(300, 190, 80, 20);
        frame.add(lblCpfCliente);

        tfCpfCliente = new JTextField();
        tfCpfCliente.setBounds(390, 190, 140, 20);
        frame.add(tfCpfCliente);

        JButton btCriar = new JButton("Criar Evento");
        btCriar.setBounds(550, 190, 120, 25);
        frame.add(btCriar);

        JButton btAtualizar = new JButton("Atualizar Lista");
        btAtualizar.setBounds(550, 220, 120, 25);
        frame.add(btAtualizar);

        labelMensagem = new JLabel("");
        labelMensagem.setForeground(Color.RED);
        labelMensagem.setBounds(20, 260, 400, 20);
        frame.add(labelMensagem);

        // ===============================
        // ÁREA DA IMAGEM / TEXTO
        // ===============================
        lblImagemEvento = new JLabel("Selecione um evento");
        lblImagemEvento.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagemEvento.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblImagemEvento.setBounds(20, 290, 640, 140);
        frame.add(lblImagemEvento);

        // ===============================
        // EVENTOS
        // ===============================
        frame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                listagem();
            }
        });

        btCriar.addActionListener(e -> criarEvento());
        btAtualizar.addActionListener(e -> listagem());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = table.getSelectedRow();
                if (i >= 0) {
                    tfData.setText(model.getValueAt(i, 0).toString());
                    tfNomeEvento.setText(model.getValueAt(i, 1).toString());
                    tfCpfCliente.setText(model.getValueAt(i, 2).toString());

                    // MOSTRAR IMAGEM DO EVENTO
                    String nomeEvento = model.getValueAt(i, 1).toString();
                    mostrarImagemEvento(nomeEvento);
                }
            }
        });

        frame.setVisible(true);
    }

    // ===============================
    // LISTAGEM
    // ===============================
    private void listagem() {
        try {
            model.setRowCount(0);
            List<Evento> lista = Fachada.listarEventos();

            for (Evento e : lista) {
                model.addRow(new Object[]{
                        e.getData(),
                        e.getNome(),
                        e.getCliente().getCpf()
                });
            }

            labelMensagem.setText("");
            lblImagemEvento.setIcon(null);
            lblImagemEvento.setText("Selecione um evento");

        } catch (Exception e) {
            labelMensagem.setText(e.getMessage());
        }
    }

    // ===============================
    // CRIAR EVENTO
    // ===============================
    private void criarEvento() {
        try {
            Fachada.criarEvento(
                    tfData.getText(),
                    tfNomeEvento.getText(),
                    tfCpfCliente.getText()
            );

            labelMensagem.setText("Evento criado com sucesso!");
            listagem();

        } catch (Exception e) {
            labelMensagem.setText(e.getMessage());
        }
    }

    // ===============================
    // MOSTRAR IMAGEM DO EVENTO
    // ===============================
    private void mostrarImagemEvento(String nomeEvento) {
        try {
            Evento evento = Fachada.buscarEvento(nomeEvento);

            if (evento.getFoto() != null) {
                ImageIcon icon = new ImageIcon(evento.getFoto());

                Image img = icon.getImage()
                        .getScaledInstance(300, 120, Image.SCALE_SMOOTH);

                lblImagemEvento.setIcon(new ImageIcon(img));
                lblImagemEvento.setText("");
            } else {
                lblImagemEvento.setIcon(null);
                lblImagemEvento.setText("Evento não possui imagem");
            }

        } catch (Exception e) {
            lblImagemEvento.setIcon(null);
            lblImagemEvento.setText("Erro ao carregar imagem: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}
