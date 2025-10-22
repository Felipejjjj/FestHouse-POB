package modelo;

import java.util.ArrayList;

import repositorio.Util;

public class Evento {
	//atributos
	private String data;
	private String nome;
	private Cliente cliente;
	private ArrayList<Convidado> listaConvidados = new ArrayList<Convidado>();
	
	//construtor
	public Evento(String data, String nome, Cliente cliente) {
		this.data = data;
		this.nome = nome;
		this.cliente = cliente;

		cliente.adicionarEvento(this);
	}

	//getters e setters
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ArrayList<Convidado> getListaConvidados() {
		return listaConvidados;
	}

	public Convidado getConvidado(int id) throws Exception{			
		for (Convidado c : listaConvidados) {
			if (c.getId() == id) {
				return c;
			}
		}

		throw new Exception("Convidado não encontrado");
	}

	public Convidado getConvidado(String nome) throws Exception{			
		for (Convidado c : listaConvidados) {
			if (c.getNome().equals(nome)) {
				return c;
			}
		}

		throw new Exception("Convidado não encontrado");
	}

	public void adicionarConvidado(Convidado convidado) {
		this.listaConvidados.add(convidado);
	}
	
	public void removerConvidado(Convidado convidado) {		
		this.listaConvidados.remove(convidado);
		Util.apagarObjeto(convidado);
	}

	public void removerConvidado(int id) throws Exception{
		this.removerConvidado(this.getConvidado(id));
	}

	//toString
	@Override
	public String toString() {
		ArrayList<String> nomesConvidados = new ArrayList<String>();
		for(Convidado c : this.getListaConvidados()) {
			nomesConvidados.add(c.getNome());
		}
		
		return "Evento [data=" + data + ", nome=" + nome + ", cliente=" + cliente.getNome() + " Convidados: " + nomesConvidados + "]";
	}
}
