
-- =====================================
-- MOVELEARN - CRIACAO DAS TABELAS
-- ORACLE DATABASE
-- =====================================


drop table TB_ML_PROGRESSO_AULA cascade constraints;
drop table TB_ML_ALTERNATIVA cascade constraints;
drop table TB_ML_QUESTAO cascade constraints;
drop table TB_ML_MATRICULA cascade constraints;
drop table TB_ML_AULA cascade constraints;
drop table TB_ML_TRAJETO cascade constraints;
drop table TB_ML_CURSO cascade constraints;
drop table TB_ML_USUARIO cascade constraints;
drop table TB_ML_TRANSPORTE cascade constraints;

drop sequence SEQ_ML_PROGRESSO_AULA;
drop sequence SEQ_ML_ALTERNATIVA;
drop sequence SEQ_ML_QUESTAO;
drop sequence SEQ_ML_MATRICULA;
drop sequence SEQ_ML_AULA;
drop sequence SEQ_ML_TRAJETO;
drop sequence SEQ_ML_CURSO;
drop sequence SEQ_ML_USUARIO;
drop sequence SEQ_ML_TRANSPORTE;

-- 1. TRANSPORTE
Create Table Tb_Ml_Transporte (
    Transporte_Id Number(10) Primary Key,
    Nome Varchar2(50),
    Permite_Leitura Char(1)
);

-- 2. USUARIO
Create Table Tb_Ml_Usuario (
    Usuario_Id Number(10) Primary Key,
    Nome Varchar2(100),
    Email Varchar2(150),
    Senha_Hash Varchar2(255),
    Dt_Criacao Timestamp
);

-- 3. CURSO
Create Table Tb_Ml_Curso (
    Curso_Id Number(10) Primary Key,
    Titulo Varchar2(100),
    Descricao Varchar2(500),
    Nivel Varchar2(20)
);

-- 4. TRAJETO
Create Table Tb_Ml_Trajeto (
    Trajeto_Id Number(10) Primary Key,
    Nome Varchar2(100),
    Duracao_Minutos Number(3),
    Estudo_Minutos Number(3),
    Frequencia_Semanal Number(1),
    Usuario_Id Number(10),
    Transporte_Id Number(10),
    Foreign Key (Usuario_Id)
        References Tb_Ml_Usuario(Usuario_Id),
    Foreign Key (Transporte_Id)
        References Tb_Ml_Transporte(Transporte_Id)
);

-- 5. AULA
Create Table Tb_Ml_Aula (
    Aula_Id Number(10) Primary Key,
    Titulo Varchar2(150),
    Conteudo Clob,
    Duracao_Minutos Number(3),
    Ordem Number(3),
    Curso_Id Number(10),
    Foreign Key (Curso_Id)
        References Tb_Ml_Curso(Curso_Id)
);

-- 6. MATRICULA
Create Table Tb_Ml_Matricula (
    Matricula_Id Number(10) Primary Key,
    Usuario_Id Number(10),
    Curso_Id Number(10),
    Data_Matricula Timestamp,
    Status Varchar2(20),
    Foreign Key (Usuario_Id)
        References Tb_Ml_Usuario(Usuario_Id),
    Foreign Key (Curso_Id)
        References Tb_Ml_Curso(Curso_Id)
);

-- 7. QUESTAO
Create Table Tb_Ml_Questao (
    Questao_Id Number(10) Primary Key,
    Enunciado Varchar2(500),
    Ordem Number(3),
    Aula_Id Number(10),
    Foreign Key (Aula_Id)
        References Tb_Ml_Aula(Aula_Id)
);

-- 8. ALTERNATIVA
Create Table Tb_Ml_Alternativa (
    Alternativa_Id Number(10) Primary Key,
    Texto Varchar2(200),
    Correta Char(1),
    Questao_Id Number(10),
    Foreign Key (Questao_Id)
        References Tb_Ml_Questao(Questao_Id)
);

-- 9. PROGRESSO_AULA
Create Table Tb_Ml_Progresso_Aula (
    Progresso_Id Number(10) Primary Key,
    Concluido Char(1),
    Dt_Conclusao Timestamp,
    Matricula_Id Number(10),
    Aula_Id Number(10),
    Foreign Key (Matricula_Id)
        References Tb_Ml_Matricula(Matricula_Id),
    Foreign Key (Aula_Id)
        References Tb_Ml_Aula(Aula_Id)
);

-- =====================================
-- SEQUENCES
-- =====================================

