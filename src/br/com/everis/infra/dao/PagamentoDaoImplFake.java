package br.com.everis.infra.dao;

import java.util.List;

import br.com.everis.dominio.Pagamento;

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


