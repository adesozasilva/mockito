package br.com.adesozasilva.domain;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.adesozasilva.infra.dao.FilmeDAO;
import br.com.adesozasilva.infra.dao.FilmeDaoImpl;
import br.com.adesozasilva.service.FilmeService;


public class FilmesComMockitoTest {
	
	private FilmeService filmeService;
	private FilmeDAO filmeDAO;
	private List<Filme> filmes;

	@Before
	public void setup() {
		filmeDAO = mock(FilmeDaoImpl.class);
		filmeService = new FilmeService(filmeDAO);
		filmes = Arrays.asList(
				new Filme("O Poderoso chefão",
						"The Godfather (Brasil: O Poderoso Chefão /Portugal: O Padrinho) é um filme norte-americano de 1972", Calendar.getInstance()),
				new Filme("American Gangster",
						"American Gangster  é um filme estadunidense de 2007 do gênero policial, dirigido por Ridley Scott,", Calendar.getInstance()));
	}

	@Test
	public void deverRetornarTodosOsFilmes() {
		when(filmeDAO.retornaTodosOsFilmes()).thenReturn(filmes);
		assertEquals(2, filmeService.retornaTodosOsFilmes().size());
	}
	
	@Test
	public void deveAdicionarFilme() {
		Filme filme = new Filme("Os Intocáveis",
				"Os Intocáveis é um filme de drama policial norte-americano de 1987, dirigido por Brian De Palma e escrito por David Mamet. ",
				Calendar.getInstance());
		filmeService.adicionaFilme(filme);
		
		verify(filmeDAO).adicionaFilme(filme);
		
	}

}