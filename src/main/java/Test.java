import java.util.Iterator;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

public class Test {
	 private static CtMethod<?> m;

	public static void main(String[] args) {
		Launcher l=new Launcher();
		l.run();
		CtClass<?> classe=l.getFactory().Class().get(Test1.class);
		Iterator<CtMethod<?>> method=classe.getMethods().iterator();
		while (method.hasNext()){
			m=method.next();
		}
		classe.removeMethod(m);
		l.getModelBuilder().compile();
		
		
	}
}
