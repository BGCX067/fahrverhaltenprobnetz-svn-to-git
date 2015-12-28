package de.dhbwstuttgart.vincon.fahrverhalten;

/**
 * The Class Measure represents one data set at a specific time
 */
public class Measure {

	/** The Constant NONE. */
	public static final int NONE = 0;
	
	/** The Constant DECELERATE. */
	public static final int DECELERATE = 1;
	
	/** The Constant OVERTAKE. */
	public static final int OVERTAKE = 2;

	/** The id. */
	private int id;
	
	/** The d front. */
	private float dFront;
	
	/** The d left. */
	private float dLeft;
	
	/** The v front. */
	private float vFront;
	
	/** The v left. */
	private float vLeft;
	
	/** The v own. */
	private float vOwn;
	
	/** The reaction. */
	private int reaction;

	/**
	 * Instantiates a new measure.
	 *
	 * @param id the id
	 * @param dFront the d front
	 * @param vFront the v front
	 * @param dLeft the d left
	 * @param vLeft the v left
	 * @param vOwn the v own
	 * @param reaction the reaction
	 */
	public Measure(int id, float dFront, float vFront, float dLeft,
			float vLeft, float vOwn, int reaction) {
		this.id = id;
		this.dFront = dFront;
		this.vFront = vFront;
		this.dLeft = dLeft;
		this.vLeft = vLeft;
		this.vOwn = vOwn;
		this.reaction = reaction;
	}

	/**
	 * Instantiates a new measure.
	 *
	 * @param data the data
	 */
	public Measure(String[] data) {
		this.id = (int) (Float.parseFloat(data[0].replace(',', '.')) * 10);
		this.dFront = Float.parseFloat(data[1].replace(',', '.'));
		this.vFront = Float.parseFloat(data[2].replace(',', '.'));
		this.dLeft = Float.parseFloat(data[3].replace(',', '.'));
		this.vLeft = Float.parseFloat(data[4].replace(',', '.'));
		this.vOwn = Float.parseFloat(data[5].replace(',', '.'));
		if (data[6].equals("überholen")) {
			this.reaction = OVERTAKE;
		} else if (data[6].equals("verzögern")) {
			this.reaction = DECELERATE;
		}
		if (data[6].equals("keine")) {
			this.reaction = NONE;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return id + ": dFront=" + dFront + " vFront=" + vFront + " dLeft="
				+ dLeft + " vLeft=" + vLeft + " vOwn=" + vOwn + " reaction="
				+ reaction;
	}

	/**
	 * Prints the discreet.
	 */
	public void printDiscreet() {
		System.out.println("Id: " + id);
		System.out.println();
		System.out.println("vFront: " + getvFrontDiscreet());
		System.out.println("dFront: " + getdFrontDiscreet());
		System.out.println();
		System.out.println("vLeft: " + getvLeftDiscreet());
		System.out.println("dLeft: " + getdLeftDiscreet());
		System.out.println();
		System.out.println("vOwn: " + getvOwnDiscreet());
		System.out.println("reaction: " + getReactionDiscreet());
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the d front discreet.
	 *
	 * @return the d front discreet
	 */
	public String getdFrontDiscreet() {
		if (dFront < 40)
			return States.Distance.SHORT;
		if (dFront > 48)
			return States.Distance.FAR;
		return States.Distance.MIDDLE;
	}

	/**
	 * Gets the d left discreet.
	 *
	 * @return the d left discreet
	 */
	public String getdLeftDiscreet() {
		if (dLeft > -50)
			return States.Distance.SHORT;
		if (dLeft < -70)
			return States.Distance.FAR;
		return States.Distance.MIDDLE;
	}

	/**
	 * Gets the v front discreet.
	 *
	 * @return the v front discreet
	 */
	public String getvFrontDiscreet() {
		if (vFront < 28)
			return States.Velocity.SLOW;
		if (vFront > 28)
			return States.Velocity.FAST;
		return States.Velocity.MIDDLE;
	}

	/**
	 * Gets the v left discreet.
	 *
	 * @return the v left discreet
	 */
	public String getvLeftDiscreet() {
		if (vLeft < 44)
			return States.Velocity.SLOW;
		if (vLeft > 45)
			return States.Velocity.FAST;
		return States.Velocity.MIDDLE;
	}

	/**
	 * Gets the v own discreet.
	 *
	 * @return the v own discreet
	 */
	public String getvOwnDiscreet() {
		if (vOwn < 32)
			return States.Velocity.SLOW;
		if (vOwn > 32)
			return States.Velocity.FAST;
		return States.Velocity.MIDDLE;
	}

	/**
	 * Gets the reaction discreet.
	 *
	 * @return the reaction discreet
	 */
	public String getReactionDiscreet() {
		if (reaction == DECELERATE)
			return States.Reaction.DECELERATE;
		else if (reaction == OVERTAKE)
			return States.Reaction.OVERTAKE;
		else
			return States.Reaction.NONE;
	}

	/**
	 * Gets the d front.
	 *
	 * @return the d front
	 */
	public float getdFront() {
		return dFront;
	}

	/**
	 * Gets the d left.
	 *
	 * @return the d left
	 */
	public float getdLeft() {
		return dLeft;
	}

	/**
	 * Gets the v front.
	 *
	 * @return the v front
	 */
	public float getvFront() {
		return vFront;
	}

	/**
	 * Gets the v left.
	 *
	 * @return the v left
	 */
	public float getvLeft() {
		return vLeft;
	}

	/**
	 * Gets the v own.
	 *
	 * @return the v own
	 */
	public float getvOwn() {
		return vOwn;
	}

	/**
	 * Gets the reaction.
	 *
	 * @return the reaction
	 */
	public float getReaction() {
		return reaction;
	}

}
