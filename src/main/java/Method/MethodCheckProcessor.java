package Method;

import java.util.HashMap;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtMethod;

public class MethodCheckProcessor extends AbstractProcessor<CtMethod<?>> {
	private HashMap<String, Integer> map;

	public MethodCheckProcessor(HashMap<String, Integer> map) {
		this.map = map;
	}

	@Override
	public boolean isToBeProcessed(CtMethod<?> candidate) {
		return true;
	}

	@Override
	public void process(CtMethod<?> element) {
		String method = element.getReference().toString();
		if (!method.contains(ConstantesMethod.SIGNATURE_MAIN) && !this.map.containsKey(method))
			this.map.put(method, 0);
		new CheckMethod(element).verifier(); 
	}

}
