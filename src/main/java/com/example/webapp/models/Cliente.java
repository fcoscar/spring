package com.example.webapp.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String nombre;
	private String apellido;
	private String correo;
	private String fecha;
	@OneToMany(mappedBy = "cliente",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference//https://stackoverflow.com/questions/36983215/failed-to-write-http-message-org-springframework-http-converter-httpmessagenotw
	private List<Prestamos> prestamos;
	private String telefono;

	@PrePersist
	public void fecha(){
		fecha = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
	}

	public int clientePrestamos() {
		return getPrestamos().size();
	}
}
