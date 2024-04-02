import React from 'react';

import Login from './views/login';
import { Route, Routes, BrowserRouter } from 'react-router-dom';

import ListagemCliente from './views/Listagens/listagem-cliente';
import ListagemFuncionario from './views/Listagens/listagem-funcionario';
import ListagemHoteis from './views/Listagens/listagem-hoteis';
import ListagemProdutos from './views/Listagens/listagem-produtos';
import ListagemServicos from './views/Listagens/listagem-servicos';
import ListagemComodidades from './views/Listagens/listagem-comodidades';
import ListagemQuarto from './views/Listagens/listagem-quarto';
import ListagemHospedagem from './views/Listagens/listagem-hospedagem';
import ListagemReserva from './views/Listagens/listagem-reserva';

import ListagemCargos from './views/Listagens/SubListagem/listagem-cargos';
import ListagemTipoCama from './views/Listagens/SubListagem/listagem-tipo-cama';
import ListagemTipoComodidade from './views/Listagens/SubListagem/listagem-tipo-comodidade';
import ListagemTipoProduto from './views/Listagens/SubListagem/listagem-tipo-produto';
import ListagemTipoQuarto from './views/Listagens/SubListagem/listagem-tipo-quarto';
import ListagemTipoServicos from './views/Listagens/SubListagem/listagem-tipo-servico';
import ListagemAvaliacaoQuarto from './views/Listagens/SubListagem/listagem-avaliacaoQuarto';
import ListagemAvaliacaoHospedagem from './views/Listagens/SubListagem/listagem-avaliacaoHospedagem';


import CadastroProduto from './views/Cadastros/cadastro-produto';
import CadastroHotel from './views/Cadastros/cadastro-hotel';
import CadastroCliente from './views/Cadastros/cadastro-cliente';
import CadastroFuncionario from './views/Cadastros/cadastro-funcionario';
import CadastroServicos from './views/Cadastros/cadastro-servicos';
import CadastroQuarto from './views/Cadastros/cadastro-quarto';
import CadastroComodidades from './views/Cadastros/cadastro-comodidades';
import CadastroReserva from './views/Cadastros/cadastro-reserva';
import CadastroHospedagem from './views/Cadastros/cadastro-hospedagem';
import CadastroHorarioServico from './views/Cadastros/SubCadastros/cadastro-horarioServico';
import CadastroAvaliacaoQuarto from './views/Cadastros/SubCadastros/cadastro-avaliacaoQuarto';
import CadastroAvaliacaoHospedagem from './views/Cadastros/SubCadastros/cadastro-avaliacaoHospedagem';


import CadastroTipoProduto from './views/Cadastros/SubCadastros/cadastro-tipo-produto';
import CadastroTipoServico from './views/Cadastros/SubCadastros/cadastro-tipo-servicos';
import CadastroTipoQuarto from './views/Cadastros/SubCadastros/cadastro-tipo-quarto';
import CadastroTipoCama from './views/Cadastros/SubCadastros/cadastro-tipo-cama';
import CadastroTipoComodidade from './views/Cadastros/SubCadastros/cadastro-tipo-comodidade';
import CadastroCargo from './views/Cadastros/SubCadastros/cadastro-cargo';


function Rotas(props) {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/login' element={<Login />} />
        <Route path='/listagem-cliente' element={<ListagemCliente />} />
        <Route path='/listagem-funcionario' element={<ListagemFuncionario />} />
        <Route path='/listagem-hoteis' element={<ListagemHoteis />} />   
        <Route path='/listagem-produtos' element={<ListagemProdutos />} />
        <Route path='/listagem-servicos' element={<ListagemServicos />} />
        <Route path='/listagem-comodidades' element={<ListagemComodidades />} />
        <Route path='/listagem-quarto' element={<ListagemQuarto />} />      
        <Route path='/listagem-reserva' element={<ListagemReserva />} />
        <Route path='/listagem-hospedagem' element={<ListagemHospedagem />} />    

        <Route path='/listagem-cargos' element={<ListagemCargos />} />
        <Route path='/listagem-tipo-cama' element={<ListagemTipoCama />} />
        <Route path='/listagem-tipo-comodidade' element={<ListagemTipoComodidade />} />   
        <Route path='/listagem-tipo-quarto' element={<ListagemTipoQuarto />} />
        <Route path='/listagem-tipo-produto' element={<ListagemTipoProduto />} />
        <Route path='/listagem-tipo-servico' element={<ListagemTipoServicos />} />
        <Route path='/listagem-avaliacao-quarto' element={<ListagemAvaliacaoQuarto />} />
        <Route path='/listagem-avaliacao-hospedagem' element={<ListagemAvaliacaoHospedagem />} />
        

        <Route path='/cadastro-produto/:idParam?' element={<CadastroProduto />} /> 
        <Route path='/cadastro-hotel/:idParam?' element={<CadastroHotel />} />   
        <Route path='/cadastro-cliente/:idParam?' element={<CadastroCliente />} />    
        <Route path='/cadastro-funcionario/:idParam?' element={<CadastroFuncionario />} />   
        <Route path='/cadastro-servicos/:idParam?' element={<CadastroServicos />} />      
        <Route path='/cadastro-quarto/:idParam?' element={<CadastroQuarto />} />      
        <Route path='/cadastro-comodidades/:idParam?' element={<CadastroComodidades />} />   
        <Route path='/cadastro-reserva/:idParam?' element={<CadastroReserva />} />         
        <Route path='/cadastro-hospedagem/:idParam?' element={<CadastroHospedagem />} />   
        <Route path='/cadastro-horarioServico/:idParam?' element={<CadastroHorarioServico />} />   
        <Route path='/cadastro-avaliacaoQuarto/:idParam?' element={<CadastroAvaliacaoQuarto />} />   
        <Route path='/cadastro-avaliacaoHospedagem/:idParam?' element={<CadastroAvaliacaoHospedagem />} />   
        

        <Route path='/cadastro-tipo-produto/:idParam?' element={<CadastroTipoProduto />} />     
        <Route path='/cadastro-tipo-servicos/:idParam?' element={<CadastroTipoServico/>} />  
        <Route path='/cadastro-tipo-quarto/:idParam?' element={<CadastroTipoQuarto />} />     
        <Route path='/cadastro-tipo-cama/:idParam?' element={<CadastroTipoCama />} />   
        <Route path='/cadastro-tipo-comodidade/:idParam?' element={<CadastroTipoComodidade />} />   
        <Route path='/cadastro-cargo/:idParam?' element={<CadastroCargo />} />        
      </Routes>
    </BrowserRouter>
  );
}

export default Rotas;