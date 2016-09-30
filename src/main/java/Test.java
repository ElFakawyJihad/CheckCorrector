import java.io.File;
import java.util.Iterator;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

public class Test {

	public static void main(String[] args) {
		Launcher l = new Launcher();
		CtClass<?> classe = l.getFactory().Class().get(Test1.class);
		Iterator<CtMethod<?>> methodsIterator = classe.getMethods().iterator();
		l.run();
		CtMethod<?> method = methodsIterator.next();
		classe.removeMethod(method);
		l.getModelBuilder().compile();

	}
}
