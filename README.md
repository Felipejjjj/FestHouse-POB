<h1 align="center">
    Fest House
</h1>

Projeto desenvolvido como primeira nota para a disciplina de POB (Persistencia de Objetos) no contexto de um sistema de gerenciamento de eventos.

# 📘 Classes Principais

## 🗺️  `Localizacao`
Representa uma localização geográfica composta por latitude e longitude.

### Atributos

* `latitude`: coordenada de latitude.
* `longitude`: coordenada de longitude.

### Construtor

* `Localizacao(double latitude, double longitude)` — cria uma nova localização com os valores informados.



## 👤  `Cliente`
Representa um cliente com CPF, nome, localização e uma lista de eventos associados.

### Atributos

* `cpf`: CPF do cliente.
* `nome`: nome do cliente.
* `eventos`: lista de eventos vinculados ao cliente.
* `localizacao`: localização geográfica do cliente.

### Construtor

* `Cliente(String cpf, String nome, Localizacao localizacao)` — cria um cliente com CPF, nome e localização definidos.

### Métodos

* `adicionarEvento(Evento evento)` — adiciona um evento à lista de eventos do cliente.
* `removerEvento(Evento evento)` — remove um evento da lista de eventos do cliente.


## 🎉  `Evento`
Representa um evento com data, nome, cliente responsável e convidados.

### Atributos

* `data`: data do evento.
* `nome`: nome do evento.
* `cliente`: cliente responsável pelo evento.
* `listaConvidados`: lista de convidados do evento.

### Construtor

* `Evento(String data, String nome, Cliente cliente)` — cria um novo evento com data, nome e cliente associados.

### Métodos

* `adicionarConvidado(Convidado convidado)` — adiciona um convidado ao evento.
* `removerConvidado(Convidado convidado)` — remove um convidado do evento.

Perfeito 👍
Aqui está a **documentação em Markdown (MD)** para as classes `Convidado`, `Util` e `Repositorio`, no **mesmo estilo simples e direto** das anteriores:

---

## 👥  `Convidado`
Representa um convidado associado a um evento, contendo um identificador, nome e senha.

### Atributos

* `id`: identificador numérico do convidado (pretende-se ser sequencial e autoincrementado).
* `nome`: nome do convidado.
* `senha`: senha de acesso do convidado.
* `evento`: evento ao qual o convidado está vinculado.

### Construtores

* `Convidado(String nome, String senha, Evento evento)` — cria um convidado e o adiciona automaticamente ao evento informado.
* `Convidado(int id, String nome, String senha, Evento evento)` — cria um convidado com ID definido manualmente (versão provisória até implementação do ID automático).

# 💼 Classes Utilitárias

## ⚙️ `Util`
Responsável por gerenciar a **conexão com o banco de dados DB4O**, configurando o comportamento de persistência e o uso de **cascata** entre as classes.

### Atributos

* `manager`: objeto `ObjectContainer` responsável pela conexão com o banco.

### Métodos

* `conectarBanco()` — cria e retorna a conexão com o banco DB4O. Caso já exista, reutiliza a mesma instância.
* `desconectar()` — encerra a conexão ativa com o banco de dados.


## 🗄️ `Repositorio`
Fornece métodos estáticos para realizar operações de **persistência**, **remoção** e **consulta** de objetos no banco DB4O.

### Atributos

* `manager`: conexão ativa com o banco, obtida pela classe `Util`.

### Métodos

* `gravarObjeto(Object objeto)` — armazena o objeto no banco e confirma a operação.
* `apagarObjeto(Object objeto)` — remove o objeto do banco e confirma a operação.
* `<T> getObjetos(Class<T> classe)` — retorna uma lista de objetos do tipo especificado presentes no banco.

## 🔢 `InstanciasConvidados`
Classe responsável por **armazenar e controlar a quantidade de instâncias criadas de objetos do tipo `Convidado`**, permitindo implementar uma lógica de **ID sequencial e autoincremental**.

### Atributos

* `instancias`: número atual de instâncias de convidados registradas.

### Construtor

* `InstanciasConvidados(int instancias)` — inicializa o contador de instâncias com o valor informado.

### Métodos

* `getInstancias()` — retorna o número atual de instâncias armazenadas.
* `acrescentarInstancia()` — incrementa o contador em 1, representando a criação de um novo convidado.