import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Stack from '@mui/material/Stack';

import Card from '../../../components/card';

import FormGroup from '../../../components/form-group';

import { mensagemSucesso, mensagemErro } from '../../../components/toastr';

import '../../../custom.css';

import axios from 'axios';
import { URL_servico } from '../../../config/axios';

function CadastroTipoServico() {
  
  const { idParam } = useParams();

  const navigate = useNavigate();

  const baseURL = `${URL_servico}/tipoServico`;

  const [id, setId] = useState(0);
  const [var0, setVar0] = useState('');
  const [var1, setVar1] = useState('');

  const [dados, setDados] = React.useState([]);

  //ESSA é A PARTE DO BOTAO EDITAR
  
  function inicializar() {
    if (idParam == null) {
      setId('');
      setVar0('');
      setVar1('');
    } else {
      setId(dados.id);
      setVar0(dados.titulo);
      setVar1(dados.descricao);
    }
  }

  async function salvar() {
    let data = {
      id,
      var0,
      var1
    };
    data = JSON.stringify(data);
    if (idParam == null) {
      await axios
        .post(baseURL, data, {
          headers: { 'Content-Type': 'application/json' },
        })
        .then(function (response) {
          mensagemSucesso(`Tipo de serviço ${var0} cadastrado com sucesso!`);
          navigate(`/listagem-tipo-servico`);
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
          mensagemSucesso(`Tipo de serviço ${var0} alterado com sucesso!`);
          navigate(`/listagem-tipo-servico`);
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
    }
  }
  
  useEffect(() => {
      buscar(); // eslint-disable-next-line
  }, [id]);
 
  if (!dados) return null;

  return (
    <div className='container'>
      <Card title='Cadastro de Tipo de Serviço'>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='bs-component'>
              <FormGroup label='Titulo: *' htmlFor='inputTitulo'>
                <input
                  type='text'
                  id='inputTitulo'
                  value={var0}
                  className='form-control'
                  name='titulo'
                  onChange={(e) => setVar0(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Descricao: *' htmlFor='inputDescricao'>
                <input
                  type='text'
                  id='inputDescricao'
                  value={var1}
                  className='form-control'
                  name='descricao'
                  onChange={(e) => setVar1(e.target.value)}
                />
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

export default CadastroTipoServico;