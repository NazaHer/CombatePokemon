import { useEffect, useState } from 'react';
import axios from 'axios';

function InicioCombate() {
  const [pokemones, setPokemones] = useState([]);
  const [seleccionados, setSeleccionados] = useState([]);
  const [combate, setCombate] = useState(null);

  useEffect(() => {
    axios.get('http://localhost:8080/pokemon/lista')
      .then(res => setPokemones(res.data))
      .catch(err => console.error(err));
  }, []);

  const manejarSeleccion = (e, index) => {
    const nuevaSeleccion = [...seleccionados];
    nuevaSeleccion[index] = parseInt(e.target.value);
    setSeleccionados(nuevaSeleccion);
  };

  const iniciarCombate = () => {
    if (seleccionados.length !== 3 || seleccionados.includes(undefined)) {
      alert('Debes seleccionar 3 Pokémon.');
      return;
    }

    axios.post('http://localhost:8080/combate/iniciar', seleccionados)
      .then(res => {
        setCombate(res.data);
      })
      .catch(err => {
        console.error('Error al iniciar combate:', err);
      });
  };

  return (
    <div className="container">
      <h2 className="titulo">Elegí tus 3 Pokémon</h2>

      <div className="select-row">
        {[0, 1, 2].map(i => (
          <select key={i} onChange={(e) => manejarSeleccion(e, i)} className="select-pokemon">
            <option value="">-- Seleccionar Pokémon --</option>
            {pokemones.map(p => (
              <option key={p.id_pokemon} value={p.id_pokemon}>
                {p.nombre}
              </option>
            ))}
          </select>
        ))}
      </div>

      <button onClick={iniciarCombate} className="btn-iniciar">
        Iniciar Combate
      </button>

      {combate && (
        <div className="resultado-combate">
          <h3>Combate Iniciado</h3>
          <p><strong>Tu Pokémon:</strong> {combate.activoJugador.pokemon.nombre}</p>
          <p><strong>IA Pokémon:</strong> {combate.activoIA.pokemon.nombre}</p>
        </div>
      )}
    </div>
  );
}

export default InicioCombate;
