package com.xlbs.queryservice.serviceimplement;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.xlbs.serviceinterface.serviceinterface.I_QueryInterface;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.xlbs.queryservice.servicefactory.SessionFactoryManager;
import com.xlbs.queryservice.serviceinterface.I_Hibernate;
import com.xlbs.queryservice.utils.Assert;
import com.xlbs.queryservice.utils.CalendarUtils;


public class QueryInterfaceImp implements I_QueryInterface {
	
	protected final Logger log = Logger.getLogger(QueryInterfaceImp.class);
	
	public static final int FLUSH_NEVER = 0;
	public static final int FLUSH_EAGER = 2;
	public static final int FLUSH_AUTO = 1;
	public static final int FLUSH_COMMIT = 3;
	public static final int FLUSH_ALWAYS = 4;
	
	private int flushMode = FLUSH_AUTO;
	public int getFlushMode() {
		return flushMode;
	}
	
	private String[] filterNames;
	public String[] getFilterNames() {
		return filterNames;
	}
	public void setFilterNames(String[] filterNames) {
		this.filterNames = filterNames;
	}
	
	private int timeOut;
	public int getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	
	
	public Object execute(I_Hibernate action, boolean nativeSession,
			boolean isWriteOperations) throws RuntimeException {
		Assert.notNull(action, "Callback object must not be null");
		Session session = getSession(isWriteOperations);
		Transaction ts = null;
		try {
			enableFilters(session);
			ts = session.beginTransaction();
			Object result = action.execute(session);
			flushIfNecessary(session, true);
			ts.commit();
			return result;
		} catch (HibernateException ex) {
			ts.rollback();
			throw new RuntimeException(ex);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} catch (RuntimeException ex) {
			throw ex;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			log.debug("关闭session");
			session.close();
		}
	}
	
	private Session getSession(boolean isWriteOperations) {
		return SessionFactoryManager.getSession();
	}
	
	protected void enableFilters(Session session) {
		String[] filterNames = getFilterNames();
		if (filterNames != null) {
			for (int i = 0; i < filterNames.length; i++) {
				session.enableFilter(filterNames[i]);
			}
		}
	}
	
	protected void flushIfNecessary(Session session, boolean existingTransaction)
			throws HibernateException {
		if (getFlushMode() == FLUSH_EAGER
				|| (!existingTransaction && getFlushMode() != FLUSH_NEVER)) {
			session.flush();
		}
	}
	
	@Override
	public List<Map> querySqlReturnListMap(final String sql) throws RuntimeException {
		List list = (List) execute(new I_Hibernate() {
			@Override
			public Object execute(Session session) throws HibernateException {
				Connection conn = null;
				Statement stat = null;
				ResultSet rs = null;
				ArrayList<Map> data = new ArrayList<Map>();
				try {
					conn = session.connection();
					stat = conn.createStatement();
					stat.setQueryTimeout(timeOut);
					rs = stat.executeQuery(sql);
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();
					while (null != rs && rs.next()) {
						Map map = new LinkedHashMap();
						for (int i = 1; i <= columnCount; i++) {
							int type = rsmd.getColumnType(i);
							Object value = null;
							//循环获取ResultSet中的数据的部分代码
							switch (type) {
							case Types.TIMESTAMP: // 如果是时间戳类型
							case Types.DATE:
								try{
									value = rs.getTimestamp(i);
									value = CalendarUtils.getCalendar(rs.getTimestamp(i));
								}catch (Exception e) {
									value = rs.getObject(i);
								}
							    break;
							case Types.TIME: // 如果是时间类型
							    value = rs.getTime(i);
							    break;
							default: //日期类型可以使用getObject()来获取
							    value = rs.getObject(i);
							    break;
							}
							map.put(rsmd.getColumnLabel(i), value);
						}
						data.add(map);
					}
				} catch (SQLException e) {
					log.error("JDBC查询出错!SQL:" + sql, e);
					throw new RuntimeException(e);
				} finally {
					try {
						if (stat != null){
							stat.close();
						}
					} catch (Throwable e) {
						log.error("关闭Statement出错!", e);
					}
				}
				return data;
			}
		}, true,false);
		
		return list;
	}

	@Override
	public Serializable save(final Object entity) {
		Serializable result = (Serializable) execute(new I_Hibernate() {
			@Override
			public Object execute(Session session) throws HibernateException,
					SQLException {
				return session.save(entity);
			}
		},true,false);
		return result;
	}
	
	
	

}
