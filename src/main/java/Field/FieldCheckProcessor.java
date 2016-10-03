package Field;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtField;

public class FieldCheckProcessor extends  AbstractProcessor<CtField<?>>{

	@Override
	public void process(CtField<?> element) {
		new CheckField(element).verifSyntax();
	}

}
