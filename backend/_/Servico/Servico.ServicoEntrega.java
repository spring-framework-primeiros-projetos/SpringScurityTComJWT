package _.Servico;

import _..item;
import _.Frete.Construtor;
import API.List;
import _.Enumeradores.Servico;
import _.Enumeradores.Pagamento;
import API.Date;
import _.Atores.Transportador;
import _.Transporte.Caminhao;

/**
 * A classe especializada ServicoEntrega trata de registrar no sistema o serviço solicitado pelo ator solicitante para ser atendido pelo ator transportador no sistema.
 */
public class ServicoEntrega implements Servico.Despacho, Servico.Despacho {

	/**
	 * A variável local id será o identificador da solicitaçaõ correpondente a Classe.
	 */
	private long id;

	/**
	 * A variável local enderecoOrigem alocará o endereço no qual o ator transportador deve estar para carregar o veículo com os itens que serão tranportados.
	 */
	private Servico.Endereco enderecoOrigem;

	/**
	 * A variável local enderecoDestino alocará o endereço no qual o ator transportador deve estar para descarregar o veículo com os itens que foram tranportados.
	 */
	private Servico.Endereco enderecoDestino;

	/**
	 * A variável local itens alocará o identificador da lista de itens com um identificador único.
	 */
	private API.List<item> itens;

	/**
	 *  A variável local frete alocará o identificador do orçamento efetuado para o serviço solicitado.
	 */
	private Frete.Construtor frete;

	/**
	 * A variável statusServico alocará em qual status se encntra o serviço solicitado pelo ator Solicitante.
	 */
	private Enumeradores.Servico statusServico;

	/**
	 * A variável local statusPagamento alocará o status atual do pagamento do serviço solicitado.
	 */
	private Enumeradores.Pagamento statusPagamento;

	/**
	 * A variável Atendimento alocará a data e hora que o serviço deve ser prestado ao cliente.
	 */
	private API.Date Atendimento;

	/**
	 * A variável distancia alocará a distância  total e exata entre o endereçoOrigem e enderecoDestino.
	 */
	private float Distancia;

	private Atores.Transportador Transportador;

	private Transporte.Caminhao Veiculo;

	/**
	 * o método Distancia cálcula a distãncia total e exata entre o enderecoOrigem e enderecoDestino.
	 */
	public float Distancia(Servico.Endereco EnderecoOrigem, Servico.Endereco EnderecoDestino) {
		return 0;
	}

	public Servico.ServicoEntrega DespachoEntrega() {
		return null;
	}

	public Enumeradores.Servico MudaStatus() {
		return null;
	}

	public long SolicitaServico(long id) {
		return 0;
	}

	public Enumeradores.Pagamento PagarServico(float valor) {
		return null;
	}


	/**
	 * @see _.Servico.Despacho#CalculaTotal()
	 */
	public float CalculaTotal() {
		return 0;
	}

}
