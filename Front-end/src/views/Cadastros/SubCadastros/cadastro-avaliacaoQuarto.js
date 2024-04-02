import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Stack from '@mui/material/Stack';

import Card from '../../../components/card';

import FormGroup from '../../../components/form-group';

import { mensagemSucesso, mensagemErro } from '../../../components/toastr';

import '../../../custom.css';

import axios from 'axios';
import { URL_hospedagem } from '../../../config/axios';
import { URL_quarto } from '../../../config/axios';

const baseURL = `${URL_hospedagem}/avaliacaoQuarto`;

function CadastroAvaliacaoQuarto() {
  
  const { idParam } = useParams();

  const navigate = useNavigate();


  const [id, setId] = useState('');
  const [var0, setVar0] = useState('');//nota
  const [var1, setVar1] = useState('');//comentario
  const [var2, setVar2] = useState('');//tipoQuarto_id

  const [dados, setDados] = React.useState([]);

  function inicializar() {
    if (idParam == null) {
      setId('');
      setVar0('');
      setVar1('');
      setVar2('');
    } else {
      setId(dados.id);
      setVar0(dados.nota);
      setVar1(dados.comentario);
      setVar2(dados.tipoQuarto_id);
    }
  }

  async function salvar() {
    let data = {
      id,
      var0,
      var1,
      var2,
    };
    data = JSON.stringify(data);
    if (idParam == null) {
      await axios
        .post(baseURL, data, {
          headers: { 'Content-Type': 'application/json' },
        })
        .then(function (response) {
          mensagemSucesso(`Avaliação cadastrada com sucesso!`);
          navigate(`/listagem-avaliacao-quarto`);
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
          mensagemSucesso(`Avaliação alterada com sucesso!`);
          navigate(`/listagem-avaliacao-quarto`);
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
      setVar0(dados.nota);
      setVar1(dados.comentario);
      setVar2(dados.tipoQuarto_id);
    }
  }

  const [dados2, setDados2] = React.useState(null); //tipo quarto

  useEffect(() => {
    axios.get(`${URL_quarto}/tipoQuarto`).then((response) => {
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
      <Card title='Cadastro de Avaliação do Quarto'>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='bs-component'>
              <FormGroup label='Tipo de Quarto: *' htmlFor='selectTipo'>
                <select
                  className='form-select'
                  id='selectTipo'
                  name='tipo'
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
              <FormGroup label='Nota: *' htmlFor='inputNota'>
                <input
                  type='number'
                  min = '0.5'
                  max = '5'
                  step ='0.5'
                  id='inputNota'
                  value={var0}
                  className='form-control'
                  name='nota'
                  onChange={(e) => setVar0(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Comentário: *' htmlFor='inputComentario'>
                <input
                  type='text'
                  id='inputComentario'
                  value={var1}
                  className='form-control'
                  name='comentario'
                  onChange={(e) => setVar1(e.target.value)}
                />
              </FormGroup>
              
              <br></br>
              <Stack spacing={1} padding={1} direction='row'>
                <button
                  type='button'
                  className='btn btn-success'
                  onClick={salvar}
                >
                  Salvar
                </button>
                <button
                  type='button'
                  className='btn btn-danger'
                  onClick={inicializar}
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

export default CadastroAvaliacaoQuarto;