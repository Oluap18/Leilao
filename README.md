Projeto realizado na cadeira de Sistemas Distribuido.
```
Enunciado deste projeto em enunciado.pdf

Para executar este projeto deverá compilar os ficheiros:

```
Cliente.java
Server.java
```

Após, deverá executar incialmente o server através de:
```
java Server [portNumberServer]
```
Assim, o leilão estará pronto a receber conexões.

De seguida deverá executar, para cada cliente:
```
java Cliente [ipServer] [portNumberServer]
```
Comandos para executar programa:

```
r 'Username' 'Password': para fazer o registo do utilizador

a 'Username' 'Password': para autenticar o utilizador.

i 'Descrição do item': para leiloar um item.

listar: para listar todos os leilões ativos. * se é o dono, + se tem a licitação mais alta.

l 'número do leilão' 'valor': para licitar num leilão.

f 'número do leilão': para terminar um leilão.

h 'número do leilão': para verificar o histório de notificações do leilão.

sair: para sair da aplicação.
```
Happy hunting, and may the odds be ever in your favor!