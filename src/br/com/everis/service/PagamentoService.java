package br.com.everis.service;

import java.util.List;

import br.com.everis.dominio.Pagamento;
import br.com.everis.infra.dao.PagamentoDAO;

public class PagamentoService {
	
	private PagamentoDAO pagamentoDAO;
	
	public PagamentoService(PagamentoDAO pagamentoDAO) {
	   this.pagamentoDAO = pagamentoDAO;
	}
	
	
	public List<Pagamento> retornaTodosOsPagamentos() {
		return pagamentoDAO.retornaTodosOsPagamentos();
		
	}
	
	public void adicionaPagamentos(Pagamento pagamento) {
		pagamentoDAO.adicionaPagamento(pagamento);
		
	}

	

}
