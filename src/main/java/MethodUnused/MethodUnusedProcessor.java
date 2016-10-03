package MethodUnused;

import java.util.HashMap;

import Method.CheckMethod;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtMethod;

public class MethodUnusedProcessor extends AbstractProcessor<CtMethod<?>> {
	private HashMap<String, Integer> map;

	public MethodUnusedProcessor(HashMap<String, Integer> map) {
		this.map = map;

	}

	@Override
	public void process(CtMethod<?> element) {
		String reference = element.getReference().toString();
		if (map.containsKey(reference) && map.get(reference) == 0) {
			new CheckMethod(element).addComment(ConstantesMethodUnused.UNUSED);
		}
	}

}
