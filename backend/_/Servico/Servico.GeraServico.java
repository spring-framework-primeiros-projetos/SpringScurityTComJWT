package _.Servico;

/**
 *  A iterface GeraServico busca agregar todas a formas de solicitar serviços ao sistema.
 */
public interface GeraServico {

	/**
	 * O método GeraServico é método que gera o serviço no sistema de forma a atender a solicitação.
	 */
	public abstract Servico.Despacho GeraServico();

}
