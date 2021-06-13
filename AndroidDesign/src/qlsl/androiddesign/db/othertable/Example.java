package qlsl.androiddesign.db.othertable;

import com.j256.ormlite.field.DatabaseField;

public class Example {

	public Example() {

	}

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return getClass().getSimpleName() + ":  id=" + id + "  name=" + name;
	}

}
