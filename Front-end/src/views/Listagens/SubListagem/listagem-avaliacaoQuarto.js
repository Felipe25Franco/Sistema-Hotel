import React from 'react';

import Card from '../../../components/card';
import { mensagemSucesso, mensagemErro } from '../../../components/toastr';

import '../../../custom.css';

import { useNavigate } from 'react-router-dom';
import FormGroup from '../../../components/form-group';

import Stack from '@mui/material/Stack';
import { IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';

import axios from 'axios';

import { URL_hospedagem } from '../../../config/axios';
import { URL_quarto } from '../../../config/axios';

const baseURL = `${URL_hospedagem}/avaliacaoQuarto`;


function ListagemAvaliacaoQuarto() {
  const navigate = useNavigate();

  const cadastrar = () => {
    navigate(`/cadastro-avaliacaoQuarto`);
  };

  const editar = (id) => {
    navigate(`/cadastro-avaliacaoQuarto/${id}`);
  };

  const [dados, setDados] = React.useState(null);
  const [filtroTipoQuarto, setFiltroTipoQuarto] = React.useState(null);
  const [dados2, setDados2] = React.useState(null);
  const [dadosTela, setDadosTela] = React.useState(null);

  async function excluir(id) {
    const confirmacao = window.confirm('Você tem certeza que deseja excluir a Avaliação?');
    
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
        mensagemSucesso(`Avaliação excluída com sucesso!`);
        setDados(
          dados.filter((dado) => {
            return dado.id !== id;
          })
        );
      })
      .catch(function (error) {
        mensagemErro(`Erro ao excluir Avaliação`);
      });
  }

  React.useEffect(() => {
    axios.get(baseURL).then((response) => {
      setDados(response.data);
      setDadosTela(response.data);
    });
  }, []);

  React.useEffect(() => {
    axios.get(`${URL_quarto}/tipoQuarto`).then((response) => {
      setDados2(response.data);
    });
  }, []);

  React.useEffect(() => {
    //setTableData([]);
    //console.log(filtroTipoQuarto)
    if((dados)!=null && (filtroTipoQuarto)!=null && (filtroTipoQuarto)!=' ' && (filtroTipoQuarto)!= 0){
      // console.log(dados[5].tipoQuarto_id)
      // console.log(filtroTipoQuarto)
      // console.log(dados[5].tipoQuarto_id === filtroTipoQuarto)
      setDadosTela(dados.filter(obj => obj.tipoQuarto_id == filtroTipoQuarto));
    } else {
      setDadosTela(dados);
    }
  }, [filtroTipoQuarto]);

  if (!dados) return null;
  if (!dados2) return null;
  if (!dadosTela) return null;
  //if (!filtroTipoQuarto) return null;

  return (
    <div className='container'>
      <Card title='Listagem de Avaliações de Quartos'>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='bs-component'>
              <Stack spacing={10} padding={0} direction='row'>
                <button
                  type='button'
                  className='btn btn-warning'
                  onClick={() => cadastrar()}
                >
                  Nova Avaliação
                </button>
                <FormGroup label='Filtro - Tipo de Quarto: ' htmlFor='selectTipoQuarto'>
                  <select
                    className='form-select'
                    id='selectTipoQuarto'
                    name='tipoQuarto'
                    value={filtroTipoQuarto}
                    onChange={(e) => setFiltroTipoQuarto(e.target.value)}
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
              </Stack>
              <table className='table table-hover'>
                <thead>
                  <tr>
                    <th scope='col'>Tipo do Quarto</th>
                    <th scope='col'>Avaliação</th>
                    <th scope='col'>Ações</th>
                  </tr>
                </thead>
                <tbody>
                  {dadosTela.map((dado) => (
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



export default ListagemAvaliacaoQuarto;