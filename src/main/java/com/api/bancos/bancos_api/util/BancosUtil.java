package com.api.bancos.bancos_api.util;

import java.util.Map;

import com.google.gson.Gson;

public class BancosUtil {

	static Map<String, String> BANCOS_MAP = Map.ofEntries(
	        Map.entry("c6bank", "Banco c6"),
	        Map.entry("nubank", "Nubank"),
	        Map.entry("itau", "Itaú"),
	        Map.entry("bancoitau", "Itaú"),
	        Map.entry("itaucard", "Itaú"),
	        Map.entry("bancointer", "Banco Inter"),
	        Map.entry("inter", "Banco Inter"),
	        Map.entry("bancobb", "Banco do Brasil"),
	        Map.entry("bb", "Banco do Brasil")
	    );
	
	private static Gson gson = new Gson();
	
	public static String getJsonBancos() {
        return gson.toJson(BANCOS_MAP);
	}
	
	public static Map<String, String> getMapBancos() {
        return BANCOS_MAP;
    }
}
