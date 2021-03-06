package br.com.app_cadastro.security.jwt;


import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtProvider {

	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey = "secret";
	@Value("${security.jwt.token.expire-length:3600000}")
	private long validate = 3600000; //1hora
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		}
	
	//BASE64 CONVERTE SÉRIE DE TEXTO PARA VALOR GERADO ALEATORIAMENTE
	
	@SuppressWarnings("deprecation")
	public String createToken(String username, List<String>roles) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("roles", roles);
		Date now = new Date();
		Date tempoValidade = new Date(now.getTime() + validate);
		//capturo o dado dos usuários e as permissões
		
		return Jwts.builder().setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(tempoValidade)
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}
	
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
	}

	private String getUsername(String token) {
		// TODO Auto-generated method stub
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(bearerToken != null && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7,bearerToken.length());
		}
		return bearerToken;
	}
	
	public boolean validateToken(String token) {
		try {
			Jws<Claims>claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			
			if(claims.getBody().getExpiration().before(new Date())) {
				return false;
			}
			return true;
		}catch(Exception e) {
			throw  new JwtException("Token expirado ou inválido");
		}
	}
}
