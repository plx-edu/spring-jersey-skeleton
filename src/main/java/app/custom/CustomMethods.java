package app.custom;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import app.component.User;


public class CustomMethods {
	
	// Makes salt for password encryption
//	public static byte[] generateSalt() throws NoSuchAlgorithmException {
	public byte[] generateSalt() {
		byte[] salt = new byte[16];
		SecureRandom sr = new SecureRandom();
		sr.nextBytes(salt);
		return salt;
	}// generateSalt()
	
	// Hash password
	// *********************************************************************************************
	// **********************     TEMPORARY NOTE     ***********************************************
	// https://stackoverflow.com/questions/2860943/how-can-i-hash-a-password-in-java/2861125#2861125
	// *********************************************************************************************
	// *********************************************************************************************
	public String hashPassword(String password, byte[] salt) {
		String hashedPassword = "";
		
		try {
			
			// Generate Hash
			KeySpec ks = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1"); // e1 catch
			byte[] hash = skf.generateSecret(ks).getEncoded(); // e2 catch
			
			// Convert Hash (for storage)
			Base64.Encoder enc = Base64.getEncoder();
			hashedPassword = enc.encodeToString(hash);
			
//			System.out.printf("enc salt: %s%n", enc.encodeToString(salt));
//			System.out.printf("enc hash: %s%n", hashedPassword);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (InvalidKeySpecException e2) {
			e2.printStackTrace();
		}
		
		return hashedPassword;
	}// hashPassword()
	
	
	public boolean validPassword(String password) {
		System.out.println(":: Password validation returns "+(password != null && password.length() >= 8));
		return (password != null && password.length() >= 8);
	}// validPassword()
	
	public boolean validHandle(String handle) {
		if(handle!= null) {
			// At least one character, letter or number
			// Maximum 25
			String regex = "^[a-zA-Z0-9]{1,25}$";
			
			// Create a Pattern object
			Pattern pattern = Pattern.compile(regex);
			// Matcher object
			Matcher matcher = pattern.matcher(handle);
			System.out.println(":: Handle format validation returns "+matcher.matches()+" for '"+handle+"'");
			
			return matcher.matches();
//			return matcher.find();
		}else {
			return false;
		}
	}// validHandle()
	
	public boolean validID(String id) {
		if(id!= null) {
			// Six characters containing
			// letters, numbers, minus sign and/or underscore
			String regex = "^[a-zA-Z0-9-_]{6}$";
			
			// Create a Pattern object
			Pattern pattern = Pattern.compile(regex);
			// Matcher object
			Matcher matcher = pattern.matcher(id);
			System.out.println(":: ID format validation returns "+matcher.matches()+" for '"+id+"'");
			
			return matcher.matches();
		}else {
			return false;
		}
	}// validID()
	
	public User errorUser() {
		User user = new User();
		user.setName("error");
		return user;
	}
}// - CustomMethods




