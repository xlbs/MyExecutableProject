package com.xlbs.serviceinterface.serviceinterface;

import com.xlbs.serviceinterface.serviceexception.DataOperatException;

import java.util.List;
import java.util.Map;

public interface I_SqlDataOperat {

    final int batchSize = 500;

    /**
     * 通过sql语句保存
     * @param sql sql语句
     * @return 返回boolean类型
     * @throws DataOperatException
     */
    public boolean savesql(String sql) throws DataOperatException;

    /**
     * 通过sql语句进行删除
     * @param sql sql语句
     * @return 返回boolean类型
     * @throws DataOperatException
     */
    public boolean deleteSql(String sql) throws DataOperatException;

    /**
     * 通过sql语句查询
     * @param sql sql语句
     * @return 返回list<Map>类型，Map为一行记录的key-value
     * @throws DataOperatException
     */
    public List<Map> querySql(String sql) throws DataOperatException;

    /**
     * 更新sql
     * @param sql sql语句
     * @return 返回boolean类型
     * @throws DataOperatException
     */
    public boolean updateSql(String sql) throws DataOperatException;

    /**
     * 获取最大id
     * @param tableName 表名
     * @param id_name 字段名
     * @return int类型
     */
    public int getMaxId(String tableName,String id_name);






}
