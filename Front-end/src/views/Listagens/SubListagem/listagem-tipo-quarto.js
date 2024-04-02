import React from 'react';

import Card from '../../../components/card';
import { mensagemSucesso, mensagemErro } from '../../../components/toastr';

import '../../../custom.css';

import { useNavigate } from 'react-router-dom';

import Stack from '@mui/material/Stack';
import { IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';

import axios from 'axios';

import { URL_tipoQuarto } from '../../../config/axios';

const baseURL = `${URL_tipoQuarto}/tipoQuarto`;


function ListagemTipoQuarto() {
  const navigate = useNavigate();

  const cadastrar = () => {
    navigate(`/cadastro-tipo-quarto`);
  };

  const editar = (id) => {
    navigate(`/cadastro-tipo-quarto/${id}`);
  };

  const [dados, setDados] = React.useState(null);

  async function excluir(id) {
    const confirmacao = window.confirm('Você tem certeza que deseja excluir o tipo de quarto?');
    
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
        mensagemSucesso(`Tipo de quarto excluído com sucesso!`);
        setDados(
          dados.filter((dado) => {
            return dado.id !== id;
          })
        );
      })
      .catch(function (error) {
        mensagemErro(`Erro ao excluir tipo de quarto`);
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
      <Card title='Listagem de Tipos de Quarto'>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='bs-component'>
              <button
                type='button'
                className='btn btn-warning'
                onClick={() => cadastrar()}
              >
                Novo Tipo de Quarto
              </button>
              <table className='table table-hover'>
                <thead>
                  <tr>
                    <th scope='col'>Tipo</th>
                    <th scope='col'>Valor</th>
                    <th scope='col'>Máximo de Ocupantes</th>
                    <th scope='col'>Tamanho</th>
                    <th scope='col'>Avaliação</th>
                    <th scope='col'>Ações</th>
                  </tr>
                </thead>
                <tbody>
                  {dados.map((dado) => (
                    <tr key={dado.id}>
                      <td>{dado.titulo}</td>
                      <td>{dado.precoBase}</td>
                      <td>{dado.limiteAdulto + dado.limiteCrianca}</td>
                      <td>{dado.area}</td>
                      <td>{dado.avaliacaoMedia}</td>
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



export default ListagemTipoQuarto;
