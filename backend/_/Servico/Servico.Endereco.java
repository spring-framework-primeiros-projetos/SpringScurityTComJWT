package _.Servico;

import API.String;
import _.Enumeradores.Estado;

/**
 * A classe Endereço busca colocar o endereço do cleinte registrado no sistema facilitando a busca pelo serviço.
 */
public class Endereco {

	/**
	 * A variável local rua alocará a rua passada pelo cliente.
	 */
	private API.String rua;

	/**
	 * A variável local numero alocará o número do endereço
	 */
	private API.String numero;

	/**
	 * A varável local complemento alocará o complemento do endereço
	 */
	private API.String complemento;

	/**
	 * A variável local cep alocará o cep do endereço.
	 */
	private int cep;

	/**
	 * A variável local Bairro alocará o bairo do endereço.
	 */
	private API.String bairro;

	/**
	 * A variável local Cidade alocará a cidade do endereço.
	 */
	private API.String cidade;

	/**
	 * A variável local estado alocará o estado do endereço.
	 */
	private Enumeradores.Estado estado;

	public Servico.Endereco PassaEndereco(API.String rua, int numero, API.String complemento) {
		return null;
	}

}
