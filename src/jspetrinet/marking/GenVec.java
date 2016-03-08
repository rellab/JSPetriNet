package jspetrinet.marking;

import java.io.Serializable;
import java.util.Arrays;

public final class GenVec implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8822262392158365575L;
	private final byte[] vec;

//	public GenVec(String label, int length) {
//		this.label = label;
//		this.vec = new int [length];
//	}

//	public GenVec(String label, int[] v) {
//		this.label = label;
//		this.vec = v;
//	}

	public GenVec(int size) {
		this.vec = new byte [size];
	}

	// getter
//	public final int[] get() {
//		return vec;
//	}

	public final int get(int i) {
		return vec[i];
	}
	
	public final void set(int i, int value) {
		vec[i] = (byte) value;
	}
	
	public final void set(int i, byte value) {
		vec[i] = value;
	}

	@Override
	public final int hashCode() {
		return Arrays.hashCode(vec);
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenVec other = (GenVec) obj;
		if (!Arrays.equals(vec, other.vec))
			return false;
		return true;
	}
}
