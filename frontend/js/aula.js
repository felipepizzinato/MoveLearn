const cursoSelect=document.getElementById('curso');
const aulaSelect=document.getElementById('aula');
const mensagem=document.getElementById('mensagem');
let aulaAtual=null;
async function api(url,opcoes){const r=await fetch(API_URL+url,opcoes);if(!r.ok)throw new Error(`HTTP ${r.status}`);if(r.status===204)return null;return r.json();}
async function carregarCursos(){const cursos=await api('/cursos');for(const c of cursos){const op=document.createElement('option');op.value=c.cursoId;op.textContent=c.titulo;cursoSelect.append(op);}const p=new URLSearchParams(location.search).get('cursoId');if(p&&cursos.some(c=>String(c.cursoId)===p)){cursoSelect.value=p;await carregarAulas();}}
async function carregarAulas(){
    aulaSelect.replaceChildren();document.getElementById('conteudo-aula').hidden=true;
    if(!cursoSelect.value)return;
    const aulas=await api(`/cursos/${cursoSelect.value}/aulas`);
    const vazio=document.createElement('option');vazio.value='';vazio.textContent='Selecione a aula';aulaSelect.append(vazio);
    for(const a of aulas){const op=document.createElement('option');op.value=a.aulaId;op.textContent=`${a.ordem}. ${a.titulo}`;aulaSelect.append(op);}
    if(!aulas.length)mensagem.textContent='Nenhuma aula cadastrada neste curso.';else mensagem.textContent='';
}
async function carregarAula(){
    const box=document.getElementById('conteudo-aula');box.hidden=true;
    if(!aulaSelect.value)return;
    aulaAtual=await api(`/aulas/${aulaSelect.value}`);
    const questoes=await api(`/aulas/${aulaSelect.value}/questoes`);
    document.getElementById('titulo-aula').textContent=aulaAtual.titulo;
    document.getElementById('duracao-aula').textContent=`Duração estimada: ${aulaAtual.duracaoMinutos} minutos`;
    document.getElementById('texto-aula').textContent=aulaAtual.conteudo||'';
    const lista=document.getElementById('lista-questoes');lista.replaceChildren();
    for(const [i,q] of questoes.entries()){
        const fieldset=document.createElement('fieldset');fieldset.dataset.questaoId=q.questaoId;
        const legend=document.createElement('legend');legend.textContent=`${i+1}. ${q.enunciado}`;fieldset.append(legend);
        for(const alternativa of q.alternativas){const label=document.createElement('label');label.className='alternativa';const input=document.createElement('input');input.type='radio';input.name=`questao-${q.questaoId}`;input.value=alternativa.alternativaId;label.append(input,document.createTextNode(' '+alternativa.texto));fieldset.append(label);}
        lista.append(fieldset);
    }
    document.getElementById('resultado').textContent='';box.hidden=false;
}
async function concluir(){
    if(!aulaAtual)return;
    const resultado=document.getElementById('resultado');
    const campos=[...document.querySelectorAll('#lista-questoes fieldset')];
    if(campos.some(c=>!c.querySelector('input:checked'))){resultado.textContent='Responda todas as questões antes de concluir.';return;}
    try{
        let acertos=0;
        for(const campo of campos){const resposta=await api(`/aulas/${aulaAtual.aulaId}/respostas`,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({questaoId:Number(campo.dataset.questaoId),alternativaId:Number(campo.querySelector('input:checked').value)})});if(resposta.correta)acertos++;}
        if(acertos!==campos.length){resultado.textContent=`Você acertou ${acertos} de ${campos.length}. Revise as respostas e tente novamente.`;return;}
        const matriculas=await api(`/matriculas?usuarioId=${USUARIO_ID}`);
        let matricula=matriculas.find(m=>String(m.cursoId)===String(aulaAtual.cursoId));
        if(!matricula)matricula=await api('/matriculas',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({usuarioId:USUARIO_ID,cursoId:Number(aulaAtual.cursoId)})});
        await api('/progressos',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({matriculaId:matricula.matriculaId,aulaId:aulaAtual.aulaId})});
        resultado.textContent='Parabéns! Aula concluída e progresso registrado.';
    }catch(e){resultado.textContent='Não foi possível registrar a conclusão. Confira a matrícula e a API.';console.error(e);}
}
cursoSelect.addEventListener('change',()=>carregarAulas().catch(e=>{mensagem.textContent='Erro ao consultar aulas.';console.error(e)}));
aulaSelect.addEventListener('change',()=>carregarAula().catch(e=>{mensagem.textContent='Erro ao consultar aula.';console.error(e)}));
document.getElementById('concluir').addEventListener('click',concluir);
carregarCursos().catch(e=>{mensagem.textContent='Erro ao carregar cursos.';console.error(e)});
