package RemoteConsole;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Ciphers
{
	public static Cipher RSA_ENCODE;
	
	public static Cipher AES_DECODE;
	public static Cipher AES_ENCODE;
	
	public static SecretKey AES_SECRET_KEY;
	public static PublicKey RSA_PUBLIC_KEY;
	
	public static void init() throws NoSuchAlgorithmException, NoSuchPaddingException, IOException, InvalidKeySpecException, InvalidKeyException, ClassNotFoundException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
	{
		File publicKeyFile = new File("public_key");
		FileInputStream fileInputStream = new FileInputStream(publicKeyFile);
		
		byte[] encodedPublicKey = new byte[(int) publicKeyFile.length()];
		fileInputStream.read(encodedPublicKey);
		fileInputStream.close();
		
		KeyFactory keyFac = KeyFactory.getInstance("RSA");
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
		RSA_PUBLIC_KEY = keyFac.generatePublic(publicKeySpec);
		
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		AES_SECRET_KEY = keyGen.generateKey();
		
		RSA_ENCODE = Cipher.getInstance("RSA");
		RSA_ENCODE.init(Cipher.ENCRYPT_MODE, RSA_PUBLIC_KEY);
		
		AES_DECODE = Cipher.getInstance("AES/CTR/NoPadding");
		byte[] AES_DECODE_IV = new byte[AES_DECODE.getBlockSize()];
		IvParameterSpec AES_DECODE_PS = new IvParameterSpec(AES_DECODE_IV);
		AES_DECODE.init(Cipher.DECRYPT_MODE, AES_SECRET_KEY, AES_DECODE_PS);
		
		AES_ENCODE = Cipher.getInstance("AES/CTR/NoPadding");
		byte[] AES_ENCODE_IV = new byte[AES_DECODE.getBlockSize()];
		IvParameterSpec AES_ENCODE_PS = new IvParameterSpec(AES_ENCODE_IV);
		AES_ENCODE.init(Cipher.ENCRYPT_MODE, AES_SECRET_KEY, AES_ENCODE_PS);
		
		System.out.println(new SealedObject("AES key test succesfull!", AES_DECODE).getObject(AES_ENCODE));
	}
}
