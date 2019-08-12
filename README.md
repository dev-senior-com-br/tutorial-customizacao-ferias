# Customização - Validação de Férias      
      
Projeto de customização da validação de férias da Senior      
      
## Pré requisitos    

* Java 1.8 ou superior
* Maven
* Acesso ao usuário admin do tenant da plataforma (para criação da [aplicação](https://documentacao.senior.com.br/seniorxplatform/index.htm#administracao/gerenciamento-de-aplicacoes.htm))
      
## Configuração    
    
###  Criar aplicação na plataforma Senior X    
    
 1. Logar com admin da plataforma    
 2. Acessar Tecnologia > Administração > Gerenciamento de aplicações    
 3. Nova aplicação    
 4. Definir um nome e uma descrição para a aplicação    
 5. Na aplicação criada, acessar "Ações > Gerenciamento de chaves"    
 6. Clicar em "Gerar Chave"    
 7. O primeiro campo gerado é o ACCESS_KEY , e o segundo campo é o SECRET (será utilizado no próximo passo).    
    
### Definir as variáveis de ambiente para execução      
      
|      Nome     |    Valor                                      |      
| ------------- | --------------------------------------------- |      
| PLATFORM_URL  | https://platform.senior.com.br                |      
| TENANT        | \<nome tenant>                                |      
| ACCESS_KEY    | \<access key> obtido na criação da aplicação  |      
| SECRET        | \<secret> obtido na criação da aplicação     |      

### Definir a porta que será usada     

* Definir no arquivo application.properties
```
server.port = 8080
```

## Package 

* Na raiz do projeto, executar o seguinte comando para gerar o jar
```
mvn-package
```     
* Será gerado um arquivo customizacao-ferias.jar dentro da pasta target

## Deploy      

* Executar aplicação
```
java -jar -DPLATFORM_URL=https://platform.senior.com.br -DTENANT=<tenant> -DACCESS_KEY=<access-key> -DSECRET=<secret> customizacao-ferias.jar
```

* Ou utilizar qualquer [outra forma](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html) de executar uma aplicação Spring