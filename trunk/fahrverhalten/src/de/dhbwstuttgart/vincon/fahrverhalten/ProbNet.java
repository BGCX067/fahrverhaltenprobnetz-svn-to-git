package de.dhbwstuttgart.vincon.fahrverhalten;

import java.io.File;
import java.util.ArrayList;

import norsys.netica.Environ;
import norsys.netica.Net;
import norsys.netica.Node;
import norsys.netica.Streamer;

/**
 * The Class ProbNet.
 */
public class ProbNet {

	/** The error. */
	public static int error = 0;

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		executeNetWithData(new File("P_013_Bsp_A.csv"));
		executeNetWithData(new File("P_013_Bsp_B.csv"));
		executeNetWithData(new File("P_013_Bsp_C.csv"));
		executeNetWithData(new File("P_013_Bsp_D.csv"));
		System.out.println("Errors: " + error);
	}

	/**
	 * Execute net with data.
	 * 
	 * @param file
	 *            the file
	 */
	public static void executeNetWithData(File file) {
		Reader reader = new Reader(file);
		ArrayList<Measure> data = reader.getData();
		Measure oldMeasure = null;

		try {
			Environ env = new Environ(null);
			Net net = new Net(new Streamer("fahrverhalten.dne"));

			
			// read notes
			Node noteDFront = net.getNode("d_front");
			Node noteVFront = net.getNode("v_front");
			Node noteVFrontHist = net.getNode("v_front_hist");
			Node noteDLeft = net.getNode("d_left");
			Node noteVLeft = net.getNode("v_left");
			Node noteVLeftHist = net.getNode("v_left_hist");
			Node noteVOwn = net.getNode("v_own");
			Node noteVOwnHist = net.getNode("v_own_hist");
			Node noteReaction = net.getNode("reaction");
			Node noteCrashFront = net.getNode("crash_front");
			Node noteCrashLeft = net.getNode("crash_left");
			Node noteRiskTaking = net.getNode("risktaking");

			net.compile();

			// check each measure
			for (Measure measure : data) {
				noteDFront.finding().clear();
				noteVFront.finding().clear();
				noteVFrontHist.finding().clear();
				noteDLeft.finding().clear();
				noteVLeft.finding().clear();
				noteVLeftHist.finding().clear();
				noteVOwn.finding().clear();
				noteVOwnHist.finding().clear();

				noteDFront.finding().enterState(measure.getdFrontDiscreet());
				noteVFront.finding().enterState(measure.getvFrontDiscreet());
				noteDLeft.finding().enterState(measure.getdLeftDiscreet());
				noteVLeft.finding().enterState(measure.getvLeftDiscreet());
				noteVOwn.finding().enterState(measure.getvOwnDiscreet());

				if (oldMeasure != null) {
					noteVFrontHist.finding().enterState(
							oldMeasure.getvFrontDiscreet());
					noteVLeftHist.finding().enterState(
							oldMeasure.getvLeftDiscreet());
					noteVOwnHist.finding().enterState(
							oldMeasure.getvOwnDiscreet());
				}

				double beliefOvertake = noteReaction
						.getBelief(States.Reaction.OVERTAKE);
				double beliefDecelerate = noteReaction
						.getBelief(States.Reaction.DECELERATE);
				double beliefNone = noteReaction
						.getBelief(States.Reaction.NONE);

				String reaction;
				Double reactionValue;

				if (Math.max(beliefOvertake, beliefDecelerate) == beliefOvertake) {
					reaction = States.Reaction.OVERTAKE;
					reactionValue = beliefOvertake;
				} else {
					reaction = States.Reaction.DECELERATE;
					reactionValue = beliefDecelerate;
				}

				if (Math.max(reactionValue, beliefNone) == beliefNone) {
					reaction = States.Reaction.NONE;
					reactionValue = beliefNone;
				}

				// check for error and print information about it
				if (!reaction.equals(measure.getReactionDiscreet())) {
					error++;

					System.out.println("Overtake: " + beliefOvertake
							+ " Decelerate: " + beliefDecelerate + " None: "
							+ beliefNone);
					measure.printDiscreet();
					if (oldMeasure != null) {
						System.out.println();
						System.out.println("vFrontHist: "
								+ oldMeasure.getvFrontDiscreet());
						System.out.println("vLeftHist: "
								+ oldMeasure.getvLeftDiscreet());
						System.out.println("vOwnHist: "
								+ oldMeasure.getvOwnDiscreet());
					}
					System.out.println();
					System.out.println(reaction + " - "
							+ measure.getReactionDiscreet());
					System.out.println("crashFront: "
							+ (int) (noteCrashFront.getBelief("yes") * 100));
					System.out.println("crashLeft "
							+ (int) (noteCrashLeft.getBelief("yes") * 100));

					System.out.println();
				}

				oldMeasure = measure;

			}

			net.finalize();
			env.finalize();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}
}
