import React, { useState, useEffect }  from 'react';

import Card from '../../../components/card';
import { mensagemSucesso, mensagemErro } from '../../../components/toastr';

import '../../../custom.css';

import { useNavigate,useParams } from 'react-router-dom';
import FormGroup from '../../../components/form-group';

import Stack from '@mui/material/Stack';
import { IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';

import axios from 'axios';

import { URL_hospedagem } from '../../../config/axios';
import { URL_quarto, URL_avaliacaoQuarto } from '../../../config/axios';

const baseURL = `${URL_avaliacaoQuarto}`;


function ListagemAvaliacaoQuartoHospedagem() {
  const { idParam } = useParams();

  const navigate = useNavigate();


  const editar = (id) => {
    navigate(`/cadastro-avaliacaoQuarto/${id}`);
  };

  const [dados, setDados] = React.useState(null);

  React.useEffect(() => {
    console.log(idParam)
    if (idParam != null) {
      axios.get(`${baseURL}/${idParam}`).then((response) => {
        setDados(response.data);
      });
    }
  }, [idParam]);
  
  const [dados2, setDados2] = React.useState(null);

 React.useEffect(() => {
    axios.get(`${URL_quarto}/tipoQuartos`).then((response) => {
      setDados2(response.data);
    });
  }, []);
  
  if (!dados2) return null;
  if (!dados) return null;
  console.log(dados)
  return (
    <div className='container'>
      <Card title={`Listagem de Avaliações de Quartos - Hospedagem ID: ${idParam}`}>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='bs-component'>
              <table className='table table-hover'>
                <thead>
                  <tr>
                    <th scope='col'>Tipo do Quarto</th>
                    <th scope='col'>Avaliação</th>
                    <th scope='col'>Ações</th>
                  </tr>
                </thead>
                <tbody>
                  {dados.map((dado) => (
                    <tr key={dado.id}>
                      {/* <td>{dado.tipoQuarto_id}</td> */}
                      {/* <td>{dados2.filter(obj => obj.tipoQuarto_id == dado.tipoQuarto_id).titulo}</td> */}
                      <td>{dados2.find(obj => obj.id === dado.tipoQuarto_id).titulo}</td>
                      <td>{dado.nota}</td>
                      <td>
                        <Stack spacing={1} padding={0} direction='row'>
                          <IconButton
                            aria-label='edit'
                            onClick={() => editar(dado.id)}
                          >
                            <EditIcon />
                          </IconButton>
                        </Stack>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>{' '}
            </div>
          </div>
        </div>
      </Card>
    </div>
  );
}



export default ListagemAvaliacaoQuartoHospedagem;