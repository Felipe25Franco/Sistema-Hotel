import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Stack from '@mui/material/Stack';

import Card from '../../components/card';

import FormGroup from '../../components/form-group';

import { mensagemSucesso, mensagemErro } from '../../components/toastr';

import '../../custom.css';

import axios from 'axios';
import { BASE_URL } from '../../config/axios';
import { URL_comodidade } from '../../config/axios';

function CadastroComodidades() {
  
  const { idParam } = useParams();

  const navigate = useNavigate();

  const baseURL = `${URL_comodidade}/comodidade`;
  const url_tipo = `${URL_comodidade}/tipoComodidade`;

  const [id, setId] = useState(0);
  const [var0, setVar0] = useState('');
  const [var1, setVar1] = useState('');
  const [var2, setVar2] = useState(0);
  
  const [dados, setDados] = React.useState([]); // comodidades
  
  function inicializar() {
    if (idParam == null) {
      setId(0);
      setVar0('');
      setVar1('');
      setVar2(0);
    } else {
      setId(dados.id);
      setVar0(dados.titulo);
      setVar1(dados.descricao);
      setVar2(dados.tipoComodidade_id);
    }
  }

  async function salvar() {
    let data = {
      id,
      var0,
      var1,
      var2
    };
    data = JSON.stringify(data);
    if (idParam == null) {
      await axios
        .post(baseURL, data, {
          headers: { 'Content-Type': 'application/json' },
        })
        .then(function (response) {
          mensagemSucesso(`Comodidade ${var0} cadastrado com sucesso!`);
          navigate(`/listagem-comodidades`);
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
          mensagemSucesso(`Comodidade ${var0} alterado com sucesso!`);
          navigate(`/listagem-comodidades`);
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
      setVar2(dados.tipoComodidade_id);
    }
  }
  
  const [dados2, setDados2] = React.useState(null); //cat comodidades

  useEffect(() => {
    axios.get(`${url_tipo}`).then((response) => {
      setDados2(response.data);
    });
  }, []);
  
  useEffect(() => {
    buscar(); // eslint-disable-next-line
  }, [id]);

  if (!dados) return null;
  if (!dados2) return null;

  return (
    <div className='container'>
      <Card title='Cadastro de Comodidade'>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='bs-component'>
              <FormGroup label='Título: *' htmlFor='inputTitulo'>
                <input
                  type='text'
                  id='inputTitulo'
                  value={var0}
                  className='form-control'
                  name='titulo'
                  onChange={(e) => setVar0(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Descrição: *' htmlFor='inputDescricao'>
                <textarea 
                  id='inputDescricao' 
                  name="descricao" 
                  className='form-control' 
                  value={var1} 
                  rows="3" cols="150" 
                  onChange={(e) => setVar1(e.target.value)}>
                </textarea>
              </FormGroup>
              <FormGroup label='Categoria: *' htmlFor='selectCategoria'>
                <select
                  className='form-select'
                  id='selectCategoria'
                  name='categoria'
                  value={var2}
                  onChange={(e) => setVar2(e.target.value)}
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

export default CadastroComodidades;