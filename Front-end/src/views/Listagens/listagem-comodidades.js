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
import { URL_comodidade } from '../../config/axios';

const baseURL = `${URL_comodidade}/comodidade`;


function ListagemComodidades() {
  const navigate = useNavigate();

  const cadastrar = () => {
    navigate(`/cadastro-comodidades`);
  };

  const editar = (id) => {
    navigate(`/cadastro-comodidades/${id}`);
  };

  const [dados, setDados] = React.useState(null);

  async function excluir(id) {
    const confirmacao = window.confirm('Você tem certeza que deseja excluir a comodidade?');
    
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
        mensagemSucesso(`Comodidades excluídas com sucesso!`);
        setDados(
          dados.filter((dado) => {
            return dado.id !== id;
          })
        );
      })
      .catch(function (error) {
        mensagemErro(`Erro ao excluir comodidade`);
      });
  }

  React.useEffect(() => {
    axios.get(baseURL).then((response) => {
      setDados(response.data);
    });
  }, []);

  if (!dados) return null;

  return (
    <div className='container'>
      <Card title='Listagem Comodidades'>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='bs-component'>
              <button
                type='button'
                className='btn btn-warning'
                onClick={() => cadastrar()}
              >
                Nova comodidade
              </button>
              <table className='table table-hover'>
                <thead>
                  <tr>
                    <th scope='col'>Nome</th>
                    <th scope='col'>Descrição</th>
                    
                    <th scope='col'>Ações</th>
                  </tr>
                </thead>
                <tbody>
                  {dados.map((dado) => (
                    <tr key={dado.id}>
                      <td>{dado.titulo}</td>
                      <td>{dado.descricao}</td>
                      
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



export default ListagemComodidades;