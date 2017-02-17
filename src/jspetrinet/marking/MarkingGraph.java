package jspetrinet.marking;

import java.util.HashMap;
import java.util.Map;

import jspetrinet.exception.*;
import jspetrinet.petri.*;

public class MarkingGraph {

	protected Net net;
	protected Mark imark;

	protected final Map<Mark,Mark> markSet;

	protected int numOfGenTrans;
	protected final Map<GenVec,MarkGroup> genGroup;
	protected final Map<GenVec,MarkGroup> immGroup;
	
	private CreateMarking createMarking;
	
	public MarkingGraph() {
		markSet = new HashMap<Mark,Mark>();
		genGroup = new HashMap<GenVec,MarkGroup>();
		immGroup = new HashMap<GenVec,MarkGroup>();
		numOfGenTrans = 0;
	}
	
	public MarkingGraph(Net net) {
		this();
		this.net = net;
		numOfGenTrans = net.getNumOfGenTrans();
	}

	public void setCreateMarking(CreateMarking createMarking) {
		this.createMarking = createMarking;
	}
	
	public final int size() {
		return markSet.size();
	}
	
	public final int immSize() {
		int total = 0;
		for (MarkGroup mg: immGroup.values()) {
			total += mg.size();
		}
		return total;
	}
	
	public final Net getNet() {
		return net;
	}

	public final Mark getInitialMark() {
		return imark;
	}

	public final Map<GenVec,MarkGroup> getImmGroup() {
		return immGroup;
	}

	public final Map<GenVec,MarkGroup> getGenGroup() {
		return genGroup;
	}

	public final boolean containtsMark(Mark m) {
		return markSet.containsKey(m);
	}
	
	public final void addMark(Mark m) {
		markSet.put(m, m);
	}

	public Mark create(Mark init, Net net) throws JSPNException {
		this.net = net;
		this.imark = init;
		markSet.clear();
		numOfGenTrans = net.getNumOfGenTrans();
		immGroup.clear();
		genGroup.clear();		
		Mark ret = this.createMarking.create(init, net);
		CreateGroupMarkingGraph.createMarkGroupGraph(net, immGroup, genGroup);
		return ret;
	}
}
