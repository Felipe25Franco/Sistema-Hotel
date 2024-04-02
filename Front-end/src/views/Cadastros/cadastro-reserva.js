import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Stack from '@mui/material/Stack';
import { IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import AddBoxIcon from '@mui/icons-material/AddBox';

import Card from '../../components/card';

import FormGroup from '../../components/form-group';

import { mensagemSucesso, mensagemErro } from '../../components/toastr';

import '../../custom.css';

import axios from 'axios';
import { BASE_URL, URL_quarto, URL_status } from '../../config/axios';
import { URL_hospedagem,  URL_hotel} from '../../config/axios';

function CadastroReserva() {
  
  const { idParam } = useParams();

  const navigate = useNavigate();

  const baseURL = `${URL_hospedagem}/reserva`;

  const [id, setId] = useState('');
  const [var0, setVar0] = useState('');
  const [var1, setVar1] = useState('');
  const [var2, setVar2] = useState('');
  const [var3, setVar3] = useState('');
  const [var4, setVar4] = useState('');
  const [var5, setVar5] = useState('');
  const [var6, setVar6] = useState('');
  const [var7, setVar7] = useState('');
  const [var8, setVar8] = useState('');

  const [dados, setDados] = React.useState([]);
  const [tableData, setTableData] = useState([]);

  function inicializar() {
    if (idParam == null) {
      setId('');
      setVar0('');
      setVar1('');
      setVar2('');
      setVar3('');
      setVar4('');
      setVar5('');
      setVar6('');
      setVar7('');
      setTableData([]);
    } else {
      setId(dados.id);
      setVar0(dados.status);
      setVar1(dados.dataInicio);
      setVar2(dados.dataFim);
      setVar3(dados.valorResrva);
      setVar4(dados.cliente_id);
      setVar5(dados.funcionario_id);
      setVar6(dados.hotel_id);
      setVar7(dados.hospedagem_id);
      setVar8(dados.tipoQuarto_id);
      setTableData(dados.listaQuartos);
    }
  }

  async function salvar() {
    let data = {
      id,
      var0,
      var1,
      var2,
      var3,
      var4,
      var5,
      var6,
      var7,
      var8
    };
    data = JSON.stringify(data);
    if (idParam == null) {
      await axios
        .post(baseURL, data, {
          headers: { 'Content-Type': 'application/json' },
        })
        .then(function (response) {
          mensagemSucesso(`Reserva ${id} cadastrado com sucesso!`);
          navigate(`/listagem-reserva`);
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
          mensagemSucesso(`Produto ${id} alterado com sucesso!`);
          navigate(`/listagem-reserva`);
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
      setVar0(dados.status);
      setVar1(dados.dataInicio);
      setVar2(dados.dataFim);
      setVar3(dados.valorResrva);
      setVar4(dados.cliente_id);
      setVar5(dados.funcionario_id);
      setVar6(dados.hotel_id);
      setVar7(dados.hospedagem_id);
      setVar8(dados.tipoQuarto_id);
      setTableData(dados.listaQuartos);
    }
  }

  const [dados3, setDados3] = React.useState(null); //tipo Produto
  
  useEffect(() => {
    axios.get(`${URL_status}/statusReserva`).then((response) => {
      setDados3(response.data);
    });
  }, []);

  const [dados2, setDados2] = React.useState(null); //tipo Produto
  
  useEffect(() => {
    axios.get(`${URL_quarto}/tipoQuarto`).then((response) => {
      setDados2(response.data);
    });
  }, []);

  const [dados5, setDados5] = React.useState(null); //tipo Produto
  
  useEffect(() => {
    axios.get(`${URL_hotel}/hotel`).then((response) => {
      setDados5(response.data);
    });
  }, []);
  
  useEffect(() => {
    buscar(); // eslint-disable-next-line
  }, [id]);
  
  //tabela interativa------------------------------------------------
  const InteractiveTable = () => {
    // const [tableData, setTableData] = useState([]);
    // setTableData = var16;
    const addRow = () => {
  
      const newRow = {
        id: tableData.length + 1,
        tipoQuarto: "null",
        num: 0,
        qtd: 0
      };
  
      setTableData([...tableData, newRow]);
    };
  
    const removeRow = (id) => {
  
      const updatedTableData = tableData.filter(row => row.id !== id);
  
      setTableData(updatedTableData);
    };
  
    const handleChange = (id, column, value) => {
      const updatedRows = tableData.map((row) =>
        row.id === id ? { ...row, [column]: value } : row
      );
      setTableData(updatedRows);
    };
  
    if (!tableData) return null;
    return (
      <div>
        <table className="table table-hover">
          <thead>
            <tr className="table-dark">
              <th scope="col">Tipo</th>
              {/* <th scope="col">Nº</th> */}
              <th scope="col">Quantidade</th>
              <th scope="col">Ações</th>
            </tr>
          </thead>
          <tbody>
            {tableData.map(row => (
              <tr key={row.id} className="table-light">
                <td>
                  <select
                    className='form-select'
                    value={row.tipoQuarto}
                    onChange={(e) => handleChange(row.id, 'tipoQuarto', e.target.value)}
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
                </td>
                {/* <td>
                  <select
                    className='form-select'
                    value={row.num}
                    onChange={(e) => handleChange(row.id, 'num', e.target.value)}
                  >
                    <option key='0' value='0'>
                      {' '}
                    </option>
                    {dados3.map((dado) => (
                      <option key={dado.id} value={dado.id}>
                        {dado.numero}
                      </option>
                    ))}
                  </select>
                </td> */}
                <td>
                  <input 
                    type='number' 
                    className='form-control'
                    value = {row.qtd}
                    onChange={(e) => handleChange(row.id, 'qtd', e.target.value)}>
                  </input>
                </td>
                <td>
                  <IconButton
                    aria-label='delete'
                    onClick={() => removeRow(row.id)}
                  >
                    <DeleteIcon />
                  </IconButton>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
          <IconButton
            aria-label='add'
            onClick={() => addRow()}
          >
            <AddBoxIcon />
          </IconButton>
      </div>
    );
  };

  if (!dados) return null;
  if (!dados3) return null;
  if (!dados2) return null;
  if (!dados5) return null;
  return (
    <div className='container'>
      <Card title='Cadastro de Reserva'>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='bs-component'>
            <FormGroup label='Hotel: *' htmlFor='selectHotel'>
                <select
                  className='form-select'
                  id='selectHotel'
                  name='hotel'
                  value={var6}
                  onChange={(e) => setVar6(e.target.value)}
                >
                  <option key='0' value='0'>
                    {' '}
                  </option>
                  {dados5.map((dado) => (
                    <option key={dado.id} value={dado.id}>
                      {dado.titulo}
                    </option>
                  ))}
                </select>
              </FormGroup>
              <FormGroup label='Status: *' htmlFor='selectStatus'>
                <select
                  className='form-select'
                  id='selectStatus'
                  name='status'
                  value={var0}
                  onChange={(e) => setVar0(e.target.value)}
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
              
              <FormGroup label='Quartos: *' htmlFor='selectQuartos'>
              <div class="card">
                <div class="card-body">
                  <InteractiveTable />
                </div>
              </div>
              </FormGroup>
              <FormGroup label='Data de Início: *' htmlFor='inputDataInicio'>
                <input
                  type='date'
                  id='inputDataInicio'
                  value={var1}
                  className='form-control'
                  name='datainicio'
                  onChange={(e) => setVar1(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Data de Término: *' htmlFor='inputDataFim'>
                <input
                  type='date'
                  id='inputDataFim'
                  value={var2}
                  className='form-control'
                  name='datafim'
                  onChange={(e) => setVar2(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Valor da Resrva: *' htmlFor='inputValorResrva'>
                <input
                  type='text'
                  id='inputValorResrva'
                  value={var3}
                  className='form-control'
                  name='valorresrva'
                  onChange={(e) => setVar3(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='ID cliente: *' htmlFor='inputIDCliente'>
                <input
                  type='text'
                  id='inputIDCliente'
                  value={var4}
                  className='form-control'
                  name='IDCliente'
                  onChange={(e) => setVar4(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='ID funcionário:' htmlFor='inputIDFuncionario'>
                <input
                  type='text'
                  id='inputIDFuncionario'
                  value={var5}
                  className='form-control'
                  name='IDFuncionario'
                  onChange={(e) => setVar5(e.target.value)}
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

export default CadastroReserva;
