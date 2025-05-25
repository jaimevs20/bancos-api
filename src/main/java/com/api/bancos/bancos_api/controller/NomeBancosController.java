package com.api.bancos.bancos_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.bancos.bancos_api.model.Banco;
import com.api.bancos.bancos_api.model.BancosBrDTO;
import com.api.bancos.bancos_api.service.BancoService;

@RestController
@RequestMapping("/bancos")
public class NomeBancosController {

	@Autowired
	BancoService bancoService;
	
	@GetMapping("/nomebanco")
	public ResponseEntity<Banco> obterNomeBancoPorEmissor(@RequestParam String emissor) {
		
		if(!bancoService.obterNomeBancoPorEmissor(emissor).getEmissor().isBlank()) {
			return ResponseEntity.ok(bancoService.obterNomeBancoPorEmissor(emissor));
		} else {
			if (bancoService.getBancosBrasilApi(emissor) == null) {
				return ResponseEntity.internalServerError().build();
			} else if (bancoService.getBancosBrasilApi(emissor).getEmissor().isBlank()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(bancoService.getBancosBrasilApi(emissor));
			}
		}
	}
}
