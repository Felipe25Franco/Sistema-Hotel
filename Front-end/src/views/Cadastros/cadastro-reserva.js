import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Stack from '@mui/material/Stack';
import { IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import AddBoxIcon from '@mui/icons-material/AddBox';

import InteractiveTable from '../../components/interactiveTable';
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

  const baseURL = `${URL_hospedagem}/reservas`;

  const [id, setId] = useState(0);
  const [var0, setVar0] = useState('');
  const [var1, setVar1] = useState('');
  const [var2, setVar2] = useState('');
  const [var3, setVar3] = useState(0);
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
      setVar3(dados.valorReserva);
      setVar4(dados.idCliente);
      setVar5(dados.idFuncionario);
      setVar6(dados.idHotel);
      setVar7(dados.idHospedagem);
      setVar8(dados.idTipoQuarto);
      setTableData(dados.listaQuartos);
    }
  }

  async function salvar() {
    let data = {
      id,
      status:var0,
      dataInicio:var1,
      dataFim:var2,
      valorReserva:var3,
      idCliente:var4,
      idFuncionario:var5,
      idHotel:var6,
      idHospedagem:var0,
      listaQuartos:tableData,
    };
    data = JSON.stringify(data);
    console.log(data)
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
      setVar3(dados.valorReserva);
      setVar4(dados.idCliente);
      setVar5(dados.idFuncionario);
      setVar6(dados.idHotel);
      setVar7(dados.idHospedagem);
      setVar8(dados.idTipoQuarto);
      setTableData(dados.listaQuartos);
      //console.log(tableData);
      // for(let i =1;i<=tableData.length;i++) {
      //   tableData[i].id = null;
      // }
      // console.log(tableData);
    }
  }

  const [dados3, setDados3] = React.useState(null); 
  
  useEffect(() => {
    axios.get(`${URL_status}/statusReservas`).then((response) => {
      setDados3(response.data);
    });
  }, []);

  const [dados2, setDados2] = React.useState(null); 
  
  useEffect(() => {
    axios.get(`${URL_quarto}/tipoQuartos`).then((response) => {
    //axios.get(`${baseURL}/hotel/${var6}`).then((response) => {
      setDados2(response.data);
    });
  }, [var6,var1,var2]);

  const [dados5, setDados5] = React.useState(null); 
  
  useEffect(() => {
    axios.get(`${URL_hotel}/hoteis`).then((response) => {
      setDados5(response.data);
    });
  }, []);
  
  useEffect(() => {
    buscar(); // eslint-disable-next-line
  }, [id]);
  


  //tabela interativa------------------------------------------------
  const InteractiveTableHold = () => {
    // const [tableData, setTableData] = useState([]);
    // setTableData = var16;
    const addRow = () => {
  
      const newRow = {
        idRow: tableData.length + 1,
        tipoQuarto: "null",
        qtd: 0
      };
  
      setTableData([...tableData, newRow]);
    };
  
    const removeRow = (id) => {
      // console.log(tableData);

      const updatedTableData = tableData.filter(row => row.idRow !== id);
      //acertar idRow da tabela inteira
      for(let i =1;i<=updatedTableData.length;i++) {
        updatedTableData[i-1].idRow = i;
      }
      //console.log(updatedTableData);
  
      setTableData(updatedTableData);
    };
  
    const handleChange = (id, column, value) => {
      const updatedRows = tableData.map((row) =>
        row.idRow === id ? { ...row, [column]: value } : row
      );
      setTableData(updatedRows);
    };
  
    if (!tableData) return null;
    //console.log(tableData)
    if (!validarHotel(var6) && idParam==null) return <div>Selecione um Hotel primeiro</div>;
    if (!validarData(var1, var2) && idParam==null) return <div>Selecione um período de estadia válido</div>;
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
              <tr key={row.idRow} className="table-light">
                <td>
                  <select
                    className='form-select'
                    value={row.tipoQuarto}
                    onChange={(e) => handleChange(row.idRow, 'tipoQuarto', e.target.value)}
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
                    onChange={(e) => handleChange(row.idRow, 'num', e.target.value)}
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
                    onChange={(e) => handleChange(row.idRow, 'qtd', e.target.value)}>
                  </input>
                </td>
                <td>
                  <IconButton
                    aria-label='delete'
                    onClick={() => removeRow(row.idRow)}
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
                  onChange={(e) => {setVar6(e.target.value);console.log(var6)}}
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
                  onChange={(e) => {setVar2(e.target.value);}}
                />
              </FormGroup>
              
              <FormGroup label='Quartos: *' htmlFor='selectQuartos'>
              <div className="card">
                <div className="card-body">
                  <InteractiveTableHold/>
                  {/* <InteractiveTable tableData = {tableData} infoTable={[{head:"Tipo", prop:"tipoQuarto", initial:null, type: "select", extraData: null},{head:"Quantidade", prop:"qtd", initial:0, type: "number", extraData: null}]}/> */}
                </div>
              </div>
              </FormGroup>
              <FormGroup label='Valor da Reserva: *' htmlFor='inputValorResrva'>
                <input
                  type='number'
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

//validacoes liberacoes controles

function validarData(var1, var2) {
  if(var1===''||var2==='') return false;
  if(var1<=var2) return true;
  return false;
}

function validarHotel(var6) {
  if(var6===''||var6===null||var6===0) return false;
  return true;
}

export default CadastroReserva;
