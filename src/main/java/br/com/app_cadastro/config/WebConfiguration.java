package br.com.app_cadastro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorParameter(false)//sempre especificará o formato padrão primeiro - JSON
		.ignoreAcceptHeader(false)//não vai ignorar o formato especificado no cabeçalho da requisição
		.defaultContentType(MediaType.APPLICATION_JSON) //É o formato padrão
		.mediaType("json", MediaType.APPLICATION_JSON)//é o formato suportado pela API
		.mediaType("xml", MediaType.APPLICATION_XML);
	}
}