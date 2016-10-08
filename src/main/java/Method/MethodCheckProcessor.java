package Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DuplicateCode.Duplicate;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtMethod;

public class MethodCheckProcessor extends AbstractProcessor<CtMethod<?>> {
	private HashMap<String, Integer> map;
	private ArrayList<String> useFile;
	private HashMap<List<CtStatement>, ArrayList<String>> duplicate;

	public MethodCheckProcessor(HashMap<String, Integer> map, ArrayList<String> useFile,
			HashMap<List<CtStatement>, ArrayList<String>> duplicate) {
		this.map = map;
		this.useFile = useFile;
		this.duplicate = duplicate;
	}

	@Override
	public boolean isToBeProcessed(CtMethod<?> candidate) {
		return true;
	}

	@Override
	public void process(CtMethod<?> element) {
		new Duplicate(element, duplicate).verif();
		String method = element.getReference().toString();
		if (!method.contains(ConstantesMethod.SIGNATURE_MAIN)) {
			if (!this.map.containsKey(method)) {
				this.map.put(method, 0);
			}
			new CheckMethod(element, useFile).verifier();
		}
	}

}
