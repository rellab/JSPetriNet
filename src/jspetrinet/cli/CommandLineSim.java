package jspetrinet.cli;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import jp.rel.jmtrandom.Random;
import jspetrinet.JSPetriNet;
import jspetrinet.ast.AST;
import jspetrinet.exception.JSPNException;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkingGraph;
import jspetrinet.petri.Net;
import jspetrinet.sim.CompReward;
import jspetrinet.sim.EventValue;
import jspetrinet.sim.MCSimulation;
import jspetrinet.sim.MCSimulation2;
import jspetrinet.sim.Utility;

class DValue {
	private double value;
	
	public DValue() {
		this.value = 0.0;
	}
	
	public void add(double x) {
		value += x;
	}
	
	public double getValue() {
		return value;
	}
}

public class CommandLineSim {
	
	public static void cmdSimulation(String[] args) {
		Options options = new Options();
		options.addOption(CommandLineOptions.INPETRI, true, "input PetriNet file");
		options.addOption(CommandLineOptions.INITMARK, true, "initial marking");
		options.addOption(CommandLineOptions.FIRINGLIMIT, true, "limit");
		options.addOption(CommandLineOptions.SEED, true, "seed");
		options.addOption(CommandLineOptions.SIMTIME, true, "time");
		options.addOption(CommandLineOptions.SIMRUN, true, "run");
		options.addOption(CommandLineOptions.REWARD, true, "reward");
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		Net net = CommandLineCommons.loadNet(cmd);

		Mark imark = CommandLineCommons.getInitialMark(cmd, net);
		int limits = CommandLineCommons.getLimit(cmd, 1000);

		int run = 1;
		if (cmd.hasOption(CommandLineOptions.SIMRUN)) {
			run = Integer.parseInt(cmd.getOptionValue(CommandLineOptions.SIMRUN));
		}

		double endTime;
		if (cmd.hasOption(CommandLineOptions.SIMTIME)) {
			String label = "simtime" + System.currentTimeMillis();
			try {
				JSPetriNet.eval(net, label + " = " + cmd.getOptionValue(CommandLineOptions.SIMTIME) + ";");
			} catch (JSPNException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			try {
				endTime = Utility.convertObjctToDouble(((AST) net.get(label)).eval(net));
			} catch (JSPNException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
//			endTime = Double.valueOf(cmd.getOptionValue(Opts.SIMTIME));
		} else {
			System.err.println("Time (-time) should be set for the simulation.");
			return;
		}

		int seed;
		if (cmd.hasOption(CommandLineOptions.SEED)) {
			seed = Integer.valueOf(cmd.getOptionValue(CommandLineOptions.SEED));
		} else {
			seed = (int) System.currentTimeMillis();
		}

		MarkingGraph mp = new MarkingGraph(net);
		MCSimulation2 mc = new MCSimulation2(mp, new Random(seed));

		if (cmd.hasOption(CommandLineOptions.REWARD)) {
			String rewardLabel = cmd.getOptionValue(CommandLineOptions.REWARD);
			try {
				List<AST> rwdList = CommandLineCommons.parseReward(net, rewardLabel);
				Map<AST,DValue> totalRwd = new HashMap<AST,DValue>();
				Map<AST,DValue> total2Rwd = new HashMap<AST,DValue>();
				Map<AST,DValue> itotalRwd = new HashMap<AST,DValue>();
				Map<AST,DValue> itotal2Rwd = new HashMap<AST,DValue>();
				for (AST rwd : rwdList) {
					totalRwd.put(rwd, new DValue());
					total2Rwd.put(rwd, new DValue());
					itotalRwd.put(rwd, new DValue());
					itotal2Rwd.put(rwd, new DValue());
				}
				PrintWriter pw = new PrintWriter(System.out);
				pw.print("Start simulation...");
				long start = System.nanoTime();
				for (int k=0; k<run; k++) {
					List<EventValue> result = mc.runSimulation(imark, endTime, limits, null);
					for (AST rwd : rwdList) {
						double[] tmp = CompReward.resultCumulativeReward(net, result, rwd, 0.0, endTime);
						totalRwd.get(rwd).add(tmp[0]);
						total2Rwd.get(rwd).add(tmp[0]*tmp[0]);
						itotalRwd.get(rwd).add(tmp[1]);
						itotal2Rwd.get(rwd).add(tmp[1]*tmp[1]);
					}
				}
				mc.makeMarking();
				pw.println("done");
				pw.println("computation time    : " + (System.nanoTime() - start) / 1000000000.0 + " (sec)");
				pw.println(JSPetriNet.markingToString(net, mp));
				for (AST rwd : rwdList) {
					double mean = totalRwd.get(rwd).getValue() / run;
					double s2 = (total2Rwd.get(rwd).getValue() - totalRwd.get(rwd).getValue() * totalRwd.get(rwd).getValue() / run) / (run - 1);
					double lcl = mean - 1.96 * Math.sqrt(s2 / run);
					double ucl = mean + 1.96 * Math.sqrt(s2 / run);

					double imean = itotalRwd.get(rwd).getValue() / run;
					double is2 = (itotal2Rwd.get(rwd).getValue() - itotalRwd.get(rwd).getValue() * itotalRwd.get(rwd).getValue() / run) / (run - 1);
					double ilcl = imean - 1.96 * Math.sqrt(is2 / run);
					double iucl = imean + 1.96 * Math.sqrt(is2 / run);

					pw.println(rwd.toString());
					pw.println(mean + " [" + lcl + "," + ucl + "]");
					pw.println(imean + " [" + ilcl + "," + iucl + "]");
				}
				pw.close();
			} catch (JSPNException e) {
				e.printStackTrace();
			}
		} else {
			try {
				PrintWriter pw = new PrintWriter(System.out);
				pw.print("Start simulation...");
				long start = System.nanoTime();
				for (int k=0; k<run; k++) {
					List<EventValue> result = mc.runSimulation(imark, endTime, limits, null);
					for (EventValue ev : result) {
						pw.print(String.format("%.2f", ev.getEventTime())+" : ");
						pw.println(JSPetriNet.markToString(net, ev.getEventMarking()));
					}
					pw.println("----------");
				}
				mc.makeMarking();
				pw.println("done");
				pw.println("computation time    : " + (System.nanoTime() - start) / 1000000000.0 + " (sec)");
				pw.println(JSPetriNet.markingToString(net, mp));
				pw.close();
			} catch (JSPNException e) {
				System.err.println("Failed: " + e.getMessage());
				e.printStackTrace();
				return;
			}
		}
	}
}
