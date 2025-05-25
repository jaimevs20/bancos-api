package com.api.bancos.bancos_api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BancosBrDTO {
	
	private String ispb;
	private String name;
	private String code;
	private String fullName;
}
