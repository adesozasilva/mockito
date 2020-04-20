package br.com.adesozasilva.infra.dao;

import java.util.List;

import br.com.adesozasilva.domain.Filme;

public interface FilmeDAO {

	List<Filme> retornaTodosOsFilmes();
	void adicionaFilme(Filme filme);

}
