public class NehalemPlaces extends Places {
	private static String name = "nehalem";
	private static PlaceID[] placeIDs = { new PlaceIDImpl(0, name), //*\label{lst:locality-implementation-mafushi-place-ids-start}
			new PlaceIDImpl(1, name) }; //*\label{lst:locality-implementation-mafushi-place-ids-stop}
	private static int[][] places = { { 0, 2, 4, 6 }, //*\label{lst:locality-implementation-mafushi-place-0}
			{ 1, 3, 5, 7 } }; //*\label{lst:locality-implementation-mafushi-place-1}
	private static int[] units = { 0, 2, 4, 6, 1, 3, 5, 7 }; //*\label{lst:locality-implementation-mafushi-places-units}

	public NehalemPlaces() {
		super(name, placeIDs, places, units);
	}
}
