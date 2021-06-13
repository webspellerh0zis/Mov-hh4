package qlsl.androiddesign.daoimpl.subdaoimpl;

import java.sql.SQLException;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import qlsl.androiddesign.dao.subdao.ExampleDao;
import qlsl.androiddesign.daoimpl.basedaoimpl.BaseDaoImpl;
import qlsl.androiddesign.db.othertable.Example;

public class ExampleDaoImpl extends BaseDaoImpl<Example, Integer> implements
		ExampleDao {

	public ExampleDaoImpl(Class<Example> dataClass) throws SQLException {
		super(dataClass);
	}

	public ExampleDaoImpl(ConnectionSource connectionSource,
			Class<Example> dataClass) throws SQLException {
		super(connectionSource, dataClass);
	}

	public ExampleDaoImpl(ConnectionSource connectionSource,
			DatabaseTableConfig<Example> tableConfig) throws SQLException {
		super(connectionSource, tableConfig);
	}

}
