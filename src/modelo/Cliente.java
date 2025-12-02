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
			ArrayList<String> nomesEventos = new ArrayList<String>();
			for(Evento e : this.getEventos()) {
				nomesEventos.add(e.getNome());
			}
			
			return "CPF: " + this.cpf + " nome: " + this.nome + " Localização: " + this.localizacao + " Eventos: " + nomesEventos;
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
	    }
		
		//métodos gerais
		public void adicionarEvento(Evento evento) {
			this.eventos.add(evento);
		}
		
		public void removerEvento(Evento evento) {
			this.eventos.remove(evento);
		}
}
