package qlsl.androiddesign.db.basetable;

/**
 * 基层表,不提供序列化功能,由子类自行决定是否序列化<br/>
 * 对主键ID进行公用处理,提供基本的getId,setID函数<br/>
 * 主要用于给BaseDao中的saveOrUpdate函数提供getID的渠道<br/>
 * 当不扩展此类时,亦可将saveOrUpdate的统一处理转变为分步处理<br/>
 * 由于ID不一定为主键,故不强行注解<br/>
 * 在子类中若ID作为主键时,仍需定义和注解,getID,setId可选择是否覆盖<br/>
 * 规范起见,建议子类仍覆盖getId,setId函数<br/>
 */
public class BaseTable {

	public Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String toString() {
		return "id:" + id;
	}
}
