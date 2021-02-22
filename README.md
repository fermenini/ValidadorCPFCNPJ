@Author: João Victor Fernandes Fonseca Menini

# ValidadorCPFCNPJ

Validador de CPF/CNPJ usando SpringBoot para consumo com REST JSON. 


Neste projeto foi criado um validador que permite a criação de um usuário com nome e senha, deleção, validação de CNPJ ou CPF utilizando este usuário e aplicando cobrança de 0,10 para cada validação feita. Além disso, é possível extrair uma consulta que informa mediante autenticação de usuário e senha, os débitos totais, bem como todas as consultas realizadas em determinado mês e ano. 

Para isto, foram utilizadas as tecnologias descritas abaixo: 

- JAVA 8  - PostgreSQL - SpringBoot - Maven - JPA - Swagger

Para o pleno funcionamento da aplicação é necessário um DataBase que possua o nome de "validadordb", além dos preenchimento de usuário e password dentro do arquivo "application.properties". Todas tabelas necessárias para este projeto serão criadas automaticamente assim que iniciado pela primeira vez, pois durante o desenvolvimento foi definido ddl-auto. 

Testes unitários que foram usados para validação de todos os métodos criados no controller estão presentes em "src/test/java" com o nome de: ValidadorcpfcnpjApplicationTests.java

Para dúvidas, instruções e documentação sobre o projeto, bem como a possibilidade de fazer requisições utilize o endereço abaixo após "start" da aplicação:

http://localhost:8080/swagger-ui.html
