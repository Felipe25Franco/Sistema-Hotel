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
import { URL_servico } from '../../config/axios';
import { URL_hotel } from '../../config/axios';
import { URL_status } from '../../config/axios';

function CadastroServicos() {
  
  const { idParam } = useParams();

  const navigate = useNavigate();

  const baseURL = `${URL_servico}/servicos`;

  const [id, setId] = useState(0);
  const [var0, setVar0] = useState('');//nome
  const [var1, setVar1] = useState('');//desc
  const [var2, setVar2] = useState(0);//valor h orario
  const [var3, setVar3] = useState('');//status
  const [var4, setVar4] = useState(false);//tipo reserva
  const [var5, setVar5] = useState('');//id hotel
  const [var6, setVar6] = useState('');//id tipo serv
  const [horarios, setHorarios] = useState([]);//id tipo serv

  const currentDate = new Date();
  console.log(currentDate.getDate())
  console.log(`${currentDate.getFullYear()}-${String(currentDate.getMonth() + 1).padStart(2, '0')}-${String(currentDate.getDate()).padStart(2, '0')}`)
  console.log(`${currentDate.getFullYear()}-${currentDate.getMonth()+1}-${currentDate.getDay()}`)
  const [date, setDate] = useState(`${currentDate.getFullYear()}-${String(currentDate.getMonth() + 1).padStart(2, '0')}-${String(currentDate.getDate()).padStart(2, '0')}`);
  const [tableData, setTableData] = useState([]);
   //ESSA é A PARTE DO BOTAO EDITAR
   const [dados, setDados] = React.useState([]);
  
   function inicializar() {
     if (idParam == null) {
       setId('');
       setVar0('');
       setVar1('');
       setVar2('');
       setVar3('');
       setVar4(false);
       setVar5('');
       setHorarios([]);
       setTableData([]);
     } else {
       setId(dados.id);
       setVar0(dados.titulo);
       setVar1(dados.descricao);
       setVar2(dados.valorPorHorario);
       setVar3(dados.status);
       setVar4(dados.tipoReserva);
       setVar5(dados.idHotel);
       setVar6(dados.idTipoServico);
       setHorarios(dados.horarioServicos);
       setTableData(dados.horarioServicos);
     }
   }
 
   async function salvar() {
     let data = {
       id,
       titulo:var0,
       descricao:var1,
       status:var3,
       valorPorHorario:var2,
       tipoReserva:var4,
       idHotel:var5,
       idTipoServico:var6,
       //horarioServicos:horarios
       horarioServicos:tableData
     };
     data = JSON.stringify(data);
     //console.log(data)
     if (idParam == null) {
       await axios
         .post(baseURL, data, {
           headers: { 'Content-Type': 'application/json' },
         })
         .then(function (response) {
           mensagemSucesso(`Serviço ${var0} cadastrado com sucesso!`);
           navigate(`/listagem-servicos`);
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
           mensagemSucesso(`Serviço ${var0} alterado com sucesso!`);
           navigate(`/listagem-servicos`);
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
       setVar0(dados.titulo);
       setVar1(dados.descricao);
       setVar2(dados.valorPorHorario);
       setVar3(dados.status);
       setVar4(dados.tipoReserva);
       setVar5(dados.idHotel);
       setVar6(dados.idTipoServico);
       setHorarios(dados.horarioServicos);
       setTableData(dados.horarioServicos);
     }
     console.log(dados)
   }

  const [dados2, setDados2] = React.useState(null); //tipo servcio

  useEffect(() => {
    axios.get(`${URL_servico}/tipoServicos`).then((response) => {
      setDados2(response.data);
    });
  }, []);

  const [dados3, setDados3] = React.useState(null); //hotel

  useEffect(() => {
    //axios.get(`${URL_produto}/tipoProduto`).then((response) => {
    axios.get(`${URL_hotel}/hoteis`).then((response) => {
      setDados3(response.data);
    });
  }, []);

  const [dados4, setDados4] = React.useState(null); //tipo Produto
  
  useEffect(() => {
    axios.get(`${URL_status}/statusServicos`).then((response) => {
      setDados4(response.data);
    });
  }, []);
  
  useEffect(() => {
    buscar(); // eslint-disable-next-line
  }, [id]);

  // useEffect(() => {
  //   // ativar tabela d acordo
  //   setTableData([]);
  //   // console.log(date)
  //   // console.log(horarios)
  //   setTableData(horarios.filter(row => row.data == date));
  // }, [date]);

  //tabela interativa------------------------------------------------
  const InteractiveTable = () => {
    // const [tableData, setTableData] = useState([]);
    // setTableData = var16;
    // console.log(tableData)
    const addRow = () => {
  
      const newRow = {
        idRow: tableData.length + 1,
        status: "",
        vagasTotal: 0,
        vagasOcupadas: 0,
        horaInicio: "00:00",
        horaFim: "00:00",
        data: date //data: date 
      };
  
      setTableData([...tableData, newRow]);
    };
  
    const removeRow = (id) => {
  
      const updatedTableData = tableData.filter(row => row.idRow !== id);
      for(let i =1;i<=updatedTableData.length;i++) {
        updatedTableData[i-1].idRow = i;
      }
  
      setTableData(updatedTableData);
    };
  
    const handleChange = (id, column, value) => {
      const updatedRows = tableData.map((row) =>
        row.idRow === id ? { ...row, [column]: value } : row
      );
      setTableData(updatedRows);
    };
  
    if (!tableData) return null;
    return (
      <div>
        <table className="table table-hover">
          <thead>
            <tr className="table-dark">
              <th scope="col">Horário de Início</th>
              <th scope="col">Horário de Término</th>
              <th scope="col">Vagas Limite</th>
              <th scope="col">Vagas Ocupadas</th>
              <th scope="col">Status</th>
              <th scope="col">Ações</th>
            </tr>
          </thead>
          <tbody>
            {tableData.filter(row => row.data == date).map(row => (
              <tr key={row.idRow} className="table-light">
                <td>
                  <input 
                    type='time' 
                    className='form-control'
                    value = {row.horaInicio}
                    onChange={(e) => handleChange(row.idRow, 'horaInicio', e.target.value)}>
                  </input>
                </td>
                <td>
                  <input 
                    type='time' 
                    className='form-control'
                    value = {row.horaFim}
                    onChange={(e) => handleChange(row.idRow, 'horaFim', e.target.value)}>
                  </input>
                </td>
                <td>
                  <input 
                    type='number' 
                    className='form-control'
                    value = {row.vagasTotal}
                    onChange={(e) => handleChange(row.idRow, 'vagasTotal', e.target.value)}>
                  </input>
                </td>
                <td>
                  <input 
                    type='number' 
                    className='form-control'
                    value = {row.vagasOcupadas}
                    onChange={(e) => handleChange(row.idRow, 'vagasOcupadas', e.target.value)}>
                  </input>
                </td>
                <td>
                  <input 
                    type='text' 
                    className='form-control'
                    value = {row.status}
                    onChange={(e) => handleChange(row.idRow, 'status', e.target.value)}>
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
  if (!dados2) return null;
  if (!dados3) return null;
  if (!dados4) return null;

  if (idParam>0) return (
    <div className='container'>
      <Card title='Cadastro de Serviço'>
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
                  {dados3.map((dado) => (
                    <option key={dado.id} value={dado.id}>
                      {dado.titulo}
                    </option>
                  ))}
                </select>
              </FormGroup>
              <FormGroup label='Nome: *' htmlFor='inputNome'>
                <input
                  type='text'
                  id='inputNome'
                  value={var0}
                  className='form-control'
                  name='nome'
                  onChange={(e) => setVar0(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Descrição: *' htmlFor='inputDescricao'>
                <textarea
                  id='inputDescricao'
                  value={var1}
                  className='form-control'
                  name='descricao'
                  onChange={(e) => setVar1(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Tipo: *' htmlFor='selectTipo'>
                <select
                  className='form-select'
                  id='selectTipo'
                  name='tipo'
                  value={var5}
                  onChange={(e) => setVar5(e.target.value)}
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
              <FormGroup label='Tipo de reserva: *' htmlFor='inputTipoReserva'>
                {/* <input 
                  type="checkbox" 
                  className="form-check-input" 
                  id="btncheck1" 
                  autocomplete="off"
                  checked={var4}
                  name='tiporeserva'
                  onChange={(e) => setVar4(e.target.checked)}>
                    </input> */}

                    <div class="form-check">
                      <input class="form-check-input" type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked={var4} onChange={(e) => setVar4(e.target.checked)}/>
                        Agendada
                    </div>
                    <div class="form-check">
                      <input class="form-check-input" type="radio" name="optionsRadios" id="optionsRadios2" value="option2" checked={!var4} onChange={(e) => setVar4(!e.target.checked)}/>
                        Não agendada
                    </div>
                {/* <input
                  type='texte' //checkbox
                  id='inputTipoReserva'
                  value={var4}
                  className='form-control'
                  name='tiporeserva'
                  onChange={(e) => setVar4(e.target.checked)}
                /> */}
              </FormGroup>
              <FormGroup label='Preço por horário: *' htmlFor='inputPreco'>
                <input
                  type='number'
                  id='inputPreco'
                  value={var2}
                  className='form-control'
                  name='preco'
                  onChange={(e) => setVar2(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Status: *' htmlFor='selectStatus'>
                <select
                  className='form-select'
                  id='selectStatus'
                  name='status'
                  value={var3}
                  onChange={(e) => setVar3(e.target.value)}
                >
                  <option key='0' value='0'>
                    {' '}
                  </option>
                  {dados4.map((dado) => (
                    <option key={dado.id} value={dado.id}>
                      {dado.titulo}
                    </option>
                  ))}
                </select>
              </FormGroup>
              {/* parte do data hoario */}
              <br></br>
              <div class="card">
                <div class="card-body">
                Manejamento de Horários
              <br></br>
              <FormGroup label='Data:' htmlFor='selectDate'>
                <input
                  type="date"
                  className='form-control'
                  id="selectDate"
                  value={date}
                  onChange={(e) => {setDate(e.target.value);}}
                />
              </FormGroup>
              <FormGroup label='Horários: *' htmlFor='selectHorarios'>
              <div class="card">
                <div class="card-body">
                  <InteractiveTable />
                </div>
              </div>
              </FormGroup>
                </div>
              </div>
              

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

  return (
    <div className='container'>
      <Card title='Cadastro de Serviço'>
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
                  {dados3.map((dado) => (
                    <option key={dado.id} value={dado.id}>
                      {dado.titulo}
                    </option>
                  ))}
                </select>
              </FormGroup>
              <FormGroup label='Nome: *' htmlFor='inputNome'>
                <input
                  type='text'
                  id='inputNome'
                  value={var0}
                  className='form-control'
                  name='nome'
                  onChange={(e) => setVar0(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Descrição: *' htmlFor='inputDescricao'>
                <textarea
                  id='inputDescricao'
                  value={var1}
                  className='form-control'
                  name='descricao'
                  onChange={(e) => setVar1(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Tipo: *' htmlFor='selectTipo'>
                <select
                  className='form-select'
                  id='selectTipo'
                  name='tipo'
                  value={var5}
                  onChange={(e) => setVar5(e.target.value)}
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
              <FormGroup label='Tipo de reserva: *' htmlFor='inputTipoReserva'>
                {/* <input 
                  type="checkbox" 
                  className="form-check-input" 
                  id="btncheck1" 
                  autocomplete="off"
                  checked={var4}
                  name='tiporeserva'
                  onChange={(e) => setVar4(e.target.checked)}>
                    </input> */}

                    <div class="form-check">
                      <input class="form-check-input" type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked={var4} onChange={(e) => setVar4(e.target.checked)}/>
                        Agendada
                    </div>
                    <div class="form-check">
                      <input class="form-check-input" type="radio" name="optionsRadios" id="optionsRadios2" value="option2" checked={!var4} onChange={(e) => setVar4(!e.target.checked)}/>
                        Não agendada
                    </div>
                {/* <input
                  type='texte' //checkbox
                  id='inputTipoReserva'
                  value={var4}
                  className='form-control'
                  name='tiporeserva'
                  onChange={(e) => setVar4(e.target.checked)}
                /> */}
              </FormGroup>
              <FormGroup label='Preço por horário: *' htmlFor='inputPreco'>
                <input
                  type='number'
                  id='inputPreco'
                  value={var2}
                  className='form-control'
                  name='preco'
                  onChange={(e) => setVar2(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Status: *' htmlFor='selectStatus'>
                <select
                  className='form-select'
                  id='selectStatus'
                  name='status'
                  value={var3}
                  onChange={(e) => setVar3(e.target.value)}
                >
                  <option key='0' value='0'>
                    {' '}
                  </option>
                  {dados4.map((dado) => (
                    <option key={dado.id} value={dado.id}>
                      {dado.titulo}
                    </option>
                  ))}
                </select>
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

export default CadastroServicos;
