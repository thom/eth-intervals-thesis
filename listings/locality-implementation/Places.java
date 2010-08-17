public abstract class Places {
	protected static class PlaceIDImpl extends PlaceID { //*\label{lst:locality-implementation-places-place-id-start}
		public PlaceIDImpl(int id, String machine) {
			super(id, machine);
		}
	} //*\label{lst:locality-implementation-places-place-id-stop}

	public final String name;
	public final int length;
	public final int unitsLength;
	protected final PlaceID[] placeIDs;
	protected final int[][] places;
	protected final int[] units;

	public Places(String name, PlaceID[] placeIDs, int[][] places, 
			int[] units) {
		this.name = name;
		this.placeIDs = placeIDs;
		this.places = places;
		this.units = units;
		this.length = places.length;
		this.unitsLength = units.length;
	}

	public PlaceID getPlaceID(int id) {
		return placeIDs[id % placeIDs.length];
	}

	public int[] get(int id) {
		return places[id % length];
	}

	public int getUnit(int id) {
		return units[id % unitsLength];
	}
}
