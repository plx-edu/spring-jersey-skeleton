package app.custom;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

/**
 * Generate an n size string of random characters between
 * "a-z", "A-Z", "0-9", "-_"
 * Use case sensitive collation for id in database
 * in MariaDB/MySQL use utf8mb4_bin
 *
 */
public class CustomIdGenerator implements IdentifierGenerator, Configurable {
	
	/**
	 * id length we want
	 */
	private int n;
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		// List of characters we want
		final String alphanum = "abcdefghijklmnopqrstuvwxyz" // lowercase ascii
				+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ" // uppercase ascii
				+ "0123456789" // digits
				+ "-_"; // special characters
		// Random string creation
		StringBuilder sb = new StringBuilder(Math.abs(n));
		SecureRandom secureRand = new SecureRandom();
		while (sb.length() < Math.abs(n)) {
			int rn = secureRand.nextInt(alphanum.length());
			sb.append(alphanum.charAt(rn));
		}
		
		return sb.toString();
	}// generate()

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		n = Integer.valueOf(params.getProperty("length"));
	}

}// - CustomIdGenerator