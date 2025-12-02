package appswing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import requisito.Fachada;
import modelo.Cliente;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaCliente extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;

    private JTextField tfCpf, tfNome, tfLat, tfLon;

    public TelaCliente() {
        setTitle("Clientes");
        setSize(700, 500);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new Object[]{"CPF", "Nome", "Latitude", "Longitude"}, 0);
        tabela = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(5, 2));

        painel.add(new JLabel("CPF:"));
        tfCpf = new JTextField();
        painel.add(tfCpf);

        painel.add(new JLabel("Nome:"));
        tfNome = new JTextField();
        painel.add(tfNome);

        painel.add(new JLabel("Latitude:"));
        tfLat = new JTextField();
        painel.add(tfLat);

        painel.add(new JLabel("Longitude:"));
        tfLon = new JTextField();
        painel.add(tfLon);

        JButton btCriar = new JButton("Criar");
        JButton btAtualizar = new JButton("Atualizar Lista");

        painel.add(btCriar);
        painel.add(btAtualizar);

        add(painel, BorderLayout.SOUTH);

        atualizarTabela();

        btCriar.addActionListener(e -> criarCliente());
        btAtualizar.addActionListener(e -> atualizarTabela());

        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = tabela.getSelectedRow();
                if (i >= 0) {
                    tfCpf.setText(modelo.getValueAt(i, 0).toString());
                    tfNome.setText(modelo.getValueAt(i, 1).toString());
                    tfLat.setText(modelo.getValueAt(i, 2).toString());
                    tfLon.setText(modelo.getValueAt(i, 3).toString());
                }
            }
        });

        setVisible(true);
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        List<Cliente> lista = Fachada.listarClientes();

        for (Cliente c : lista) {
            modelo.addRow(new Object[]{
                    c.getCpf(), c.getNome(),
                    c.getLocalizacao().getLatitude(),
                    c.getLocalizacao().getLongitude()
            });
        }
    }

    private void criarCliente() {
        try {
            String cpf = tfCpf.getText();
            String nome = tfNome.getText();
            double lat = Double.parseDouble(tfLat.getText());
            double lon = Double.parseDouble(tfLon.getText());

            Fachada.criarCliente(cpf, nome, lat, lon);

            JOptionPane.showMessageDialog(this, "Cliente criado!");
            atualizarTabela();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
