package com.xlbs.queryservice.serviceinterface;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public interface I_Hibernate {

	Object execute(Session session) throws HibernateException, SQLException;

}
