package com.validador.validadorcpfcnpj.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
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
	private long id;
	
	@NotNull
	private String nmUsuario;
	
	@NotNull
	private String senha; 
	
	@JsonIgnore
	private List<Requisicoes> requisicoesUsuario = new ArrayList<>();
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	@Cascade({org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.PERSIST})
	@OneToMany(mappedBy = "usuarioRequisicao")
	public List<Requisicoes> getRequisicoesUsuario() {
		return requisicoesUsuario;
	}

	public void setRequisicoesUsuario(List<Requisicoes> requisicoesUsuario) {
		this.requisicoesUsuario = requisicoesUsuario;
	}
	
	
}

