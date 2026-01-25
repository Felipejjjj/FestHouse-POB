package org.example.requisito;

import java.util.List;
import java.util.Random;

import org.example.modelo.Cliente;
import org.example.modelo.Convidado;
import org.example.modelo.Evento;
import org.example.modelo.Localizacao;
import org.example.repositorio.ClienteRepositorio;
import org.example.repositorio.ConvidadoRepositorio;
import org.example.repositorio.EventoRepositorio;

public class Fachada {
    private static ClienteRepositorio clienteRep = new ClienteRepositorio();
    private static EventoRepositorio eventoRep = new EventoRepositorio();
    private static ConvidadoRepositorio convidadoRep = new ConvidadoRepositorio();

    // -=-=-=-=--=-=-=-=- CLIENTE -=-=-=-=--=-=-=-=-
    public static void criarCliente(String cpf, String nome, double lat, double lon) throws Exception {
        clienteRep.conectar();
        clienteRep.begin();

        try {
            // Validação simples de campos
            if (nome == null || nome.isEmpty()) {
                throw new Exception("Nome não pode ser vazio.");
            }
            if (cpf == null || cpf.isEmpty()) {
                throw new Exception("CPF não pode ser vazio.");
            }

            // Regra: Unicidade de Cliente (CPF)
            Cliente clienteExistente = clienteRep.ler(cpf);
            if (clienteExistente != null) {
                throw new Exception("Cliente com CPF " + cpf + " já existe!");
            }

            // Criar Localização e Cliente
            Localizacao loc = new Localizacao(lat, lon);
            Cliente novoCliente = new Cliente(cpf, nome, loc);
            novoCliente.setLocalizacao(loc);
            //loc.setCliente(novoCliente); //ver isso depois

            clienteRep.criar(novoCliente);
            clienteRep.commit();
        } catch (Exception e) {
            clienteRep.rollback();
            throw e;
        } finally {
            clienteRep.desconectar();
        }
    }

    public static List<Cliente> listarClientes() {
        clienteRep.conectar();
        List<Cliente> lista = clienteRep.listar();
        clienteRep.desconectar();
        return lista;
    }

    // -=-=-=-=--=-=-=-=- EVENTO -=-=-=-=--=-=-=-=-
    public static void criarEvento(String data, String nomeEvento, String cpfCliente) throws Exception {
        eventoRep.conectar();
        eventoRep.begin();

        try {
            // Validação de campos
            if (nomeEvento == null || nomeEvento.isEmpty()) {
                throw new Exception("Nome do evento não pode ser vazio.");
            }

            // Regra: Unicidade de Evento (Nome)
            Evento eventoExistente = eventoRep.ler(nomeEvento);
            if (eventoExistente != null) {
                throw new Exception("Evento com nome '" + nomeEvento + "' já existe!");
            }

            // Buscar Cliente dono do evento
            Cliente cliente = clienteRep.ler(cpfCliente);
            if (cliente == null) {
                throw new Exception("Cliente com CPF " + cpfCliente + " não encontrado.");
            }

            // Criar e salvar Evento
            // O construtor do evento já adiciona ele na lista de eventos do cliente
            Evento novoEvento = new Evento(data, nomeEvento, cliente);

            eventoRep.criar(novoEvento);
            // Necessário atualizar o cliente pois a lista de eventos dele mudou
            clienteRep.atualizar(cliente);

            eventoRep.commit();
        } catch (Exception e) {
            eventoRep.rollback();
            throw e;
        } finally {
            eventoRep.desconectar();
        }
    }
   
    public static void alterarDataEvento(String nomeEvento, String novaData) throws Exception {
        eventoRep.conectar();
        eventoRep.begin();
        try {
            Evento evento = eventoRep.ler(nomeEvento);
            if (evento == null) {
                throw new Exception("Evento '" + nomeEvento + "' não encontrado.");
            }

            evento.setData(novaData);
            eventoRep.atualizar(evento);

            eventoRep.commit();
        } catch (Exception e) {
            eventoRep.rollback();
            throw e;
        } finally {
            eventoRep.desconectar();
        }
    }

    /* REMOVI ESTA ALTERAÇÃO
    public static void alterarNomeEvento(String nomeAtual, String novoNome) throws Exception {
        eventoRep.conectar();
        eventoRep.begin();
        try {
            Evento evento = eventoRep.ler(nomeAtual);
            if (evento == null) {
                throw new Exception("Evento '" + nomeAtual + "' não encontrado.");
            }

            // verifica unicidade
            if (eventoRep.ler(novoNome) != null) {
                throw new Exception("Já existe um evento com o nome '" + novoNome + "'.");
            }

            evento.setNome(novoNome);
            eventoRep.atualizar(evento);

            eventoRep.commit();
        } catch (Exception e) {
            eventoRep.rollback();
            throw e;
        } finally {
            eventoRep.desconectar();
        }
    }
    */


    public static List<Evento> listarEventos() {
        eventoRep.conectar();
        List<Evento> lista = eventoRep.listar();
        eventoRep.desconectar();
        return lista;
    }

