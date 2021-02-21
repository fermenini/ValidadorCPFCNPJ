package com.validador.validadorcpfcnpj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.validador.validadorcpfcnpj.models.Usuario;

public interface RepositoryUsuario extends JpaRepository<Usuario, Long> {

	/*
	 * Utilização do JPARepository afim de facilitar e evitar criação de DAO 
	 * e facilitar todo gerenciamento/persistência.
	 */
	
	@Query("SELECT Usuario FROM usuario where nmUsuario = :nomeUsuarioConsultar and senha = :senhaUsuarioConsultar ")
	Usuario findByUsuario(@Param("nomeUsuarioConsultar") String nomeUsuarioConsultar, @Param("senhaUsuarioConsultar") String senhaUsuarioConsultar);
	
}
