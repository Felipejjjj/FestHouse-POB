package appswing;

import javax.swing.*;
import requisito.Fachada;
import modelo.Evento;
import modelo.Cliente;
import modelo.Convidado;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaConsulta extends JFrame {

    private JTextField tfFiltro;
    private JTextArea taResultado;

    public TelaConsulta() {
        setTitle("Consultas");
        setSize(700, 500);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(6, 1));

        tfFiltro = new JTextField();
        painel.add(new JLabel("Filtro:"));
        painel.add(tfFiltro);

        JButton btCliente = new JButton("Clientes por Nome");
        JButton btEventoNome = new JButton("Eventos por Nome");
        JButton btEventoData = new JButton("Eventos por Data");
        JButton btConvidadoNome = new JButton("Convidados por Nome");
        JButton btConvidadoEvento = new JButton("Convidados de Evento");
        JButton btEventosN = new JButton("Eventos com mais de N convidados");

        painel.add(btCliente);
        painel.add(btEventoNome);
        painel.add(btEventoData);
        painel.add(btConvidadoNome);
        painel.add(btConvidadoEvento);
        painel.add(btEventosN);

        add(painel, BorderLayout.NORTH);

        taResultado = new JTextArea();
        add(new JScrollPane(taResultado), BorderLayout.CENTER);

        btCliente.addActionListener(e -> listarClientes());
        btEventoNome.addActionListener(e -> listarEventosPorNome());
        btEventoData.addActionListener(e -> listarEventosPorData());
        btConvidadoNome.addActionListener(e -> listarConvidadosPorNome());
        btConvidadoEvento.addActionListener(e -> listarConvidadosDeEvento());
        btEventosN.addActionListener(e -> listarEventosNConvidados());

        setVisible(true);
    }

    private void listarClientes() {
        taResultado.setText("");
        List<Cliente> lista = Fachada.consultarClientesPorNome(tfFiltro.getText());
        lista.forEach(c -> taResultado.append(c + "\n"));
    }

    private void listarEventosPorNome() {
        taResultado.setText("");
        List<Evento> lista = Fachada.consultarEventosPorNome(tfFiltro.getText());
        lista.forEach(e -> taResultado.append(e + "\n"));
    }

    private void listarEventosPorData() {
        taResultado.setText("");
        List<Evento> lista = Fachada.consultarEventosPorData(tfFiltro.getText());
        lista.forEach(e -> taResultado.append(e + "\n"));
    }

    private void listarConvidadosPorNome() {
        taResultado.setText("");
        List<Convidado> lista = Fachada.consultarConvidadosPorNome(tfFiltro.getText());
        lista.forEach(c -> taResultado.append(c + "\n"));
    }

    private void listarConvidadosDeEvento() {
        taResultado.setText("");
        List<Convidado> lista = Fachada.consultarConvidadosDeEvento(tfFiltro.getText());
        lista.forEach(c -> taResultado.append(c + "\n"));
    }

    private void listarEventosNConvidados() {
        taResultado.setText("");
        try {
            int n = Integer.parseInt(tfFiltro.getText());
            List<Evento> lista = Fachada.consultarEventosComMaisDeNConvidados(n);
            lista.forEach(e -> taResultado.append(e + "\n"));
        } catch (Exception e) {
            taResultado.setText("Digite um número válido!");
        }
    }
}
