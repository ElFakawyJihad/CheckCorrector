package Method;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtMethod;

public class MethodCheckProcessor extends AbstractProcessor<CtMethod<?>> {

	@Override
	public boolean isToBeProcessed(CtMethod<?> candidate) {
		return true;
	}

	@Override
	public void process(CtMethod<?> element) {
		new CheckMethod(element).verifier();
	}

}
