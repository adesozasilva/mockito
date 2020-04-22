package br.com.adesozasilva.domain;

import br.com.adesozasilva.infra.dao.FilmeDAO;
import br.com.adesozasilva.infra.dao.FilmeDaoImpl;
import br.com.adesozasilva.service.FilmeService;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class FilmeServiceTest {

	private FilmeService filmeService;
	private FilmeDAO filmeDAO;
	private List<Filme> filmes;

	@Before
	public void setup() {
		FilmeDAO filmeDAO = new FilmeDaoImpl();
		filmeService = new FilmeService(filmeDAO);
	}

	@Test
	public void deverRetornarTodosOsFilmes() {
		filmeService.adicionaFilme(new Filme("O Poderoso chefão",
				"O Poderoso Chefão é um filme norte-americano de 1972",
				Calendar.getInstance()));
		filmeService.adicionaFilme(new Filme("American Gangster",
				"American Gangster  é um filme estadunidense de 2007 do gênero policial",
				Calendar.getInstance()));
		assertEquals(2, filmeService.retornaTodosOsFilmes().size());
	}

	@Test
	public void deveAdicionarFilme() {
		Filme filme = new Filme("Os Intocáveis",
				"Os Intocáveis é um filme de drama policial norte-americano de 1987",
				Calendar.getInstance());

		filmeService.adicionaFilme(filme);

		assertEquals(3, filmeService.retornaTodosOsFilmes().size());
	}

}
