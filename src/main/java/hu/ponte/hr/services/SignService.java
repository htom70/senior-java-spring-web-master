package hu.ponte.hr.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class SignService {

	Logger logger = LoggerFactory.getLogger(SignService.class);

	public String sign(byte[] content) {
		String signatureString = null;
		Signature signature = null;
		try {
			signature = Signature.getInstance("SHA256withRSA");
		} catch (NoSuchAlgorithmException e) {
			logger.error("No such algorithm");
		}
		try {
			signature.initSign(getPrivatkey());
		} catch (InvalidKeyException e) {
			logger.error("Invalid key");
		}
		try {
			signature.update(content);
			byte[] signatureByteArray = signature.sign();
			signatureString = Base64.encodeBase64String(signatureByteArray);
		} catch (SignatureException e) {
			logger.error("Error occured during signature");
		}
		return signatureString;
	}

	private PrivateKey getPrivatkey() {
		File file = null;
		try {
			file = ResourceUtils.getFile("classpath:config/keys/key.private");
		} catch (FileNotFoundException e) {
			logger.error("Private key file not found");
		}
		byte[] keyBytes = new byte[0];
		try {
			keyBytes = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			logger.error("IO errod occured");
		}
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			logger.error("No such algorithm");
		}
		PrivateKey privateKey = null;
		try {
			privateKey = keyFactory.generatePrivate(spec);
		} catch (InvalidKeySpecException e) {
			logger.error("Invalid key");
		}
		return privateKey;
	}
}
