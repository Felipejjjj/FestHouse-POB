package appswing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import requisito.Fachada;
import modelo.Evento;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaEvento extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;

    private JTextField tfData, tfNomeEvento, tfCpfCliente;

    public TelaEvento() {
        setTitle("Eventos");
        setSize(700, 500);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new Object[]{"Data", "Nome", "CPF do Cliente"}, 0);
        tabela = new JTable(modelo);

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painel = new JPanel(new GridLayout(4, 2));

        painel.add(new JLabel("Data:"));
        tfData = new JTextField();
        painel.add(tfData);

        painel.add(new JLabel("Nome:"));
        tfNomeEvento = new JTextField();
        painel.add(tfNomeEvento);

        painel.add(new JLabel("CPF do Cliente:"));
        tfCpfCliente = new JTextField();
        painel.add(tfCpfCliente);

        JButton btCriar = new JButton("Criar");
        JButton btAtualizar = new JButton("Atualizar Lista");

        painel.add(btCriar);
        painel.add(btAtualizar);

        add(painel, BorderLayout.SOUTH);

        atualizarTabela();

        btCriar.addActionListener(e -> criarEvento());
        btAtualizar.addActionListener(e -> atualizarTabela());

        setVisible(true);
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        List<Evento> lista = Fachada.listarEventos();

        for (Evento e : lista) {
            modelo.addRow(new Object[]{
                    e.getData(),
                    e.getNome(),
                    e.getCliente().getCpf()
            });
        }
    }

    private void criarEvento() {
        try {
            Fachada.criarEvento(
                    tfData.getText(),
                    tfNomeEvento.getText(),
                    tfCpfCliente.getText()
            );

            JOptionPane.showMessageDialog(this, "Evento criado!");
            atualizarTabela();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
