package persistencia.jpa.bean;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;


@Entity
@Table(name = "restaurante")
public class Restaurante implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;  
    @Column(name ="fecha_alta", columnDefinition = "DATE")
    private LocalDate fechaAlta;
    @Column(name ="valoracion_global")
    private Double valoracionGlobal;
    @Column(name ="num_valoraciones")
    private Integer numValoraciones;
    @Column(name ="num_penalizaciones")
    private Integer numPenalizaciones;
    @JoinColumn(name = "responsable")
    @ManyToOne
    private Usuario responsable;

    private static final long serialVersionUID = 1L;

    public Restaurante() {
        super();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Double getValoracionGlobal() {
		return valoracionGlobal;
	}

	public void setValoracionGlobal(Double valoracionGlobal) {
		this.valoracionGlobal = valoracionGlobal;
	}

	public Integer getNumValoraciones() {
		return numValoraciones;
	}

	public void setNumValoraciones(Integer numValoraciones) {
		this.numValoraciones = numValoraciones;
	}

	public Integer getNumPenalizaciones() {
		return numPenalizaciones;
	}

	public void setNumPenalizaciones(Integer numPenalizaciones) {
		this.numPenalizaciones = numPenalizaciones;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setResponsable(Usuario u) {
		responsable=u;
		
	}
  
    
    
    
    
}