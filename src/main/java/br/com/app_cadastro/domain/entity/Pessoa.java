package br.com.app_cadastro.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;


@Entity
@Table(name="tb_pessoas")
@Data
public class Pessoa implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@NotBlank
	@Size(max=20)
	private String nome;
	
	@NotBlank
	@Size(max=40)
	private String sobrenome;
	
	@NotBlank
	@Size(max=35)
	
	private String nomeSocial;
	
	@NotBlank
	@Size(max=80)
	@Email
	private String email;
	
	
}
