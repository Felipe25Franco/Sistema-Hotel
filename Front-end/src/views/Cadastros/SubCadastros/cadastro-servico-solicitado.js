import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Stack from '@mui/material/Stack';
import { IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import AddBoxIcon from '@mui/icons-material/AddBox';

import Card from '../../../components/card';

import FormGroup from '../../../components/form-group';

import { mensagemSucesso, mensagemErro } from '../../../components/toastr';

import '../../../custom.css';

import axios from 'axios';
import { BASE_URL, URL_servico } from '../../../config/axios';
import { URL_hotel } from '../../../config/axios';
import { URL_status } from '../../../config/axios';

function CadastroServicoSolicitado() {
  
  const { idParam } = useParams();
  const { idParam2 } = useParams();

  const navigate = useNavigate();

  const baseURL = `${URL_servico}/servicoSolicitados`;

  const [id, setId] = useState(0);
  const [var0, setVar0] = useState('');//nome
  const [var1, setVar1] = useState('');//desc
  const [var2, setVar2] = useState('');//valor h orario

  const currentDate = new Date();
  //console.log(currentDate.getDate())
  //console.log(`${currentDate.getFullYear()}-${String(currentDate.getMonth() + 1).padStart(2, '0')}-${String(currentDate.getDate()).padStart(2, '0')}`)
  //console.log(`${currentDate.getFullYear()}-${currentDate.getMonth()+1}-${currentDate.getDay()}`)
  const [date, setDate] = useState(`${currentDate.getFullYear()}-${String(currentDate.getMonth() + 1).padStart(2, '0')}-${String(currentDate.getDate()).padStart(2, '0')}`);
  const [tableData, setTableData] = useState([]);
   //ESSA é A PARTE DO BOTAO EDITAR
   const [dados, setDados] = React.useState([]);
  
   function inicializar() {
     if (idParam != null && idParam2 != null) {
       setId('');
       setVar0('');
       setVar1(idParam);
       setVar2('');
       setTableData([]);
     } else {
      setId(dados.id);
      setVar0(dados.idServico);
      setVar1(dados.idHospedagem);
      setVar2(dados.valorTotal);
      setTableData(dados.relacaoHorarioServico);
     }
   }
 
   async function salvar() {
     let data = {
       id,
       idServico:var0,
       idHospedagem:var1,
       valorTotal:var2,
       relacaoHorarioServico:tableData
     };
     data = JSON.stringify(data);
     if (idParam != null && idParam2 != null) {
      console.log("pos data")
      console.log(data)
       await axios
         .post(baseURL, data, {
           headers: { 'Content-Type': 'application/json' },
         })
         .then(function (response) {
           mensagemSucesso(`Serviço Solicitado ${id} cadastrado com sucesso!`);
           navigate(`/listagem-servicos-solicitados/${idParam}`);
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
           mensagemSucesso(`Serviço Solicitado ${id} alterado com sucesso!`);
           navigate(`/listagem-servicos-solicitados/${var1}`);
         })
         .catch(function (error) {
           mensagemErro(error.response.data);
         });
     }
   }
 
  async function buscar() {
    // console.log("idParam");
    // console.log(idParam)
    // console.log(idParam==null);
    // console.log(idParam2)
    // console.log(idParam2==null);
     if (idParam != null && idParam2 == null) {
      console.log("buscar")
       await axios.get(`${baseURL}/${idParam}`).then((response) => {setDados(response.data)});
       setId(dados.id);
       setVar0(dados.idServico);
       setVar1(dados.idHospedagem);
       setVar2(dados.valorTotal);
       setTableData(dados.relacaoHorarioServico);
       console.log(dados)
     }
     else {
      
      if (idParam != null && idParam2 != null) {
        setVar1(idParam);
      }
    }
  }

  // async function buscar2() {
  //    if (idParam != null && idParam2 == null) {
  //     console.log("teste")
  //      await axios.get(`${baseURL}/servicos`).then((response) => {setDados(response.data)});
  //      setId(dados.id);
  //      setVar0(dados.idServico);
  //      setVar1(dados.idHospedagem);
  //      setVar2(dados.valorTotal);
  //      setTableData(dados.relacaoHorarioServico);
  //      console.log(dados)
  //    }
  //    else {
      
  //     if (idParam != null && idParam2 != null) {
  //       setVar1(idParam);
  //     }
  //   }
  // }

  const [dados2, setDados2] = React.useState(null); //tipo servcio

  useEffect(() => {
    axios.get(`${BASE_URL}/tipoServicos`).then((response) => {
      setDados2(response.data);
    });
  }, []);

  const [dados3, setDados3] = React.useState(null); //hotel

  useEffect(() => {
    //axios.get(`${URL_produto}/tipoProduto`).then((response) => {
    axios.get(`${BASE_URL}/hospedagens`).then((response) => {
      setDados3(response.data);
    });
  }, []);

  const [dados4, setDados4] = React.useState(null); //tipo Produto
  
  useEffect(() => {
    axios.get(`${BASE_URL}/servicos`).then((response) => {
      setDados4(response.data);
      console.log("dadso4 inicio")
      setDados4(response.data.map(x => {
        return { ...x, horarioServicos: x.horarioServicos.map((x)=>true?{ ...x, ["idHorarioServico"]: x.id }:x) };
      }));
    });
  }, []);

  // useEffect(() => {
  //   axios.get(`${BASE_URL}/servicos`).then((response) => {
  //     setDados4(response.data);
  //   });
  // }, []);
  // const [dados5, setDados5] = React.useState(null); //tipo Produto
  
  // useEffect(() => {
  //   axios.get(`${BASE_URL}/relacaoHorarioServicos`).then((response) => {
  //     setDados4(response.data);
  //   });
  // }, []);
  
  useEffect(() => {
    buscar(); // eslint-disable-next-line
  }, [id]);

  function filerTableData() {
    
    if(idParam2 != null && idParam != null && dados4 != null && var0 != null && var0 !='' && var0 !=' ' && var0 !=0)  {
      console.log("horarios do servico selecionado")
      console.log(dados4.find(row => row.id == var0).horarioServicos)
      setTableData(dados4.find(row => row.id == var0).horarioServicos);
      // console.log(filterHorarios)
      // console.log("adicionando o idHS")
      // filterHorarios = filterHorarios.map((x)=>true?{ ...x, ["idHorarioServico"]: x.id }:x)

      // console.log(filterHorarios.forEach(element => {
      //   (element.idHorarioServico=element.id)
      // }));
      //setTableData(tableData.map((x)=>true?{ ...x, ["idHorarioServico"]: x.id }:x));
      //setTableData(tableData.forEach((x)=>x.idHorarioServico = x.id))
      // var tableDataTemp = tableData;
      // console.log("tableDataTemp");
      // console.log(tableDataTemp);
      // const tableDataTemp = (tableData.map(element => {
      //   (element.idHorarioServico=element.id)
      // }));
      // console.log("tableDataTemp");
      // console.log(tableDataTemp);
      //setTableData(tableDataTemp)
      //setTableData(tableData.map((x)=>{return { ...x, ["idHorarioServico"]: x.id }}))
    }
    else {
      if(idParam2 != null && idParam != null)
        setTableData([])
    }
  }

  useEffect(() => {
    filerTableData();
  }, [var0]);

  // useEffect(() => {
  //   // console.log(tableData)
  //   // console.log(dados4)
  // }, [tableData]);
  // // useEffect(() => {
  // //   // ativar tabela d acordo
  // //   setTableData([]);
  // //   setTableData(horarios.filter(row => row.data == date));
  // // }, [date]);

  //tabela interativa------------------------------------------------
  const InteractiveTable = () => {
    // const [tableData, setTableData] = useState([]);
    // setTableData = var16;
    // console.log(tableData)
    const addRow = () => {
  
      const newRow = {
        idRow: tableData.length + 1,
        id: '',
        select: true,
        quantidadeVagas: 0,
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
      console.log("checkbox")
      console.log(value)
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
            <th scope="col">Selecionar</th>
              <th scope="col">Horário de Início</th>
              <th scope="col">Horário de Término</th>
              <th scope="col">Quantidade</th>
              <th scope="col">Vagas Limite</th>
              <th scope="col">Vagas Ocupadas</th>
              <th scope="col">Status</th>
            </tr>
          </thead>
          <tbody>
            {tableData.filter(row => (row.data == date && var0 == row.idServico)).map(row => (
              <tr key={row.idRow} className="table-light">
                <td>
                  <input 
                    type='checkbox' 
                    //className='form-control'
                    //value = {row.select}
                    checked = {row.select}
                    onChange={(e) => handleChange(row.idRow, 'select', e.target.checked)}>
                  </input>
                </td>
                <td>
                  <input 
                    type='time' 
                    className='form-control'
                    value = {row.horaInicio}
                    readonly="readonly"
                    onChange={(e) => handleChange(row.idRow, 'horaInicio', e.target.value)}>
                  </input>
                </td>
                <td>
                  <input 
                    type='time' 
                    className='form-control'
                    value = {row.horaFim}
                    readonly="readonly"
                    onChange={(e) => handleChange(row.idRow, 'horaFim', e.target.value)}>
                  </input>
                </td>
                <td>
                  <input 
                    type='number' 
                    className='form-control'
                    value = {row.quantidadeVagas}
                    onChange={(e) => handleChange(row.idRow, 'quantidadeVagas', e.target.value)}>
                  </input>
                </td>
                <td>
                  <input 
                    type='number' 
                    className='form-control'
                    value = {row.vagasTotal}
                    readonly="readonly"
                    onChange={(e) => handleChange(row.idRow, 'vagasTotal', e.target.value)}>
                  </input>
                </td>
                <td>
                  <input 
                    type='number' 
                    className='form-control'
                    value = {row.vagasOcupadas}
                    readonly="readonly"
                    onChange={(e) => handleChange(row.idRow, 'vagasOcupadas', e.target.value)}>
                  </input>
                </td>
                <td>
                  <input 
                    type='text' 
                    className='form-control'
                    value = {row.status}
                    readonly="readonly"
                    onChange={(e) => handleChange(row.idRow, 'status', e.target.value)}>
                  </input>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  };
  
  if (!dados) return null;
  if (!dados2) return null;
  if (!dados3) return null;
  if (!dados4) return null;

  return (
    <div className='container'>
      <Card title={`Cadastro de Serviço Solicitado para Hospedagem ID: ${idParam}`}>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='bs-component'>
            <FormGroup label='Serviço: *' htmlFor='selectServico'>
                <select
                  className='form-select'
                  id='selectServico'
                  name='servico'
                  value={var0}
                  //disabled = {false}
                  disabled = {(idParam2 == null && idParam != null)}
                  onChange={(e) => {setVar0(e.target.value)}}
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
                  onChange={(e) => setDate(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Horários: *' htmlFor='selectHorarios'>
              <div class="card">
                <div class="card-body">
                  <InteractiveTable />
                </div>
              </div>
              </FormGroup>
              <FormGroup label='Valor Total: *' htmlFor='inputValorTotal'>
                <input
                  type='number'
                  id='inputValorTotal'
                  value={var2}
                  className='form-control'
                  name='valorTotal'
                  disabled={(idParam2 == null && idParam != null)}
                  onChange={(e) => setVar2(e.target.value)}
                />
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

}

export default CadastroServicoSolicitado;
