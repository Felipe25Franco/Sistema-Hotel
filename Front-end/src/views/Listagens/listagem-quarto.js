import React from 'react';

import Card from '../../components/card';
import { mensagemSucesso, mensagemErro } from '../../components/toastr';

import '../../custom.css';

import { useNavigate } from 'react-router-dom';

import Stack from '@mui/material/Stack';
import { IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';

import axios from 'axios';

import { BASE_URL } from '../../config/axios';
import { URL_quarto } from '../../config/axios';
import { URL_status } from '../../config/axios';

const baseURL = `${URL_quarto}/quarto`;


function ListagemQuarto() {
  const navigate = useNavigate();

  const cadastrar = () => {
    navigate(`/cadastro-quarto`);
  };

  const editar = (id) => {
    navigate(`/cadastro-quarto/${id}`);
  };

  const [dados, setDados] = React.useState(null);

  async function excluir(id) {
    const confirmacao = window.confirm('Você tem certeza que deseja excluir o quarto?');
    
    if (!confirmacao) {
      return;
    }
    let data = JSON.stringify({ id });
    let url = `${baseURL}/${id}`;

    await axios
      .delete(url, data, {
        headers: { 'Content-Type': 'application/json' },
      })
      .then(function (response) {
        mensagemSucesso(`Quarto excluído com sucesso!`);
        setDados(
          dados.filter((dado) => {
            return dado.id !== id;
          })
        );
      })
      .catch(function (error) {
        mensagemErro(`Erro ao excluir o Quarto`);
      });
  }


  React.useEffect(() => {
    axios.get(baseURL).then((response) => {
      setDados(response.data);
    });
  }, []);

  const [dados2, setDados2] = React.useState(null);

  React.useEffect(() => {
    axios.get(`${URL_status}/statusQuarto`).then((response) => {
      setDados2(response.data);
    });
  }, []);
  
  if (!dados2) return null;
  if (!dados) return null;

  return (
    <div className='container'>
      <Card title='Listagem de Quartos'>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='bs-component'>
              <button
                type='button'
                className='btn btn-warning'
                onClick={() => cadastrar()}
              >
                Novo Quarto
              </button>
              <table className='table table-hover'>
                <thead>
                  <tr>
                    <th scope='col'>Número</th>
                    <th scope='col'>Andar</th>
                    <th scope='col'>Bloco</th>
                    <th scope='col'>Status</th>
                    <th scope='col'>Ações</th>
                  </tr>
                </thead>
                <tbody>
                  {dados.map((dado) => (
                    <tr key={dado.id}>
                      <td>{dado.numero}</td>
                      <td>{dado.andar}</td>
                      <td>{dado.bloco}</td>
                      <td>{dados2[dado.status-1].titulo}</td>
                      <td>
                        <Stack spacing={1} padding={0} direction='row'>
                          <IconButton
                            aria-label='edit'
                            onClick={() => editar(dado.id)}
                          >
                            <EditIcon />
                          </IconButton>
                          <IconButton
                            aria-label='delete'
                            onClick={() => excluir(dado.id)}
                          >
                            <DeleteIcon />
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



export default ListagemQuarto;