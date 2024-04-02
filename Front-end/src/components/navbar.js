import React, { useState } from 'react';
import 'bootswatch/dist/lumen/bootstrap.css';

import NavbarItem from './navbarItem';

function Navbar(props) {
  const [dropdown1Open, setDropdown1Open] = useState(false);
  const [dropdown2Open, setDropdown2Open] = useState(false);
  const [dropdown3Open, setDropdown3Open] = useState(false);
  const [dropdown4Open, setDropdown4Open] = useState(false);
  const [dropdown6Open, setDropdown6Open] = useState(false);
  const [dropdown5Open, setDropdown5Open] = useState(false);
  const [tipoComodidadeDropdownOpen, setTipoComodidadeDropdownOpen] = useState(false); 
  const [tipoQuartosDropdownOpen, setTipoQuartosDropdownOpen] = useState(false);

  const toggleDropdown1 = () => {
    setDropdown1Open(!dropdown1Open);
  };

  const toggleDropdown2 = () => {
    setDropdown2Open(!dropdown2Open);
  };

  const toggleDropdown3 = () => {
    setDropdown3Open(!dropdown3Open);
  };

  const toggleDropdown4 = () => {
    setDropdown4Open(!dropdown4Open);
  };

  const toggleDropdown5 = () => {
    setDropdown5Open(!dropdown5Open);
  };

  const toggleDropdown6 = () => {
    setDropdown6Open(!dropdown6Open);
  };

  const toggleTipoComodidadeDropdown = () => {
    setTipoComodidadeDropdownOpen(!tipoComodidadeDropdownOpen);
  };

  const toggleTipoQuartosDropdown = () => {
    setTipoQuartosDropdownOpen(!tipoQuartosDropdownOpen);
  };

  const handleMouseEnter1 = () => {
    setDropdown1Open(true);
  };

  const handleMouseLeave1 = () => {
    setDropdown1Open(false);
  };

  const handleMouseEnter2 = () => {
    setDropdown2Open(true);
  };

  const handleMouseLeave2 = () => {
    setDropdown2Open(false);
  };

  const handleMouseEnter3 = () => {
    setDropdown3Open(true);
  };

  const handleMouseLeave3 = () => {
    setDropdown3Open(false);
  };

  const handleMouseEnter4 = () => {
    setDropdown4Open(true);
  };

  const handleMouseLeave4 = () => {
    setDropdown4Open(false);
  };

  const handleMouseEnter5 = () => {
    setDropdown5Open(true);
  };

  const handleMouseLeave5 = () => {
    setDropdown5Open(false);
  };

  const handleMouseEnter6 = () => {
    setDropdown6Open(true);
  };

  const handleMouseLeave6 = () => {
    setDropdown6Open(false);
  };

  const handleMouseEnterTipoComodidade = () => {
    setTipoComodidadeDropdownOpen(true);
  };

  const handleMouseLeaveTipoComodidade = () => {
    setTipoComodidadeDropdownOpen(false);
  };

  const handleMouseEnterTipoQuartos = () => {
    setTipoQuartosDropdownOpen(true);
  };

  const handleMouseLeaveTipoQuartos = () => {
    setTipoQuartosDropdownOpen(false);
  };

  return (
    <div className='navbar navbar-expand-lg fixed-top navbar-dark bg-primary'>
      <div className='container'>
        <a href='/' className='navbar-brand'>
          REDE DE HOTÉIS X
        </a>
        <button
          className='navbar-toggler'
          type='button'
          data-toggle='collapse'
          data-target='#navbarResponsive'
          aria-controls='navbarResponsive'
          aria-expanded='false'
          aria-label='Toggle navigation'
        >
          <span className='navbar-toggler-icon'></span>
        </button>
        <div className='collapse navbar-collapse' id='navbarResponsive'>
          <ul className='navbar-nav'>
            <NavbarItem render='true' href='/login' label='Entrar' />
          </ul>
          

          <ul className="nav-item-dropdown" onMouseEnter={handleMouseEnter4} onMouseLeave={handleMouseLeave4}>
            <a className={`nav-link dropdown-toggle ${dropdown4Open ? 'show' : ''}`} onClick={toggleDropdown4}> Hotéis
            </a>
            <div className={`dropdown-menu ${dropdown4Open ? 'show' : ''}`}>
              <a className="dropdown-item" href='/listagem-hoteis'>Hotéis</a>
              <a className="dropdown-item" href='/listagem-avaliacao-hospedagem'>Avaliações de Hotéis</a>
            </div>
          </ul>


          <ul className='navbar-nav'>
            <NavbarItem render='true' href='/listagem-cliente' label='Clientes' />
          </ul>


          
          <ul className="nav-item-dropdown" onMouseEnter={handleMouseEnter3} onMouseLeave={handleMouseLeave3}>
            <a className={`nav-link dropdown-toggle ${dropdown3Open ? 'show' : ''}`} onClick={toggleDropdown3}> Funcionários
            </a>
            <div className={`dropdown-menu ${dropdown3Open ? 'show' : ''}`}>
              <a className="dropdown-item" href='/listagem-funcionario'>Funcionários</a>
              <a className="dropdown-item" href='/listagem-cargos'>Cargos</a>
            </div>
          </ul>



          <ul className="nav-item-dropdown" onMouseEnter={handleMouseEnter1} onMouseLeave={handleMouseLeave1}>
            <a className={`nav-link dropdown-toggle ${dropdown1Open ? 'show' : ''}`} onClick={toggleDropdown1}> Produtos
            </a>
            <div className={`dropdown-menu ${dropdown1Open ? 'show' : ''}`}>
              <a className="dropdown-item" href='/listagem-produtos'>Produtos</a>
              <a className="dropdown-item" href='/listagem-tipo-produto'>Tipos de Produto</a>
            </div>
          </ul>



          <ul className="nav-item-dropdown" onMouseEnter={handleMouseEnter2} onMouseLeave={handleMouseLeave2}>
            <a className={`nav-link dropdown-toggle ${dropdown2Open ? 'show' : ''}`} onClick={toggleDropdown2}> Serviços
            </a>
            <div className={`dropdown-menu ${dropdown2Open ? 'show' : ''}`}>
              <a className="dropdown-item" href='/listagem-servicos'>Serviços</a>
              <a className="dropdown-item" href='/listagem-tipo-servico'>Tipos de Serviço</a>
            </div>
          </ul>


          
          <ul className="nav-item-dropdown" onMouseEnter={handleMouseEnter5} onMouseLeave={handleMouseLeave5}>
            <a className={`nav-link dropdown-toggle ${dropdown5Open ? 'show' : ''}`} onClick={toggleDropdown5}> Quartos
            </a>
            <div className={`dropdown-menu ${dropdown5Open ? 'show' : ''}`}>




              


              <ul className="nav-item-dropdown" onMouseEnter={handleMouseEnter6} onMouseLeave={handleMouseLeave6}>
                <a className={`nav-link-dropdown-toggle ${dropdown6Open ? 'show' : ''}`} onClick={toggleDropdown6}> Quartos </a>
                <div className={`dropdown-menu ${dropdown6Open ? 'show' : ''}`} style={{ position: 'absolute', left: '100%', top: '0px' }}>
                  <a className="dropdown-item" href='/listagem-quarto'>Quartos</a>
                  <a className="dropdown-item" href='/listagem-avaliacao-quarto'>Avaliações de Quartos</a>
                </div>
              </ul>




              <ul className="nav-item-dropdown" onMouseEnter={handleMouseEnterTipoQuartos} onMouseLeave={handleMouseLeaveTipoQuartos}>
                <a className={`nav-link-dropdown-toggle ${tipoQuartosDropdownOpen ? 'show' : ''}`} onClick={toggleTipoQuartosDropdown}> Tipos de Quarto </a>
                <div className={`dropdown-menu ${tipoQuartosDropdownOpen ? 'show' : ''}`} style={{ position: 'absolute', left: '100%', top: '50px' }}>
                  <a className="dropdown-item" href='/listagem-tipo-quarto'>Tipos de Quarto</a>
                  <a className="dropdown-item" href='/listagem-tipo-cama'>Tipos de Cama</a>
                </div>
              </ul>
              <ul className="nav-item-dropdown" onMouseEnter={handleMouseEnterTipoComodidade} onMouseLeave={handleMouseLeaveTipoComodidade}>
                <a className={`nav-link-dropdown-toggle ${tipoComodidadeDropdownOpen ? 'show' : ''}`} onClick={toggleTipoComodidadeDropdown}>Comodidades </a>
                <div className={`dropdown-menu ${tipoComodidadeDropdownOpen ? 'show' : ''}`} style={{ position: 'absolute', left: '100%', top: '95px' }}>
                  <a className="dropdown-item" href='/listagem-comodidades'>Comodidades</a>
                  <a className="dropdown-item" href='/listagem-tipo-comodidade'>Tipos de Comodidade</a>
                </div>
              </ul>
            </div>
          </ul>



          <ul className='navbar-nav'>
            <NavbarItem render='true' href='/listagem-reserva' label='Reservas' />
          </ul>
          <ul className='navbar-nav'>
            <NavbarItem render='true' href='/listagem-hospedagem' label='Hospedagens' />
          </ul>

          
          
        </div>
      </div>
    </div>
  );
}

export default Navbar;