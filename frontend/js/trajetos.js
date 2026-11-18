const formulario=document.getElementById('form-trajeto');
const mensagem=document.getElementById('mensagem');
const lista=document.getElementById('lista-trajetos');
const cancelar=document.getElementById('cancelar');
async function requisicao(url,opcoes) { const r=await fetch(API_URL+url,opcoes); if(!r.ok) throw new Error(`HTTP ${r.status}`); if(r.status===204) return null; return r.json(); }
async function carregarTransportes(){
    const dados=await requisicao('/transportes');
    const campo=document.getElementById('transporte');
    for(const t of dados){const option=document.createElement('option');option.value=t.transporteId; option.textContent=t.nome; campo.append(option);}
}
function limparFormulario(){formulario.reset();document.getElementById('trajeto-id').value='';document.getElementById('titulo-form').textContent='Novo trajeto';cancelar.hidden=true;}
async function carregarTrajetos(){
    const dados=await requisicao(`/trajetos?usuarioId=${USUARIO_ID}`);
    lista.replaceChildren();
    if(!dados.length){lista.textContent='Nenhum trajeto cadastrado.';return;}
    for(const t of dados){
        const item=document.createElement('article');item.className='curso';
        const titulo=document.createElement('h4');titulo.textContent=t.nome;
        const info=document.createElement('p');info.textContent=`${t.duracaoMinutos} min de trajeto | ${t.estudoMinutos} min para estudar | ${t.frequenciaSemanal} dia(s)/semana`;
        const acoes=document.createElement('div');acoes.className='acoes';
        const editar=document.createElement('button');editar.className='secundario';editar.textContent='Editar';editar.onclick=()=>{
            document.getElementById('trajeto-id').value=t.trajetoId;document.getElementById('nome').value=t.nome;
            document.getElementById('transporte').value=t.transporteId;document.getElementById('duracao').value=t.duracaoMinutos;
            document.getElementById('estudo').value=t.estudoMinutos;document.getElementById('frequencia').value=t.frequenciaSemanal;
            document.getElementById('titulo-form').textContent='Editar trajeto';cancelar.hidden=false;window.scrollTo({top:0,behavior:'smooth'});
        };
        const excluir=document.createElement('button');excluir.className='secundario';excluir.textContent='Excluir';excluir.onclick=async()=>{
            if(!confirm('Deseja excluir este trajeto?'))return;
            try{await requisicao(`/trajetos/${t.trajetoId}`,{method:'DELETE'});mensagem.textContent='Trajeto excluído.';await carregarTrajetos();}
            catch(e){mensagem.textContent='Não foi possível excluir o trajeto.';console.error(e);}
        };
        acoes.append(editar,excluir);item.append(titulo,info,acoes);lista.append(item);
    }
}
formulario.addEventListener('submit',async event=>{
    event.preventDefault();
    const duracao=Number(document.getElementById('duracao').value);
    const estudo=Number(document.getElementById('estudo').value);
    if(estudo>duracao){mensagem.textContent='O tempo de estudo não pode ultrapassar a duração.';return;}
    const id=document.getElementById('trajeto-id').value;
    const trajeto={nome:document.getElementById('nome').value.trim(),duracaoMinutos:duracao,estudoMinutos:estudo,frequenciaSemanal:Number(document.getElementById('frequencia').value),usuarioId:USUARIO_ID,transporteId:Number(document.getElementById('transporte').value)};
    try{await requisicao(id?`/trajetos/${id}`:'/trajetos',{method:id?'PUT':'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(trajeto)});
        limparFormulario();mensagem.textContent='Trajeto salvo com sucesso.';await carregarTrajetos();}
    catch(e){mensagem.textContent='Erro ao salvar. Proibido meio de transporte como motorista';console.error(e);}
});
cancelar.onclick=limparFormulario;
(async()=>{try{await carregarTransportes();await carregarTrajetos();}catch(e){mensagem.textContent='Não foi possível carregar os dados. Confira a API e o usuário.';console.error(e);}})();
