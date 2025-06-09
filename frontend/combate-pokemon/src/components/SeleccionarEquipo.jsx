import { useEffect, useState } from "react";
import axios from "axios";
import HealthBar from "./HealthBar";

function SeleccionarEquipo() {
  const [pokemonDisponibles, setPokemonDisponibles] = useState([]);
  const [seleccionados, setSeleccionados] = useState(["", "", ""]);
  const [combate, setCombate] = useState(null);
  const [cambiando, setCambiando] = useState(false);
  const [nuevoPokemon, setNuevoPokemon] = useState("");
  const [mensajeFinal, setMensajeFinal] = useState("");

  // Cargar lista de Pokémon disponibles
  useEffect(() => {
    axios.get("http://localhost:8080/pokemon/lista")
      .then(response => setPokemonDisponibles(response.data))
      .catch(error => console.error("Error al obtener los Pokémon:", error));
  }, []);

  // Verifica si debe forzarse el cambio de Pokémon
  useEffect(() => {
    if (combate && combate.activoJugador.saludActual <= 0) {
      const hayReemplazo = combate.equipoJugador.some(
        p => p.saludActual > 0 && p.pokemon.id_pokemon !== combate.activoJugador.pokemon.id_pokemon
      );
      if (hayReemplazo) {
        setCambiando(true);
      }
    }
  }, [combate]);

  // Verifica si se terminó el combate
  useEffect(() => {
    if (!combate) return;

    const jugadorPerdio = combate.activoJugador.saludActual <= 0 &&
      combate.equipoJugador.every(p => p.saludActual <= 0);
    const jugadorGano = combate.activoIA.saludActual <= 0 &&
      combate.equipoIA.every(p => p.saludActual <= 0);

    if (jugadorPerdio) {
      setMensajeFinal("¡Perdiste el combate!");
      setTimeout(() => reiniciarJuego(), 4000);
    } else if (jugadorGano) {
      setMensajeFinal("¡Ganaste el combate!");
      setTimeout(() => reiniciarJuego(), 4000);
    }
  }, [combate]);

  const reiniciarJuego = () => {
    setCombate(null);
    setSeleccionados(["", "", ""]);
    setMensajeFinal("");
    setCambiando(false);
    setNuevoPokemon("");
  };

  const manejarCambio = (index, id) => {
    const nuevosSeleccionados = [...seleccionados];
    nuevosSeleccionados[index] = id;
    setSeleccionados(nuevosSeleccionados);
  };

  const manejarEnvio = () => {
    const ids = seleccionados.map(id => Number(id));
    if (ids.includes(NaN) || ids.includes(0) || new Set(ids).size < 3) {
      alert("Seleccioná 3 Pokémon diferentes antes de comenzar.");
      return;
    }

    axios.post("http://localhost:8080/combate/iniciar", ids)
      .then(response => setCombate(response.data))
      .catch(error => console.error("Error al iniciar el combate:", error));
  };

  const manejarAtaque = (id_ataque) => {
    axios.post("http://localhost:8080/combate/turno", id_ataque, {
      headers: { "Content-Type": "application/json" }
    })
      .then(response => setCombate(response.data))
      .catch(error => {
        console.error("Error al procesar el turno:", error);
        alert("Error al procesar el turno");
      });
  };

  const manejarConfirmarCambio = () => {
    const indice = combate.equipoJugador.findIndex(
      p => p.pokemon.id_pokemon === Number(nuevoPokemon)
    );

    if (indice === -1) {
      alert("Pokémon no válido para cambio");
      return;
    }

    axios.post("http://localhost:8080/combate/cambiar", indice, {
      headers: { "Content-Type": "application/json" }
    })
      .then(response => {
        setCombate(response.data);
        setCambiando(false);
        setNuevoPokemon("");
      })
      .catch(error => {
        console.error("Error al cambiar de Pokémon:", error);
        alert(error.response?.data || "Error en el cambio");
      });
  };

  const hayPokemonParaCambiar = combate?.equipoJugador.some(
    p => p.saludActual > 0 && p.pokemon.id_pokemon !== combate.activoJugador.pokemon.id_pokemon
  );

  return (
    <div>
      {!combate && (
        <>
          <h2>Elegí tus 3 Pokémon</h2>
          <div className="select-row">
          {seleccionados.map((valor, index) => (
          <div key={index} className="select-container">
          <label>Pokémon {index + 1}:</label>
      <select value={valor} onChange={e => manejarCambio(index, e.target.value)}>
        <option value="">-- Seleccionar --</option>
        {pokemonDisponibles.map(p => (
          <option key={p.id_pokemon} value={p.id_pokemon}>
            {p.nombre}
          </option>
        ))}
      </select>
    </div>
  ))}
</div>

          <button onClick={manejarEnvio}>Iniciar combate</button>
        </>
      )}

      {combate && (
        <div>
          <h2>¡Combate iniciado!</h2>

          <div>
            <p><strong>Tu Pokémon activo:</strong> {combate.activoJugador.pokemon.nombre}</p>
            <HealthBar saludActual={combate.activoJugador.saludActual} saludMaxima={combate.activoJugador.pokemon.saludMaxima} />
          </div>

          <h4>Ataques:</h4>
          {cambiando ? (
            <p>Seleccioná un Pokémon para continuar el combate.</p>
          ) : (
            combate.activoJugador.pokemon.ataques.map((ataque, index) => (
              <button key={index} onClick={() => manejarAtaque(ataque.id_ataque)}>
                {ataque.nombre} (Potencia: {ataque.potencia})
              </button>
            ))
          )}

          <button
            onClick={() => setCambiando(true)}
            disabled={!hayPokemonParaCambiar}
            title={!hayPokemonParaCambiar ? "No hay otros Pokémon vivos para cambiar" : ""}
          >
            Cambiar Pokémon
          </button>

          {cambiando && (
            <div>
              <h4>Elegí un Pokémon para cambiar</h4>
              <select value={nuevoPokemon} onChange={e => setNuevoPokemon(e.target.value)}>
                <option value="">-- Seleccionar --</option>
                {combate.equipoJugador
                  .filter(p => p.saludActual > 0 && p.pokemon.id_pokemon !== combate.activoJugador.pokemon.id_pokemon)
                  .map((p, index) => (
                    <option key={index} value={p.pokemon.id_pokemon}>
                      {p.pokemon.nombre}
                    </option>
                  ))}
              </select>
              <button onClick={manejarConfirmarCambio}>Confirmar cambio</button>
              <button onClick={() => setCambiando(false)}>Cancelar</button>
            </div>
          )}

          <div>
            <p><strong>IA Pokémon activo:</strong> {combate.activoIA.pokemon.nombre}</p>
            <HealthBar saludActual={combate.activoIA.saludActual} saludMaxima={combate.activoIA.pokemon.saludMaxima} />
          </div>
        </div>
      )}

      {mensajeFinal && (
        <div>
          <h2>{mensajeFinal}</h2>
        </div>
      )}
    </div>
  );
}

export default SeleccionarEquipo;
