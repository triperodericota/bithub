import unity.relational.Attribute;
import unity.relational.Relation;
import unity.relational.Tuple;
import unity.functions.*;

// User defined function for testing purposes;
// Returns 2* the input numerical value.

public class F_Double extends Function {

	private Expression expr;

	public F_Double(Expression e) {
		expr = e;
		returnType = expr.getReturnType();
	}

	public Object evaluate(Tuple t) {
		Object val = expr.evaluate(t);

		if (val != null)
			if (val instanceof Double)
				return new Double(((Double) val).doubleValue() * 2);
			else if (val instanceof Float)
				return new Float(((Float) val).floatValue() * 2);
			else if (val instanceof Integer)
				return new Integer(((Integer) val).intValue() * 2);
			else if (val instanceof Number)
				return new Double(((Number) val).doubleValue() * 2);

		return null;
	}

	public int getReturnType() {
		return returnType;
	}

	public static int[] getParamListTypes() {
		return new int[] { Attribute.TYPE_NUMBER };
	}

	public static String getFunctionName() {
		return "DOUBLE";
	}

	public String toString(Relation relation) {
		return "DOUBLE(" + expr.toString(relation) + ")";
	}
}