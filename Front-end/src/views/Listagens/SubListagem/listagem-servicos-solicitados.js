import React from 'react';

import Card from '../../../components/card';
import { mensagemSucesso, mensagemErro } from '../../../components/toastr';

import '../../../custom.css';

import { useNavigate,useParams } from 'react-router-dom';

import Stack from '@mui/material/Stack';
import { IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';

import axios from 'axios';

import { BASE_URL } from '../../../config/axios';
import { URL_servico } from '../../../config/axios';
import { URL_status } from '../../../config/axios';

const baseURL = `${URL_servico}/servicoSolicitados`;


function ListagemServicosSolicitados() {
    const { idParam } = useParams();

    const navigate = useNavigate();

    const cadastrar = (id) => {
        navigate(`/cadastro-servico-solicitado/${'hospedagem'}/${id}`);
    };

    const editar = (id) => {
        navigate(`/cadastro-servico-solicitado/${id}`);
    };

    const [dados, setDados] = React.useState(null);

    async function excluir(id) {
        const confirmacao = window.confirm('Você tem certeza que deseja excluir o serviço-solicitados?');
    
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
                mensagemSucesso(`Serviço excluído com sucesso!`);
                setDados(
                    dados.filter((dado) => {
                        return dado.id !== id;
                    })
                );
            })
            .catch(function (error) {
                mensagemErro(`Erro ao excluir o Serviço`);
            });
    }

    React.useEffect(() => {
        axios.get(`${URL_servico}/servicoSolicitados/hospedagens/${idParam}`).then((response) => {
            setDados(response.data);
        });
    }, []);

    //comentar o de cima e usar o de baixo qdo....

    // React.useEffect(() => {
    //     console.log(idParam)
    //     if (idParam != null) {
    //       axios.get(`${baseURL}/${idParam}`).then((response) => {
    //         setDados(response.data);
    //       });
    //     }
    //   }, [idParam]);

    const [dados2, setDados2] = React.useState(null);
  
    React.useEffect(() => {
      axios.get(`${URL_servico}/servicos`).then((response) => {
        setDados2(response.data);
      });
    }, []);
    
    if (!dados2) return null;
    if (!dados) return null;

    return (
        <div className='container'>
            <Card title={`Listagem de Serviços Solicitados da Hospedagem ID: ${idParam}`}>
                <div className='row'>
                    <div className='col-lg-12'>
                        <div className='bs-component'>
                            <button
                                type='button'
                                className='btn btn-warning'
                                onClick={() => cadastrar(idParam)}
                            >
                                Novo Serviço
                            </button>
                            <table className='table table-hover'>
                                <thead>
                                    <tr>
                                        <th scope='col'>Serviço</th>
                                        <th scope='col'>Valor</th>
                                        <th scope='col'>Ações</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {dados.map((dado) => (
                                        <tr key={dado.id}>
                                            <td>{dados2.find(obj => obj.id === dado.idServico).titulo}</td>
                                            <td>{dado.valorTotal}</td>
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



export default ListagemServicosSolicitados;