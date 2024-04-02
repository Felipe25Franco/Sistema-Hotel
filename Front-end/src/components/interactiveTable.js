// InteractiveTable.js
import React, { useState, useEffect } from 'react';
import { IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import AddBoxIcon from '@mui/icons-material/AddBox';

import axios from 'axios';
import { URL_quarto } from '../config/axios';



const InteractiveTable = () => {

  const [dados3, setDados3] = React.useState(null); //tipo Produto
  
  useEffect(() => {
    axios.get(`${URL_quarto}/quarto`).then((response) => {
      setDados3(response.data);
    });
  }, []);

  const [dados4, setDados4] = React.useState(null); //tipo Produto

  useEffect(() => {
    axios.get(`${URL_quarto}/tipoQuarto`).then((response) => {
      setDados4(response.data);
    });
  }, []);

  const [tableData, setTableData] = useState([]);

  const addRow = () => {

    const newRow = {
      id: tableData.length + 1,
      tipoQuarto: "null",
      num: 0,
      quantidade: 0
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

  return (
    <div>
      <table className="table table-hover">
        <thead>
          <tr>
            <th scope="col">Tipo</th>
            <th scope="col">Nº</th>
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
                  {dados4.map((dado) => (
                    <option key={dado.id} value={dado.id}>
                      {dado.titulo}
                    </option>
                  ))}
                </select>
              </td>
              <td>
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
              </td>
              <td>
                <input 
                  type='number' 
                  className='form-control'
                  value = {row.quantidade}
                  onChange={(e) => handleChange(row.id, 'quantidade', e.target.value)}>
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

export default InteractiveTable;
