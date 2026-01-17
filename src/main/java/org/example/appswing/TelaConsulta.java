package org.example.appswing;

import javax.swing.*;
import requisito.Fachada;

import modelo.Evento;
import modelo.Cliente;
import modelo.Convidado;

import java.awt.event.*;
import java.util.List;

public class TelaConsulta {

    private JDialog frame;
    private JTextField tfFiltro;
    private JTextArea taResultado;

    public TelaConsulta() {
        initialize();
    }

    private void initialize() {

        frame = new JDialog();
        frame.setTitle("Consultas");
        frame.setModal(true);
        frame.setSize(750, 520);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JLabel lblFiltro = new JLabel("Filtro:");
        lblFiltro.setBounds(20, 20, 60, 25);
        frame.add(lblFiltro);

        tfFiltro = new JTextField();
        tfFiltro.setBounds(80, 20, 220, 25);
        frame.add(tfFiltro);

        JButton btCliente = new JButton("Clientes por Nome");
        btCliente.setBounds(320, 20, 160, 25);
        frame.add(btCliente);

       
        JButton btEventoNome = new JButton("Eventos por Nome");
        btEventoNome.setBounds(20, 65, 160, 25);
        frame.add(btEventoNome);

        JButton btEventoData = new JButton("Eventos por Data");
        btEventoData.setBounds(200, 65, 160, 25);
        frame.add(btEventoData);

        JButton btConvidadoNome = new JButton("Convidados por Nome");
        btConvidadoNome.setBounds(380, 65, 170, 25);
        frame.add(btConvidadoNome);

        JButton btConvidadoEvento = new JButton("Convidados de Evento");
        btConvidadoEvento.setBounds(570, 65, 160, 25);
        frame.add(btConvidadoEvento);

       

        JButton btEventoN = new JButton("Eventos com mais de N convidados");
        btEventoN.setBounds(20, 105, 300, 25);
        frame.add(btEventoN);

       

        taResultado = new JTextArea();
        taResultado.setEditable(false);

        JScrollPane scroll = new JScrollPane(taResultado);
        scroll.setBounds(20, 155, 710, 300);
        frame.add(scroll);



        btCliente.addActionListener(e -> consultarClientes());
        btEventoNome.addActionListener(e -> consultarEventosNome());
        btEventoData.addActionListener(e -> consultarEventosData());
        btConvidadoNome.addActionListener(e -> consultarConvidadosNome());
        btConvidadoEvento.addActionListener(e -> consultarConvidadosEvento());
        btEventoN.addActionListener(e -> consultarEventosN());

        frame.setVisible(true);
    }

    private void limpar() {
        taResultado.setText("");
    }

    private void consultarClientes() {
        limpar();
        List<Cliente> lista = Fachada.consultarClientesPorNome(tfFiltro.getText());
        lista.forEach(c -> taResultado.append(c + "\n"));
    }

    private void consultarEventosNome() {
        limpar();
        List<Evento> lista = Fachada.consultarEventosPorNome(tfFiltro.getText());
        lista.forEach(e -> taResultado.append(e + "\n"));
    }

    private void consultarEventosData() {
        limpar();
        List<Evento> lista = Fachada.consultarEventosPorData(tfFiltro.getText());
        lista.forEach(e -> taResultado.append(e + "\n"));
    }

    private void consultarConvidadosNome() {
        limpar();
        List<Convidado> lista = Fachada.consultarConvidadosPorNome(tfFiltro.getText());
        lista.forEach(c -> taResultado.append(c + "\n"));
    }

    private void consultarConvidadosEvento() {
        limpar();
        List<Convidado> lista = Fachada.consultarConvidadosDeEvento(tfFiltro.getText());
        lista.forEach(c -> taResultado.append(c + "\n"));
    }

    private void consultarEventosN() {
        limpar();
        try {
            int n = Integer.parseInt(tfFiltro.getText());
            List<Evento> lista = Fachada.consultarEventosComMaisDeNConvidados(n);
            lista.forEach(e -> taResultado.append(e + "\n"));
        } catch (Exception e) {
            taResultado.setText("Digite um número válido!");
        }
    }
}
