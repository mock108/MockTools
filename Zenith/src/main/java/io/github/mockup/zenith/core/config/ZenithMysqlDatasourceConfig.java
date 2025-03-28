package io.github.mockup.zenith.core.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

import io.github.mockup.zenith.core.config.ZenithEncrypterConfig.ZenithEncrypter;

@Configuration
public class ZenithMysqlDatasourceConfig {
	@Autowired
	private ZenithEncrypter zenithEncrypter;
	@Value("${zenith.mysql.driver-class-name}")
	private String driverClassName;
	@Value("${zenith.mysql.url}")
	private String url;
	@Value("${zenith.mysql.username}")
	private String userName;
	@Value("${zenith.mysql.password}")
	private String password;

	@Bean
	@ConfigurationProperties(prefix = "zenith.mysql.hikari")
	DataSource dataSource() {
		DataSourceProperties properties = new DataSourceProperties();
		properties.setDriverClassName(driverClassName);
		properties.setUrl(url);
		properties.setUsername(zenithEncrypter.decrypt(userName));
		properties.setPassword(zenithEncrypter.decrypt(password));
		return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}
}
