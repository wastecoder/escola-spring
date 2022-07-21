# Aplicação de Escola CLI
Aplicação de registro escolares por linha de comandos.

## Descrição do projeto
![image](https://user-images.githubusercontent.com/101117204/180096971-6afd7ee2-9ed3-44e5-a552-4c8e8e9fd54f.png)
- É baseado no sistema criado no [curso](https://www.youtube.com/playlist?list=PL3ZslI15yo2p5LMl-r7KtsVkC6hsucsJp) do Samuka Martins.
- Nele o usuário cria **professores** e **alunos**.
- Após isso, eles podem ser relacionados em **disciplina**.
- Todos têm as funções básicas de **CRUD**: _cadastrar, consultar, listar, alterar e remover_.

## Instalação
Após clonar e importar o repositório, basta configurar o banco de dados:
- No seu servidor local, crie o banco de dados __escola__, sem tabelas.
- No arquivo **application.properties** altere a _url, username e password_ adequado ao seu servidor.
- **OBS:** se você não usa _MySQL_, terá que importar a dependência.

## Funcionalidades
- [x] Menu de navegação e exibição
- [x] Mensagem de aviso de sucesso e erro
- [x] Ao alterar, campos vazios não são atualizados
- [x] Não permite alunos duplicados em disciplinas
- [x] Tabelas intermediárias são criadas apenas quando necessário
- [x] Cascade: professores deletados são substituidos como null na disciplina
- [x] Relatório: busca filtrada de professores e alunos ⸻ JPQL & Native SQL

## BUG
No _@OneToMany()_ é recomendado usar **Lazy Fetch**.

<br/>

Quando uso isso dá um bug:
1. Após cadastrar ou alterar um objeto, é salvo no _programa_.
   1. Dá para ver a mudança ao listar ou consultar.
2. Porém, no _banco de dados_ não é salvo a alteração.
   1. Se fechar o programa, some.
3. Para salvar/alterar de fato, basta sair do _service_ atual.

<br/>

Ainda não consegui achar a solução para ele.
- Por isso usei os dois modos
- @OneToMany() do **professor**:
  - Usa _Eager Featch_
  - Não precisa fazer o passo 3
- @OneToMany() do **aluno**:
  - Usa _Lazy Featch_ e _@Transactional_
  - É o recomendado, mas precisa contornar o bug
