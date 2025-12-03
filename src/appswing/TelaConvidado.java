package appswing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import requisito.Fachada;
import modelo.Convidado;

import java.awt.event.*;
import java.util.List;

public class TelaConvidado {

    private JDialog frame;
    private JTable table;
    private DefaultTableModel model;

    private JTextField tfNome, tfEvento, tfIdRemover;
    private JLabel labelMensagem;

    public TelaConvidado() {
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("Convidados");
        frame.setSize(700, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 20, 640, 150);
        frame.add(scrollPane);

        table = new JTable();
        model = new DefaultTableModel(new Object[]{"ID", "Nome", "Senha", "Evento"}, 0);
        table.setModel(model);
        scrollPane.setViewportView(table);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 190, 60, 20);
        frame.add(lblNome);

        tfNome = new JTextField();
        tfNome.setBounds(90, 190, 150, 20);
        frame.add(tfNome);

        JLabel lblEvento = new JLabel("Evento:");
        lblEvento.setBounds(260, 190, 60, 20);
        frame.add(lblEvento);

        tfEvento = new JTextField();
        tfEvento.setBounds(330, 190, 150, 20);
        frame.add(tfEvento);

        JLabel lblId = new JLabel("ID p/ remover:");
        lblId.setBounds(20, 220, 100, 20);
        frame.add(lblId);

        tfIdRemover = new JTextField();
        tfIdRemover.setBounds(130, 220, 110, 20);
        frame.add(tfIdRemover);

        JButton btCriar = new JButton("Criar");
        btCriar.setBounds(520, 190, 100, 25);
        frame.add(btCriar);

        JButton btApagar = new JButton("Apagar");
        btApagar.setBounds(520, 220, 100, 25);
        frame.add(btApagar);

        labelMensagem = new JLabel("");
        labelMensagem.setForeground(java.awt.Color.RED);
        labelMensagem.setBounds(20, 260, 400, 20);
        frame.add(labelMensagem);

        frame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                listagem();
            }
        });

        btCriar.addActionListener(e -> criarConvidado());
        btApagar.addActionListener(e -> apagarConvidado());
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = table.getSelectedRow();
                if (i >= 0) {
                    tfIdRemover.setText(model.getValueAt(i, 0).toString());
                    tfNome.setText(model.getValueAt(i, 1).toString());
                    tfEvento.setText(model.getValueAt(i, 3).toString());
                }
            }
        });

        frame.setVisible(true);
    }

    private void listagem() {
        try {
            model.setRowCount(0);
            List<Convidado> lista = Fachada.listarConvidados();

            for (Convidado c : lista) {
                model.addRow(new Object[]{
                        c.getId(),
                        c.getNome(),
                        c.getSenha(),
                        c.getEvento().getNome()
                });
            }

            labelMensagem.setText("");
        } catch (Exception e) {
            labelMensagem.setText(e.getMessage());
        }
    }

    private void criarConvidado() {
        try {
            Fachada.criarConvidado(tfNome.getText(), tfEvento.getText());
            labelMensagem.setText("Convidado criado!");
            listagem();
        } catch (Exception e) {
            labelMensagem.setText(e.getMessage());
        }
    }

    private void apagarConvidado() {
        try {
            int id = Integer.parseInt(tfIdRemover.getText());
            Fachada.apagarConvidado(id);
            labelMensagem.setText("Convidado removido!");
            listagem();
        } catch (Exception e) {
            labelMensagem.setText(e.getMessage());
        }
    }
}
