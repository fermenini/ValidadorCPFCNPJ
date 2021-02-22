package com.validador.validadorcpfcnpj.configuracoes;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ConfiguracoesSwagger {

	/*
	 * Método que gera o Docket com base nas informações do projeto. 
	 * Informamos o pacote de nossas classes, depois o caminho inicial da requisição
	 * Em seguida usamos o método externo "build" para de fato executar a geração e por fim,
	 * a apiInfo que recebe como paramêtro as informações que definimos no método metaInfo. 
	 */
	@Bean 
	public Docket validadorCpfCnpj() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.validador.validadorcpfcnpj"))
				.paths(regex("/consultar.*"))
				.build()
				.apiInfo(metaInfo());
	}
	
	/*
	 * Método que define as principais informações da API como contato do desenvolvedor, objetivo e licensas.
	 */
	private ApiInfo metaInfo() {
		ApiInfo apiInfo = new ApiInfo("Validador CPF/CNPJ", "API REST para validação de valores", "0.1", "Terms of Service", new Contact("João Victor Fernandes Fonseca Menini", "fermenini@outlook.com", "github.com/fermenini"), "Apache License Version 2.0", "https://www.apache.org/license.html", new ArrayList<VendorExtension>());
		return apiInfo;
	}
}
