package br.com.oobj.integrador.destino.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import br.com.oobj.integrador.destino.Destino;
import br.com.oobj.integrador.model.NotaFiscal;

public class GravadorDiretorio implements Destino {

	private String path;

	public GravadorDiretorio(String path) {
		this.path = path;
	}

	@Override
	public void escreverRegistros(List<NotaFiscal> notas) {

		System.out.println("Salvando arquivos obtidos no diretório...");
		File diretorio = new File(path);

		for (NotaFiscal nota : notas) {
			if (nota != null) {
				File novoArquivo = new File(diretorio.getAbsolutePath(),
						nota.getChaveDeAcesso() + "-" + nota.getId() + ".xml");

				if (!novoArquivo.exists()) {
					try (BufferedWriter fr = new BufferedWriter(new FileWriter(novoArquivo))) {
						fr.write(nota.getConteudoArquivo());
						fr.close();
					} catch (IOException e) {
						System.err.println("Arquivo não pode ser gerado... " + e.getMessage());
					}
				}
			}else {
				//TODO 
				System.out.println("Codar tratativa.. Registro null");
			}
		}
	}
}
