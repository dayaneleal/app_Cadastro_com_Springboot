package br.com.app_cadastro.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;


//SourceClassName = origem do objeto que sera convertido
//DestinationClassName = para qual classe o objeto tera um novo formato
//mapper.map = faz a transferencia de informação de uma classe origem para seu destino

public class DozerConverter {
//cria nova instancia do mapper
	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
	public static<SourceClassName, DestinationClassName> DestinationClassName 
	parseObject(SourceClassName source, Class<DestinationClassName> destination){
		return mapper.map(source, destination);
	}
	
	public static <SourceClassName, DestinationClassName> List<DestinationClassName> parseListObject(
			List<SourceClassName> source, Class<DestinationClassName> destination) {

		List<DestinationClassName> destinationObject = new ArrayList<>();

//		source.stream().map(obj -> mapper.map(obj, destination)).collect(Collectors.toList());
		for (Object obj : source) {
			destinationObject.add(mapper.map(obj, destination));
		}
		return destinationObject;
	}

	
}
