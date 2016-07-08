import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import jspetrinet.JSPetriNet;
import jspetrinet.analysis.MRGPAnalysis;
import jspetrinet.analysis.MarkingMatrix;
import jspetrinet.ast.ASTree;
import jspetrinet.exception.*;
import jspetrinet.marking.*;
import jspetrinet.parser.TokenMgrError;
import jspetrinet.petri.Net;
import jspetrinet.sim.EventValue;
import jspetrinet.sim.MCSimulation;
import jspetrinet.sim.Random;
import jspetrinet.sim.RandomGenerator;
import jspetrinet.sim.Utility;

class Opts {
	// modes
	public static String VIEW="view";
	public static String MARKING="mark";
	public static String SIMULATION="sim";

	// options
	public static String INPETRI="spn";
	public static String OUTPETRI="graph";
	public static String OUTMAT="out";
	public static String INITMARK="imark";
	public static String DEPTH="limit";
	public static String REWARD="reward";

	public static String SEED="seed";
	public static String SIMTIME="time";
}

public class CommandLineMain {
	
	static private Map<String,Integer> parseMark(String str) {
		Map<String,Integer> result = new HashMap<String,Integer>();
		String[] ary = str.split(",", 0);
		Pattern p = Pattern.compile("(\\w+):([0-9]+)");
		for (String s : ary) {
			Matcher m = p.matcher(s);
			if (m.matches()) {
				String key = m.group(1);
				int value = Integer.parseInt(m.group(2));
				result.put(key, value);
			}
		}
		return result;
	}
	
	static private List<ASTree> parseReward(Net net, String str) throws ASTException {
		List<ASTree> result = new ArrayList<ASTree>();
		String[] ary = str.split(",", 0);
		for (String s : ary) {
			Object obj = net.get(s);
			if (obj instanceof ASTree) {
				result.add((ASTree) obj);
			} else {
				throw new TypeMismatch();
			}
		}
		return result;
	}
	
	private static Net loadNet(CommandLine cmd) {
		Net net = new Net(null, "");
		InputStream in = null;
		if (cmd.hasOption(Opts.INPETRI)) {
			try {
				in = new FileInputStream(cmd.getOptionValue(Opts.INPETRI));
			} catch (FileNotFoundException e) {
				System.err.println("Did not find file: " + cmd.getOptionValue(Opts.INPETRI));
				System.exit(1);
			}
		} else {
			in = System.in;
		}
		try {
			net = JSPetriNet.load(net, in);
		} catch (TokenMgrError ex) {
			System.out.println("token error: " + ex.getMessage());
			System.exit(1);
		} catch (jspetrinet.parser.ParseException ex) {
			System.out.println("parse error: " + ex.getMessage());
			System.exit(1);
		} catch (ASTException e) {
			System.out.println("Error: " + e.getMessage());
			System.exit(1);
		}
		net.setIndex();
		return net;
	}
	
