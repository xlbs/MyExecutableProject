package com.xlbs.queryservice.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author tanker 创建于2014-1-15 用jaxb处理
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "hibernate")
@XmlType(propOrder = { "driverClass", "connectionUrl", "userName", "password",
		"dialect", "maxFetchDepth", "fetchSize", "batchSize", "maxPoolSize",
		"minPoolSize", "showSql", "queryTimeout", "maxStatements", "acquireIncrement", "idleTestPeriod" })
public class Hibernate {
	/**
	 * hibernate方言版本
	 */
	private String dialect;
	private String maxStatements;
	private String acquireIncrement;
	private String idleTestPeriod;
	public String getMaxStatements() {
		return maxStatements;
	}

	public void setMaxStatements(String maxStatements) {
		this.maxStatements = maxStatements;
	}

	public String getAcquireIncrement() {
		return acquireIncrement;
	}

	public void setAcquireIncrement(String acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}

	public String getIdleTestPeriod() {
		return idleTestPeriod;
	}

	public void setIdleTestPeriod(String idleTestPeriod) {
		this.idleTestPeriod = idleTestPeriod;
	}

	/**
	 * hibernate设置外连接抓取树的最大深度
	 */
	private String maxFetchDepth;
	/**
	 * 用来决定JDBC的获取量大小
	 */
	private String fetchSize;
	/**
	 * 会开启Hibernate使用JDBC2的批量更新功能
	 *	取值. 建议值在 5 和 30之间。
	 */
	private String batchSize;
	/**
	 * 查询超时时间
	 */
	private String queryTimeout;
	/**
	 * 数据库连接url
	 */
	private String connectionUrl;
	/**
	 * 驱动
	 */
	private String driverClass;
	/**
	 * 连接用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 连接池最大数量
	 */
	private String maxPoolSize;
	/**
	 * 连接池最小数量
	 */
	private String minPoolSize;
	/**
	 *是否显示sql
	 */
	private String showSql;

	/**
	 * @return the showSql
	 */
	public String getShowSql() {
		return showSql;
	}

	/**
	 * @param showSql
	 *            the showSql to set
	 */
	public void setShowSql(String showSql) {
		this.showSql = showSql;
	}

	public String getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(String maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public String getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(String minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public String getConnectionUrl() {
		return connectionUrl;
	}

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getMaxFetchDepth() {
		return maxFetchDepth;
	}

	public void setMaxFetchDepth(String maxFetchDepth) {
		this.maxFetchDepth = maxFetchDepth;
	}

	public String getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(String fetchSize) {
		this.fetchSize = fetchSize;
	}

	public String getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(String batchSize) {
		this.batchSize = batchSize;
	}

	public String getQueryTimeout() {
		return queryTimeout;
	}

	public void setQueryTimeout(String queryTimeout) {
		this.queryTimeout = queryTimeout;
	}

}
