package MethodUnused;

import java.util.HashMap;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;

public class CheckBodyProcessor extends AbstractProcessor<CtBlock<?>> {
	private HashMap<String, Integer> map;

	public CheckBodyProcessor(HashMap<String, Integer> map) {
		this.map = map;
	}

	@Override
	public void process(CtBlock<?> element) {
		element.getStatements();
		for (int i = 0; i < element.getStatements().size(); i++) {
			String representation = element.getStatement(i).getShortRepresentation();
			String reference=recupMethod(representation);
			if (map.containsKey(reference)) {
				int value = map.get(reference);
				map.put(reference, value + 1);
			}
		}
	}

	public String recupMethod(String representation) {
		int i = representation.indexOf('#');
		if (i == -1) {
			return "";
		}
		String before = "";
		for (int a = i; a > 0; a--) {
			char actuel = representation.charAt(a);
			if (actuel == ' ') {
				break;
			} else {
				before = actuel + before;
			}
		}
		String after = "";
		for (int a = i + 1; a < representation.length(); a++) {
			char actuel = representation.charAt(a);
			if (actuel == ' ') {
				break;
			}
			if (actuel == ')') {
				after = after + ')';
				break;
			} else {
				after=after+actuel;
			}
		}
		return before+after;
	}

}
