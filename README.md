# Customização - Validação de Férias      
      
Projeto exemplo usado no [tutorial](https://dev.senior.com.br/documentacao/criando-a-extensao/) de customização da validação de férias da Senior.      
      
# Pré requisitos    

* Java 1.8 ou superior
* Maven
      
# Importando o projeto:

* [Importar projeto no Eclipse](documentation/ImportProjectEclipse.md)
* [Importar projeto no IntelliJ](documentation/ImportProjectIntelliJ.md)

# Projeto

Esse projeto permite a customização da validação de férias com as seguintes funcionalidades:

### Adicionando uma nova mensagem de erro
* Para adicionar uma mensagem de erro nova, deve-se usar o método `addValidationMessage()` localizado na classe package-private `ValidationMessageService` passando como parâmetro as mensagens de erros atuais, a solicitação de férias e a nova mensagem.
### Removendo uma mensagem de erro
* Para remover uma mensagem de erro do projeto de validação de férias da Senior, deve-se utilizar o método `removeValidationMessage()` localizado na classe `ValidationMessageService` passando como parâmetro as mensagens de erro atuais, a solicitação de férias e o tipo correspondente 
a mensagem que será removida. 
### Criando uma nova validação
* Novas validações devem estar contidas dentro do pacote validation e devem implementar a interface `VacationScheduleValidatorBase`. As novas validações devem ser chamadas na classe `ValidatorService` dentro do método `validate()`. Pode-se usar como base as validações já criadas. Recomenda-se que 
após criar uma validação nova ou alterar uma existente, sejam implementados testes unitários para garantir a funcionalidade da nova implementação.
### Obtendo o valor de um campo criado pelo usuário na plataforma
* Para obter o valor de um campo customizado, a classe que possui esse campo deve ter os métodos `getCustom()` e `setCustom()`, assim como é feito na classe `VacationPolicy`. Desse modo, quando você precisar do valor do campo, deve-se usar o método `getCustom()` passando por parâmetro o ID do campo
que foi criado na plataforma.
### Documentação
* Todos os métodos de validação deste projeto possuem documentação explicando a funcionalidade do método, o retorno e os parâmetros. Para o auxílio do entendimento do código também contém comentários explicando trechos específicos de código.
### Testes Automatizados
* No projeto as classes de serviço e handler estão cobertos por testes automatizados, o cenário e o resultado de todos os teste está documentado sempre acima do teste.

# Executando o projeto    
    
###  Criar aplicação na plataforma Senior X    
    
 1. Logar com admin da plataforma    
 2. Acessar Tecnologia > Administração > Gerenciamento de aplicações    
 3. Nova aplicação    
 4. Definir um nome e uma descrição para a aplicação    
 5. Na aplicação criada, acessar "Ações > Gerenciamento de chaves"    
 6. Clicar em "Gerar Chave"    
 7. O primeiro campo gerado é o ACCESS_KEY , e o segundo campo é o SECRET (será utilizado no próximo passo).    
    
### Definir a porta que será usada     

* Definir no arquivo application.properties
```
server.port = 8080
```

## Package 

* Na raiz do projeto, executar o seguinte comando para gerar o jar
```
mvn package
```     
* Será gerado um arquivo customizacao-ferias.jar dentro da pasta target

## Deploy      

### Variáveis necessárias para execução:
      
|      Nome     |    Valor                                      |      
| ------------- | --------------------------------------------- |      
| PLATFORM_URL  | https://platform.senior.com.br                |      
| TENANT        | \<nome tenant>                                |      
| ACCESS_KEY    | \<access key> obtido na criação da aplicação  |      
| SECRET        | \<secret> obtido na criação da aplicação      |     

* Executar aplicação
```
java -jar -DPLATFORM_URL=https://platform.senior.com.br -DTENANT=<tenant> -DACCESS_KEY=<access-key> -DSECRET=<secret> customizacao-ferias.jar
```

* Ou utilizar qualquer [outra forma](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html) de executar uma aplicação Spring
