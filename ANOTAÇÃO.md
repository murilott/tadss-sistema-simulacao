### Precisará de:

- Cliente
Chega 1 cliente em 5-50 segundos -> entra na fila

- Fila
Recebe cliente

- Posto
diminui 1 cliente da fila em 30-120 segundos -> descarta

### Problema????
Quantos postos são necessários para um cliente não esperar mais de 120 segundos na fila



### Enunciado


1. Introdução e Contextualização

A gestão de fluxo de usuários em instituições financeiras representa um desafio logístico onde o equilíbrio entre o custo operacional e a satisfação do cliente é primordial. Este projeto propõe o desenvolvimento de um simulador estocástico para o banco Firmeza Investimentos, focado na otimização do dimensionamento de recursos humanos durante períodos de alta demanda (horários de pico).
2. Definição do Problema

O objetivo central é determinar o número ideal de postos de atendimento (n) necessários para que o tempo de espera em fila não exceda o limiar crítico de 120 segundos. O cenário de análise compreende a janela temporal das 11:00 às 13:00, sob as seguintes premissas de fluxo:

    Distribuição de Chegada: Intervalo aleatório entre 5 e 50 segundos.

    Tempo de Serviço: Intervalo aleatório entre 30 e 120 segundos por cliente.

3. Metodologia e Desenvolvimento

O discente deverá implementar um sistema utilizando o paradigma de programação concorrente, simulando o comportamento de múltiplos processos (atendentes) e uma fila de tarefas (clientes). A simulação deve ser executada de forma iterativa, variando o número de atendentes (n), a fim de identificar o ponto de convergência com o objetivo estabelecido.
4. Requisitos de Análise Estatística

Para cada cenário simulado, deverão ser extraídas e discutidas as seguintes métricas de desempenho:

    Vazão (Throughput): Volume total de clientes atendidos no período. // OK

    Tempos de Resposta: Identificação dos tempos máximos de espera e de atendimento. // QUASE OK

    Tempo de Ciclo (Lead Time): Média do tempo total de permanência do cliente no sistema (entrada à saída).

    Indicador de Nível de Serviço: Tempo médio de espera em fila, com validação estatística do cumprimento da meta de 2 minutos.

5. Entregáveis

A entrega deve ser formalizada via sistema acadêmico, contendo:

    Repositório de Código: Arquivo compactado (.zip) com o código-fonte devidamente documentado.

    Relatório Técnico: Arquivo em formato PDF contendo a fundamentação teórica, a metodologia de simulação, a apresentação dos dados coletados (gráficos/tabelas) e a conclusão técnica fundamentada nos resultados.