	private static Mark getInitialMark(CommandLine cmd, Net net) {
		Mark imark = null;
		if (cmd.hasOption(Opts.INITMARK)) {
			try {
				imark = JSPetriNet.mark(net, parseMark(cmd.getOptionValue(Opts.INITMARK)));
			} catch (ASTException e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
			System.out.println("Initial marking: " + JSPetriNet.markToString(net, imark));
		} else {
			System.err.println("Marking process requires an initial marking (-" + Opts.INITMARK + ").");
			System.exit(1);
		}
		return imark;
	}
	
	private static int getLimit(CommandLine cmd, int defaultValue) {
		if (cmd.hasOption(Opts.DEPTH)) {
			return Integer.parseInt(cmd.getOptionValue(Opts.DEPTH));
		} else {
			return defaultValue;
		}
	}

	public static void cmdView(String[] args) {
		Options options = new Options();
		options.addOption(Opts.INPETRI, true, "input Petrinet file");
		options.addOption(Opts.OUTPETRI, true, "output file");
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		Net net = loadNet(cmd);

		PrintWriter bw;
		if (cmd.hasOption(Opts.OUTPETRI)) {
			try {
				bw = new PrintWriter(cmd.getOptionValue(Opts.OUTPETRI));
			} catch (FileNotFoundException e) {
				System.err.println("Fail to write in the file: " + cmd.getOptionValue(Opts.OUTPETRI));
				return;
			}
		} else {
			bw = new PrintWriter(System.out);
		}
		JSPetriNet.writeDotfile(bw, net);
		bw.close();
	}
		
	public static void cmdAnalysis(String[] args) {
		Options options = new Options();
		options.addOption(Opts.INPETRI, true, "input PetriNet file");
		options.addOption(Opts.INITMARK, true, "initial marking");
		options.addOption(Opts.DEPTH, true, "test mode (input depth for DFS)");
		options.addOption(Opts.OUTMAT, true, "matrix (output)");
		options.addOption(Opts.OUTPETRI, true, "marking graph (output)");
		options.addOption(Opts.REWARD, true, "the label of reward");
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		Net net = loadNet(cmd);
		Mark imark = getInitialMark(cmd, net);
		int depth = getLimit(cmd, 0);
		
		PrintWriter pw0;
		pw0 = new PrintWriter(System.out);
		MarkingGraph mp;
		try {
			mp = JSPetriNet.marking(pw0, net, imark, depth);
		} catch (ASTException e1) {
			System.err.println(e1.getMessage());
			return;
		}
		pw0.flush();

		MarkingMatrix mat = new MarkingMatrix(mp, true);
		MRGPAnalysis mrgp = new MRGPAnalysis(mat);

		PrintWriter pw1, pw2, pw6;
		if (cmd.hasOption(Opts.OUTMAT)) {
			try {
				pw1 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(Opts.OUTMAT) + ".states")));
				pw2 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(Opts.OUTMAT) + ".matrix")));
				pw6 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(Opts.OUTMAT) + ".init")));
			} catch (FileNotFoundException e) {
				System.err.println("Fail to write in the file: " + cmd.getOptionValue(Opts.OUTMAT));
				return;
			} catch (IOException e) {
				System.err.println("Fail to write in the file: " + cmd.getOptionValue(Opts.OUTMAT));
				return;
			}			
			mrgp.writeMarkSet(pw1);
			mrgp.writeMatrix(pw2);
			mrgp.writeStateVec(pw6, imark);
			pw1.close();
			pw2.close();
			pw6.close();
		} else {
			pw1 = new PrintWriter(System.out);
			pw2 = new PrintWriter(System.out);
			pw6 = new PrintWriter(System.out);
			mrgp.writeMarkSet(pw1);
			mrgp.writeMatrix(pw2);
			mrgp.writeStateVec(pw6, imark);
			pw1.flush();
			pw2.flush();
			pw6.flush();
		}
		
		if (cmd.hasOption(Opts.REWARD)) {
			String rewardLabel = cmd.getOptionValue(Opts.REWARD);

			PrintWriter pw5;
			if (cmd.hasOption(Opts.OUTMAT)) {
				try {
					pw5 = new PrintWriter(new BufferedWriter(new FileWriter(cmd.getOptionValue(Opts.OUTMAT) + ".reward")));
				} catch (FileNotFoundException e) {
					System.err.println("Fail to write in the file: " + cmd.getOptionValue(Opts.OUTMAT));
					return;
				} catch (IOException e) {
					System.err.println("Fail to write in the file: " + cmd.getOptionValue(Opts.OUTMAT));
					return;
				}			
				try {
					mrgp.writeStateRewardVec(pw5, parseReward(net, rewardLabel));
				} catch (ASTException ex) {
					System.err.println(ex.getMessage());
					return;
				}
				pw5.close();
			} else {
				pw5 = new PrintWriter(System.out);
				try {
					mrgp.writeStateRewardVec(pw5, parseReward(net, rewardLabel));
				} catch (ASTException ex) {
					System.err.println(ex.getMessage());
					return;
				}
				pw5.flush();
			}
		}

		if (cmd.hasOption(Opts.OUTPETRI)) {
			PrintWriter pw3, pw4;
			try {
				pw3 = new PrintWriter(new BufferedWriter(new FileWriter("group_" + cmd.getOptionValue(Opts.OUTPETRI))));
				pw4 = new PrintWriter(new BufferedWriter(new FileWriter("mark_" + cmd.getOptionValue(Opts.OUTPETRI))));
			} catch (FileNotFoundException e) {
				System.err.println("Fail to write in the file: " + cmd.getOptionValue(Opts.OUTPETRI));
				return;
			} catch (IOException e) {
				System.err.println("Fail to write in the file: " + cmd.getOptionValue(Opts.OUTPETRI));
				return;
			}			
			mat.getMarkingGraph().dotMarkGroup(pw3);
			mat.getMarkingGraph().dotMarking(pw4);
			pw3.close();
			pw4.close();
		}
	}

	public static void cmdSimulation(String[] args) {
		Options options = new Options();
		options.addOption(Opts.INPETRI, true, "input PetriNet file");
		options.addOption(Opts.INITMARK, true, "initial marking");
		options.addOption(Opts.DEPTH, true, "limit");
		options.addOption(Opts.SEED, true, "seed");
		options.addOption(Opts.SIMTIME, true, "time");
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		Net net = loadNet(cmd);
		
		MCSimulation mc = new MCSimulation(net);
		Mark imark = getInitialMark(cmd, net);
		int limits = getLimit(cmd, 1000);
		
		double endTime;
		if (cmd.hasOption(Opts.SIMTIME)) {
			String label = "simtime" + System.currentTimeMillis();
			try {
				JSPetriNet.eval(net, label + " = " + cmd.getOptionValue(Opts.SIMTIME) + ";");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (jspetrinet.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (ASTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			try {
				endTime = Utility.convertObjctToDouble(((ASTree) net.get(label)).eval(net));
			} catch (ASTException e) {
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
		if (cmd.hasOption(Opts.SEED)) {
			seed = Integer.valueOf(cmd.getOptionValue(Opts.SEED));
		} else {
			seed = (int) System.currentTimeMillis();
		}

		Random rnd = new RandomGenerator(seed);
		try {
			List<EventValue> result = mc.runSimulation(imark, 0.0, endTime, limits, rnd);
			PrintWriter pw = new PrintWriter(System.out);
			mc.resultEvent(pw, result);
			pw.close();
		} catch (ASTException e) {
			System.err.println("Failed: " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.print("Require mode: " + Opts.VIEW + ", " + Opts.MARKING + ", " + Opts.SIMULATION);
			System.err.println();
			return;
		}

		String mode = args[0];
		String[] newargs = new String [args.length-1];
		for (int i=1; i<args.length; i++) {
			newargs[i-1] = args[i];
		}

		if (mode.equals(Opts.VIEW)) {
			cmdView(newargs);
		} else if (mode.equals(Opts.MARKING)) {
			cmdAnalysis(newargs);
		} else if (mode.equals(Opts.SIMULATION)) {
			cmdSimulation(newargs);
		} else {
			System.err.print("Require mode: " + Opts.VIEW + ", " + Opts.MARKING + ", " + Opts.SIMULATION);
			System.err.println();
			return;
		}
	}

}
