package DuplicateCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtMethod;

public class Duplicate {
	private CtMethod<?> element;
	private HashMap<List<CtStatement>, ArrayList<String>> duplicate;

	public Duplicate(CtMethod<?> element, HashMap<List<CtStatement>, ArrayList<String>> duplicate) {
		this.element = element;
		this.duplicate = duplicate;
	}

	public Duplicate(HashMap<List<CtStatement>, ArrayList<String>> duplicate) {
		this.duplicate = duplicate;
	}

	public void verif() {
		CtBlock<?> block = element.getBody().clone();
		List<CtStatement> statements = block.getStatements();
		if (duplicate.containsKey(statements)) {
			duplicate.get(statements).add(element.getReference().toString());
		} else {
			ArrayList<String> list = new ArrayList<String>();
			list.add(element.getReference().toString());
			duplicate.put(statements, list);
		}
	}

	public HashMap<ArrayList<String>, String> getDuplication() {
		HashMap<ArrayList<String>, String> dupRetour = new HashMap<ArrayList<String>, String>();
		Iterator<ArrayList<String>> iterator = this.duplicate.values().iterator();
		while (iterator.hasNext()) {
			ArrayList<String> list = iterator.next();
			if (list.size() > 1) {
				String value = list.get(0);
				list.remove(0);
				dupRetour.put(list, value);
			}
		}
		return dupRetour;

	}
}
