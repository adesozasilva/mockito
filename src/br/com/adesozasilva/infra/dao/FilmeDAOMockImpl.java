package br.com.adesozasilva.infra.dao;

import java.util.List;

import br.com.adesozasilva.domain.Filme;

public class FilmeDAOMockImpl implements FilmeDAO {


	public List<Filme> retornaTodosOsFilmes() {
		List<Filme> filmes = BancoDeDados.lerDoBanco("filmes-mock");
		return filmes;

	}

	public void adicionaFilme(Filme filme) {
		List<Filme> filmes = BancoDeDados.lerDoBanco("filmes-mock");
		filmes.add(filme);
		BancoDeDados.gravaNoBanco(filmes, "filmes-mock");
		
	}
}


