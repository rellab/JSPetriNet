package jspetrinet.ast;

import jspetrinet.exception.*;

public final class ASTLogical extends ASTBinary {
	
	private Object lhs;
	private Object rhs;

	public ASTLogical(AST left, AST right, String op) {
		super(left, right, op);
	}

	public final Object and() throws JSPNException {
		if (lhs instanceof Boolean && rhs instanceof Boolean) {
			return (Boolean) lhs && (Boolean) rhs;
		} else {
			throw new TypeMismatch();
		}
	}

	public final Object or() throws JSPNException {
		if (lhs instanceof Boolean && rhs instanceof Boolean) {
			return (Boolean) lhs || (Boolean) rhs;
		} else {
			throw new TypeMismatch();
		}
	}

	@Override
	public final Object eval(ASTEnv m) throws JSPNException {
		lhs = this.getLeft().eval(m);
		rhs = this.getRight().eval(m);
		
		if (lhs instanceof ASTNaN || rhs instanceof ASTNaN) {
			return new ASTNaN(new ASTLogical(AST.getAST(lhs), AST.getAST(rhs), op));
		}

		switch(op) {
		case "&&":
			return and();
		case "||":
			return or();
		default:
			throw new TypeMismatch();
		}
	}

}
