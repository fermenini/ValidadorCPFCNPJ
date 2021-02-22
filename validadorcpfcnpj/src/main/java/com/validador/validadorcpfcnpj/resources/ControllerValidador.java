package com.validador.validadorcpfcnpj.resources;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.validador.validadorcpfcnpj.BO.ValidadorBO;
import com.validador.validadorcpfcnpj.models.Requisicoes;
import com.validador.validadorcpfcnpj.models.Usuario;
import com.validador.validadorcpfcnpj.repository.RepositoryRequisicoes;
import com.validador.validadorcpfcnpj.repository.RepositoryUsuario;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/consultar")
@Api(value="Validador CPF/CNPJ")
@CrossOrigin(origins="*")
public class ControllerValidador {

	@Autowired
	RepositoryUsuario repositoryUsuario;
	
	@Autowired
	RepositoryRequisicoes repositoryRequisicoes;
	
	
	
	/*
	 * Classe que constitui uma consulta mensal para retorno de relatório demonstrativo de débitos
	 * em um determinado mês. Usado em métodos abaixo para receber o JSON com dados necessários.
	 */
	public static class DadosUsuarioConsulta {
		private String nome;
		private String senha;
		private int mes;
		private int ano;
		
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public String getSenha() {
			return senha;
		}
		public void setSenha(String senha) {
			this.senha = senha;
		}
		public int getMes() {
			return mes;
		}
		public void setMes(int mes) {
			this.mes = mes;
		}
		public int getAno() {
			return ano;
		}
		public void setAno(int ano) {
			this.ano = ano;
		}
	}
	
	/*
	 * Método GET que faz a consulta de um cadastro de um usuário caso ele exista, os dados sejam autenticados e seus débitos existentes
	 * bem como a quantidade de consultas realizadas. Em caso de não existência retornamos uma mensagem esclarecedora. 
	 * Pra isso usamos a classe auxiliar DadosUsuarioConsulta que tem parametro pré definidos justamente pra este método. 
	 */
	@ApiOperation(value="Consulta um determinado usuario no banco com seus debitos")
	@PostMapping("/usuario")
	public String consultaCadastroUsuario(@RequestBody DadosUsuarioConsulta usuarioConsultar) {
		Usuario user = repositoryUsuario.findByUsuario(usuarioConsultar.getNome(), usuarioConsultar.getSenha());
		if(user == null) {
			return "Usuario ou senha incorretos. Usuario não está na base de dados.";
		}else {
			BigDecimal cont = new BigDecimal(0);
			for(Requisicoes req : user.getRequisicoesUsuario()) {
				if(((req.getDtRequisicao().getMonth() + 1) == usuarioConsultar.mes) && ((req.getDtRequisicao().getYear() + 1900) == usuarioConsultar.ano)) {
					cont = cont.add(BigDecimal.ONE);			
					}
			}
			return ("Usuario de nome: " + user.getNmUsuario() + " possui um débito de: R$" + (cont.multiply(BigDecimal.TEN.divide(new BigDecimal(100)))).toString() + " em razão de: " + cont + (cont.compareTo(BigDecimal.ONE) == 1? " Consultas" : " Consulta") + " em " + usuarioConsultar.mes + "/" + usuarioConsultar.ano);
		}
			
	}
	/*
	 * Método que recebe um valor de nome e senha para efetuar a persistência e salvar um novo usuário
	 */
	@ApiOperation(value="Cadastrar um novo usuario")
	@PostMapping("/novoUsuario")
	public String criarCadastroUsuario(@RequestBody Usuario usuarioCadastrar) {
		repositoryUsuario.save(usuarioCadastrar);
		return "Usuário: " + usuarioCadastrar.getNmUsuario() + " e senha: " + usuarioCadastrar.getSenha() + " cadastrado com sucesso.";
	}
	
	/*
	 * Método responsável por fazer a deleção de um usuário existente, se e somente se, ele existe e não possui débitos ativos.
	 */
	@ApiOperation(value="Deletar um usuario existente")
	@DeleteMapping("/usuario")
	public String deletarCadastroUsuario(@RequestBody Usuario usuarioDeletar ) {
		Usuario user = repositoryUsuario.findByUsuario(usuarioDeletar.getNmUsuario(), usuarioDeletar.getSenha());
		if(user == null || user.getRequisicoesUsuario().size() > 0) {
			return "Não é possível deletar o usuário pois ele não existe ou possui débitos ativos.";
		}else {
			repositoryUsuario.deletarUsuario(usuarioDeletar.getNmUsuario(), usuarioDeletar.getSenha());
			return "Usuário deletado com sucesso.";
		}
	}
	
	/*
	 * Método responsável por validar um CPF ou CNPJ informado e adicionar ao usuário uma nova requisição em sua lista de requisicoes.
	 * Para isto, validamos a autenticidade do usuario que tentar fazer a requisicao e logo em seguida
	 * Se o valor informado é um valor válido, ou seja, não é nulo e nem branco (em um destes casos, é considerado consulta de mesma forma e feito a adição de débito).
	 * Se válido, verificamos então se ele é um valor que constitui um CNPJ ou CPF válido.
	 */
	@ApiOperation(value="Cadastrar nova requisicao e validar valor")
	@PostMapping("/validar")
	public String cadastrarRequisicao(@RequestBody Requisicoes novaRequisicao) {
		Usuario user = repositoryUsuario.findByUsuario(novaRequisicao.getUsuarioRequisicao().getNmUsuario(), novaRequisicao.getUsuarioRequisicao().getSenha());
		if(user == null) {
			return "Usuario ou senha incorretos. Usuario não está na base de dados.";
		}else {
			novaRequisicao.setDtRequisicao(new Date(System.currentTimeMillis()));
			novaRequisicao.setUsuarioRequisicao(user);
			user.getRequisicoesUsuario().add(novaRequisicao);
			repositoryRequisicoes.save(novaRequisicao);
			repositoryUsuario.save(user);
			if(novaRequisicao.getValorValidar() == null || novaRequisicao.getValorValidar().isBlank()) {
				return "Por favor preencha o campo de valor a ser validado com números em formato de CPF ou CNPJ. Este valor não é um CPF/CNPJ. Obrigado " + user.getNmUsuario() + ", seu débito atual é de: R$" + (new BigDecimal(user.getRequisicoesUsuario().size()).multiply(BigDecimal.TEN.divide(new BigDecimal(100)))).toString();
			}else if(ValidadorBO.isValid(novaRequisicao.getValorValidar())) {
				return "O valor informado é um CPF/CNPJ válido. Obrigado " + user.getNmUsuario() + ", seu débito atual é de: R$" + (new BigDecimal(user.getRequisicoesUsuario().size()).multiply(BigDecimal.TEN.divide(new BigDecimal(100)))).toString();
			}else {
				return "O valor informado é um CPF/CNPJ inválido. Obrigado " + user.getNmUsuario() + ", seu débito atual é de: R$" + (new BigDecimal(user.getRequisicoesUsuario().size()).multiply(BigDecimal.TEN.divide(new BigDecimal(100)))).toString();
			}
			
		}
	}
	
	
}
