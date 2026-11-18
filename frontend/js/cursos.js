const API_URL = 'http://localhost:8080';

async function carregarCursos() {
    const lista = document.getElementById('lista-cursos');
    const mensagem = document.getElementById('mensagem');

    try {
        const resposta = await fetch(`${API_URL}/cursos`);
        if (!resposta.ok) {
            throw new Error(`Erro HTTP ${resposta.status}`);
        }

        const cursos = await resposta.json();
        if (!Array.isArray(cursos)) {
            throw new Error('Resposta inesperada da API');
        }

        mensagem.textContent = cursos.length === 0 ? 'Nenhum curso cadastrado.' : '';
        lista.replaceChildren();

        for (const curso of cursos) {
            const artigo = document.createElement('article');
            artigo.className = 'curso';

            const titulo = document.createElement('h3');
            titulo.textContent = curso.titulo ?? 'Curso sem título';

            const descricao = document.createElement('p');
            descricao.textContent = curso.descricao ?? '';

            const nivel = document.createElement('p');
            nivel.className = 'nivel';
            nivel.textContent = `Nível: ${curso.nivel ?? 'Não informado'}`;

            artigo.append(titulo, descricao, nivel);
            lista.append(artigo);
        }
    } catch (erro) {
        mensagem.textContent = 'Não foi possível carregar os cursos. Verifique se a API está ligada e se o CORS está configurado.';
        console.error('Erro ao carregar cursos:', erro);
    }
}

document.addEventListener('DOMContentLoaded', carregarCursos);
