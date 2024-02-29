import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Stack from '@mui/material/Stack';

import Card from '../../../components/card';

import FormGroup from '../../../components/form-group';

import { mensagemSucesso, mensagemErro } from '../../../components/toastr';

import '../../../custom.css';

import axios from 'axios';
import { BASE_URL } from '../../../config/axios';

function CadastroHorarioServico() {
  
  const { idParam } = useParams();

  const navigate = useNavigate();

  const baseURL = `${BASE_URL}/horarioServico`;

  const [id, setId] = useState('');
  const [var1, setVar1] = useState('');
  const [var2, setVar2] = useState('');
  const [var3, setVar3] = useState('');
  const [var4, setVar4] = useState('');
  const [var5, setVar5] = useState('');

  return (
    <div className='container'>
      <Card title='Cadastro de Horários de Serviço'>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='bs-component'>
              <FormGroup label='Status: *' htmlFor='inputStatus'>
                <input
                  type='text'
                  id='inputStatus'
                  value={var1}
                  className='form-control'
                  name='status'
                  onChange={(e) => setVar1(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Vagas Totais: *' htmlFor='inputVagaTotal'>
                <input
                  type='text'
                  id='inputVagaTotal'
                  value={var2}
                  className='form-control'
                  name='vagatotal'
                  onChange={(e) => setVar2(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Hora Início: *' htmlFor='inputHoraInicio'>
                <input
                  type='text'
                  id='inputHoraInicio'
                  value={var3}
                  className='form-control'
                  name='horainicio'
                  onChange={(e) => setVar3(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Hora Fim: *' htmlFor='inputHoraFim'>
                <input
                  type='text'
                  id='inputHoraFim'
                  value={var4}
                  className='form-control'
                  name='horafim'
                  onChange={(e) => setVar4(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Data: *' htmlFor='inputData'>
                <input
                  type='text'
                  id='inputData'
                  value={var5}
                  className='form-control'
                  name='data'
                  onChange={(e) => setVar5(e.target.value)}
                />
              </FormGroup>

              <br></br>
              <Stack spacing={1} padding={1} direction='row'>
                <button
                  type='button'
                  className='btn btn-success'
                >
                  Salvar
                </button>
                <button
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

export default CadastroHorarioServico;