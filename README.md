# Sistema de Simulação de Atendimento

## Descrição
Este projeto simula o funcionamento de um sistema de atendimento com múltiplos postos atendendo clientes a partir de uma fila compartilhada.

Durante a execução:
- clientes são gerados em intervalos aleatórios;
- os clientes entram em uma fila de espera;
- os postos consomem os clientes da fila;
- cada atendimento possui um tempo aleatório;
- ao final, os resultados são exportados para arquivos CSV.

## Estrutura principal
- `Main.java`: inicia várias simulações em paralelo.
- `Simulador.java`: controla a execução completa de uma simulação.
- `Fila.java`: representa a fila compartilhada de clientes.
- `Cliente.java`: armazena os dados de cada cliente atendido.
- `Posto.java`: representa um posto de atendimento.
- `Resultados.java`: agrupa os resultados de uma simulação.
- `Relatorio.java`: exporta os dados finais para `clientes.csv` e `postos.csv`.

## O que o código gera
Ao final da execução, o projeto gera:
- `clientes.csv`, com informações de espera e atendimento de cada cliente;
- `postos.csv`, com o total de clientes atendidos e o tempo total de atendimento por posto.

## Observação
Os tempos de chegada, atendimento, duração total da simulação e a ativação de logs podem ser ajustados diretamente em `Main.java`.
