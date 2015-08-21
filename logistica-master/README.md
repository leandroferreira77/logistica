## Case - Entregando Mercadorias com menor custo

Este case tem como objetivo disponibilizar um "serviço" para malha logística que visa obter sempre uma rota com o menor custo para a entrega de mercadorias.

## Arquitetura

Levou-se em consideração, que o cálculo da rota com o menor custo para a travessia de um grafo entre dois vértices onde as informações devem ser persistidas para que elas não se percam entre os deployments da aplicação,entāo foi utilizado um banco de dados (NoSQL) não relacional e orientado a grafos, o qual pareceu-me uma boa escolha optar pela utilização do [Neo4j](http://www.neo4j.org).

A aplicação foi dividida em diferentes camadas lógicas tendo papéis bem definidos, onde cada uma depende estritamente das camadas inferiores, facilitando assim a manutenção do código deste case. Além disso, a utilização do [Spring Framework] para a inversão de controle (IoC) e injeção de dependência (DI) aumenta a flexibilidade e a testabilidade da aplicação. 

As funcionalidades do sistema foram expostas como APIs REST através do framework [Spring MVC] (http://projects.spring.io/spring-framework/) como forma de promover sua integração com outros sistemas. Além disso, foi feita uma aplicação web de exemplo utilizando o [Dojo Toolkit] a qual foi criada para exemplificar a utilização destas APIs.

## Pré-Requisitos para executar a aplicação

- [Java Runtime Edition 7+](http://www.oracle.com/technetwork/java/javase/downloads/index.html?ssSourceSiteId=otnjp)

- [Apache Tomcat 7+](http://tomcat.apache.org/)

## Instalação da aplicação

Para instalar a aplicação, basta baixar o WAR disponível em [logistica.war](http://www.cnprodutos.com:8080/examples/war/logistica.war) e copiá-lo para o diretório TOMCAT_HOME/webapps.

Para confirmar que a instalação foi bem sucedida, basta acessar a [aplicação web de exemplo](http://localhost:8080/logistica).

## APIs REST

#### POST /logistica/services/shipping/logisticsNetwork/{network-name}

Cria ou atualiza as informações da malha logística especificada. O formato de malha logística adotado é bastante simples, onde cada linha representa uma rota no formato descrito abaixo:

**origem** **destino** **distância**

```
A B 10
B D 15
A C 20
C D 30
B E 50
D E 30
```

*Cada rota será considerada como de sentido único entre a origem e o destino. Desta forma, no exemplo acima embora exista uma rota de A para B não há nenhuma rota de B para A.*

##### Requisição

Na URI da requisição deve se informar o nome da malha logísica que será criada ou atualizada. No corpo da mensagem, os dados referentes a malha logística.

**Representações aceitáveis**

#### GET /logistica/services/shipping/shippingDetails/{origin}/{destination}?vehicleMileage={vehicleMileage}&fuelPrice={fuelPrice}

Calcula a rota com o menor custo entre um ponto de origem e um ponto de destino, levando em consideração a autonomia do veículo e o preço do combustível informado.

##### Requisição

Na URI da requisição deve se informar a origem e o destino da rota em questão. A autonomia do veículo e o preço do combustível devem ser incluídos como parâmetros de consulta da requisição.

Exemplo

```
GET /logistica/services/shipping/shippingDetails/A/D?vehicleMileage=10&fuelPrice=2.5 HTTP/1.1
Host: localhost:8080
User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:28.0) Gecko/20100101 Firefox/28.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-us,en;q=0.8,pt-br;q=0.5,pt;q=0.3
Accept-Encoding: gzip, deflate
Content-Type: application/x-www-form-urlencoded
X-Requested-With: XMLHttpRequest
Referer: http://localhost:8080/logistica/
Connection: keep-alive
```

#### Resposta

- 200 - application/json

	Caso a requisição tenha sido processada com sucesso. No corpo da mensagem será retornada uma representação em JSON da rota encontrada.

Exemplo

```
	{
		"shippingRate":6.25,
		"shippingRoute": {
			"origin":"A",
			"destination":"D",
			"legs":[
				{
					"origin":"A",
					"destination":"B",
					"distance":10.0
				},
				{
					"origin":"B",
					"destination":"D",
					"distance":15.0
				}
			],
			"length":25.0
		}
	}
```

- 204

	Caso não exista nenhuma rota entre a origem e o destino.

- 400

	Caso algum parâmetro inválido tenha sido fornecido.

- 500

	Caso tenha ocorrido algum erro durante o processamento da requisição.
