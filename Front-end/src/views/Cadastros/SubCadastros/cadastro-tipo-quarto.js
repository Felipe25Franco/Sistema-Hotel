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
import { URL_quarto, URL_comodidade, URL_tipoQuarto } from '../../../config/axios';

function CadastroTipoQuarto() {
  
  const { idParam } = useParams();
  const { idParam2 } = useParams();
  //console.log(idParam)
  //console.log(idParam2)
  //if(idParam2==1) {const vizualizar = true} else {const vizualizar = false};
  const navigate = useNavigate();

  const baseURL = `${URL_quarto}/tipoQuartos`;

  const [id, setId] = useState(0);
  const [var1, setVar1] = useState('');//titulo
  const [var2, setVar2] = useState('');//descricao
  const [var3, setVar3] = useState('');//limiteAdulto
  const [var4, setVar4] = useState('');//limiteCrianca
  const [var5, setVar5] = useState('');//precoBase
  const [var6, setVar6] = useState('');//avaliacaoMedia
  const [var7, setVar7] = useState('');//diasCancelarReserva
  const [var8, setVar8] = useState('');//area
  const [listaCama, setListaCama] = useState([]);//area
  const [listaComodidade, setListaComodidade] = useState([]);//area

  const [dados, setDados] = React.useState([]);
  const [tableData, setTableData] = useState([]);
  const [tableData2, setTableData2] = useState([]);
  
  function inicializar() {
    if (idParam == null) {
      setId(0);
      setVar1('');
      setVar2('');
      setVar3('');
      setVar4('');
      setVar5('');
      setVar6('');
      setVar7('');
      setVar8('');
      setListaCama('');
      setListaComodidade('');
      setTableData('');
      setTableData2('');
    } else {
      setId(dados.id);
      setVar1(dados.titulo);
      setVar2(dados.descricao);
      setVar3(dados.limiteAdulto);
      setVar4(dados.limiteCrianca);
      setVar5(dados.precoBase);
      setVar6(dados.avaliacaoMedia);
      setVar7(dados.diasCancelarReserva);
      setVar8(dados.area);
      setListaCama(dados.camaTipoQuarto);
      setListaComodidade(dados.comodidadeTipoQuarto);
      setTableData(dados.camaTipoQuarto);
      setTableData2(dados.comodidadeTipoQuarto);
    }
  }

  async function salvar() {
    let data = {
      id,
      titulo:var1,
      descricao:var2,
      limiteAdulto:var3,
      limiteCrianca:var4,
      precoBase:var5,
      avaliacaoMedia:var6,
      diasCancelarReserva:var7,
      area:var8,
      camaTipoQuarto:tableData,
      comodidadeTipoQuarto:tableData2,
    };
    data = JSON.stringify(data);
    if (idParam == null) {
      await axios
        .post(baseURL, data, {
          headers: { 'Content-Type': 'application/json' },
        })
        .then(function (response) {
          mensagemSucesso(`Tipo de quarto ${var1} cadastrado com sucesso!`);
          navigate(`/listagem-tipo-quarto`);
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
          mensagemSucesso(`Tipo de quarto ${var1} alterado com sucesso!`);
          navigate(`/listagem-tipo-quarto`);
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
      setVar1(dados.titulo);
      setVar2(dados.descricao);
      setVar3(dados.limiteAdulto);
      setVar4(dados.limiteCrianca);
      setVar5(dados.precoBase);
      setVar6(dados.avaliacaoMedia);
      setVar7(dados.diasCancelarReserva);
      setVar8(dados.area);
      setListaCama(dados.camaTipoQuarto);
      setListaComodidade(dados.comodidadeTipoQuarto);
      setTableData(dados.camaTipoQuarto);
      setTableData2(dados.comodidadeTipoQuarto);
    }
  }
  
  const [dados2, setDados2] = React.useState(null); //tipo Produto
  
  useEffect(() => {
    axios.get(`${URL_comodidade}/comodidades`).then((response) => {
      setDados2(response.data);
    });
  }, []);

  const [dados3, setDados3] = React.useState(null); //tipo Produto
  
  useEffect(() => {
    axios.get(`${URL_quarto}/tipoCamas`).then((response) => {
      setDados3(response.data);
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
        idRow: tableData.length + 1,
        idTipoCama: 0,
        quantidade: 0
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
              <th scope="col">Tipo de Cama</th>
              {/* <th scope="col">Nº</th> */}
              <th scope="col">Quantidade</th>
              <th scope="col">Ações</th>
            </tr>
          </thead>
          <tbody>
            {tableData.map(row => (
              <tr key={row.idRow} className="table-light">
                <td>
                  <select  disabled={vizualizar()}
                    className='form-select'
                    value={row.idTipoCama}
                    onChange={(e) => handleChange(row.idRow, 'idTipoCama', e.target.value)}
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
                  <input  readOnly={vizualizar()}
                    type='number' 
                    className='form-control'
                    value = {row.quantidade}
                    onChange={(e) => handleChange(row.idRow, 'quantidade', e.target.value)}>
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

  //tabela interativa------------------------------------------------
  const InteractiveTable2 = () => {
    // const [tableData, setTableData] = useState([]);
    // setTableData = var16;
    const addRow = () => {
  
      const newRow = {
        idRow: tableData2.length + 1,
        idComodidade: 0,
        quantidade: 0
      };
  
      setTableData2([...tableData2, newRow]);
    };
  
    const removeRow = (id) => {
  
      const updatedTableData2 = tableData2.filter(row => row.idRow !== id);
      for(let i =1;i<=updatedTableData2.length;i++) {
        updatedTableData2[i-1].idRow = i;
      }
      setTableData2(updatedTableData2);
    };
  
    const handleChange = (id, column, value) => {
      const updatedRows = tableData2.map((row) =>
        row.idRow === id ? { ...row, [column]: value } : row
      );
      setTableData2(updatedRows);
    };
  
    if (!tableData2) return null;
    return (
      <div>
        <table className="table table-hover">
          <thead>
            <tr className="table-dark">
              <th scope="col">Comodidades</th>
              {/* <th scope="col">Nº</th> */}
              <th scope="col">Quantidade</th>
              <th scope="col">Ações</th>
            </tr>
          </thead>
          <tbody>
            {tableData2.map(row => (
              <tr key={row.idRow} className="table-light">
                <td>
                  <select  disabled={vizualizar()}
                    className='form-select'
                    value={row.idComodidade}
                    onChange={(e) => handleChange(row.idRow, 'idComodidade', e.target.value)}
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
                  <input  readOnly={vizualizar()}
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

  function vizualizar() {return (idParam2 == 1 ? true : false)};
  console.log(vizualizar())

  if (!dados) return null;
  if (!dados2) return null;
  if (!dados3) return null;

  return (
    <div className='container'>
      <Card title='Cadastro de Tipo de Quarto'>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='bs-component'>
              <FormGroup label='Título: *' htmlFor='inputTitulo'>
                <input 
                  readOnly={vizualizar()}
                  type='text'
                  id='inputTitulo'
                  value={var1}
                  className='form-control'
                  name='titulo'
                  onChange={(e) => setVar1(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Descrição: *' htmlFor='inputDescricao'>
                <input 
                  readOnly={vizualizar()}
                  type='text'
                  id='inputDescricao'
                  value={var2}
                  className='form-control'
                  name='descricao'
                  onChange={(e) => setVar2(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Limite de Adultos: *' htmlFor='inputLimiteAdulto'>
                <input 
                  readOnly={vizualizar()}
                  type='number'
                  id='inputLimiteAdulto'
                  value={var3}
                  className='form-control'
                  name='limiteadulto'
                  onChange={(e) => setVar3(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Limite de Crianças: *' htmlFor='inputLimiteCrianca'>
                <input 
                  readOnly={vizualizar()}
                  type='number'
                  id='inputLimiteCrianca'
                  value={var4}
                  className='form-control'
                  name='limitecrianca'
                  onChange={(e) => setVar4(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Preço base: *' htmlFor='inputPrecoBase'>
                <input 
                  readOnly={vizualizar()}
                  type='number'
                  id='inputPrecoBase'
                  value={var5}
                  className='form-control'
                  name='precobase'
                  onChange={(e) => setVar5(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Limite de dias para cancelamento de reserva: *' htmlFor='inputDiasCancelarReserva'>
                <input 
                  readOnly={vizualizar()}
                  type='number'
                  id='inputDiasCancelarReserva'
                  value={var7}
                  className='form-control'
                  name='diascancelarreserva'
                  onChange={(e) => setVar7(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Área (m²): *' htmlFor='inputArea'>
                <input 
                  readOnly={vizualizar()}
                  type='number'
                  id='inputArea'
                  value={var8}
                  className='form-control'
                  name='area'
                  onChange={(e) => setVar8(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Camas: *' htmlFor='selectCamas'>
              <div class="card">
                <div class="card-body">
                  <InteractiveTable />
                </div>
              </div>
              </FormGroup>
              <FormGroup label='Comodidades: *' htmlFor='selectComodidades'>
              <div class="card">
                <div class="card-body">
                  <InteractiveTable2 />
                </div>
              </div>
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

export default CadastroTipoQuarto;