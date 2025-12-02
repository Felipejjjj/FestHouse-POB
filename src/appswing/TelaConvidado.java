package appswing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import requisito.Fachada;
import modelo.Convidado;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaConvidado extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;

    private JTextField tfNome, tfEvento, tfIdRemover;

    public TelaConvidado() {
        setTitle("Convidados");
        setSize(700, 500);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new Object[]{"ID", "Nome", "Senha", "Evento"}, 0);
        tabela = new JTable(modelo);

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painel = new JPanel(new GridLayout(4, 2));

        painel.add(new JLabel("Nome:"));
        tfNome = new JTextField();
        painel.add(tfNome);

        painel.add(new JLabel("Evento:"));
        tfEvento = new JTextField();
        painel.add(tfEvento);

        painel.add(new JLabel("ID para remover:"));
        tfIdRemover = new JTextField();
        painel.add(tfIdRemover);

        JButton btCriar = new JButton("Criar");
        JButton btApagar = new JButton("Apagar");

        painel.add(btCriar);
        painel.add(btApagar);

        add(painel, BorderLayout.SOUTH);

        atualizarTabela();

        btCriar.addActionListener(e -> criarConvidado());
        btApagar.addActionListener(e -> removerConvidado());

        setVisible(true);
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        List<Convidado> lista = Fachada.listarConvidados();

        for (Convidado c : lista) {
            modelo.addRow(new Object[]{
                    c.getId(), c.getNome(), c.getSenha(), c.getEvento().getNome()
            });
        }
    }

    private void criarConvidado() {
        try {
            Fachada.criarConvidado(tfNome.getText(), tfEvento.getText());
            JOptionPane.showMessageDialog(this, "Convidado criado!");
            atualizarTabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void removerConvidado() {
        try {
            int id = Integer.parseInt(tfIdRemover.getText());
            Fachada.apagarConvidado(id);
            JOptionPane.showMessageDialog(this, "Convidado removido!");
            atualizarTabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
