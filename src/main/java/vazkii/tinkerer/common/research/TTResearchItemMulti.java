package vazkii.tinkerer.common.research;

import java.util.ArrayList;

public class TTResearchItemMulti implements IRegisterableResearch {

	private ArrayList<TTResearchItem> researches = new ArrayList<TTResearchItem>();

	public TTResearchItemMulti(ArrayList<TTResearchItem> researches) {
		this.researches = researches;
	}

	public TTResearchItemMulti() {
		this.researches = new ArrayList<TTResearchItem>();
	}

	@Override
	public void registerResearch() {
		for (TTResearchItem researchItem : researches) {
			researchItem.registerResearchItem();
		}
	}

	public void addResearch(TTResearchItem researchItem) {
		researches.add(researchItem);
	}

}
