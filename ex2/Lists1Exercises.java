public class Lists1Exercises {
	/**
	 * Returns an IntList identical to L, but with
	 * each element incremented by x. L is not allowed
	 * to change.
	 */
	public static IntList incrList(IntList L, int x) {
		if (L == null) {
			return null;
		}
		IntList Q = new IntList(L.first + x, null);
		IntList temp = Q;
		for (IntList P = L.rest; P != null; P = P.rest) {
			temp.rest = new IntList(P.first + x, null);
			temp = temp.rest;
		}
		return Q;
	}

	/**
	 * Returns an IntList identical to L, but with
	 * each element incremented by x. Not allowed to use
	 * the 'new' keyword.
	 */
	public static IntList dincrList(IntList L, int x) {
		for (IntList P = L; P != null; P = P.rest) {
			P.first += x;
		}

		return L;
	}

	public static void main(String[] args) {
		IntList L = new IntList(5, null);
		L.rest = new IntList(7, null);
		L.rest.rest = new IntList(9, null);

		System.out.println(L.size());
		System.out.println(L.iterativeSize());

		// Test your answers by uncommenting. Or copy and paste the
		// code for incrList and dincrList into IntList.java and
		// run it in the visualizer.
		// System.out.println(L.get(1));
		// System.out.println(incrList(L, 3));
		// System.out.println(dincrList(L, 3));
	}
}