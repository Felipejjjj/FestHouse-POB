package modelo;

public class Convidado {
	//atributos
		private int id; //Atenção! depois ver como fazer este atributo seguir o modelo do professor id: sequencial (1,2,3,...) e autoincrementado
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
		
		//este construtor possivelmente vai ser apagado ou modificado após implementar a função do ID ser sequencial e autoincrementado
		public Convidado(int id, String nome, String senha, Evento evento) {
			this.id = id;
			this.nome = nome;
			this.senha = senha;
			this.evento = evento;
			
			evento.adicionarConvidado(this);
		}
		
		//tostring
		@Override
		public String toString() {
			return "id: " + this.id + " nome: " + this.nome + " senha: " + this.senha + " Evento: " + this.evento;
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
