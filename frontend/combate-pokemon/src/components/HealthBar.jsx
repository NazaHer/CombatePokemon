function HealthBar({ saludActual, saludMaxima }) {
  const vida = Math.max(saludActual, 0);
  const porcentaje = Math.max((vida / saludMaxima) * 100, 0);

  const getColor = () => {
    if (porcentaje > 60) return "green";
    if (porcentaje > 30) return "orange";
    return "red";
  };

  return (
    <div style={{ backgroundColor: "#ddd", width: "100%", height: "20px", borderRadius: "5px" }}>
      <div
        style={{
          width: `${porcentaje}%`,
          height: "100%",
          backgroundColor: porcentaje > 50 ? "green" : porcentaje > 20 ? "orange" : "red",
          borderRadius: "5px",
          transition: "width 0.5s ease"
        }}
      />
      <span>{`${vida}/${saludMaxima}`}</span>
    </div>
  );
}

export default HealthBar;
