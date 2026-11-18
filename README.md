# MoveLearn — Aprendizagem em Movimento


<img width="350" height="126" alt="image" src="https://github.com/user-attachments/assets/d2850cec-993d-4c53-9cc5-e1f393e0664d" />


O **MoveLearn** é uma plataforma web de microaprendizagem desenvolvida para transformar parte do tempo gasto em deslocamentos cotidianos em oportunidades de aprendizado.

O projeto foi desenvolvido para a **Global Solution 2026/2 da FIAP**, com o tema *CitizenTech — Tecnologia com o cidadão no centro da inovação*.

## 1. Contexto e problema

O deslocamento diário faz parte da rotina de milhões de pessoas, especialmente daquelas que precisam percorrer longas distâncias entre suas casas, instituições de ensino e locais de trabalho.

Esse tempo, muitas vezes, limita a disponibilidade para outras atividades, como estudar, desenvolver habilidades profissionais ou aprender um novo idioma.

A Global Solution 2026/2 apresenta as **horas perdidas no trânsito** como um dos problemas urbanos que podem ser abordados por soluções tecnológicas centradas no cidadão.

Partindo desse cenário, identificamos uma oportunidade: embora não seja possível eliminar o tempo de deslocamento, podemos oferecer ferramentas que permitam aproveitar parte desse período para o desenvolvimento pessoal e educacional.

O principal desafio é adaptar o aprendizado à rotina de cada pessoa, considerando sua disponibilidade de tempo e as condições em que realiza seus trajetos.

## 2. Nossa solução

O MoveLearn propõe uma plataforma de microaprendizagem que organiza conteúdos educacionais em aulas curtas, permitindo que o usuário estude de acordo com o tempo disponível durante seus deslocamentos.

O funcionamento é simples:

1. **Cadastro de trajetos:** o usuário informa seu trajeto, meio de transporte, duração e frequência semanal.
2. **Definição da disponibilidade:** escolhe quanto tempo do deslocamento pretende dedicar aos estudos.
3. **Escolha do curso:** seleciona um tema de seu interesse.
4. **Recomendação de aulas:** o sistema identifica conteúdos compatíveis com seu tempo disponível.
5. **Aprendizagem:** o usuário acessa as microaulas e responde a exercícios de fixação.
6. **Acompanhamento:** as aulas concluídas são registradas, permitindo acompanhar sua evolução.

A proposta não é substituir métodos tradicionais de ensino, mas oferecer uma alternativa complementar, acessível e adaptada à rotina do cidadão.

Por questões de segurança, a versão atual prioriza conteúdos que exigem leitura e interação apenas em modalidades de deslocamento nas quais isso seja adequado, como o transporte público.

## 3. Escopo da entrega — MVP

A versão atual do MoveLearn é um **Produto Mínimo Viável (MVP)**, desenvolvido para demonstrar as funcionalidades essenciais da solução.

O sistema conta com três cursos previamente cadastrados:

| Curso | Quantidade de aulas | Questões |
| --- | --- | --- |
| Inglês básico | 3 | 6 |
| Ciência de Dados | 3 | 6 |
| Machine Learning | 3 | 6 |
| **Total** | **9** | **18** |

Cada aula contém conteúdo explicativo e exercícios de múltipla escolha para fixação do aprendizado.

Os materiais educacionais são armazenados no banco de dados e disponibilizados dinamicamente pela aplicação. Dessa forma, o conteúdo não precisa ser escrito diretamente nas páginas HTML.

O MVP também contempla:

- Gerenciamento de trajetos e disponibilidade de estudo.
- Consulta aos cursos e às aulas disponíveis.
- Recomendação de aulas baseada no tempo do usuário.
- Visualização e resolução de exercícios.
- Registro das aulas concluídas.
- Acompanhamento do progresso educacional.

Nesta primeira versão, a recomendação utiliza regras de negócio implementadas em Java, sem depender de modelos de inteligência artificial.

## 4. Tecnologias utilizadas

O MoveLearn foi desenvolvido utilizando as seguintes tecnologias:

| Camada | Tecnologias |
| --- | --- |
| Frontend | HTML5, CSS3 e JavaScript |
| Backend | Java 21 |
| API REST | Jersey e Grizzly |
| Persistência | JDBC |
| Banco de dados | Oracle Database |
| Gerenciamento de dependências | Maven |

O backend segue uma organização em camadas:

