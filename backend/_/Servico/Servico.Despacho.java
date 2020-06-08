package _.Servico;

/**
 * Classe genérica abstrata para determinar um padrão para construção de solicitações de serviço para o sistema. 
 */
public abstract interface Despacho {

	/**
	 * A variável local total alocará o total de custo do serviço solicitado pelo ator Solicitante.
	 */
	private float total;

	/**
	 * O método CalculaTotal executará o cálculo de todos os valores e alocará no total
	 */
	public abstract float CalculaTotal();

}