Create Sequence Seq_Ml_Transporte Start With 1 Increment by 1;
create sequence seq_ml_usuario start with 1 increment By 1;
Create Sequence Seq_Ml_Curso Start With 1 Increment By 1;
Create Sequence Seq_Ml_Trajeto Start With 1 Increment By 1;
Create Sequence Seq_Ml_Aula Start With 1 Increment By 1;
Create Sequence Seq_Ml_Matricula Start With 1 Increment By 1;
Create Sequence Seq_Ml_Questao Start With 1 Increment By 1;
Create Sequence Seq_Ml_Alternativa Start With 1 Increment By 1;
Create Sequence Seq_Ml_Progresso_Aula Start With 1 Increment By 1;

-- 1. TRANSPORTES
INSERT INTO TB_ML_TRANSPORTE (transporte_id, nome, permite_leitura) VALUES (SEQ_ML_TRANSPORTE.NEXTVAL, 'Trem', 'S');
INSERT INTO TB_ML_TRANSPORTE (transporte_id, nome, permite_leitura) VALUES (SEQ_ML_TRANSPORTE.NEXTVAL, 'Metrô', 'S');
INSERT INTO TB_ML_TRANSPORTE (transporte_id, nome, permite_leitura) VALUES (SEQ_ML_TRANSPORTE.NEXTVAL, 'Ônibus (passageiro)', 'S');
INSERT INTO TB_ML_TRANSPORTE (transporte_id, nome, permite_leitura) VALUES (SEQ_ML_TRANSPORTE.NEXTVAL, 'Carro (motorista)', 'N');

-- 2. USUARIOS PARA DEMONSTRACAO; substitua o campo senha_hash antes de criar login real.
INSERT INTO TB_ML_USUARIO (usuario_id, nome, email, senha_hash, dt_criacao) VALUES (SEQ_ML_USUARIO.NEXTVAL, 'Aluno Demo', 'aluno.demo@movelearn.test', 'DEMO_SEM_LOGIN', CURRENT_TIMESTAMP);
INSERT INTO TB_ML_USUARIO (usuario_id, nome, email, senha_hash, dt_criacao) VALUES (SEQ_ML_USUARIO.NEXTVAL, 'Maria Demo', 'maria.demo@movelearn.test', 'DEMO_SEM_LOGIN', CURRENT_TIMESTAMP);

-- 3. CURSOS
INSERT INTO TB_ML_CURSO (curso_id, titulo, descricao, nivel) VALUES (SEQ_ML_CURSO.NEXTVAL, 'Inglês Básico', 'Microaulas de inglês para situações do cotidiano.', 'INICIANTE');
INSERT INTO TB_ML_CURSO (curso_id, titulo, descricao, nivel) VALUES (SEQ_ML_CURSO.NEXTVAL, 'Ciência de Dados', 'Introdução à análise e interpretação de dados.', 'INICIANTE');
INSERT INTO TB_ML_CURSO (curso_id, titulo, descricao, nivel) VALUES (SEQ_ML_CURSO.NEXTVAL, 'Machine Learning', 'Conceitos iniciais de aprendizado de máquina.', 'INICIANTE');

-- 4. TRAJETOS DE EXEMPLO
INSERT INTO TB_ML_TRAJETO (trajeto_id, nome, duracao_minutos, estudo_minutos, frequencia_semanal, usuario_id, transporte_id) VALUES (SEQ_ML_TRAJETO.NEXTVAL, 'Ida ao trabalho', 60, 20, 5, (SELECT usuario_id FROM TB_ML_USUARIO WHERE email = 'aluno.demo@movelearn.test'), (SELECT transporte_id FROM TB_ML_TRANSPORTE WHERE nome = 'Trem'));
INSERT INTO TB_ML_TRAJETO (trajeto_id, nome, duracao_minutos, estudo_minutos, frequencia_semanal, usuario_id, transporte_id) VALUES (SEQ_ML_TRAJETO.NEXTVAL, 'Volta para casa', 50, 15, 5, (SELECT usuario_id FROM TB_ML_USUARIO WHERE email = 'aluno.demo@movelearn.test'), (SELECT transporte_id FROM TB_ML_TRANSPORTE WHERE nome = 'Ônibus (passageiro)'));
INSERT INTO TB_ML_TRAJETO (trajeto_id, nome, duracao_minutos, estudo_minutos, frequencia_semanal, usuario_id, transporte_id) VALUES (SEQ_ML_TRAJETO.NEXTVAL, 'Faculdade', 40, 20, 3, (SELECT usuario_id FROM TB_ML_USUARIO WHERE email = 'maria.demo@movelearn.test'), (SELECT transporte_id FROM TB_ML_TRANSPORTE WHERE nome = 'Metrô'));

