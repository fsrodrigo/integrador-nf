package br.com.oobj.integrador.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class Utilitarios {

	public void moverArquivoProcessado(File arquivoAtual) {

		File parentFile = arquivoAtual.getParentFile().getParentFile();
		File diretorioProcessados = new File(parentFile.getAbsolutePath(), "processados");
		File arquivoProcessado = new File(diretorioProcessados.getAbsolutePath(), arquivoAtual.getName());

		if (!diretorioProcessados.exists()) {
			diretorioProcessados.mkdir();
		}
		try {
			if (!arquivoProcessado.exists()) {
				FileUtils.moveFileToDirectory(arquivoAtual, diretorioProcessados, false);
			} else {
				tratarArquivoExistente(arquivoAtual, diretorioProcessados, 1);
			}
		} catch (IOException e) {
			System.err.println("falha ao mover o arquivo para o diretório de processados..." + e.getMessage());
		}
	}

	private void tratarArquivoExistente(File arquivoAtual, File diretorioProcessados, int contador) {

		File fileNovoNome = new File(arquivoAtual.getParent(), FilenameUtils.getBaseName(arquivoAtual.getName()) + "-"
				+ String.valueOf(contador) + "." + FilenameUtils.getExtension(arquivoAtual.getName()));
		try {
			arquivoAtual.renameTo(fileNovoNome);
			FileUtils.moveFileToDirectory(fileNovoNome, diretorioProcessados, false);
		} catch (FileExistsException e) {
			System.out.println("Arquivo já existente no diretório de processados... Vou renomear os conflitantes.");
			fileNovoNome.renameTo(arquivoAtual);
			tratarArquivoExistente(arquivoAtual, diretorioProcessados, ++contador);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
