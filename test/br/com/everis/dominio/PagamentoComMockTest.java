package br.com.everis.dominio;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.everis.infra.dao.PagamentoDAO;
import br.com.everis.infra.dao.PagamentoDaoImpl;
import br.com.everis.service.PagamentoService;


public class PagamentoComMockTest {
	
	private PagamentoService pagamentoService;
	private PagamentoDAO pagamentoDAO;
	private List<Pagamento> pagamentos;

	@Before
	public void montaCenario() {
		pagamentoDAO = mock(PagamentoDaoImpl.class);
		pagamentoService = new PagamentoService(pagamentoDAO);
		
		pagamentos = Arrays.asList(new Pagamento("Conta de Luz", 300, Calendar.getInstance()),
				new Pagamento("Conta de Luz", 300, Calendar.getInstance()));
	}

	@Test
	public void deverRetornarTodosOsPagamentos() {
		PagamentoDAO pagamentoDAO = Mockito.mock(PagamentoDaoImpl.class);
		PagamentoService pagamentoService = new PagamentoService(pagamentoDAO);
		
		when(pagamentoDAO.retornaTodosOsPagamentos()).thenReturn(pagamentos);
		assertEquals(2, pagamentoService.retornaTodosOsPagamentos().size());
	}
	
	@Test
	public void deveAdicionarPagamento() {
		Pagamento pagamento = new Pagamento("Conta do Bar", 350, Calendar.getInstance());
		pagamentoService.adicionaPagamentos(pagamento);
		
		verify(pagamentoDAO).adicionaPagamento(pagamento);
		
	}
	

}
