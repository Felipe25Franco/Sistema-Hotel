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
import { URL_hotel } from '../../../config/axios';

const baseURL = `${URL_hospedagem}/avaliacaoHospedagem`;


function ListagemAvaliacaoHospedagem() {
  const navigate = useNavigate();

  const cadastrar = () => {
    navigate(`/cadastro-avaliacaoHospedagem`);
  };

  const editar = (id) => {
    navigate(`/cadastro-avaliacaoHospedagem/${id}`);
  };

  const [dados, setDados] = React.useState(null);
  const [filtroHotel, setFiltroHotel] = React.useState(null);
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
    axios.get(`${URL_hotel}/hotel`).then((response) => {
      setDados2(response.data);
    });
  }, []);

  React.useEffect(() => {
    //setTableData([]);
    //console.log(filtroHotel)
    if((dados)!=null && (filtroHotel)!=null && (filtroHotel)!=' ' && (filtroHotel)!= 0){
      // console.log(dados[5].hotel_id)
      // console.log(filtroHotel)
      // console.log(dados[5].hotel_id === filtroHotel)
      setDadosTela(dados.filter(obj => obj.hotel_id == filtroHotel));
    } else {
      setDadosTela(dados);
    }
  }, [filtroHotel]);

  if (!dados) return null;
  if (!dados2) return null;
  if (!dadosTela) return null;
  //if (!filtroHotel) return null;

  return (
    <div className='container'>
      <Card title='Listagem de Avaliações de Hotéis'>
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
                <FormGroup label='Filtro - Hotel: ' htmlFor='selectHotel'>
                  <select
                    className='form-select'
                    id='selectHotel'
                    name='hotel'
                    value={filtroHotel}
                    onChange={(e) => setFiltroHotel(e.target.value)}
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
                    <th scope='col'>Hotel</th>
                    <th scope='col'>Avaliação</th>
                    <th scope='col'>Ações</th>
                  </tr>
                </thead>
                <tbody>
                  {dadosTela.map((dado) => (
                    <tr key={dado.id}>
                      {/* <td>{dado.hotel_id}</td> */}
                      {/* <td>{dados2.filter(obj => obj.hotel_id == dado.hotel_id).titulo}</td> */}
                      <td>{dados2.find(obj => obj.id === dado.hotel_id).titulo}</td>
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



export default ListagemAvaliacaoHospedagem;