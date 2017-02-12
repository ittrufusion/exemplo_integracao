# Exemplo integracao com o Fusion
O exemplo em Java é uma aplicação que integra-se ao Fusion. Seguindo o mesmo modelo você pode integrar a sua aplicação a ele.

A integração ocorre por meio de webservices REST que estão documentados por um [Arquivo Swagger](https://raw.githubusercontent.com/ittrufusion/exemplo_integracao/master/src/main/resources/com/ittru/fusion/example/swagger.yaml). User o [Editor do Swagger](http://editor.swagger.io/#/) para visualizar a documentação e gerar artefatos compatíveis com diversas linguagens de programação.


## Integrando com um novo sistema
Para incluir um novo sistema, é necessário apenas que ele seja capaz de responder a 4 métodos REST:

1. list: a partir do CPF do usuário, faz uma pesquisa no banco de dados e retorna a lista de documentos a serem assinados. Para cada documento, deve ser informado o identificador, um segredo que garante o sigilo do documento, o número, a descrição e o tipo. O identificador é um campo que deve ser único para cada documento. O segredo deve ser uma string qualquer, que é diferente para cada documento. Uma pessoa que tenha acesso ao segredo, poderá pedir para visualizar o PDF.

Exemplo de requisição:
```http
GET /fusion-api/doc/list?cpf=11122233344 HTTP/1.1
Host: www.meusistema.com
Authorization: a28df19d-8e08-4a97-8ff1-c099f02118a7
```
Resposta:
```json
{
  "list": [  
    {
      "id": "PRO201700001",
      "code": "PRO-2017/00001",
      "descr": "Prontuário Médico de Fulano de Tal",
      "kind": "Protuário Médico",
      "secret": "1485778951000526544"
    },
    {
      "id": "PRO201700002",
      "code": "PRO-2017/00002",
      "descr": "Prontuário Médico de Cicrano de Tal",
      "kind": "Protuário Médico",
      "secret": "1485778951000526555"
    }
  ]
}
```

2. hash: a partir do identificador do documento, retornar os hashes SHA1 e SHA256 do PDF. Além disso, se houver interesse em produzir assinaturas sem política no padrão PKCS7, retornar o conteúdo do PDF se for solicitado.

Exemplo de requisição:
```http
GET /fusion-api/doc/PRO201700001/hash HTTP/1.1
Host: www.meusistema.com
Authorization: a28df19d-8e08-4a97-8ff1-c099f02118a7
```
Resposta:
```json
{
  "sha1": "fNg6XzkjgFXIkhpFB7SuO9aeAMc=",
  "sha256": "NKM+w6A39D5o/uHT9jgiiMCzqDt7d8rrmnxIH3KgGvk=",
  "secret": "1485778951000526544"
}
```


3. save: a partir do identificador do documento e de uma assintura, gravar essa assinatura no banco de dados.

Exemplo de requisição:
```http
PUT /fusion-api/doc/PRO201700001/sign HTTP/1.1
Host: www.meusistema.com
Authorization: a28df19d-8e08-4a97-8ff1-c099f02118a7

{
   "certificate": "MIIHtDCCBZygAwIBAgIIDYyeuV0D52QwDQYJKoZIhvcNAQELBQAwczELMAkGA1UEBhMCQlIxEzARBgNVBAoTCklDUC1CcmFzaWwxNTAzBgNVBAsTLEF1dG9yaWRhZGUgQ2VydGlmaWNhZG9yYSBkYSBKdXN0aWNhIC0...",
   "cn": "FULANO DE TA:11122233344",
   "cpf": "11122233344",
   "envelope": "MIILZAYJKoZIhvcNAQcCoIILVTCCC1ECAQExDzANBglghkgBZQMEAgEFADALBgkqhkiG9w0BBwGggge4MIIHtDCCBZygAwIBAgIIDYyeuV0D52QwDQYJKoZIhvcNAQELBQAwczELMAkGA1UEBhMCQlIxEzARBgNVB...",
   "name": "FULANO DE TAL",
   "sha1": "duXdZ/YUhf508P8cbWLBjtvIpFQ=",
   "sha256": "RVN+aLXPIDzSC4KryxkH/4sq2rN4jywzLQaaPsOnjCE=",
   "subject": "CN=FULANO DE TAL:11122233344, OU=Autoridade Certificadora 1 - AC1 v4, OU=Cert-JUS Institucional - A3, O=ICP-Brasil, C=BR",
   "time": "2016-06-01T12:23:54.063-03:00"
}
```
Resposta:
```json
{
  "status": "OK"
}
```
4. view: a partir do identificador do documento, retornar o conteúdo do PDF.

Exemplo de requisição:
```http
GET /fusion-api/doc/PRO201700001/pdf HTTP/1.1
Host: www.meusistema.com
Authorization: a28df19d-8e08-4a97-8ff1-c099f02118a7
```
Resposta:
```json
{
  "doc": "fNg6XzkjgFXIkhpFB7SuO9aeAMcNKM+w6A39D5uHT9jgiiMCzqDt7d8rrmnxIH3KgGvk...",
  "secret": "1485778951000526544"
}
```

## Protegendo seu Webservice
Para que seu webservice não possa ser acessado por um usuário mal intencionado, ele deve ser protegido por um código de autorização. Esse código deve ser um [GUID aleatório](https://www.guidgenerator.com/) e será compartilhado apenas com o Fusion. Toda vez que o Fusion chamar seu webservice, ele enviará este código no HTTP Header "Authorization". 

Seu webservice deve funcionar apenas em HTTPS para proteger esse código.

## Garantindo o Sigilo dos Seus Documentos
Cada documento deverá possuir um segredo que pode ser um GUID ou outra string qualquer. Quando seu webservice listar os documentos pendentes de assinatura, ele informará ao Fusion qual é o segredo (secret) de cada documento. Outros retornos de chamadas ao seu webservice deverão repetir o mesmo segredo para cada documento. 

Comparando o segredo original e os segredos retornados posteriormente, o Fusion garante que não houve nenhuma falha de segurança e, consequentemente, protege o sigilo de todos seus documentos.
