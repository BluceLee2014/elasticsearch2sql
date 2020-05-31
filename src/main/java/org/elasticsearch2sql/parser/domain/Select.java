package org.elasticsearch2sql.parser.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 将sql语句转换为select 对象
 * 
 * @author ansj
 */
@Data
public class Select implements Serializable {

	// Using this functions, will cause query to execute as aggregation.
	private final List<String> aggsFunctions = Arrays.asList("SUM", "MAX", "MIN", "AVG", "TOPHITS", "COUNT", "STATS","EXTENDED_STATS","PERCENTILES","SCRIPTED_METRIC");

	private Where where;
	private From from;
	private List<Field> fields = new ArrayList<>();

	private int offset;
	private int rowCount;

    private boolean selectAll = false;

	public boolean isAgg = false;

	public void addField(Field field) {
		if (field == null ) {
			return;
		}
		if(field.getName().equals("*")){
			this.selectAll = true;
		}

//		if(field instanceof  MethodField && aggsFunctions.contains(field.getName().toUpperCase())) {
//			isAgg = true;
//		}

		fields.add(field);
	}
}

