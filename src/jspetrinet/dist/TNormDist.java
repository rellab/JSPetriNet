package jspetrinet.dist;

import jspetrinet.ast.ASTEnv;
import jspetrinet.common.Utility;
import jp.rel.jmtrandom.Random;
import jspetrinet.ast.AST;
import jspetrinet.exception.JSPNException;

public class TNormDist extends Dist {
	
	public static final String dname = "tnorm";

	private AST mean;
	private AST sd;
	private Object meanObj;
	private Object sdObj;

	public TNormDist(AST mean, AST sd) {
		this.mean = mean;
		this.sd = sd;
	}
	
	public final AST getMean() {
		return mean;
	}

	public final AST getSd() {
		return sd;
	}
	
	public final void setParam(AST mean, AST sd) {
		this.mean = mean;
		this.sd = sd;
	}

	@Override
	public void updateObj(ASTEnv env) throws JSPNException {
		meanObj = mean.eval(env);
		sdObj = sd.eval(env);
	}

	@Override
	public String toString() {
		return dname + "(" + meanObj + "," + sdObj + ")";
	}

	@Override
	public double nextImpl(Random rnd) throws JSPNException {
		double mean_value = Utility.convertObjctToDouble(meanObj);
		double sd_value = Utility.convertObjctToDouble(sdObj);
		return rnd.nextTruncNormal(mean_value, sd_value);
	}
}
