package br.com.everis.infra.dao;

import java.util.List;

import br.com.everis.dominio.Pagamento;

public interface PagamentoDAO {

	List <Pagamento> retornaTodosOsPagamentos();
	void adicionaPagamento(Pagamento pagamento);

}
