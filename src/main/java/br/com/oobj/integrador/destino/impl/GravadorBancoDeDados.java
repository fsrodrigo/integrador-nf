package br.com.oobj.integrador.destino.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.oobj.integrador.dao.NotaFiscalDAO;
import br.com.oobj.integrador.destino.Destino;
import br.com.oobj.integrador.model.NotaFiscal;

@Service
public class GravadorBancoDeDados implements Destino {

	private NotaFiscalDAO notaFiscalDAO;

	@Autowired
	public GravadorBancoDeDados(NotaFiscalDAO notaFiscalDAO) {
		this.notaFiscalDAO = notaFiscalDAO;
	}

	@Override
	public void escreverRegistros(List<NotaFiscal> notas) {
		System.out.println("Gravando registros no banco de dados...");

		for (NotaFiscal nota : notas) {
			//System.out.println("Fazendo insert SQN no banco para a nota: " + nota.getNomeArquivo());
			notaFiscalDAO.inserirNotaFiscal(nota);

		}

	}
}
