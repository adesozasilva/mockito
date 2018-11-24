package br.com.everis.dominio;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import br.com.everis.infra.dao.PagamentoDaoImpl;
import br.com.everis.infra.dao.PagamentoDaoImplFake;
import br.com.everis.service.PagamentoService;


public class PagamentoTest {
	
	private PagamentoService pagamentoService;

	@Before
	public void montaCenario() {
		pagamentoService = new PagamentoService(new PagamentoDaoImplFake());
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
