import React from 'react';

import Card from '../../components/card';
import { mensagemSucesso, mensagemErro } from '../../components/toastr';

import '../../custom.css';

import { useNavigate } from 'react-router-dom';

import Stack from '@mui/material/Stack';
import { IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import HotelIcon from '@mui/icons-material/Hotel';

import axios from 'axios';

import { BASE_URL } from '../../config/axios';
import { URL_hospedagem } from '../../config/axios';
import { URL_status } from '../../config/axios';

const baseURL = `${URL_hospedagem}/reservas`;


function ListagemReserva() {
  const navigate = useNavigate();

  const cadastrar = () => {
    navigate(`/cadastro-reserva`);
  };

  const editar = (id) => {
    navigate(`/cadastro-reserva/${id}`);
  };

  const cadastrarHospedagem = (id) => {
    navigate(`/cadastro-hospedagem/${'reserva'}/${id}`);
  };
  const editarHospedagem = (id) => {
    navigate(`/cadastro-hospedagem/${id}`);
  };


  const [dados, setDados] = React.useState(null);

  async function excluir(id) {
    const confirmacao = window.confirm('Você tem certeza que deseja excluir a reserva?');
    
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
        mensagemSucesso(`Reserva excluída com sucesso!`);
        setDados(
          dados.filter((dado) => {
            return dado.id !== id;
          })
        );
      })
      .catch(function (error) {
        mensagemErro(`Erro ao excluir Reserva`);
      });
  }

  React.useEffect(() => {
    axios.get(baseURL).then((response) => {
      setDados(response.data);
    });
  }, []);

  const [dados2, setDados2] = React.useState(null);

  React.useEffect(() => {
    axios.get(`${URL_status}/statusReservas`).then((response) => {
      setDados2(response.data);
    });
  }, []);

  //teste botao pra hospedar
  const BotaoHospedar = (dados) => {  
    if (!dados) return null;
    //console.log(dados['status']);
    //console.log(dados.dados.status);
    // if (dados.dados.status > 4) return null;
    
    return (
      <IconButton
          aria-label='cadastrarHospedagem'
          onClick={() => (dados.dados.idHospedagem==null)?cadastrarHospedagem(dados.dados.id):editarHospedagem(dados.dados.idHospedagem)}
        >
          <HotelIcon />
      </IconButton>
    );
  }

  if (!dados2) return null;
  if (!dados) return null;
  

  return (
    <div className='container'>
      <Card title='Listagem de Reservas'>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='bs-component'>
              <button
                type='button'
                className='btn btn-warning'
                onClick={() => cadastrar()}
              >
                Nova Reserva
              </button>
              <table className='table table-hover'>
                <thead>
                  <tr>
                    <th scope='col'>ID</th>
                    <th scope='col'>Status</th>
                    <th scope='col'>Data de Início</th>
                    <th scope='col'>Data de Término</th>
                    <th scope='col'>Valor</th>
                    <th scope='col'>Ações</th>
                  </tr>
                </thead>
                <tbody>
                  {dados.map((dado) => (
                    <tr key={dado.id}>
                      <td>{dado.id}</td>
                      <td>{dados2[dado.status-1].titulo}</td>
                      <td>{dado.dataInicio}</td>
                      <td>{dado.dataFim}</td>
                      <td>{dado.valorResrva}</td>
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
                          <div >
                            <BotaoHospedar dados={dado}  />
                          </div>
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



export default ListagemReserva;