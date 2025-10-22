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

### Construtor

* `Convidado(String nome, String senha, Evento evento)` — cria um convidado e o adiciona automaticamente ao evento informado.

# 💼 Classes Utilitárias

## 🏷️ Classe: `Util`
A classe `Util` é responsável por **gerenciar a conexão com o banco de dados db4o**, além de **oferecer métodos utilitários para gravação, remoção e consulta de objetos persistidos**.
Também **configura o comportamento de cascata** (update/delete/activate) e integra o sistema de **controle automático de IDs** através da classe interna `ControleID`.

---

### 🔧 Atributos

* `manager` Mantém a instância única de conexão com o banco db4o. 

---

### 🧩 Métodos Principais

*  `public static ObjectContainer conectarBanco()` — Estabelece e retorna a conexão com o banco de dados local (`banco.db4o`). Aplica as configurações de cascata e ativa o controle de IDs automáticos para classes com campo `id`.

* `public static void desconectar()` - Fecha a conexão com o banco de dados caso esteja aberta, liberando recursos.

* `public static void gravarObjeto(Object objeto)` - Grava (ou atualiza) um objeto no banco e realiza `commit()` da operação.

* `public static void apagarObjeto(Object objeto)` - Remove um objeto do banco e realiza `commit()` da operação.

* `public static <T> List<T> getObjetos(Class<T> classe)` - Retorna todos os objetos persistidos de uma determinada classe.

* `public static void apagarEvento(Evento evento)` - Remove um evento e todos os **convidados associados** a ele.
Também **atualiza o cliente** que possuía o evento, removendo a referência.

* `public static void apagarCliente(Cliente cliente)` - Remove um cliente e **todos os eventos associados** (e, indiretamente, seus convidados). Evita inconsistências limpando listas antes da exclusão definitiva.

---

## 🧭 Classe Interna: `ControleID`
Classe interna responsável por **gerenciar a geração automática de IDs únicos** para objetos persistidos no banco db4o.
Utiliza um **banco auxiliar (`sequencia.db4o`)** para armazenar os últimos IDs gerados por classe.

---

### 🔧 Atributos

* `private static ObjectContainer sequencia` - Banco auxiliar que armazena a sequência de IDs.     
* `private static TreeMap<String, RegistroID> registros` -  Cache com o último ID registrado por classe.      
* `private static boolean salvar` - Indica se há modificações pendentes a serem salvas. 

---

### 🧩 Métodos Principais

* `public static void ativar(boolean ativa, ObjectContainer manager)` - Ativa o controle de IDs automáticos. Lê os registros de IDs existentes e registra *triggers* (eventos db4o) para:

  * **Antes de gravar um objeto** (`creating`): incrementa e atribui o próximo ID.
  * **Após commit** (`created`): salva alterações de IDs no banco auxiliar.
  * **Antes de fechar o banco** (`closing`): encerra o banco de sequências.

* `private static void lerRegistrosID()` - Carrega os registros de IDs armazenados no banco auxiliar.

* `private static void salvarRegistrosID()` - Salva os registros de IDs modificados (após commits).

* `private static RegistroID obterRegistroID(String nomeclasse)` - Obtém (ou cria) o registro de ID para a classe informada.

---

## 🧱 Classe Interna: `RegistroID`
Classe auxiliar usada pelo `ControleID` para **armazenar o último ID gerado de cada classe**.
Cada instância representa o estado de ID de uma classe específica persistida no banco auxiliar.

---

### 🔧 Atributos

* `private String nomedaclasse` - Nome da classe controlada.                  
* `private int ultimoid` - Último ID gerado.                           
* `transient private boolean modificado` - Indica se houve alteração (não persistido). 

---

### 🧩 Métodos Principais

* `public RegistroID(String nomedaclasse)` - Construtor que inicializa o registro para a classe especificada, com ID inicial igual a `0`.

* `public void incrementarID()` - Incrementa o último ID gerado e marca o registro como modificado.

* `public int getid()` - Retorna o último ID gerado.

* `@Override public String toString()` - Retorna uma representação textual contendo o nome da classe e o último ID gerado.