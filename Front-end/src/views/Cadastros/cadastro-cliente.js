import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Stack from '@mui/material/Stack';

import Card from '../../components/card';

import FormGroup from '../../components/form-group';

import { mensagemSucesso, mensagemErro } from '../../components/toastr';

import '../../custom.css';

import axios from 'axios';
import { BASE_URL } from '../../config/axios';
import { URL_endereco } from '../../config/axios';

function CadastroCliente() {
  
  const { idParam } = useParams();

  const navigate = useNavigate();

  const baseURL = `${URL_endereco}/cliente`;
  const baseURL_endereco = `${URL_endereco}/endereco`;
  const baseURL_uf = `${URL_endereco}/uf`;
  const baseURL_pais = `${URL_endereco}/pais`;

  const [id, setId] = useState(0);
  const [var0, setVar0] = useState('');//cpf
  const [var1, setVar1] = useState('');//nome
  const [var2, setVar2] = useState('');//dataN
  const [var11, setVar11] = useState('');//email
  const [var12, setVar12] = useState('');//senha1
  const [var13, setVar13] = useState('');//senha2
  const [var14, setVar14] = useState('');//tel1
  const [var15, setVar15] = useState('');//tel2

  const [var3, setVar3] = useState('');//pais
  const [var4, setVar4] = useState('');//uf
  const [var5, setVar5] = useState('');//cidade
  const [var6, setVar6] = useState('');//cep
  const [var7, setVar7] = useState(0);//num
  const [var8, setVar8] = useState(0);//com
  const [var9, setVar9] = useState('');//log
  const [var10, setVar10] = useState('');//bai 

  const [var16, setVar16] = useState(0);//id end 
  const [var17, setVar17] = useState(0);//id uf 
  const [var18, setVar18] = useState(0);//id pais 

  //ESSA é A PARTE DO BOTAO EDITAR
  const [dados, setDados] = React.useState([]);
    
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
      setVar8('');
      setVar9('');
      setVar10('');
      setVar11('');
      setVar12('');
      setVar13('');
      setVar14('');
      setVar15('');
      setVar16('');
      setVar17('');
      setVar18('');
    } else {
      setId(dados.id);
      setVar0(dados.cpf);
      setVar1(dados.nome);
      setVar2(dados.dataNacimento);
      setVar11(dados.email);
      setVar12(dados.senha);
      setVar13(dados.senha);
      //setVar14(`+${dados.ddi1} (${dados.ddd1}) ${dados.num1}`);
      setVar14("+"+dados.ddi1 + " (" + dados.ddd1 + ") " + dados.num1);
      setVar15(`+${dados.ddi2} (${dados.ddd2}) ${dados.num2}`);
      //setVar16(dados.endereco_id);
      setVar5(dados.cidade);//cidade
      setVar6(dados.cep);//cep
      setVar7(dados.numero);//num
      setVar8(dados.complemento);//com
      setVar9(dados.logradouro);//log
      setVar10(dados.bairro);//bai 
      setVar17(dados.UF_id);//id uf
      setVar18(dados.pais_id);//ud pais 
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
      var7
      //completar aqiiioaisaisaa
    };
    data = JSON.stringify(data);
    if (idParam == null) {
      await axios
        .post(baseURL_endereco, data, {
          headers: { 'Content-Type': 'application/json' },
        })
        .then(function (response) {
          mensagemSucesso(`Cliente ${var1} cadastrado com sucesso!`);
          navigate(`/listagem-cliente`);
        })
        .catch(function (error) {
          mensagemErro(error.response.data);
        });
    } else {
      await axios
        .put(`${baseURL_endereco}/${idParam}`, data, {
          headers: { 'Content-Type': 'application/json' },
        })
        .then(function (response) {
          mensagemSucesso(`Cliente ${var1} alterado com sucesso!`);
          navigate(`/listagem-cliente`);
        })
        .catch(function (error) {
          mensagemErro(error.response.data);
        });
    }
  }
  
  async function buscar() {
    // console.log("buscar")
    if (idParam != null) { //evitar busca desnecessaria
      await axios.get(`${baseURL}/${idParam}`).then((response) => {
        setDados(response.data);
      });
      setId(dados.id);
      setVar0(dados.cpf);
      setVar1(dados.nome);
      setVar2(dados.dataNacimento);
      setVar11(dados.email);
      setVar12(dados.senha);
      setVar13(dados.senha);
      //setVar14(`+${dados.ddi1} (${dados.ddd1}) ${dados.num1}`);
      setVar14("+"+dados.ddi1 + " (" + dados.ddd1 + ") " + dados.num1);
      setVar15(`+${dados.ddi2} (${dados.ddd2}) ${dados.num2}`);
      //setVar16(dados.endereco_id);
      setVar5(dados.cidade);//cidade
      setVar6(dados.cep);//cep
      setVar7(dados.numero);//num
      setVar8(dados.complemento);//com
      setVar9(dados.logradouro);//log
      setVar10(dados.bairro);//bai 
      setVar17(dados.UF_id);//id uf
      setVar18(dados.pais_id);//ud pais 

    }
  }


  const [dados3, setDados3] = React.useState(null); //uf

  useEffect(() => {
    axios.get(`${baseURL_uf}`).then((response) => {
      setDados3(response.data);
    });
    // setVar4(dados3.titulo)
  }, []); 

  const [dados4, setDados4] = React.useState(null); //pais

  useEffect(() => {
    axios.get(`${baseURL_pais}`).then((response) => {
      setDados4(response.data);
    });
    // setVar3(dados4.titulo)
  }, []); 
  
  
  useEffect(() => {
    buscar(); // eslint-disable-next-line
  }, [id]);
  
  /* useEffect(() => {
    buscar2(); // eslint-disable-next-line
  }, [var16, dados]); */

  if (!dados) return null;
  // if (!dados2) return null;
  if (!dados3) return null;
  if (!dados4) return null;

  return (
    <div className='container'>
      <Card title='Cadastro de Cliente'>
        <div className='row'>
          <div className='col-lg-12'>
            <div className='bs-component'>
            <FormGroup label='CPF: *' htmlFor='inputCPF'>
              <input
                type='number'
                id='inputCPF'
                value={var0}
                className='form-control'
                name='cpf'
                onChange={(e) => setVar0(e.target.value)}
              />
            </FormGroup>
            <FormGroup label='Nome: *' htmlFor='inputNome'>
                <input
                  type='text'
                  id='inputNome'
                  value={var1}
                  className='form-control'
                  name='nome'
                  onChange={(e) => setVar1(e.target.value)}
                />
              </FormGroup>
            <FormGroup label='Data de Nascimento: *' htmlFor='inputDataNascimento'>
                <input
                  type='date'
                  id='inputDataNascimento'
                  value={var2}
                  className='form-control'
                  name='datanascimento'
                  onChange={(e) => setVar2(e.target.value)}
                />
              </FormGroup>
            <FormGroup label='E-mail: *' htmlFor='inputEmail'>
                <input
                  type='email'
                  id='inputEmail'
                  value={var11}
                  className='form-control'
                  name='email'
                  onChange={(e) => setVar11(e.target.value)}
                />
              </FormGroup>
            {/* <FormGroup label='Senha1: *' htmlFor='inputSenha1'>
                <input
                  type='password'
                  id='inputSenha1'
                  value={var12}
                  className='form-control'
                  name='senha1'
                  onChange={(e) => setVar12(e.target.value)}
                />
              </FormGroup> */}
            {/* <FormGroup label='Senha2: *' htmlFor='inputSenha2'>
                <input
                  type='password'
                  id='inputSenha2'
                  value={var13}
                  className='form-control'
                  name='senha2'
                  onChange={(e) => setVar13(e.target.value)}
                />
              </FormGroup> */}
              <FormGroup label='Telefone 1: *' htmlFor='inputTelefone'>
                <input
                  type='text'
                  id='inputTelefone'
                  value={var14}
                  className='form-control'
                  name='telefone'
                  onChange={(e) => setVar14(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Telefone 2: *' htmlFor='inputTelefone2'>
                <input
                  type='text'
                  id='inputTelefone2'
                  value={var15}
                  className='form-control'
                  name='telefone2'
                  onChange={(e) => setVar15(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Logradouro: *' htmlFor='inputLogradouro'>
                <input
                  type='text'
                  id='inputLogradouro'
                  value={var9}
                  className='form-control'
                  name='logradouro'
                  onChange={(e) => setVar9(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Nº: *' htmlFor='inputNum'>
                <input
                  type='number'
                  id='inputNum'
                  value={var7}
                  className='form-control'
                  name='num'
                  onChange={(e) => setVar7(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='Complemento: ' htmlFor='inputComplemento'>
                <input
                  type='number'
                  id='inputComplemento'
                  value={var8}
                  className='form-control'
                  name='complemento'
                  onChange={(e) => setVar8(e.target.value)}
                />
                </FormGroup>
                <FormGroup label='Bairro: *' htmlFor='inputBairro'>
                <input
                  type='text'
                  id='inputBairro'
                  value={var10}
                  className='form-control'
                  name='bairro'
                  onChange={(e) => setVar10(e.target.value)}
                />
                </FormGroup>
                <FormGroup label='Cidade: *' htmlFor='inputCidade'>
                <input
                  type='text'
                  id='inputCidade'
                  value={var5}
                  // value={dados2[3].cidade}
                  className='form-control'
                  name='cidade'
                  onChange={(e) => setVar5(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='UF: *' htmlFor='selectUF'>
                <select
                  className='form-select'
                  id='selectUF'
                  name='uf'
                  value={var17}
                  onChange={(e) => setVar17(e.target.value)}
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
              <FormGroup label='CEP: *' htmlFor='inputCEP'>
                <input
                  type='text'
                  id='inputCEP'
                  value={var6}
                  className='form-control'
                  name='CEP'
                  onChange={(e) => setVar6(e.target.value)}
                />
              </FormGroup>
              <FormGroup label='País: *' htmlFor='selectPais'>
                <select
                  className='form-select'
                  id='selectPais'
                  name='pais'
                  value={var18}
                  onChange={(e) => setVar18(e.target.value)}
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

export default CadastroCliente;