-- 5. AULAS - 3 POR CURSO
-- Inglês Básico
INSERT INTO TB_ML_AULA (aula_id, titulo, conteudo, duracao_minutos, ordem, curso_id) VALUES (SEQ_ML_AULA.NEXTVAL, 'Saudações e cumprimentos', TO_CLOB('Hello significa olá e pode ser usado em diferentes contextos. Good morning significa bom dia; good afternoon, boa tarde; e good evening, boa noite ao cumprimentar alguém. Goodbye é uma despedida. Exemplo: Hello! Good morning!'), 10, 1, (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico'));
INSERT INTO TB_ML_AULA (aula_id, titulo, conteudo, duracao_minutos, ordem, curso_id) VALUES (SEQ_ML_AULA.NEXTVAL, 'Apresentações pessoais', TO_CLOB('Para se apresentar, diga My name is seguido do seu nome. A pergunta What is your name? significa Qual é o seu nome? Você também pode dizer Nice to meet you, que significa Prazer em conhecer você. Exemplo: My name is Ana. Nice to meet you!'), 15, 2, (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico'));
INSERT INTO TB_ML_AULA (aula_id, titulo, conteudo, duracao_minutos, ordem, curso_id) VALUES (SEQ_ML_AULA.NEXTVAL, 'Vocabulário cotidiano', TO_CLOB('Algumas palavras úteis são water (água), food (comida), home (casa) e work (trabalho). Para fazer pedidos educados, utilize please (por favor) e thank you (obrigado). Exemplo: Water, please. Thank you!'), 20, 3, (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico'));

-- Ciência de Dados
INSERT INTO TB_ML_AULA (aula_id, titulo, conteudo, duracao_minutos, ordem, curso_id) VALUES (SEQ_ML_AULA.NEXTVAL, 'O que é ciência de dados?', TO_CLOB('Ciência de Dados combina estatística, programação e conhecimento do problema para obter informações úteis a partir de dados. Um projeto costuma começar com uma pergunta, seguir para coleta e limpeza dos dados e terminar com análise e comunicação dos resultados. Os dados não falam por si: é preciso interpretar o contexto.'), 10, 1, (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados'));
INSERT INTO TB_ML_AULA (aula_id, titulo, conteudo, duracao_minutos, ordem, curso_id) VALUES (SEQ_ML_AULA.NEXTVAL, 'Tipos e qualidade dos dados', TO_CLOB('Dados numéricos representam quantidades, como idade ou preço. Dados categóricos representam classes, como modalidade de transporte. Valores ausentes, duplicados e formatos inconsistentes podem prejudicar as análises. Antes de tirar conclusões, confira origem, qualidade e significado de cada campo.'), 15, 2, (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados'));
INSERT INTO TB_ML_AULA (aula_id, titulo, conteudo, duracao_minutos, ordem, curso_id) VALUES (SEQ_ML_AULA.NEXTVAL, 'Média, mediana e visualização', TO_CLOB('A média é a soma dos valores dividida pela quantidade de observações. A mediana é o valor central após ordenar os dados e pode ser menos afetada por valores extremos. Gráficos de barras comparam categorias; gráficos de linhas ajudam a acompanhar mudanças no tempo. Sempre observe os eixos e as unidades.'), 20, 3, (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados'));
-- Machine Learning
INSERT INTO TB_ML_AULA (aula_id, titulo, conteudo, duracao_minutos, ordem, curso_id) VALUES (SEQ_ML_AULA.NEXTVAL, 'Introdução ao Machine Learning', TO_CLOB('Machine Learning é uma área da computação na qual modelos identificam padrões em dados para fazer previsões ou classificações. Um exemplo é estimar o tempo de uma viagem com base em dados históricos. O modelo precisa ser avaliado porque pode errar, especialmente em situações diferentes das usadas no treinamento.'), 10, 1, (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning'));
INSERT INTO TB_ML_AULA (aula_id, titulo, conteudo, duracao_minutos, ordem, curso_id) VALUES (SEQ_ML_AULA.NEXTVAL, 'Aprendizado supervisionado', TO_CLOB('No aprendizado supervisionado, o treinamento utiliza exemplos com respostas conhecidas, chamadas rótulos. Classificação prevê categorias, como spam ou não spam; regressão prevê valores numéricos, como a duração de um trajeto. A qualidade dos exemplos influencia a qualidade das previsões.'), 15, 2, (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning'));
INSERT INTO TB_ML_AULA (aula_id, titulo, conteudo, duracao_minutos, ordem, curso_id) VALUES (SEQ_ML_AULA.NEXTVAL, 'Treino, teste e avaliação', TO_CLOB('Uma parte dos dados é usada no treinamento e outra, separada, serve para avaliar o modelo em exemplos não vistos. Se o modelo memoriza os dados de treino e falha em novos casos, pode ocorrer sobreajuste (overfitting). Métricas adequadas ao problema ajudam a comparar resultados, mas não substituem análise crítica.'), 20, 3, (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning'));

-- 6. QUESTOES - 2 POR AULA
-- Inglês Básico
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'Qual expressão significa bom dia?', 1, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 1));
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'Qual palavra é usada para se despedir?', 2, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 1));
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'Como dizer Meu nome é Ana?', 1, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 2));
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'O que significa Nice to meet you?', 2, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 2));
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'Qual palavra significa água?', 1, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 3));
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'Qual expressão significa por favor?', 2, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 3));
-- Ciência de Dados
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'Qual é um objetivo da Ciência de Dados?', 1, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 1));
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'Qual costuma ser uma etapa antes da análise?', 2, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 1));
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'Qual exemplo representa um dado categórico?', 1, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 2));
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'O que pode prejudicar a qualidade dos dados?', 2, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 2));
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'Qual medida é o valor central de dados ordenados?', 1, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 3));
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'Que gráfico é útil para comparar categorias?', 2, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 3));
-- Machine Learning
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'O que modelos de Machine Learning procuram nos dados?', 1, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 1));
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'Qual é uma limitação de um modelo?', 2, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 1));
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'No aprendizado supervisionado, os exemplos possuem:', 1, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 2));
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'Prever duração de uma viagem é exemplo de:', 2, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 2));
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'Por que reservar dados de teste?', 1, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 3));
INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (SEQ_ML_QUESTAO.NEXTVAL, 'O que é overfitting?', 2, (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 3));

