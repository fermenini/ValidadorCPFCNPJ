package com.validador.validadorcpfcnpj.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name = "requisicoes")
public class Requisicoes {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idRequisicao;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_usuario_requisicao", referencedColumnName = "id_usuario")
	private Usuario usuarioRequisicao; 
	
	@NotNull
	@JsonIgnore
	private Date dtRequisicao;
	
	private String valorValidar;
	
	
	public Usuario getUsuarioRequisicao() {
		return usuarioRequisicao;
	}
	
	public void setUsuarioRequisicao(Usuario usuarioRequisicao) {
		this.usuarioRequisicao = usuarioRequisicao;
	}
	
	public Date getDtRequisicao() {
		return dtRequisicao;
	}
	
	public void setDtRequisicao(Date dtRequisicao) {
		this.dtRequisicao = dtRequisicao;
	}

	public String getValorValidar() {
		return valorValidar;
	}

	public void setValorValidar(String valorValidar) {
		this.valorValidar = valorValidar;
	}

}