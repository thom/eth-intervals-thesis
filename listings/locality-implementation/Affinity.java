public class Affinity {
	public static native void set(int physicalUnit) //*\label{lst:locality-implementation-core-affinity-set-unit}
			throws SetAffinityException;

	public static native void set(int[] physicalUnits) //*\label{lst:locality-implementation-core-affinity-set-node}
			throws SetAffinityException;

	static { System.loadLibrary("Affinity"); }
}
