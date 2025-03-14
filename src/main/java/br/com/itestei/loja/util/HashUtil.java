package br.com.itestei.loja.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class HashUtil {

	public static String hash(String palavra) {
		// "tempero" do hash
		String salt = "ZG96ZXBhc3RlaXNldmludGVlcXVhdHJvY2FsZG9kZWNhbmE=";
		// adicionar o tempero a palavra
		palavra = salt + palavra;
		// gerar hash utilizando sha256
		String hash = Hashing.sha256().hashString(palavra, StandardCharsets.UTF_8).toString();		
		
		return hash;
	}
	
}
