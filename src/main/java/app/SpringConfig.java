package app;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("app.component")
@EnableJpaRepositories("app.component")
public class SpringConfig {
	private String pckgsToScan = "app.component";
	
	
	private String driver = "com.mysql.cj.jdbc.Driver";
	// Use System.getenv() as alternative
	//	private String hostname = System.getProperty("RDS_HOSTNAME");
	//	private String dbName = System.getProperty("RDS_DB_NAME");
	//	private String userName = System.getProperty("RDS_USERNAME");
	//	private String password = System.getProperty("RDS_PASSWORD");
	//	private String port = System.getProperty("RDS_PORT");
	
	// TO BE REPLACED BY Environment Variables ABOVE FOR PROD
	private String hostname = "localhost";
	private String dbName = "test_db";
	private String userName = "admin";
	private String password = " ";
	private String port = "3306";
	
	private String jdbcUrl = "jdbc:mysql://"+hostname+":"+port+"/"+dbName+"?useSSL=true";
	
	@Bean
	public DataSource dataSource() {
		// MySQL/MariaDB Database
		DriverManagerDataSource dmds = new DriverManagerDataSource(jdbcUrl, userName, password);
		dmds.setDriverClassName(driver);
		
		// **********************   TEST   *****************************
		// Use script(s) to initialize the database
		ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
		rdp.addScript(new ClassPathResource("schema.sql"));
		rdp.execute(dmds);
//		try {
//			Connection conn = dmds.getConnection();
//			rdp.execute(dmds);
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		// *************************************************************
		
		return dmds;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		
		jpaVendorAdapter.setShowSql(true);
		jpaVendorAdapter.setGenerateDdl(false);
		
		return jpaVendorAdapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
		lemfb.setDataSource(dataSource());
		lemfb.setJpaVendorAdapter(jpaVendorAdapter());
		lemfb.setPackagesToScan(pckgsToScan);
		return lemfb;
	}
}// - SpringConfig





