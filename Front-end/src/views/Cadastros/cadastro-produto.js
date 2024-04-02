import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Stack from '@mui/material/Stack';

import Card from '../../components/card';

import FormGroup from '../../components/form-group';

import { mensagemSucesso, mensagemErro } from '../../components/toastr';

import '../../custom.css';

import axios from 'axios';
import { BASE_URL } from '../../config/axios';
import { URL_produto } from '../../config/axios';
import { URL_hotel } from '../../config/axios';

function CadastroProduto() {
  
  const { idParam } = useParams();

  const navigate = useNavigate();

  //const baseURL = `${BASE_URL}/produto`;
  const baseURL = `${URL_produto}/produto`;

  const [id, setId] = useState('');
  const [var0, setVar0] = useState('');//nome
  const [var1, setVar1] = useState('');//descricao
  const [var2, setVar2] = useState('');//preco 
  const [var3, setVar3] = useState('');//qat
  const [var4, setVar4] = useState('');//id tipo
  const [var5, setVar5] = useState('');//id hotel

  //ESSA é A PARTE DO BOTAO EDITAR
  const [dados, setDados] = React.useState([]);
  
  function inicializar() {
    if (idParam == null) {
      setId('');
      setVar0('');
      setVar1('');
      setVar2('');
      setVar3('');
      setVar4('');
      setVar5('');
    } else {
      setId(dados.id);
      setVar0(dados.titulo);
      setVar1(dados.descricao);
      setVar2(dados.preco);
      setVar3(dados.quantidadeestoque);
      setVar4(dados.tipoProduto_id);
      setVar5(dados.hotel_id);
    }
  }

  async function salvar() {
    let data = {
      id,
      var0,
      var1,
      var2,
      var3,
      var4,
      var5
    };
    data = JSON.stringify(data);
    if (idParam == null) {
      await axios
        .post(baseURL, data, {
          headers: { 'Content-Type': 'application/json' },
        })
        .then(function (response) {
          mensagemSucesso(`Produto ${var0} cadastrado com sucesso!`);
          navigate(`/listagem-produtos`);
        })
        .catch(function (error) {
          mensagemErro(error.response.data);
        });
    } else {
      await axios
        .put(`${baseURL}/${idParam}`, data, {
          headers: { 'Content-Type': 'application/json' },
        })
        .then(function (response) {
          mensagemSucesso(`Produto ${var0} alterado com sucesso!`);
          navigate(`/listagem-produtos`);
        })
        .catch(function (error) {
          mensagemErro(error.response.data);
        });
    }
  }

  async function buscar() {
    if (idParam != null) {
      await axios.get(`${baseURL}/${idParam}`).then((response) => {
        setDados(response.data);
      });
      setId(dados.id);
      setVar0(dados.titulo);
      setVar1(dados.descricao);
      setVar2(dados.preco);
      setVar3(dados.quantidadeestoque);
      setVar4(dados.tipoProduto_id);
      setVar5(dados.hotel_id);
    }
  }

  const [dados2, setDados2] = React.useState(null); //tipo Produto
  const [dados3, setDados3] = React.useState(null); //hotel
  
  useEffect(() => {
    //axios.get(`${URL_produto}/tipoProduto`).then((response) => {
    axios.get(`${URL_produto}/tipoProduto`).then((response) => {
      setDados2(response.data);
    });
  }, []);

  useEffect(() => {
    //axios.get(`${URL_produto}/tipoProduto`).then((response) => {
    axios.get(`${URL_hotel}/hotel`).then((response) => {
      setDados3(response.data);
    });
  }, []);
  
  useEffect(() => {
      buscar(); // eslint-disable-next-line
  }, [id]);
 
  if (!dados) return null;
  if (!dados2) return null;
  if (!dados3) return null;
  
  // ESSA é A PARTE DO HTML
  return (
    <div className='container'>
      <Card title='Cadastro de Produto'>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='bs-component'>
            <FormGroup label='Hotel: *' htmlFor='selectHotel'>
                <select
                  className='form-select'
                  id='selectHotel'
                  name='hotel'
                  value={var5}
                  onChange={(e) => setVar5(e.target.value)}
                >
                  <option key='0' value='0'>
                    {' '}
                  </option>
                  {dados3.map((dado) => (
                    <option key={dado.id} value={dado.id}>
                      {dado.titulo}
                    </option>
                  ))}
                </select>
              </FormGroup>
              <FormGroup label='Nome: *' htmlFor='inputNome'>
                <input
                  type='text'
                  id='inputNome'
                  value={var0}
                  className='form-control'
                  name='nome'
                  onChange={(e) => setVar0(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Descrição: *' htmlFor='inputDescricao'>
                <textarea
                  // type='text'
                  id='inputDescricao'
                  value={var1}
                  className='form-control'
                  name='descricao'
                  onChange={(e) => setVar1(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Preço: *' htmlFor='inputPreco'>
                <input
                  type='text'
                  id='inputPreco'
                  value={var2}
                  className='form-control'
                  name='preco'
                  onChange={(e) => setVar2(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Quantidade: *' htmlFor='inputQuantidade'>
                <input
                  type='text'
                  id='inputQuantidade'
                  value={var3}
                  className='form-control'
                  name='quantidade'
                  onChange={(e) => setVar3(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Categoria: *' htmlFor='selectCategoria'>
                <select
                  className='form-select'
                  id='selectCategoria'
                  name='categoria'
                  value={var4}
                  onChange={(e) => setVar4(e.target.value)}
                >
                  <option key='0' value='0'>
                    {' '}
                  </option>
                  {dados2.map((dado) => (
                    <option key={dado.id} value={dado.id}>
                      {dado.titulo}
                    </option>
                  ))}
                </select>
              </FormGroup>
              
              
              <br></br>
              <Stack spacing={1} padding={1} direction='row'>
                <button
                  onClick={salvar}
                  type='button'
                  className='btn btn-success'
                >
                  Salvar
                </button>
                <button
                  onClick={inicializar}
                  type='button'
                  className='btn btn-danger'
                >
                  Cancelar
                </button>
              </Stack>
            </div>
          </div>
        </div>
      </Card>
    </div>
  );
}

export default CadastroProduto;