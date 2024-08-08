// InteractiveTable.js
import React, { useState, useEffect } from 'react';
import { IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import AddBoxIcon from '@mui/icons-material/AddBox';

import axios from 'axios';
import { URL_quarto } from '../config/axios';



function InteractiveTable({tableData, infoTable}) {

  //let infoTable =[{head:"Título", prop:"titulo", initial:"", type: "text", extraData: null},{head:"Quantidade", prop:"qtd", initial:0, type: "number", extraData: null}];
  
  const addRow = () => {

    // const newRow2 = {
    //   id: tableData.length + 1,
    //   tipoQuarto: "null",
    //   num: 0,
    //   quantidade: 0
    // };

    const newRow = {};
    newRow.id = tableData.length + 1;
    for (const element of infoTable) {
      newRow[infoTable.prop] = infoTable.initial;
    }

    tableData = ([...tableData, newRow]);
  };

  const removeRow = (id) => {

    const updatedTableData = tableData.filter(row => row.id !== id);

    tableData = (updatedTableData);
  };

  const handleChange = (id, column, value) => {
    const updatedRows = tableData.map((row) =>
      row.id === id ? { ...row, [column]: value } : row
    );
    tableData = (updatedRows);
  };
  if(!tableData) return null;
  return (
    <div>
      <table className="table table-hover">
        <thead>
          <tr>
            {infoTable.map(obj =>(<th scope="col">{obj.head}</th>))}
            <th scope="col">Ações</th>
          </tr>
        </thead>
        <tbody>
          {tableData.map(row => (
            <tr key={row.id} className="table-light">
              {()=> 
                {
                  for (const obj of infoTable) {
                    if(!obj.type==="select") {
                      <td>
                        <input 
                          type={obj.type}
                          className='form-control'
                          value = {row[obj.prop]}
                          onChange={(e) => handleChange(row.id, obj.prop, e.target.value)}>
                        </input>
                      </td>
                    }
                    else {
                      <td>
                        <select
                          className='form-select'
                          value={row[obj.prop]}
                          onChange={(e) => handleChange(row.id, obj.prop, e.target.value)}
                        >
                          <option key='0' value='0'>
                            {' '}
                          </option>
                          {obj.extraData.map((dado) => (
                            <option key={dado.id} value={dado.id}>
                              {dado.titulo}
                            </option>
                          ))}
                        </select>
                      </td>
                    }
                  }
                }
              }
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
