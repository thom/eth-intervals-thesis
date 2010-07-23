public class Affinity {
	public static native void set(int physicalUnit)
			throws SetAffinityException;

	public static native void set(int[] physicalUnits)
			throws SetAffinityException;

	public static native boolean[] get()
			throws GetAffinityException;

	static {
		System.loadLibrary("Affinity");
	}
}
