package com.validador.validadorcpfcnpj.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_usuario")
	private long idUsuario;
	
	@NotNull
	private String nmUsuario;
	
	@NotNull
	private String senha; 
	
	@JsonIgnore
	@Cascade({org.hibernate.annotations.CascadeType.REMOVE, org.hibernate.annotations.CascadeType.PERSIST})
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Requisicoes> requisicoesUsuario = new ArrayList<>();
	

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getNmUsuario() {
		return nmUsuario;
	}

	public void setNmUsuario(String nmUsuario) {
		this.nmUsuario = nmUsuario;
	}


	public List<Requisicoes> getRequisicoesUsuario() {
		return requisicoesUsuario;
	}

	public void setRequisicoesUsuario(List<Requisicoes> requisicoesUsuario) {
		this.requisicoesUsuario = requisicoesUsuario;
	}
	
	
}

