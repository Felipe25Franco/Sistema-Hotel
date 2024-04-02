import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Stack from '@mui/material/Stack';

import Card from '../../../components/card';

import FormGroup from '../../../components/form-group';

import { mensagemSucesso, mensagemErro } from '../../../components/toastr';

import '../../../custom.css';

import axios from 'axios';
import { URL_cargos } from '../../../config/axios';
import { URL_hotel } from '../../../config/axios';

function CadastroCargo() {
  
  const { idParam } = useParams();

  const navigate = useNavigate();

  const baseURL = `${URL_cargos}/cargo`;

  const [id, setId] = useState(0);
  const [var0, setVar0] = useState('');//cargp
  const [var1, setVar1] = useState('');//desc
  const [var2, setVar2] = useState(0);//salario
  const [var3, setVar3] = useState(0);//hotelid

   //ESSA é A PARTE DO BOTAO EDITAR
   const [dados, setDados] = React.useState([]);
  
   function inicializar() {
     if (idParam == null) {
       setId('');
       setVar0('');
       setVar1('');
       setVar2('');
       setVar3('');
     } else {
       setId(dados.id);
       setVar0(dados.cargo);
       setVar1(dados.descricao);
       setVar2(dados.salarioBase);
       setVar3(dados.hotel_id);
     }
   }
 
   async function salvar() {
     let data = {
       id,
       var0,
       var1,
       var2,
       var3
     };
     data = JSON.stringify(data);
     if (idParam == null) {
       await axios
         .post(baseURL, data, {
           headers: { 'Content-Type': 'application/json' },
         })
         .then(function (response) {
           mensagemSucesso(`Cargo ${var0} cadastrado com sucesso!`);
           navigate(`/listagem-cargos`);
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
           mensagemSucesso(`Cargo ${var0} alterado com sucesso!`);
           navigate(`/listagem-cargos`);
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
       setVar0(dados.cargo);
       setVar1(dados.descricao);
       setVar2(dados.salarioBase);
       setVar3(dados.hotel_id);
     }
   }
 
   const [dados3, setDados3] = React.useState(null); //hotel
 
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
   if (!dados3) return null;

  return (
    <div className='container'>
      <Card title='Cadastro de Cargo'>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='bs-component'>
            <FormGroup label='Hotel: *' htmlFor='selectHotel'>
                <select
                  className='form-select'
                  id='selectHotel'
                  name='hotel'
                  value={var3}
                  onChange={(e) => setVar3(e.target.value)}
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
              <FormGroup label='Cargo: *' htmlFor='inputCargo'>
                <input
                  type='text'
                  id='inputCargo'
                  value={var0}
                  className='form-control'
                  name='cargo'
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
              <FormGroup label='Salário Base: *' htmlFor='inputSalarioBase'>
                <input
                  type='text'
                  id='inputSalarioBase'
                  value={var2}
                  className='form-control'
                  name='salariobase'
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

export default CadastroCargo;