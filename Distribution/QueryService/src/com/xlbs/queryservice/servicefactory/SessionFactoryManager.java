package com.xlbs.queryservice.servicefactory;

import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import com.xlbs.queryservice.config.Hibernate;
import com.xlbs.queryservice.utils.XmlUtils;

/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see
 * {@link http://hibernate.org/42.html }.
 */
public class SessionFactoryManager {

	/**
	 * Location of hibernate.cfg.xml file. Location should be on the classpath
	 * as Hibernate uses #resourceAsStream style lookup for its configuration
	 * file. The default classpath location of the hibernate config file is in
	 * the default package. Use #setConfigFile() to update the location of the
	 * configuration file for the current session.
	 */
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	
	protected static Hibernate hc;

	private static Configuration configuration = new Configuration();
	
	private static org.hibernate.SessionFactory sessionFactory;
	
	private static String configPath = "config/hibernate.xml";
	
	public static Configuration getConfiguration() {
		return configuration;
	}
	
	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static String getConfigPath() {
		return configPath;
	}
	
	public static void setConfigPath(String configPath) {
		SessionFactoryManager.configPath = configPath;
	}
	
	private SessionFactoryManager() {
		
	}
	
	
	public static void startService() {
		try {
			initProperties(configuration);
//			Set<Class<?>> set = EntityObject.getHbmClass();
//			for (Class<?> c : set) {
//				try {
//					configuration.addClass(c);
//				} catch (Exception e) {
//					System.out.println("%%%% " + e.getMessage() + "  skip%%%%");
//				}
//			}
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	/**
	 * 初始化hibernate配置参数
	 * @param configuration
	 */
	private static void initProperties(Configuration configuration) {
		String path = configPath;
		hc = (Hibernate) XmlUtils.convertXmlFileToObject(
				Hibernate.class, path);
		Properties p = configuration.getProperties();
		p.setProperty("hibernate.connection.url", hc.getConnectionUrl());
		p.setProperty("hibernate.dialect", hc.getDialect());
		p.setProperty("hibernate.connection.driver_class", hc.getDriverClass());
		p.setProperty("hibernate.connection.username", hc.getUserName());
		p.setProperty("hibernate.connection.password", hc.getPassword());
		p.setProperty("hibernate.show_sql", hc.getShowSql());
		p.setProperty("hibernate.format_sql", "true");
		p.setProperty("hibernate.c3p0.min_size", hc.getMinPoolSize());
		p.setProperty("hibernate.c3p0.timeout", hc.getQueryTimeout());
		/**
		 * DBC的标准参数，用以控制数据源内加载的PreparedStatements数量。但由于预缓存的statements
		 * 属于单个connection而不是整个连接池。所以设置这个参数需要考虑到多方面的因素。
		 * 如果maxStatements与maxStatementsPerConnection均为0，则缓存被关闭。Default: 0
		 * 建议设置为0
		 */
		p.setProperty("hibernate.c3p0.maxStatementsPerConnection", "100");
		p.setProperty("hibernate.c3p0.max_statements", "0");
		p.setProperty("hibernate.c3p0.checkoutTimeout", "100");
		p.setProperty("hibernate.c3p0.acquire_increment",hc.getAcquireIncrement());
		p.setProperty("hibernate.c3p0.idle_test_period", hc.getIdleTestPeriod());
		p.setProperty("hibernate.connection.provider_class","org.hibernate.connection.C3P0ConnectionProvider");
		
	}

	/**
	 * Returns the ThreadLocal Session instance. Lazy initialize the
	 * <code>SessionFactory</code> if needed.
	 * 
	 * @return Session
	 * @throws HibernateException
	 */
	public static Session getSession() throws HibernateException {
		Session session = (Session) threadLocal.get();
		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				startService();
			}
			session = sessionFactory.openSession();
			threadLocal.set(session);
		}

		return session;
	}

	/**
	 * Close the single hibernate session instance.
	 * 
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);

		if (session != null) {
			session.close();
		}
	}

	public static void stopService() {
		try {
			sessionFactory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}