package org.example.modelo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "Cliente20241370018")
@Entity
public class Cliente {
    //atributos
    @Id
    private String cpf;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "cliente", orphanRemoval = true)
    private List<Evento> eventos = new ArrayList<Evento>();

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(referencedColumnName = "idLocalizacao")
    private Localizacao localizacao;

    //construtores
    public Cliente() {}

    public Cliente(String cpf, String nome, Localizacao localizacao) {
        this.cpf = cpf;
        this.nome = nome;
        this.localizacao = localizacao;
    }

    //tostring ATENÇÃO: REMOVI A LÓGICA DELE BUSCAR A LISTA, POIS ISSO TAVA DANDO CONFLITO NO HIBERNATE
//    @Override
//    public String toString() {
//        ArrayList<String> nomesEventos = new ArrayList<String>();
//        for(Evento e : this.getEventos()) {
//            nomesEventos.add(e.getNome());
//        }
//
//        return "CPF: " + this.cpf + " nome: " + this.nome + " Localização: " + this.localizacao + " Eventos: " + nomesEventos;
//    }

    @Override
    public String toString() {

        return "CPF: " + this.cpf + " nome: " + this.nome + " Localização: " + this.localizacao;
    }

    //getters e setters
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Evento> getEventos() {
        return this.eventos;
    }

    public Evento getEvento(int index) throws Exception{
        try {
            Evento eventoEncontrado = this.eventos.get(index);
            return eventoEncontrado;
        }
        catch(Exception ex) {
            throw new Exception("Evento não encontrado");
        }
    }

    public Evento getEvento(String nome) throws Exception{
        for (Evento e : eventos) {
            if (e.getNome().equals(nome)) {
                return e;
            }
        }

        throw new Exception("Evento não encontrado");
    }

    public Localizacao getLocalizacao() {
        return this.localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
        if (localizacao != null) {
            localizacao.setCliente(this);
        }
    }

    //métodos gerais
    public void adicionarEvento(Evento evento) {
        this.eventos.add(evento);
    }

    public void removerEvento(Evento evento) {
        this.eventos.remove(evento);
    }
}