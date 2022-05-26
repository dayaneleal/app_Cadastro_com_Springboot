package br.com.app_cadastro.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Entity
@Table(name="user")
public class User implements UserDetails, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="user_name", unique=true) //não permite repetição do valor
	private String userName;
	@Column(name="full_name")
	private String fullName;
	@Column(name="password")
	private String password;
	@Column(name="account_non_expired")
	private Boolean accountNonExpired;
	@Column(name="account_non_locked")
	private Boolean accountNonLocked;
	@Column(name="credential_non_expired")
	private Boolean credentialNonExpired;
	@Column(name="enable")
	private Boolean enabled;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.permissions;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return this.credentialNonExpired;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.enabled;
	}

	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="user_permission", joinColumns ={@JoinColumn(name="id_user")},
	inverseJoinColumns = {@JoinColumn(name="id_permission")})
	private List<Permission>permissions;
	
	public List<String>getRoles(){
		List<String>roles = new ArrayList<>();
		for(Permission permission : this.permissions) {
			roles.add(permission.getDescription());
		}
		return roles;
	}

}
