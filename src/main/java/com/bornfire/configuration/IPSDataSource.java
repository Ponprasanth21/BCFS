package com.bornfire.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.SQLException;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import oracle.jdbc.pool.OracleDataSource;

@Configuration
@EnableTransactionManagement
@ConfigurationProperties("spring.datasource")
@EnableJpaRepositories(basePackages = "com.bornfire.entity", entityManagerFactoryRef = "datasrc", transactionManagerRef = "datasrcTransactionManager")
public class IPSDataSource {

	@NotNull
	private String username;
	@NotNull
	private String password;
	@NotNull
	private String url;
	
	@Autowired
	Environment env;

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	@Bean
    public LocalSessionFactoryBean datasrc() throws SQLException {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(srcdataSource());
        sessionFactory.setPackagesToScan("com.bornfire.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());
 
        return sessionFactory;
    }
	
	private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();       
        hibernateProperties.setProperty(
          "hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        return hibernateProperties;
    }
	

	@Bean
	DataSource srcdataSource() throws SQLException {
		OracleDataSource dataSource = new OracleDataSource();
		dataSource.setUser(username);
		dataSource.setPassword(password);
		dataSource.setURL(url);
		dataSource.setImplicitCachingEnabled(true);
		dataSource.setFastConnectionFailoverEnabled(true);
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager datasrcTransactionManager() throws SQLException {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(datasrc().getObject());
		return transactionManager;
	}
	
	
	@Bean
	public RestTemplate restTemplate() throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, KeyStoreException, KeyManagementException, UnrecoverableKeyException {
	/*	KeyStore ks = KeyStore.getInstance("JKS");
		char[] pwdArray = env.getProperty("cimBIPS.jks.password").toCharArray();

		ks.load(new FileInputStream(env.getProperty("cimBIPS.jks.file")), pwdArray);
		
		char[] pwdArray = "Passw0rd$".toCharArray();

		ks.load(new FileInputStream(ResourceUtils.getFile("classpath:cim_wildcard.jks")), pwdArray);
		
		
		SSLContext sslContext=org.apache.http.ssl.SSLContextBuilder.create()
				.loadKeyMaterial(ks, pwdArray)
				.loadTrustMaterial(new File(env.getProperty("cimBIPS.jks.file")), pwdArray, new TrustSelfSignedStrategy() {
                    @Override
                    public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                        return true;
                    }
                })
				//.loadTrustMaterial(null, new TrustSelfSignedStrategy())
				.build();
		
		SSLConnectionSocketFactory socketFactory=new SSLConnectionSocketFactory(sslContext,new String[] {"TLSv1.2"},null,NoopHostnameVerifier.INSTANCE);
		
		//HttpClient httpClient=HttpClients.custom().setSSLContextf(sslContext).build();
		HttpClient httpClient=HttpClients.custom().setSSLSocketFactory(socketFactory).build();
	
		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
				httpClient);
		
	
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate;*/
		//return builder.errorHandler(getRestErrorHandler()).build();
		
TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
		 
		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                		.loadTrustMaterial(null, acceptingTrustStrategy)
                		.build();

		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

		CloseableHttpClient httpClient = HttpClients.custom()
                		.setSSLSocketFactory(csf)
                		.build();

		HttpComponentsClientHttpRequestFactory requestFactory =
                		new HttpComponentsClientHttpRequestFactory();

		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate;

		
		/*TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
	    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
	    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
	    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
	    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
	    requestFactory.setHttpClient(httpClient);
	    RestTemplate restTemplate = new RestTemplate(requestFactory);
	    return restTemplate;*/
		
	
	}
	
	
	/*@Bean
	public RestTemplate restTemplate() {
	    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

	    Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress("172.26.46.15", 7782));
	    requestFactory.setProxy(proxy);

	    return new RestTemplate(requestFactory);
	}*/


}
