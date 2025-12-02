package appswing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("FestHouse - Sistema");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menu = new JMenuBar();

        JMenu m1 = new JMenu("Cadastro");
        JMenuItem cliente = new JMenuItem("Cliente");
        JMenuItem evento = new JMenuItem("Evento");
        JMenuItem convidado = new JMenuItem("Convidado");

        cliente.addActionListener(e -> new TelaCliente());
        evento.addActionListener(e -> new TelaEvento());
        convidado.addActionListener(e -> new TelaConvidado());

        m1.add(cliente);
        m1.add(evento);
        m1.add(convidado);

        JMenu m2 = new JMenu("Consultas");
        JMenuItem consulta = new JMenuItem("Consultar");
        consulta.addActionListener(e -> new TelaConsulta());
        m2.add(consulta);

        menu.add(m1);
        menu.add(m2);

        setJMenuBar(menu);

        JLabel label = new JLabel("FestHouse", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        add(label);

        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaPrincipal();
    }
}
