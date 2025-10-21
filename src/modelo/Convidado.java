package modelo;

public class Convidado {
	//atributos
		private int id;
		private String nome;
		private String senha;
		private Evento evento;
		
		//construtor
		public Convidado(String nome, String senha, Evento evento) {
			this.nome = nome;
			this.senha = senha;
			this.evento = evento;
			
			evento.adicionarConvidado(this);
		}
		
		//tostring
		@Override
		public String toString() {
			return "id: " + this.id + " nome: " + this.nome + " senha: " + this.senha + " Evento: " + (this.evento != null ? this.evento.getNome() : "sem evento");
		}	
		
		//getters e setters
		public int getId() {
			return id;
		}
		
		public void setId(int id) {
			this.id = id;
		}
		
		public String getNome() {
			return nome;
		}
		
		public void setNome(String nome) {
			this.nome = nome;
		}
		
		public String getSenha() {
			return senha;
		}
		
		public void setSenha(String senha) {
			this.senha = senha;
		}
		
		public Evento getEvento() {
			return evento;
		}
		
		public void setEvento(Evento evento) {
			this.evento = evento;
		}
}
