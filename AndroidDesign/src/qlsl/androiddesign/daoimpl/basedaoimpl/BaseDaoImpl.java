package qlsl.androiddesign.daoimpl.basedaoimpl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import qlsl.androiddesign.dao.basedao.BaseDao;
import qlsl.androiddesign.db.basetable.BaseTable;
import qlsl.androiddesign.method.UserMethod;

public class BaseDaoImpl<T, ID> extends com.j256.ormlite.dao.BaseDaoImpl<T, ID>implements BaseDao<T, ID> {

	protected BaseDaoImpl(Class<T> dataClass) throws SQLException {
		super(dataClass);
	}

	protected BaseDaoImpl(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
		super(connectionSource, dataClass);
	}

	protected BaseDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig) throws SQLException {
		super(connectionSource, tableConfig);
	}

	public void saveOrUpdateAll(final List<T> list) throws SQLException {

		try {
			this.callBatchTasks(new Callable<List<T>>() {

				public List<T> call() throws SQLException {
					for (T t : list) {
						BaseDaoImpl.this.createOrUpdate(t);
					}
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void saveAll(final List<T> list) throws SQLException {

		try {
			this.callBatchTasks(new Callable<List<T>>() {

				public List<T> call() throws SQLException {
					for (T t : list) {
						BaseDaoImpl.this.create(t);
					}
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param start
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @param desc
	 *            降序
	 * 
	 */
	public List<T> queryList(T t, long start, long end, String descSort) throws SQLException {
		QueryBuilder<T, ID> qb = queryBuilder();
		qb.offset(start).limit(end);
		Where<T, ID> where = qb.where();
		int fieldC = 0;
		for (FieldType fieldType : tableInfo.getFieldTypes()) {
			Object fieldValue = fieldType.getFieldValueIfNotDefault(t);
			if (fieldValue != null) {
				fieldValue = new SelectArg(fieldValue);
				where.eq(fieldType.getColumnName(), fieldValue);
				fieldC++;
			}
		}
		if (descSort != null) {
			qb.orderByRaw(descSort);
		}
		if (fieldC == 0) {
			return Collections.emptyList();
		} else {
			where.and(fieldC);
			return qb.query();
		}

	}

	public List<T> queryQuerys(Map<String, Object> objects, String descSort) throws SQLException {
		QueryBuilder<T, ID> qb = queryBuilder();
		Where<T, ID> where = qb.where();
		int fieldC = 0;
		// for (FieldType fieldType : tableInfo.getFieldTypes()) {
		// Object fieldValue = fieldType.getFieldValueIfNotDefault(t);
		// if (fieldValue != null) {
		// fieldValue = new SelectArg(fieldValue);
		// where.eq(fieldType.getColumnName(), fieldValue);
		// fieldC++;
		// }
		// }
		for (Map.Entry<String, Object> iterable_element : objects.entrySet()) {
			where.eq(iterable_element.getKey(), iterable_element.getValue());
			fieldC++;
		}

		if (descSort != null) {
			qb.orderByRaw(descSort);
		}
		if (fieldC == 0) {
			return null;
		} else {
			where.and(fieldC);
			return qb.query();
		}
	}

	public List<T> queryQuerys(T t, String descSort) throws SQLException {
		QueryBuilder<T, ID> qb = queryBuilder();
		Where<T, ID> where = qb.where();
		int fieldC = 0;
		for (FieldType fieldType : tableInfo.getFieldTypes()) {
			Object fieldValue = fieldType.getFieldValueIfNotDefault(t);
			if (fieldValue != null) {
				fieldValue = new SelectArg(fieldValue);
				where.eq(fieldType.getColumnName(), fieldValue);
				fieldC++;
			}
		}
		if (descSort != null) {
			qb.orderByRaw(descSort);
		}
		if (fieldC == 0) {
			return null;
		} else {
			where.and(fieldC);
			return qb.query();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void saveOrUpdate(BaseTable newTable, String queryColumnName,
			Object queryColumnValue,String queryColumnName2,Object queryColumnValue2) {
		QueryBuilder<T, ID> qb = queryBuilder();
		Where<T, ID> where = qb.where();
		try {
			where.eq(queryColumnName, queryColumnValue).and().eq(queryColumnName2, queryColumnValue2)
			.and().eq("userId",UserMethod.getUser().getId());
			List<BaseTable> list = (List<BaseTable>) qb.query();
			if (list.size() == 0) {
				create((T) newTable);
			} else {
				newTable.setId(list.get(0).getId());
				update((T) newTable);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void saveOrUpdate(BaseTable newTable, String queryColumnName,
			Object queryColumnValue) {
		QueryBuilder<T, ID> qb = queryBuilder();
		Where<T, ID> where = qb.where();
		try {
			where.eq(queryColumnName, queryColumnValue).and().eq("userId",UserMethod.getUser().getId());
			List<BaseTable> list = (List<BaseTable>) qb.query();
			if (list.size() == 0) {
				create((T) newTable);
			} else {
				newTable.setId(list.get(0).getId());
				update((T) newTable);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
