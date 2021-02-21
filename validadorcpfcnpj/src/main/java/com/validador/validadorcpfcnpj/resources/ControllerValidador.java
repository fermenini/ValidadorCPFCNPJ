package com.validador.validadorcpfcnpj.resources;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping(value="/consultar")
public class ControllerValidador {

	@Autowired
	RepositoryUsuario repositoryUsuario;
	
	@Autowired
	RepositoryRequisicoes repositoryRequisicoes;
	
	public class DadosUsuarioConsulta {
		private String nome;
		private String senha;
		private int mes;
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
	}
	
	@GetMapping("/usuario")
	public String consultaCadastroUsuario(@RequestBody DadosUsuarioConsulta usuarioConsultar) {
		Usuario user = repositoryUsuario.findByUsuario(usuarioConsultar.getNome(), usuarioConsultar.getSenha());
		if(user == null) {
			return "Usuario ou senha incorretos. Usuario não está na base de dados.";
		}else {
			BigDecimal cont = new BigDecimal(0);
			for(Requisicoes req : user.getRequisicoesUsuario()) {
				if(req.getDtRequisicao().getMonth() == usuarioConsultar.mes) {
					cont = cont.add(BigDecimal.ONE);			
					}
			}
			return ("Usuario de nome:" + user.getNmUsuario() + "possui um débito de: R$" + (cont.multiply(BigDecimal.TEN.divide(new BigDecimal(100)))).toString() + "em razão de: " + cont + (cont.compareTo(BigDecimal.ONE) == 1? "Consultas" : "Consulta"));
		}
			
	}
	
	@PostMapping("/usuario")
	public Usuario criarCadastroUsuario(@RequestBody Usuario usuarioCadastrar) {
		return repositoryUsuario.save(usuarioCadastrar);
	}
	
	@DeleteMapping("/usuario")
	public String deletarCadastroUsuario(@RequestBody Usuario usuarioDeletar ) {
		Usuario user = repositoryUsuario.findByUsuario(usuarioDeletar.getNmUsuario(), usuarioDeletar.getSenha());
		if(user == null || user.getRequisicoesUsuario().size() > 1) {
			return "Não é possível deletar o usuário pois ele não existe ou possui débitos ativos.";
		}else {
			repositoryUsuario.delete(usuarioDeletar);
			return "Usuário deletado com sucesso.";
		}
	}
	
	@PostMapping("/validar")
	public String cadastrarRequisicao(@RequestBody Requisicoes novaRequisicao) {
		Usuario user = repositoryUsuario.findByUsuario(novaRequisicao.getUsuarioRequisicao().getNmUsuario(), novaRequisicao.getUsuarioRequisicao().getSenha());
		if(user == null) {
			return "Usuario ou senha incorretos. Usuario não está na base de dados.";
		}else {
			novaRequisicao.setDtRequisicao(new Date(System.currentTimeMillis()));
			user.getRequisicoesUsuario().add(novaRequisicao);
			repositoryRequisicoes.save(novaRequisicao);
			repositoryUsuario.save(user);
			if(novaRequisicao.getValorValidar() == null && novaRequisicao.getValorValidar().isBlank()) {
				return "Por favor preencha o campo de valor a ser validado com números em formato de CPF ou CNPJ. Este valor não é um CPF/CNPJ. Obrigado" + user.getNmUsuario() + ", seu débito atual é de: R$" + (new BigDecimal(user.getRequisicoesUsuario().size()).multiply(BigDecimal.TEN.divide(new BigDecimal(100)))).toString();
			}else if(ValidadorBO.isValid(novaRequisicao.getValorValidar())) {
				return "O valor informado é um CPF/CNPJ válido. Obrigado " + user.getNmUsuario() + ", seu débito atual é de: R$" + (new BigDecimal(user.getRequisicoesUsuario().size()).multiply(BigDecimal.TEN.divide(new BigDecimal(100)))).toString();
			}else {
				return "O valor informado é um CPF/CNPJ inválido. Obrigado " + user.getNmUsuario() + ", seu débito atual é de: R$" + (new BigDecimal(user.getRequisicoesUsuario().size()).multiply(BigDecimal.TEN.divide(new BigDecimal(100)))).toString();
			}
			
		}
	}
	
	
}
