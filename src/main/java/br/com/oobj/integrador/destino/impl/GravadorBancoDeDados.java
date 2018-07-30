package br.com.oobj.integrador.destino.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.sql.DataSource;

import br.com.oobj.integrador.dao.NotaFiscalDAO;
import br.com.oobj.integrador.dao.impl.NotaFiscalJDBCDAO;
import br.com.oobj.integrador.destino.Destino;
import br.com.oobj.integrador.model.NotaFiscal;
import br.com.oobj.integrador.origem.Origem;

public class GravadorBancoDeDados implements Destino {

	private NotaFiscalDAO notaFiscalDAO;

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
