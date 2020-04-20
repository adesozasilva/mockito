package br.com.adesozasilva.infra.dao;

import java.util.List;

import br.com.adesozasilva.domain.Filme;

public class FilmeDaoImpl implements FilmeDAO {

	public List<Filme> retornaTodosOsFilmes() {
		List<Filme> pagamentos = BancoDeDados.lerDoBanco("filmes");
		return pagamentos;
	}

	public void adicionaFilme(Filme filme) {
		List<Filme> pagamentos = BancoDeDados.lerDoBanco("filmes");
		pagamentos.add(filme);
		BancoDeDados.gravaNoBanco(pagamentos, "filmes");
		
	}


}


