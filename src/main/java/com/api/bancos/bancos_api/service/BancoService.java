package com.api.bancos.bancos_api.service;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.api.bancos.bancos_api.model.Banco;
import com.api.bancos.bancos_api.model.BancosBrDTO;
import com.api.bancos.bancos_api.util.BancosUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Service
public class BancoService {

	public String jsonBancos() {
		return BancosUtil.getJsonBancos();
	}
	
	public Map<String, String> jsonBancosMap() {
		return BancosUtil.getMapBancos();
	}
	
	public Banco obterNomeBancoPorEmissor(String emissor) {
		
		String nomeBanco = jsonBancosMap().get(emissor);
		
		if(nomeBanco != null) {
			return new Banco(emissor, nomeBanco);
		}
		
		return new Banco("","");
	}
	
	public Banco getBancosBrasilApi(String emissor) {
		HttpClient client = HttpClient.newHttpClient();
		Gson gson = new Gson();
	    
	    HttpRequest requestGet = HttpRequest.newBuilder()
	            .uri(URI.create("https://brasilapi.com.br/api/banks/v1"))
	            .header("Content-Type", "application/json")
	            .GET()
	            .build();
	    
	    try {
	        HttpResponse<String> response = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	
	            String responseBody = response.body();	       
	            
	            List<BancosBrDTO> bancosBrJsonArray = new ArrayList<>();
	            Type listType = new TypeToken<List<BancosBrDTO>>() {}.getType();
	            
	            bancosBrJsonArray = gson.fromJson(responseBody, listType);

	            return obterFullNameEmissor(bancosBrJsonArray, emissor);
	        }
	        
	        return new Banco("","");
	    } catch (IOException | InterruptedException e) {
	    	return null;
	    }
	}

	private Banco obterFullNameEmissor(List<BancosBrDTO> bancosBrJsonArray, String emissor) {
		bancosBrJsonArray.removeIf(banco -> banco.getFullName() == null);

		String nomeBanco = bancosBrJsonArray.stream()
			.filter(banco -> banco.getFullName() 
					//  TODO: VERIFICAR TAMBÉM FULLNAME.TOLOWERCASE.TRIM.CONTAINS(emissor) 
					.toLowerCase()
					.contains(emissor))
			.filter(banco -> banco.getFullName()
					.toLowerCase()
					.trim()
					.contains(emissor))
			.findFirst()
			.map(BancosBrDTO::getFullName)
			.orElse("");
		
		return new Banco(nomeBanco.isBlank() ? "" : emissor, nomeBanco);
	
	}
	
}
