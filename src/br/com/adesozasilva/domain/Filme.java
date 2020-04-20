package br.com.adesozasilva.domain;

import java.io.Serializable;
import java.util.Calendar;

public class Filme implements Serializable {

	private String titulo;
	private String descricao;
	private Calendar dataDeLancamento;

	public Filme(String titulo, String descricao, Calendar dataDeLancamento) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.dataDeLancamento = dataDeLancamento;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Calendar getDataDeLancamento() {
		return dataDeLancamento;
	}

	public void setDataDeLancamento(Calendar dataDeLancamento) {
		this.dataDeLancamento = dataDeLancamento;
	}
}