- **Model:** representação das entidades do sistema.
- **DAO:** comunicação com o banco por meio de comandos SQL e JDBC.
- **Service:** validações e regras de negócio, incluindo recomendação de aulas.
- **Resource:** disponibilização dos endpoints REST consumidos pelo frontend.

A aplicação utiliza uma API REST para realizar a comunicação entre a interface web e o banco de dados, permitindo consultar e persistir informações reais.

## 5. Relação com os Objetivos de Desenvolvimento Sustentável

O MoveLearn está relacionado principalmente aos seguintes ODS da ONU:

### ODS 4 — Educação de Qualidade

Busca ampliar as oportunidades de aprendizagem, oferecendo conteúdos curtos e acessíveis para pessoas com pouco tempo disponível.

### ODS 10 — Redução das Desigualdades

Propõe facilitar o acesso ao desenvolvimento educacional e profissional, especialmente para pessoas cuja rotina de deslocamento dificulta a dedicação aos estudos.

### ODS 11 — Cidades e Comunidades Sustentáveis

Relaciona-se à melhoria da experiência cotidiana dos cidadãos nas cidades, aproveitando oportunidades existentes durante os deslocamentos urbanos.

Essas contribuições representam os objetivos e o potencial da solução, não resultados de impacto já comprovados.

## 6. Trabalhos futuros

O MVP representa a primeira etapa de uma proposta que poderá evoluir para uma plataforma de aprendizagem mais personalizada e automatizada.

### 6.1. Geração de conteúdos com Inteligência Artificial

Uma das principais evoluções previstas é a integração com modelos de Inteligência Artificial generativa por meio de APIs.

Atualmente, os conteúdos utilizados pelo MoveLearn são previamente cadastrados no banco de dados. Em uma versão futura, a plataforma poderá receber informações sobre o assunto que o usuário deseja estudar e utilizar IA para gerar materiais educacionais personalizados.

Por exemplo, um usuário poderá solicitar:

> "Quero aprender inglês básico para entrevistas de emprego, estudando 20 minutos por dia."

A partir dessa solicitação, a IA poderá elaborar:

- Uma estrutura de curso adequada ao objetivo informado.
- Aulas curtas, respeitando a disponibilidade do usuário.
- Explicações, exemplos e exercícios.
- Questões de múltipla escolha e suas respectivas respostas.
- Uma sequência progressiva de aprendizagem.

Os conteúdos gerados poderão passar por validação antes de serem armazenados no banco e disponibilizados na plataforma.

Essa evolução permitirá ampliar o catálogo educacional sem depender exclusivamente do cadastro manual de aulas.

### 6.2. Planejamento inteligente de estudos

Outra evolução será substituir a recomendação simples por um planejamento educacional mais completo.

O sistema poderá considerar o nível de conhecimento do usuário, seus objetivos, a frequência dos trajetos e seu desempenho nas atividades.

Com isso, será possível gerar cronogramas personalizados e ajustá-los conforme o progresso do estudante.

### 6.3. Aprendizagem por áudio

A plataforma poderá disponibilizar conteúdos em áudio, permitindo que usuários consumam determinadas aulas sem precisar visualizar a tela.

Essa funcionalidade será especialmente importante para ampliar a acessibilidade e atender a diferentes modalidades de deslocamento, respeitando as condições de segurança.

### 6.4. Integração com serviços de mobilidade

Futuramente, o MoveLearn poderá integrar APIs de mobilidade para estimar a duração dos trajetos e adaptar as recomendações de aprendizagem conforme o tempo previsto de deslocamento.

### 6.5. Personalização conforme o desempenho

Com o histórico de aprendizagem, o sistema poderá identificar dificuldades recorrentes e recomendar revisões, exercícios complementares e conteúdos de reforço.

## 7. Considerações finais

O MoveLearn demonstra como a tecnologia pode contribuir para tornar oportunidades de aprendizagem mais acessíveis e compatíveis com a rotina das pessoas.

Embora a versão atual seja um MVP com um catálogo limitado de conteúdos, sua arquitetura foi organizada para permitir a ampliação das funcionalidades e a futura integração com serviços de Inteligência Artificial.

A principal proposta do projeto é simples: **permitir que o cidadão aproveite parte do tempo de deslocamento para aprender algo novo, respeitando sua rotina e disponibilidade.**

---

**Projeto acadêmico desenvolvido para a Global Solution 2026/2 — FIAP | Engenharia de Software.**
