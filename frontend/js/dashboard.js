document.getElementById('usuario-atual').textContent = USUARIO_ID;
async function carregarResumo() {
    const msg = document.getElementById('mensagem');
    try {
        const [rTrajetos, rMatriculas] = await Promise.all([
            fetch(`${API_URL}/trajetos?usuarioId=${USUARIO_ID}`),
            fetch(`${API_URL}/matriculas?usuarioId=${USUARIO_ID}`)
        ]);
        if (!rTrajetos.ok || !rMatriculas.ok) throw new Error('Falha na consulta');
        const trajetos = await rTrajetos.json();
        const matriculas = await rMatriculas.json();
        const minutos = trajetos.reduce((s, t) => s + Number(t.estudoMinutos || 0) * Number(t.frequenciaSemanal || 0), 0);
        document.getElementById('tempo-semanal').textContent = `${minutos} minutos`;
        document.getElementById('qtd-matriculas').textContent = `${matriculas.length} matrícula(s)`;
        const lista = document.getElementById('lista-matriculas');
        lista.replaceChildren();
        if (matriculas.length === 0) { msg.textContent = 'Você ainda não tem matrículas.'; return; }
        for (const m of matriculas) {
            const cursoResp = await fetch(`${API_URL}/cursos/${m.cursoId}`);
            const curso = cursoResp.ok ? await cursoResp.json() : null;
            const artigo = document.createElement('article'); artigo.className='curso';
            const titulo = document.createElement('h4'); titulo.textContent = curso?.titulo || `Curso ${m.cursoId}`;
            const texto = document.createElement('p'); texto.textContent = `Situação: ${m.status || 'ATIVA'}`;
            const link = document.createElement('a'); link.href=`aula.html?cursoId=${m.cursoId}`; link.textContent='Acessar aulas';
            artigo.append(titulo,texto,link); lista.append(artigo);
        }
    } catch (e) { msg.textContent='Não foi possível consultar seus dados. Confira a API e o usuário de demonstração.'; console.error(e); }
}
document.addEventListener('DOMContentLoaded',carregarResumo);
