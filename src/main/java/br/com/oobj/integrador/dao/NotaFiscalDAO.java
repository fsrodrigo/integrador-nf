package br.com.oobj.integrador.dao;

import java.util.List;

import br.com.oobj.integrador.model.NotaFiscal;

public interface NotaFiscalDAO {

	// Inserir notas
	void inserirNotaFiscal(NotaFiscal nota);

	// Contar notas
	int contarNotas();

	// Listar todas as notas

	List<NotaFiscal> listarTodas();

	// Remover uma nota

	int removerNota(Long id);

	// Atualizar notas

	NotaFiscal atualizar(NotaFiscal notaFiscal);

	// Buscar pelo ID

	NotaFiscal buscarPeloId(Long id);

	// Busca retorna uma única nota, deve se passar a chave e o valor da query como
	// parâmetros.

	NotaFiscal buscaSimplesLivre(String chave, String valor);

}
