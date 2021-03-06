package jspetrinet.cli;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;

import jmatout.MATLABHeader;
import jspetrinet.JSPetriNet;
import jspetrinet.analysis.MRGPMatrixASCIIWriter;
import jspetrinet.analysis.MRGPMatrixMATLABWriter;
import jspetrinet.analysis.MarkingMatrix;
import jspetrinet.ast.AST;
import jspetrinet.common.Utility;
import jspetrinet.exception.JSPNException;
import jspetrinet.exception.TypeMismatch;
import jspetrinet.marking.Mark;
import jspetrinet.marking.MarkingGraph;
import jspetrinet.petri.ExpTrans;
import jspetrinet.petri.Net;
import jspetrinet.petri.Trans;

public class CommandLineCommons {

	public static Map<String,Integer> parseMark(String str) {
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

	public static List<AST> parseReward(Net net, String str) throws JSPNException {
		List<AST> result = new ArrayList<AST>();
		String[] ary = str.split(",", 0);
		for (String s : ary) {
			Object obj = net.get(s);
			if (obj instanceof AST) {
				result.add((AST) obj);
			} else {
				throw new TypeMismatch();
			}
		}
		return result;
	}

	public static List<Trans> parseExpTrans(Net net, String str) throws JSPNException {
		List<Trans> result = new ArrayList<Trans>();
		String[] ary = str.split(",", 0);
		for (String s : ary) {
			Object obj = net.get(s);
			if (obj instanceof ExpTrans) {
				result.add((Trans) obj);
			} else {
				throw new TypeMismatch();
			}
		}
		return result;
	}

	public static Net loadNet(CommandLine cmd) {
		Net net = new Net(null, "");
		InputStream in = null;
		if (cmd.hasOption(CommandLineOptions.INPETRI)) {
			try {
				in = new FileInputStream(cmd.getOptionValue(CommandLineOptions.INPETRI));
			} catch (FileNotFoundException e) {
				System.err.println("Error: Did not find file: " + cmd.getOptionValue(CommandLineOptions.INPETRI));
				System.exit(1);
			}
		} else {
			in = System.in;
		}
		try {
			net = JSPetriNet.load(net, in);
		} catch (JSPNException e) {
			System.out.println("Error: " + e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		net.setIndexAndSortIMM();
		return net;
	}

	// get option values

	public static Mark getInitialMark(CommandLine cmd, Net net) {
		Mark imark = null;
		if (cmd.hasOption(CommandLineOptions.INITMARK)) {
			try {
				imark = JSPetriNet.mark(net, parseMark(cmd.getOptionValue(CommandLineOptions.INITMARK)));
			} catch (JSPNException e) {
				System.err.println("Error: " + e.getMessage() + " Please check the imark option.");
				System.exit(1);
			}
		} else {
			try {
				imark = JSPetriNet.mark(net,  net.getIMark());
			} catch (JSPNException e) {
				System.err.println("Error: " + e.getMessage() + " Please check the init option of the definition of places.");
				System.err.println("The initial marking is also defined by (-" + CommandLineOptions.INITMARK + ").");
				System.exit(1);
			}
		}
		System.out.println("Initial marking: " + JSPetriNet.markToString(net, imark));
		return imark;
	}

	public static int getLimit(CommandLine cmd, int defaultValue) {
		if (cmd.hasOption(CommandLineOptions.FIRINGLIMIT)) {
			return Integer.parseInt(cmd.getOptionValue(CommandLineOptions.FIRINGLIMIT));
		} else {
			return defaultValue;
		}
	}

	public static boolean getVanish(CommandLine cmd, boolean defaultValue) {
		if (cmd.hasOption(CommandLineOptions.VANISHING)) {
			return true;
		} else {
			return defaultValue;
		}
	}
	
	public static int getRun(CommandLine cmd, int defaultValue) {
		if (cmd.hasOption(CommandLineOptions.SIMRUN)) {
			return Integer.parseInt(cmd.getOptionValue(CommandLineOptions.SIMRUN));
		} else {
			return defaultValue;
		}
	}
	
	public static double getTime(CommandLine cmd, Net net) throws JSPNException {
		String label = "simtime" + System.currentTimeMillis();
		JSPetriNet.eval(net, label + " = " + cmd.getOptionValue(CommandLineOptions.SIMTIME) + ";");
		return Utility.convertObjctToDouble(((AST) net.get(label)).eval(net));
	}
	
	public static long getSeed(CommandLine cmd) {
		if (cmd.hasOption(CommandLineOptions.SEED)) {
			return Long.valueOf(cmd.getOptionValue(CommandLineOptions.SEED));
		} else {
			return System.currentTimeMillis();
		}
	}

	public static void checkOptionMark(CommandLine cmd) {
		if (cmd.hasOption(CommandLineOptions.MATLAB) && cmd.hasOption(CommandLineOptions.TEXT)) {
			System.err.println("Output mode should be chosen either '-text' or '-bin'.");
			System.exit(1);
		}
		if (cmd.hasOption(CommandLineOptions.MATLAB)) {
			if (!cmd.hasOption(CommandLineOptions.OUT)) {
				System.err.println("The option '-bin' requires the option '-o' to write binary filise.");				
				System.exit(1);
			}
		}
	}
	
}
