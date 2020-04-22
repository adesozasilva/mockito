package br.com.adesozasilva.domain;

import br.com.adesozasilva.infra.dao.FilmeDAO;
import br.com.adesozasilva.infra.dao.FilmeDaoImpl;
import br.com.adesozasilva.service.FilmeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class FilmesComMockitoTest {

	private FilmeService filmeService;
	private FilmeDAO filmeDAO;
	private List<Filme> filmes;

	@Before
	public void setup() {
		filmeDAO = Mockito.mock(FilmeDaoImpl.class);
		filmeService = new FilmeService(filmeDAO);
		filmes = Arrays.asList(
				new Filme("O Poderoso chefão",
						"O Poderoso Chefãoé um filme norte-americano de 1972",
						Calendar.getInstance()),
				new Filme("American Gangster",
						"American Gangster  é um filme estadunidense de 2007 do gênero policial",
						Calendar.getInstance()));
	}

	@Test
	public void deverRetornarTodosOsFilmes() {
		Mockito.when(filmeDAO.retornaTodosOsFilmes()).thenReturn(filmes);
		assertEquals(2, filmeService.retornaTodosOsFilmes().size());
	}

	@Test
	public void deveAdicionarFilme() {
		Filme filme = new Filme("Os Intocáveis",
				"Os Intocáveis é um filme de drama policial norte-americano de 1987",
				Calendar.getInstance());
		filmeService.adicionaFilme(filme);

		Mockito.verify(filmeDAO).adicionaFilme(filme);

	}

}