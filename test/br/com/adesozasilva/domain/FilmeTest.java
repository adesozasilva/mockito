package br.com.adesozasilva.domain;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import br.com.adesozasilva.infra.dao.FilmeDaoImpl;
import org.junit.Before;
import org.junit.Test;

import br.com.adesozasilva.service.FilmeService;


public class FilmeTest {
	
	private FilmeService filmeService;

	@Before
	public void setup() {
		filmeService = new FilmeService(new FilmeDaoImpl());
	}

	@Test
	public void deverRetornarTodosOsPagamentos() {
		filmeService.adicionaFilme(new Filme("O Poderoso chefão",
				"The Godfather (Brasil: O Poderoso Chefão /Portugal: O Padrinho) é um filme norte-americano de 1972", Calendar.getInstance()));
		filmeService.adicionaFilme(new Filme("American Gangster",
				"American Gangster  é um filme estadunidense de 2007 do gênero policial, dirigido por Ridley Scott,", Calendar.getInstance()));

		assertEquals(2, filmeService.retornaTodosOsFilmes().size());
	}
	
	@Test
	public void deveAdicionarPagamento() {
		Filme filme = new Filme("Os Intocáveis",
				"Os Intocáveis é um filme de drama policial norte-americano de 1987, dirigido por Brian De Palma e escrito por David Mamet. ",
				Calendar.getInstance());

		filmeService.adicionaFilme(filme);
		
		assertEquals(3, filmeService.retornaTodosOsFilmes().size());
	}

}