-- 7. ALTERNATIVAS - 4 POR QUESTAO (UMA CORRETA)
-- Inglês Básico
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Good morning', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 1) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Goodbye', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 1) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Good night', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 1) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Thank you', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 1) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Hello', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 1) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Goodbye', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 1) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Morning', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 1) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Please', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 1) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'I am name Ana', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 2) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'My name is Ana', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 2) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Your name Ana', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 2) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Name my Ana', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 2) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Até amanhã', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 2) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Bom dia', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 2) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Prazer em conhecer você', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 2) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Qual é seu nome?', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 2) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Work', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 3) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Home', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 3) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Food', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 3) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Water', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 3) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Thank you', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 3) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Please', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 3) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Goodbye', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 3) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Hello', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 3) AND ordem = 2));
-- Ciência de Dados
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Decorar números', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 1) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Extrair informações úteis de dados', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 1) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Eliminar todas as incertezas', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 1) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Substituir qualquer especialista', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 1) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Limpeza dos dados', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 1) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Publicação de propaganda', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 1) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Excluir todas as linhas', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 1) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Escolher um resultado pronto', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 1) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Preço em reais', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 2) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Distância em km', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 2) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Tipo de transporte', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 2) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Quantidade de aulas', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 2) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Valores duplicados', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 2) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Nomes de colunas claros', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 2) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Datas padronizadas', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 2) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Descrição das fontes', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 2) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Média', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 3) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Mediana', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 3) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Máximo', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 3) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Soma', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 3) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Gráfico de barras', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 3) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Mapa rodoviário', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 3) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Organograma', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 3) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Fluxograma de código', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 3) AND ordem = 2));
-- Machine Learning
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Somente arquivos PDF', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 1) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Padrões para apoiar previsões', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 1) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Senhas de usuários', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 1) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Apenas imagens', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 1) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Nunca comete erros', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 1) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Não precisa de dados', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 1) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Pode errar em situações novas', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 1) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Sempre explica suas decisões', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 1) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Rótulos conhecidos', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 2) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Somente textos vazios', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 2) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Nenhuma informação', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 2) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Apenas imagens', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 2) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Classificação binária', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 2) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Regressão', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 2) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Criptografia', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 2) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Compactação', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 2) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Para apagar os resultados', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 3) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Para avaliar exemplos não vistos no treino', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 3) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Para aumentar a senha', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 3) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Para evitar qualquer erro', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 3) AND ordem = 1));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Modelo que memoriza o treino e generaliza mal', 'S', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 3) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Banco sem tabelas', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 3) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Gráfico sem título', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 3) AND ordem = 2));
INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (SEQ_ML_ALTERNATIVA.NEXTVAL, 'Algoritmo sem entrada', 'N', (SELECT questao_id FROM TB_ML_QUESTAO WHERE aula_id = (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 3) AND ordem = 2));

