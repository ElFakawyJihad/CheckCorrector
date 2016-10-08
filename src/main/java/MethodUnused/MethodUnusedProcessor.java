package MethodUnused;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import DuplicateCode.Duplicate;
import Method.CheckMethod;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtMethod;

public class MethodUnusedProcessor extends AbstractProcessor<CtMethod<?>> {
	private HashMap<String, Integer> map;
	private ArrayList<String> useFile;
	private HashMap<List<CtStatement>, ArrayList<String>> duplicate;
	private HashMap<ArrayList<String>, String> duplicList = null;

	public MethodUnusedProcessor(HashMap<String, Integer> unused, ArrayList<String> useFile,
			HashMap<List<CtStatement>, ArrayList<String>> duplicate) {
		this.map = unused;
		this.useFile = useFile;
		this.duplicate = duplicate;
	}

	@Override
	public void process(CtMethod<?> element) {
		String reference = element.getReference().toString();
		if (map.containsKey(reference) && map.get(reference) == 0) {
			new CheckMethod(element, useFile).addComment(ConstantesMethodUnused.UNUSED);
		}
		if (duplicList == null)
			duplicList = new Duplicate(duplicate).getDuplication();
		Iterator<ArrayList<String>> duplication = duplicList.keySet().iterator();
		while (duplication.hasNext()) {
			ArrayList<String> dup = duplication.next();
			if (dup.contains(element.getReference().toString())) {
				new CheckMethod(element, useFile)
						.addComment(ConstantesMethodUnused.DUPLICATE + duplicList.get(dup).replace("#", "."));
			}
		}

	}

}
