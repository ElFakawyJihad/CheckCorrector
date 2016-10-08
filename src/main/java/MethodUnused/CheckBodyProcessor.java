package MethodUnused;

import java.beans.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

public class CheckBodyProcessor extends AbstractProcessor<CtBlock<?>> {
	private HashMap<String, Integer> map;

	public CheckBodyProcessor(HashMap<String, Integer> map) {
		this.map = map;
	}

	@Override
	public void process(CtBlock<?> element) {
		// TODO à completer
		List<CtStatement> statements = element.getStatements();
		for (int i = 0; i < statements.size(); i++) {
			String representation = element.getStatement(i).getShortRepresentation();
			String reference = recupMethod(representation);
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
				after = after + actuel;
			}
		}
		return before + after;
	}

}
