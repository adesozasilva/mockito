package br.com.everis.infra.dao;

import java.util.List;

import br.com.everis.dominio.Pagamento;

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


