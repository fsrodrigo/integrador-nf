package br.com.oobj.integrador.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;

public class Utilitarios {

	public void moverArquivoProcessado(File arquivoAtual) {
		// arquivoAtual = E:\entrada\leitura
		File parentFile = arquivoAtual.getParentFile().getParentFile();

		// E:\entrada\processados
		File diretorioProcessados = new File(parentFile.getAbsolutePath(), "processados");

		File arquivoProcessado = new File(diretorioProcessados.getAbsolutePath(), arquivoAtual.getName());

		if (!diretorioProcessados.exists()) {
			diretorioProcessados.mkdir();
		}
		try {
			if (!arquivoProcessado.exists()) {
				FileUtils.moveFileToDirectory(arquivoAtual, diretorioProcessados, false);
			} else {
				//tratarArquivoExistente(arquivoAtual, diretorioProcessados, 1);
				 arquivoAtual.delete();
			}
		} catch (IOException e) {
			System.err.println("falha ao mover o arquivo para o diretório de processados..." + e.getMessage());
		}
	}

	private void tratarArquivoExistente(File arquivoAtual, File diretorioProcessados, int contador) {
		try {
			System.out.println("Diretorio Processados: " + diretorioProcessados.getAbsolutePath());
			System.out.println("Nome Arquivo Atual: " + arquivoAtual.getName());
			System.out.println("Contador: " + contador);
			
			arquivoAtual.renameTo(new File(arquivoAtual.getName() + "-" + String.valueOf(contador)));
			FileUtils.moveFileToDirectory(arquivoAtual, diretorioProcessados, false);
		} catch (FileExistsException e) {
			tratarArquivoExistente(arquivoAtual, diretorioProcessados, ++contador);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
