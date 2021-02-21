package com.validador.validadorcpfcnpj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.validador.validadorcpfcnpj.models.Requisicoes;

public interface RepositoryRequisicoes extends JpaRepository<Requisicoes, Long> {

	/*
	 * Utilização do JPARepository afim de facilitar e evitar criação de DAO 
	 * e auxiliar todo gerenciamento/persistência.
	 */
	
}
