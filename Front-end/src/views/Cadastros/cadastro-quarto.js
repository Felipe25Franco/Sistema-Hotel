import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Stack from '@mui/material/Stack';

import Card from '../../components/card';

import FormGroup from '../../components/form-group';

import { mensagemSucesso, mensagemErro } from '../../components/toastr';

import '../../custom.css';

import axios from 'axios';
import { BASE_URL } from '../../config/axios';
import { URL_quarto } from '../../config/axios';
import { URL_hotel } from '../../config/axios';
import { URL_status } from '../../config/axios';

function CadastroQuarto() {
  
  const { idParam } = useParams();

  const navigate = useNavigate();

  const baseURL = `${URL_quarto}/quarto`;

  const [id, setId] = useState(0);
  const [var0, setVar0] = useState('');//num
  const [var1, setVar1] = useState('');//andar
  const [var2, setVar2] = useState('');//bloco
  const [var3, setVar3] = useState('');//status
  const [var4, setVar4] = useState('');//hotelid
  const [var5, setVar5] = useState('');//tipoquartoid
  const [var6, setVar6] = useState('');
  const [var7, setVar7] = useState('');
  const [var8, setVar8] = useState('');
  const [var9, setVar9] = useState('');

  const [dados, setDados] = React.useState([]); // quarto

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
      setVar0(dados.numero);
      setVar1(dados.andar);
      setVar2(dados.bloco);
      setVar3(dados.status);
      setVar4(dados.hotel_id);
      setVar5(dados.tipoQuarto_id);
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
          mensagemSucesso(`Quarto ${var0} cadastrado com sucesso!`);
          navigate(`/listagem-quarto`);
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
          mensagemSucesso(`Quarto ${var0} alterado com sucesso!`);
          navigate(`/listagem-quarto`);
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
      setVar0(dados.numero);
      setVar1(dados.andar);
      setVar2(dados.bloco);
      setVar3(dados.status);
      setVar4(dados.hotel_id);
      setVar5(dados.tipoQuarto_id);
    }
  }

  const [dados2, setDados2] = React.useState(null); //tipo quarto

  useEffect(() => {
    axios.get(`${URL_quarto}/tipoQuarto`).then((response) => {
      setDados2(response.data);
    });
  }, []);
  
  const [dados3, setDados3] = React.useState(null); //tipo quarto

  useEffect(() => {
    axios.get(`${URL_hotel}/hotel`).then((response) => {
      setDados3(response.data);
    });
  }, []);

  const [dados4, setDados4] = React.useState(null); //tipo Produto
  
  useEffect(() => {
    axios.get(`${URL_status}/statusQuarto`).then((response) => {
      setDados4(response.data);
    });
  }, []);

  useEffect(() => {
      buscar(); // eslint-disable-next-line
  }, [id]);

  if (!dados) return null;
  if (!dados2) return null;
  if (!dados3) return null;
  if (!dados4) return null;


  return (
    <div className='container'>
      <Card title='Cadastro de Quarto'>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='bs-component'>
              <FormGroup label='Hotel: *' htmlFor='selectHotel'>
                <select
                  className='form-select'
                  id='selectHotel'
                  name='Hotel'
                  value={var4}
                  onChange={(e) => setVar4(e.target.value)}
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
              <FormGroup label='Status: *' htmlFor='selectStatus'>
                <select
                  className='form-select'
                  id='selectStatus'
                  name='status'
                  value={var3}
                  onChange={(e) => setVar3(e.target.value)}
                >
                  <option key='0' value='0'>
                    {' '}
                  </option>
                  {dados4.map((dado) => (
                    <option key={dado.id} value={dado.id}>
                      {dado.titulo}
                    </option>
                  ))}
                </select>
              </FormGroup>
              <FormGroup label='Tipo: *' htmlFor='selectTipo'>
                <select
                  className='form-select'
                  id='selectTipo'
                  name='tipo'
                  value={var5}
                  onChange={(e) => setVar5(e.target.value)}
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
              <FormGroup label='Número: *' htmlFor='inputNumero'>
                <input
                  type='number'
                  id='inputNumero'
                  value={var0}
                  className='form-control'
                  name='numero'
                  onChange={(e) => setVar0(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Andar: *' htmlFor='inputAndar'>
                <input
                  type='number'
                  id='inputAndar'
                  value={var1}
                  className='form-control'
                  name='andar'
                  onChange={(e) => setVar1(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Bloco: *' htmlFor='inputBloco'>
                <input
                  type='number'
                  id='inputBloco'
                  value={var2}
                  className='form-control'
                  name='bloco'
                  onChange={(e) => setVar2(e.target.value)}
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

export default CadastroQuarto;
