package app.custom;

import java.io.Serializable;
//import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public class SaltGenerator implements IdentifierGenerator, Configurable {
	
	/**
	 * salt length we want
	 */
	private int n = 16;
	
	// Makes salt for password encryption
//	protected byte[] generateSalt() throws NoSuchAlgorithmException {
//		byte[] salt = new byte[n];
//		SecureRandom sr = new SecureRandom();
//		sr.nextBytes(salt);
//		return salt;
//	}// generateSalt()


	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		// Makes salt for password encryption
		byte[] salt = new byte[n];
		SecureRandom sr = new SecureRandom();
		sr.nextBytes(salt);
		return salt;
	}
	
	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		n = Integer.valueOf(params.getProperty("length"));
	}
}// - SaltGenerator




