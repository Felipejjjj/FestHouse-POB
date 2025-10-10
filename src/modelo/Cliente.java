package modelo;

import java.util.ArrayList;

public class Cliente {
	//atributos
		private String cpf;
		private String nome;
		private ArrayList<Evento> eventos = new ArrayList<Evento>();
		private Localizacao localizacao;
		
		//construtores
		public Cliente(String cpf, String nome, Localizacao localizacao) {
			this.cpf = cpf;
			this.nome = nome;
			this.localizacao = localizacao;
		}
		
		//tostring
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
		
		public ArrayList<Evento> getEventos() {
			return this.eventos;
		}
		
		//métodos gerais
		public void adicionarEvento(Evento evento) {
			this.eventos.add(evento);
		}
		
		public void removerEvento(Evento evento) {
			this.eventos.remove(evento);
		}

}
