package com.xlbs.serviceinterface.serviceinterface;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface I_QueryInterface {
	
	/**
	 * 查询返回List<Map>
	 * @param sql sql语句
	 * @return
	 * @throws RuntimeException
	 */
	public List<Map> querySqlReturnListMap(String sql) throws RuntimeException;
	
	/**
	 * 保存对象
	 * @param entity
	 * @return
	 */
	Serializable save(Object entity) ;

}
