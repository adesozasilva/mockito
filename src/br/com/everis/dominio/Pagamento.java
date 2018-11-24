package br.com.everis.dominio;

import java.io.Serializable;
import java.util.Calendar;

public class Pagamento implements Serializable {

	private String descricao;
	private double valor;
	private Calendar data;

	public Pagamento(String descricao, double valor, Calendar data) {
		this.descricao = descricao;
		this.valor = valor;
		this.data = data;
	}
	public double getValor() {
		return valor;
	}
	public Calendar getData() {
		return data;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
