package br.com.oobj.integrador;

import br.com.oobj.integrador.destino.Destino;
import br.com.oobj.integrador.origem.Origem;

public class IntegradorNotaFiscal {
	
	private Origem origem;
	private Destino destino;
	
	
	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	public void setDestino(Destino destino) {
		this.destino = destino;
	}


	public void integradorNotaFiscal() {
		System.out.println("Iniciando a integração de NF...");
		
		destino.escreverRegistros(origem.obterNotas());
		
		System.out.println("Integração de NF finalizada...");
	}

}
