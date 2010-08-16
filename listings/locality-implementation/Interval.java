public abstract class Interval extends WorkItem {	
	public final PlaceID placeId;

	public Interval(Dependency dep, String name, PlaceID placeID) {
		this.placeID = placeID;

		// ...
	}

	public PlaceID getPlaceID() {
		return placeID;
	}

	// ...
}
