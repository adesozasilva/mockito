# Mockito - Para que serve? É de comer?

Imagine o seguinte cenário, temos um serviço que precisa ser testado, iremos testar os métodos que retornam todos os pagamentos e outro que adiciona um pagamento.

```
public class PagamentoService {
	
	private PagamentoDAO pagamentoDAO;
	
	public PagamentoService(PagamentoDAO pagamentoDAO) {
	   this.pagamentoDAO = pagamentoDAO;
	}
	
	
	public Collection<Pagamento> retornaTodosOsPagamentos() {
		return pagamentoDAO.retornaTodosOsPagamentos();
		
	}
	
	public void adicionaPagamentos(Pagamento pagamento) {
		pagamentoDAO.adicionaPagamento(pagamento);
		
	}

}
```

Este serviço faz uma comunicação com o banco de dados através de uma classe DAO(no caso PagamentoDAO).

Então vamos criar o nosso teste:

```
public class PagamentoTest {
	
	private PagamentoService pagamentoService;

	@Before
	public void montaCenario() {
		pagamentoService = new PagamentoService(new PagamentoDaoImpl());
	}

	@Test
	public void deveRetornarTodosOsPagamentos() {
	  pagamentoService.adicionaPagamentos(new Pagamento("Conta de Luz", 300,                                   Calendar.getInstance()));
	  pagamentoService.adicionaPagamentos(new Pagamento("Conta de Água", 300, Calendar.getInstance()));
	
	  assertEquals(2,pagamentoService.retornaTodosOsPagamentos().size());
	}
	
	@Test
	public void deveAdicionarPagamento() {
	   Pagamento pagamento = new Pagamento("Conta do Bar", 350, Calendar.getInstance());
	   pagamentoService.adicionaPagamentos(pagamento);
		
	   assertEquals(3, pagamentoService.retornaTodosOsPagamentos().size());
	}
	
}

```

Agora podemos rodar nosso teste e verificar se a nossa aplicação está funcionando perfeitamente

![alt text](https://github.com/adesozasilva/mockito/blob/master/testes_ok.PNG)


Sucesso! :) (podemos ir beber). Mas e se rodarmos novamente, o que será que acontece?

![alt text](https://github.com/adesozasilva/mockito/blob/master/testes_com_falha.PNG) 

Ué mas o meu código estava funcionando, o que aconteceu? :( Os testes não passam mais porque estamos usando um objeto do mundo real, o PagamentoDAO que faz a comunicação com um banco de dados, seja no ambiente de desenvolvimento, homologação ou produção. Então toda vez que rodamos os testes os registros são inseridos no banco de dados.

Ah entendi, então vamos apagar todos os dados sempre antes de rodar os testes, funcionaria, mas será que é a melhor solução, bom acho que não.


Outra solução seria criarmos um objeto “fake” igual ao nosso PagamentoDAO, vamos criar um PagamentoDAOFake

Então, agora temos duas classes DAO, a do mundo real e uma fake que faz comunicação com banco de dados de validação apenas para realizarmos os nossos testes.

```
public class PagamentoDaoImpl implements PagamentoDAO {

	public List<Pagamento> retornaTodosOsPagamentos() {
		List<Pagamento> pagamentos = BancoDeDado.lerDoBanco("pagamentos");
		return pagamentos;
	}

	public void adicionaPagamento(Pagamento pagamento) {
		List<Pagamento> pagamentos = BancoDeDado.lerDoBanco("pagamentos");
		pagamentos.add(pagamento);
		BancoDeDado.gravaNoBanco(pagamentos, "pagamentos");
		
	}
}


public class PagamentoDaoImplFake implements PagamentoDAO {

	public List<Pagamento> retornaTodosOsPagamentos() {
		List<Pagamento> pagamentos = BancoDeDado.lerDoBanco("pagamentos-fake");
		return pagamentos;

	}

	public void adicionaPagamento(Pagamento pagamento) {
		List<Pagamento> pagamentos = BancoDeDado.lerDoBanco("pagamentos-fake");
		pagamentos.add(pagamento);
		BancoDeDado.gravaNoBanco(pagamentos, "pagamentos-fake");
		
	}
}

```

E no teste alteramos para a classe de serviço receber a nossa classe fake. Mas será que isso é o melhor dos mundos? Perceba toda vez que formos realizar uma mudança na classe do mundo real, precisaremos replicar para a nossa classe fake. E ainda teremos que ficar apagando os dados para realizar os testes. :(

```
public class PagamentoTest {
	
	private PagamentoService pagamentoService;

	@Before
	public void montaCenario() {
		pagamen toService = new PagamentoService(newPagamentoDaoImplFake());
	}

	@Test
	public void deverRetornarTodosOsPagamentos() {
		pagamentoService.adicionaPagamentos(new Pagamento("Conta de Luz", 300, Calendar.getInstance()));
		pagamentoService.adicionaPagamentos(new Pagamento("Conta de Água", 300, Calendar.getInstance()));
		
		
		assertEquals(2, pagamentoService.retornaTodosOsPagamentos().size());
	}
	
	@Test
	public void deveAdicionarPagamento() {
		Pagamento pagamento = new Pagamento("Conta do Bar", 350, Calendar.getInstance());
		pagamentoService.adicionaPagamentos(pagamento);
		
		assertEquals(3, pagamentoService.retornaTodosOsPagamentos().size());
	}
	
}

```

Então como podemos resolver isso? Aí que surge o Mockito, um framework para simular os nossos objetos reais.

Com o Mockito, podemos manter uma única classe de PagamentoDAO e simular os seus comportamentos, veja como podemos fazer isso no exemplo abaixo:

```
@Test
public void deverRetornarTodosOsPagamentos() {
    PagamentoDAO pagamentoDAO = Mockito.mock(PagamentoDaoImpl.class);
    PagamentoService pagamentoService = new PagamentoService(pagamentoDAO);

     
    List<Pagamento> pagamentos = Arrays.asList(new Pagamento("Conta de Luz",   300, Calendar.getInstance()),
                                               new Pagamento("Conta de Luz", 300, Calendar.getInstance()));

    Mockito.when(pagamentoDAO.retornaTodosOsPagamentos())
    .thenReturn(pagamentos);
    
    assertEquals(2, pagamentoService.retornaTodosOsPagamentos().size());
}

```

Notem que estamos criando um pagamentoDAO a partir do método `mock` e com os métodos `when` e `thenReturn` estamos simulando o comportamento “retornaTodosOsPagamentos” e agora ele está retornando a lista que montamos.

Bacana não é?

E não para por aí, com o Mockito ainda conseguimos fazer outras verificações, como no caso abaixo:

```
@Test
public void deveAdicionarPagamento() {
   PagamentoDAO pagamentoDAO = Mockito.mock(PagamentoDaoImpl.class);
   PagamentoService pagamentoService = new PagamentoService(pagamentoDAO);
   
   Pagamento pagamento = new Pagamento("Conta do Bar", 350, Calendar.getInstance());
   
   pagamentoService.adicionaPagamentos(pagamento);
		
   Mockito.verify(pagamentoDAO).adicionaPagamento(pagamento);
		
}

```

Com o método `verify` conseguimos saber se realmente o pagamentoService chamou o método adiciona do PagamentoDAO e assim verificamos se realmente o nosso registro foi adicionado ao banco de dados, caso o método não fosse chamado o teste não passaria.

E o Mockito tem muitos outros recursos poderosos e interessantes para nos ajudar na criação dos cenários de testes.

Ficou curioso? Veja mais em https://site.mockito.org/
