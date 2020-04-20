package br.com.adesozasilva.service;

import java.util.List;

import br.com.adesozasilva.domain.Filme;
import br.com.adesozasilva.infra.dao.FilmeDAO;

public class FilmeService {
	
	private FilmeDAO filmeDAO;
	
	public FilmeService(FilmeDAO filmeDAO) {
	   this.filmeDAO = filmeDAO;
	}
	
	
	public List<Filme> retornaTodosOsFilmes() {
		return filmeDAO.retornaTodosOsFilmes();
		
	}
	
	public void adicionaFilme(Filme filme) {
		filmeDAO.adicionaFilme(filme);
		
	}

}
