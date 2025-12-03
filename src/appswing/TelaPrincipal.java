package appswing;

import javax.swing.*;
import java.awt.event.*;

public class TelaPrincipal {

    private JFrame frame;

    public TelaPrincipal() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("FestHouse - Sistema");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu menuCadastro = new JMenu("Cadastros");
        menuBar.add(menuCadastro);

        JMenuItem menuCliente = new JMenuItem("Cliente");
        menuCliente.addActionListener(e -> new TelaCliente());
        menuCadastro.add(menuCliente);

        JMenuItem menuEvento = new JMenuItem("Evento");
        menuEvento.addActionListener(e -> new TelaEvento());
        menuCadastro.add(menuEvento);

        JMenuItem menuConvidado = new JMenuItem("Convidado");
        menuConvidado.addActionListener(e -> new TelaConvidado());
        menuCadastro.add(menuConvidado);

        JMenu menuConsulta = new JMenu("Consultas");
        menuBar.add(menuConsulta);

        JMenuItem menuConsultaGeral = new JMenuItem("Consultar");
        menuConsultaGeral.addActionListener(e -> new TelaConsulta());
        menuConsulta.add(menuConsultaGeral);

        JLabel titulo = new JLabel("FestHouse", SwingConstants.CENTER);
        titulo.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 36));
        frame.add(titulo);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new TelaPrincipal();
    }
}
