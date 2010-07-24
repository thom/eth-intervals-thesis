public class Affinity {
	public static native void set(int physicalUnit) //*\label{lst:core-affinity-set-unit}
			throws SetAffinityException;
	public static native void set(int[] physicalUnits) //*\label{lst:core-affinity-set-node}
			throws SetAffinityException;
	public static native boolean[] get() //*\label{lst:core-affinity-get}
			throws GetAffinityException;
	static { System.loadLibrary("Affinity"); }
}
