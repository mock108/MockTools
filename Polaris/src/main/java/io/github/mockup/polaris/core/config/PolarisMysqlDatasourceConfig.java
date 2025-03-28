package io.github.mockup.polaris.core.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

import io.github.mockup.polaris.core.config.PolarisEncrypterConfig.PolarisEncrypter;

@Configuration
public class PolarisMysqlDatasourceConfig {
	@Autowired
	private PolarisEncrypter polarisEncrypter;
	@Value("${polaris.mysql.driver-class-name}")
	private String driverClassName;
	@Value("${polaris.mysql.url}")
	private String url;
	@Value("${polaris.mysql.username}")
	private String userName;
	@Value("${polaris.mysql.password}")
	private String password;

	@Bean
	@ConfigurationProperties(prefix = "polaris.mysql.hikari")
	DataSource dataSource() {
		DataSourceProperties properties = new DataSourceProperties();
		properties.setDriverClassName(driverClassName);
		properties.setUrl(url);
		properties.setUsername(polarisEncrypter.decrypt(userName));
		properties.setPassword(polarisEncrypter.decrypt(password));
		return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}
}
