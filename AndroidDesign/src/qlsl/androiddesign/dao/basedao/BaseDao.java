package qlsl.androiddesign.dao.basedao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.dao.Dao;

import qlsl.androiddesign.db.basetable.BaseTable;

public interface BaseDao<T, ID> extends Dao<T, ID> {

	public void saveOrUpdateAll(List<T> list) throws SQLException;

	public void saveAll(List<T> list) throws SQLException;

	public List<T> queryList(T t, long start, long end, String descSort) throws SQLException;

	public List<T> queryQuerys(T t, String descSort) throws SQLException;

	public List<T> queryQuerys(Map<String, Object> objects, String descSort) throws SQLException;
	/**
	 * 创建或更新一行数据<br/>
	 * 有符合查询条件的数据则更新第一条查询出的数据<br/>
	 * 无符合查询条件的数据则创建<br/>
	 * 适用于查询条件下最多只能查询出唯一条数据的情况<br/>
	 * 待拓展:多条件查询,排序筛选等<br/>
	 * 
	 * @param newTable
	 *            要创建或替换成的数据行
	 * @param queryColumnName
	 *            待查询的列名
	 * @param queryColumnValue
	 *            待查询的列值
	 * @throws SQLException
	 */
	public void saveOrUpdate(BaseTable newTable, String queryColumnName,
			Object queryColumnValue,String queryColumnName2,Object queryColumnValue2) throws SQLException;
	
	public void saveOrUpdate(BaseTable newTable, String queryColumnName,
			Object queryColumnValue) throws SQLException;
}
