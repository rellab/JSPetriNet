package jspetrinet.ast;

import jspetrinet.exception.*;

public class ASTVariable implements AST {

	private final String label;

	public ASTVariable(String label) {
		this.label = label;
	}
	
	public final String getLabel() {
		return label;
	}

	@Override
	public Object eval(ASTEnv env) throws JSPNException {
		Object v;
		try {
			v = env.get(label);
		} catch (NotFindObjectException e) {
			return new ASTNaN(this);
		}
		if (v instanceof AST) {
			return ((AST) v).eval(env);
		} else {
			return v;
		}
	}
	
	@Override
	public String toString() {
		return label;
	}
}
