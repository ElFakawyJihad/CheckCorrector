import Method.CheckMethod;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtMethod;

public class MethodRemoverProcessor extends AbstractProcessor<CtMethod<?>> {

	@Override
	public boolean isToBeProcessed(CtMethod<?> candidate) {
		return true;
	}

	@Override
	public void process(CtMethod<?> element) {
		new CheckMethod(element).verifier();
		System.out.println(element);
		// this.process();
		// System.out.println(element.getComments());

	}

}
