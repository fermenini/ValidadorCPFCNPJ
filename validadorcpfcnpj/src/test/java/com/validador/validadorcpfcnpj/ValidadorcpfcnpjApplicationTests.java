package com.validador.validadorcpfcnpj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.validador.validadorcpfcnpj.models.Requisicoes;
import com.validador.validadorcpfcnpj.models.Usuario;
import com.validador.validadorcpfcnpj.resources.ControllerValidador;
import com.validador.validadorcpfcnpj.resources.ControllerValidador.DadosUsuarioConsulta;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ValidadorcpfcnpjApplicationTests {
	
	@Autowired
	ControllerValidador controllerValidador;

	@Test
	public void testeDeletaUsuarioInexistente() {
		Usuario usuario = new Usuario();
		usuario.setNmUsuario("Alfredo");
		usuario.setSenha("1234");
		
		assertEquals( "Não é possível deletar o usuário pois ele não existe ou possui débitos ativos.", controllerValidador.deletarCadastroUsuario(usuario));
	}
	
	@Test
	public void testeConsultarUsuarioInexistente() {
		DadosUsuarioConsulta dadosUsuarioConsulta = new DadosUsuarioConsulta();
		dadosUsuarioConsulta.setAno(2021);
		dadosUsuarioConsulta.setMes(1);
		dadosUsuarioConsulta.setNome("Alfredo");
		dadosUsuarioConsulta.setSenha("1234");
		assertEquals( "Usuario ou senha incorretos. Usuario não está na base de dados.", controllerValidador.consultaCadastroUsuario(dadosUsuarioConsulta));
		
	}

	@Test
	public void testeRequisicaoUsuarioInexistente() {
		Requisicoes novaRequisicao = new Requisicoes();
		Usuario user = new Usuario();
		user.setNmUsuario("Alfredo");
		user.setSenha("1234");
		novaRequisicao.setUsuarioRequisicao(user);
		novaRequisicao.setDtRequisicao(new Date(System.currentTimeMillis()));
		novaRequisicao.setValorValidar("11111111");	
		
		assertEquals( "Usuario ou senha incorretos. Usuario não está na base de dados.", controllerValidador.cadastrarRequisicao(novaRequisicao));
	}
	
	@Test
	public void testeCadastroUsuario() {
		Usuario user = new Usuario();
		user.setNmUsuario("Jose");
		user.setSenha("12345");
		
		assertEquals( "Usuário: Jose e senha: 12345 cadastrado com sucesso.", controllerValidador.criarCadastroUsuario(user));
	}
	
	@Test
	public void testeRequisicaoUsuarioExistenteCPFValido() {
		Requisicoes novaRequisicao = new Requisicoes();
		Usuario user = new Usuario();
		user.setNmUsuario("Jose");
		user.setSenha("12345");
		novaRequisicao.setUsuarioRequisicao(user);
		novaRequisicao.setDtRequisicao(new Date(System.currentTimeMillis()));
		novaRequisicao.setValorValidar("469.876.240-54");	
		
		assertEquals( "O valor informado é um CPF/CNPJ válido. Obrigado Jose, seu débito atual é de: R$0.1", controllerValidador.cadastrarRequisicao(novaRequisicao));
	}
	
	@Test
	public void testeRequisicaoUsuarioExistenteCPFInvalido() {
		Requisicoes novaRequisicao = new Requisicoes();
		Usuario user = new Usuario();
		user.setNmUsuario("Jose");
		user.setSenha("12345");
		novaRequisicao.setUsuarioRequisicao(user);
		novaRequisicao.setDtRequisicao(new Date(System.currentTimeMillis()));
		novaRequisicao.setValorValidar("11111111111");	
		
		assertEquals( "O valor informado é um CPF/CNPJ inválido. Obrigado Jose, seu débito atual é de: R$0.2", controllerValidador.cadastrarRequisicao(novaRequisicao));
	}
	
	@Test
	public void testeRequisicaoUsuarioExistenteCNPJValido() {
		Requisicoes novaRequisicao = new Requisicoes();
		Usuario user = new Usuario();
		user.setNmUsuario("Jose");
		user.setSenha("12345");
		novaRequisicao.setUsuarioRequisicao(user);
		novaRequisicao.setDtRequisicao(new Date(System.currentTimeMillis()));
		novaRequisicao.setValorValidar("65639333000100");	
		
		assertEquals( "O valor informado é um CPF/CNPJ válido. Obrigado Jose, seu débito atual é de: R$0.3", controllerValidador.cadastrarRequisicao(novaRequisicao));
	}
	
	@Test
	public void testeRequisicaoUsuarioExistenteCNPJInvalido() {
		Requisicoes novaRequisicao = new Requisicoes();
		Usuario user = new Usuario();
		user.setNmUsuario("Jose");
		user.setSenha("12345");
		novaRequisicao.setUsuarioRequisicao(user);
		novaRequisicao.setDtRequisicao(new Date(System.currentTimeMillis()));
		novaRequisicao.setValorValidar("10.111.111/0001-11");	
		
		assertEquals( "O valor informado é um CPF/CNPJ inválido. Obrigado Jose, seu débito atual é de: R$0.4", controllerValidador.cadastrarRequisicao(novaRequisicao));
	}
	
	@Test
	public void testeConsultarUsuarioExistente() {
		DadosUsuarioConsulta dadosUsuarioConsulta = new DadosUsuarioConsulta();
		dadosUsuarioConsulta.setAno((new Date(System.currentTimeMillis()).getYear()+1900));
		dadosUsuarioConsulta.setMes((new Date(System.currentTimeMillis()).getMonth()+1));
		dadosUsuarioConsulta.setNome("Jose");
		dadosUsuarioConsulta.setSenha("12345");
		assertEquals( "Usuario de nome: Jose possui um débito de: R$0.4 em razão de: 4 Consultas em 2/2021", controllerValidador.consultaCadastroUsuario(dadosUsuarioConsulta));
		
	}
	
	
}