-- 8. MATRICULAS PARA DEMONSTRAR LISTAGENS E PROGRESSO
INSERT INTO TB_ML_MATRICULA (matricula_id, usuario_id, curso_id, data_matricula, status) VALUES (SEQ_ML_MATRICULA.NEXTVAL, (SELECT usuario_id FROM TB_ML_USUARIO WHERE email = 'aluno.demo@movelearn.test'), (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico'), CURRENT_TIMESTAMP, 'ATIVA');
INSERT INTO TB_ML_MATRICULA (matricula_id, usuario_id, curso_id, data_matricula, status) VALUES (SEQ_ML_MATRICULA.NEXTVAL, (SELECT usuario_id FROM TB_ML_USUARIO WHERE email = 'aluno.demo@movelearn.test'), (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados'), CURRENT_TIMESTAMP, 'ATIVA');
INSERT INTO TB_ML_MATRICULA (matricula_id, usuario_id, curso_id, data_matricula, status) VALUES (SEQ_ML_MATRICULA.NEXTVAL, (SELECT usuario_id FROM TB_ML_USUARIO WHERE email = 'maria.demo@movelearn.test'), (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning'), CURRENT_TIMESTAMP, 'ATIVA');

-- 9. PROGRESSO: 2 AULAS CONCLUIDAS, 1 PENDENTE
INSERT INTO TB_ML_PROGRESSO_AULA (progresso_id, concluido, dt_conclusao, matricula_id, aula_id) VALUES (SEQ_ML_PROGRESSO_AULA.NEXTVAL, 'S', CURRENT_TIMESTAMP, (SELECT m.matricula_id FROM TB_ML_MATRICULA m JOIN TB_ML_USUARIO u ON u.usuario_id = m.usuario_id WHERE u.email = 'aluno.demo@movelearn.test' AND m.curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico')), (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Inglês Básico') AND ordem = 1));
INSERT INTO TB_ML_PROGRESSO_AULA (progresso_id, concluido, dt_conclusao, matricula_id, aula_id) VALUES (SEQ_ML_PROGRESSO_AULA.NEXTVAL, 'S', CURRENT_TIMESTAMP, (SELECT m.matricula_id FROM TB_ML_MATRICULA m JOIN TB_ML_USUARIO u ON u.usuario_id = m.usuario_id WHERE u.email = 'aluno.demo@movelearn.test' AND m.curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados')), (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Ciência de Dados') AND ordem = 1));
INSERT INTO TB_ML_PROGRESSO_AULA (progresso_id, concluido, dt_conclusao, matricula_id, aula_id) VALUES (SEQ_ML_PROGRESSO_AULA.NEXTVAL, 'N', NULL, (SELECT m.matricula_id FROM TB_ML_MATRICULA m JOIN TB_ML_USUARIO u ON u.usuario_id = m.usuario_id WHERE u.email = 'maria.demo@movelearn.test' AND m.curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning')), (SELECT aula_id FROM TB_ML_AULA WHERE curso_id = (SELECT curso_id FROM TB_ML_CURSO WHERE titulo = 'Machine Learning') AND ordem = 1));

COMMIT;

-- CONFERENCIA RAPIDA (esperado: 4, 2, 3, 3, 9, 18, 72, 3, 3)
SELECT 'TRANSPORTE' AS tabela, COUNT(*) AS quantidade FROM TB_ML_TRANSPORTE;
SELECT 'USUARIO' AS tabela, COUNT(*) AS quantidade FROM TB_ML_USUARIO;
SELECT 'CURSO' AS tabela, COUNT(*) AS quantidade FROM TB_ML_CURSO;
SELECT 'TRAJETO' AS tabela, COUNT(*) AS quantidade FROM TB_ML_TRAJETO;
SELECT 'AULA' AS tabela, COUNT(*) AS quantidade FROM TB_ML_AULA;
SELECT 'QUESTAO' AS tabela, COUNT(*) AS quantidade FROM TB_ML_QUESTAO;
SELECT 'ALTERNATIVA' AS tabela, COUNT(*) AS quantidade FROM TB_ML_ALTERNATIVA;

