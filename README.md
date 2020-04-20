# Mockito - Para que serve? 

Imagine o seguinte cenário, temos um serviço que precisa ser testado, iremos testar os métodos que retornam todos os filmes e outro que adiciona um filme.

```
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
```

Este serviço faz uma comunicação com o banco de dados através de uma classe DAO(no caso FilmeDAO).

Então vamos criar o nosso teste:

```
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
```

Agora podemos rodar nosso teste e verificar se a nossa aplicação está funcionando perfeitamente

![alt text](https://github.com/adesozasilva/mockito/blob/master/testes_ok.png)


Sucesso!:) Mas e se rodarmos novamente, o que será que acontece?

![alt text](https://github.com/adesozasilva/mockito/blob/master/testes_com_falha.png) 

Ué!? O meu código tinha funcionado, o que aconteceu? :( Os testes não passam mais porque utilizamos um objeto do mundo real, o FilmeDAO que faz a comunicação com um banco de dados, seja no ambiente de desenvolvimento, homologação ou produção. Então toda a vez que rodamos os testes os dados são inseridos no banco.

Ah! Entendi, então vamos apagar todos os dados sempre antes de rodar os testes, funcionaria, mas será que é a melhor solução, bom penso que não.


Outra solução seria criarmos um objeto “mock” igual ao nosso PagamentoDAO, vamos criar um PagamentoDAOMockImpl

Então, agora temos duas classes DAO, a do mundo real e um "mock" que faz comunicação com banco de dados de validação apenas para realizarmos os nossos testes.

```
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
```

No teste alteramos para a classe de serviço receber a nossa implementação mock. Então, será que isso é uma boa abordagem? Perceba toda a vez que formos realizar uma mudança na classe do mundo real, precisaremos replicar para a nossa implementação. Ainda teremos que apagar os dados para realizar os testes. :(

```
public class FilmesComClasseDeMockTest {
	
	private FilmeService filmeService;

	@Before
	public void setup() {
		filmeService = new FilmeService(new FilmeDAOMockImpl());
		filmeService.adicionaFilme(new Filme("O Poderoso chefão",
				"The Godfather (Brasil: O Poderoso Chefão /Portugal: O Padrinho) é um filme norte-americano de 1972", Calendar.getInstance()));
		filmeService.adicionaFilme(new Filme("American Gangster",
				"American Gangster  é um filme estadunidense de 2007 do gênero policial, dirigido por Ridley Scott,", Calendar.getInstance()));
	}

	@Test
	public void deverRetornarTodosOsPagamentos() {
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

	@After
	public void clearContext() {
		BancoDeDados.deleteBancoDeDados("filmes-mock");
	}

}
```

Então como podemos resolver isso? Aí que surge o Mockito, um framework para simular os nossos objetos reais.

Com o Mockito, podemos manter uma única classe de FilmeDAO e simular os seus comportamentos, veja como faremos isso no exemplo abaixo:

```
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
```

Notem que criamos um pagamentoDAO a partir do método `mock` e com os métodos `when` e `thenReturn` simulamos o comportamento “retornaTodosOsPagamentos” e agora ele retorna a lista que montamos.

Bacana não é?

Ainda tem mais, com o Mockito ainda conseguimos fazer outras verificações, como no caso abaixo:

```
@Test
	public void deveAdicionarFilme() {
		Filme filme = new Filme("Os Intocáveis",
				"Os Intocáveis é um filme de drama policial norte-americano de 1987, dirigido por Brian De Palma e escrito por David Mamet. ",
				Calendar.getInstance());
		filmeService.adicionaFilme(filme);
		
		verify(filmeDAO).adicionaFilme(filme);
		
	}
```

Com o método `verify` conseguimos saber se realmente o filmeService chamou o método adiciona do filmeDAO e assim verificamos se o nosso valor foi adicionado ao banco de dados, caso ele não fosse chamado o teste não passaria.

O Mockito tem muitos outros recursos poderosos e interessantes para nos ajudar na criação dos cenários de testes.

Ficou curioso? Veja mais em https://site.mockito.org/
