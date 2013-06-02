package olap.model;

import java.util.List;

import olap.db.DBColumn;
import olap.db.MultiDimMapper;

public class TypeHelper {

	public static boolean equalTypes(String multidimType, String dbType) {
		if (multidimType.toLowerCase().equals("numeric")) {
			if (!dbType.toLowerCase().equals("numeric")) {
				return false;
			}
		}
		if (multidimType.toLowerCase().equals("string")) {
			if (!dbType.toLowerCase().equals("character varying")) {
				return false;
			}
		}
		if (multidimType.toLowerCase().equals("geometry")) {
			if (!dbType.toUpperCase().equals("USER-DEFINED")) {
				return false;
			}
		}
		if (multidimType.toLowerCase().equals("timestamp")) {
			if (!dbType.startsWith("timestamp")) {
				return false;
			}
		}
		return true;
	}

	public static boolean wrongTypes(List<DBColumn> cols, List<DBColumn> DBCols,
			List<MultiDimMapper> converters) {
		for (MultiDimMapper converter : converters) {
			for (DBColumn col : cols) {
				if (converter.getMultidim().toLowerCase()
						.equals(col.getName().toLowerCase())) {
					for (DBColumn databaseColumn : DBCols) {
						if (converter.getColumn().toLowerCase()
								.equals(databaseColumn.getName().toLowerCase())) {
							if (!equalTypes(col.getType(),
									databaseColumn.getType())) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public static String toDBType(String type) {
		if(type.toLowerCase().equals("numeric")) {
			return "numeric";
		}
		if(type.toLowerCase().equals("string")) {
			return "varchar(50)";
		}
		if(type.toLowerCase().equals("timestamp")) {
			return "timestamp";
		}
		if(type.toLowerCase().equals("geometry")) {
			return "geometry";
		}
		return "varchar(50)";
	}
}
