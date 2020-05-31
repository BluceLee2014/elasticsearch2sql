package org.elasticsearch2sql.parser.domain;

import lombok.Data;

/**
 * 搜索域
 * 
 * @author ansj
 *
 */
@Data
public class Field implements Cloneable{

	protected String name;
	private String alias;

	public Field(String name, String alias) {
		this.name = name;
		this.alias = alias;
	}
}
