package br.com.oobj.integrador.dao;

import java.util.List;

import br.com.oobj.integrador.model.NotaFiscal;

public interface NotaFiscalDAO {

	//Inserir notas
	void inserirNotaFiscal(NotaFiscal nota);
	
	//Contar notas
		int contarNotas();
	
	//Listar todas as notas
	
	List<NotaFiscal> listarTodas();
	
	//Remover uma nota
	
	int removerNota(Long id);
	
	
	//Atualizar notas
	
	NotaFiscal atualizar(NotaFiscal notaFiscal);
	
	
	//Buscar pelo ID
	
	NotaFiscal buscarPeloId(Long id);
	
}
