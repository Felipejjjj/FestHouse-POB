package org.example.modelo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "Evento20241370007")
@Entity
public class Evento {
	//atributos
	@Id
	private String nome;

	@Column(nullable = false)
	private String data;

	@ManyToOne
	private Cliente cliente;

	@OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Convidado> listaConvidados = new ArrayList<>();

	//construtores
	public Evento() {}

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

	public List<Convidado> getListaConvidados() {
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
		convidado.setEvento(this);
	}

	public void removerConvidado(Convidado convidado) throws Exception {
		this.listaConvidados.remove(convidado);
		convidado.setEvento(null);
		//Util.apagarObjeto(convidado);
		//Fachada.apagarConvidado(convidado.getId());
	}

	public void removerConvidado(int id) throws Exception{
		this.removerConvidado(this.getConvidado(id));
	}

	//toString - ATENÇÃO: REMOVI A LÓGICA DELE BUSCAR A LISTA, POIS ISSO TAVA DANDO CONFLITO NO HIBERNATE
//	@Override
//	public String toString() {
//		ArrayList<String> nomesConvidados = new ArrayList<String>();
//		for(Convidado c : this.getListaConvidados()) {
//			nomesConvidados.add(c.getNome());
//		}
//
//		return "Evento [data=" + data + ", nome=" + nome + ", cliente=" + cliente.getNome() + " Convidados: " + nomesConvidados + "]";
//	}

	@Override
	public String toString() {

		return "Evento [data=" + data + ", nome=" + nome + ", cliente=" + cliente.getNome() + "]";
	}
}