package org.example.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Localizacao20241370007")
public class Localizacao {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLocalizacao;

    @Column(nullable = false)
	private double latitude;

    @Column(nullable = false)
    private double longitude;

    @OneToOne(mappedBy = "localizacao")
    private Cliente cliente;

    //construtores
    public Localizacao() {}

    public Localizacao(Cliente cliente, double latitude, double longitude) {
        this.cliente = cliente;
		this.latitude = latitude;
		this.longitude = longitude;
	}
    
	public Localizacao(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

    //getters e setters
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

    //toString
	@Override
	public String toString() {
		return "Localizacao [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
}