    // -=-=-=-=-=-=-=-=- CONVIDADO -=-=-=-=--=-=-=-=-
    public static void criarConvidado(String nomeConvidado, String nomeEvento) throws Exception {
        convidadoRep.conectar();
        convidadoRep.begin();

        try {
            // Buscar Evento
            Evento evento = eventoRep.ler(nomeEvento);
            if (evento == null) {
                throw new Exception("Evento '" + nomeEvento + "' não encontrado.");
            }

            // Regra: Senha Aleatória e Única no Evento
            // Formato: data + numero (1 a 9999)
            Random random = new Random();
            String senhaGerada = "";
            boolean senhaUnica = false;

            // Loop pra garantir que a senha seja única
            while (!senhaUnica) {
                int numero = random.nextInt(9999) + 1; // Gera 1 a 9999
                senhaGerada = evento.getData() + "-" + numero;

                senhaUnica = true;
                for (Convidado c : evento.getListaConvidados()) {
                    if (c.getSenha().equals(senhaGerada)) {
                        senhaUnica = false;
                        break;
                    }
                }
            }

            // Criar Convidado
            // o construtor do convidado já adiciona ele na lista de convidados do evento
            Convidado novoConvidado = new Convidado(nomeConvidado, senhaGerada, evento);

            // Persistir
            convidadoRep.criar(novoConvidado);
            eventoRep.atualizar(evento);

            convidadoRep.commit();
        } catch (Exception e) {
            convidadoRep.rollback();
            throw e;
        } finally {
            convidadoRep.desconectar();
        }
    }

    public static List<Convidado> listarConvidados() {
        convidadoRep.conectar();
        List<Convidado> lista = convidadoRep.listar();
        convidadoRep.desconectar();
        return lista;
    }

    public static void alterarNomeConvidado(int idConvidado, String novoNome) throws Exception {
        convidadoRep.conectar();
        convidadoRep.begin();
        try {
            Convidado convidado = convidadoRep.ler(idConvidado);
            if (convidado == null) {
                throw new Exception("Convidado com ID " + idConvidado + " não encontrado.");
            }

            convidado.setNome(novoNome);
            convidadoRep.atualizar(convidado);

            convidadoRep.commit();
        } catch (Exception e) {
            convidadoRep.rollback();
            throw e;
        } finally {
            convidadoRep.desconectar();
        }
    }

    public static void removerConvidadoDoEvento(String nomeEvento, int idConvidado) throws Exception {
        eventoRep.conectar();
        eventoRep.begin();

        try {
            Evento evento = eventoRep.ler(nomeEvento);
            if (evento == null) {
                throw new Exception("Evento '" + nomeEvento + "' não encontrado.");
            }

            Convidado convidado = convidadoRep.ler(idConvidado);
            if (convidado == null) {
                throw new Exception("Convidado com ID " + idConvidado + " não encontrado.");
            }

            if (!evento.getListaConvidados().contains(convidado)) {
                throw new Exception("Esse convidado não pertence ao evento informado.");
            }

            evento.removerConvidado(convidado);
            eventoRep.atualizar(evento);

            convidadoRep.apagar(convidado);

            eventoRep.commit();
        } catch (Exception e) {
            eventoRep.rollback();
            throw e;
        } finally {
            eventoRep.desconectar();
        }
    }
    
    public static void apagarConvidado(int idConvidado) throws Exception {
        convidadoRep.conectar();
        convidadoRep.begin();

        try {
            Convidado convidado = convidadoRep.ler(idConvidado);
            if (convidado == null) {
                throw new Exception("Convidado com ID " + idConvidado + " não existe.");
            }

            Evento evento = convidado.getEvento();

            if (evento != null) {
                evento.removerConvidado(convidado);
                convidadoRep.apagar(convidado);
                eventoRep.atualizar(evento);
            } else {
                // Caso orfão (apenas por segurança)
                convidadoRep.apagar(convidado);
            }

            convidadoRep.commit();
        } catch (Exception e) {
            convidadoRep.rollback();
            throw e;
        } finally {
            convidadoRep.desconectar();
        }
    }
    
    /**********************************************
     * * CONSULTAS IMPLEMENTADAS NOS REPOSITORIOS DE CADA CLASSE
     **********************************************/

    // -------- CLIENTES --------
    public static List<Cliente> consultarClientesPorNome(String parteNome) {
        clienteRep.conectar();
        List<Cliente> result = clienteRep.lerPorNome(parteNome);
        clienteRep.desconectar();
        return result;
    }

    // -------- EVENTOS --------
    public static List<Evento> consultarEventosPorNome(String parteNome) {
        eventoRep.conectar();
        List<Evento> result = eventoRep.lerPorNome(parteNome);
        eventoRep.desconectar();
        return result;
    }

    public static List<Evento> consultarEventosPorData(String data) {
        eventoRep.conectar();
        List<Evento> result = eventoRep.lerPorData(data);
        eventoRep.desconectar();
        return result;
    }

    public static List<Evento> consultarEventosComMaisDeNConvidados(int n) {
        eventoRep.conectar();
        List<Evento> result = eventoRep.lerMaisDeNConvidados(n);
        eventoRep.desconectar();
        return result;
    }

    // -------- CONVIDADOS --------
    public static List<Convidado> consultarConvidadosPorNome(String parteNome) {
        convidadoRep.conectar();
        List<Convidado> result = convidadoRep.lerPorNome(parteNome);
        convidadoRep.desconectar();
        return result;
    }

    public static List<Convidado> consultarConvidadosDeEvento(String nomeEvento) {
        convidadoRep.conectar();
        List<Convidado> result = convidadoRep.lerPorEvento(nomeEvento);
        convidadoRep.desconectar();
        return result;
    }

}
