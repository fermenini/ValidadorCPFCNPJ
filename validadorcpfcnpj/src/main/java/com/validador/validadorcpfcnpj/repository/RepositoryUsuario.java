package com.validador.validadorcpfcnpj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.validador.validadorcpfcnpj.models.Usuario;

public interface RepositoryUsuario extends JpaRepository<Usuario, Long> {

	/*
	 * Utilização do JPARepository afim de facilitar e evitar criação de DAO 
	 * e facilitar todo gerenciamento/persistência.
	 */
	
	/*
	 * Método que aciona uma Query HQL para obter um usuário do banco de dados a partir de seu nmUsuario e senha.
	 * Utilizado principalmente para autenticar o usuario
	 */
	@Query(" FROM Usuario where nmUsuario = :nomeUsuarioConsultar and senha = :senhaUsuarioConsultar ")
	Usuario findByUsuario(@Param("nomeUsuarioConsultar") String nomeUsuarioConsultar, @Param("senhaUsuarioConsultar") String senhaUsuarioConsultar);
	
	/*
	 * Método que aciona uma Query HQL para deletar um usuário do banco de dados a partir de seu nmUsuario e senha.
	 * Utilizado no método delete 
	 */
	@Transactional
	@Modifying
	@Query(" DELETE FROM Usuario where nmUsuario = :nmUsuarioDeletar and senha = :senhaUsuarioDeletar ")
	Integer deletarUsuario(@Param("nmUsuarioDeletar") String nmUsuarioDeletar, @Param("senhaUsuarioDeletar") String senhaUsuarioDeletar);
	
}
