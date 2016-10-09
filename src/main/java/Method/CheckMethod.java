package Method;

import java.util.ArrayList;

import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

public class CheckMethod {
	private CtMethod<?> method;
	private ArrayList<String> useFile;

	public CheckMethod(CtMethod<?> method, ArrayList<String> useFile) {
		this.method = method;
		this.useFile = useFile;
	}

	public void verifLengthMethod() {
		if (this.method.getSimpleName().length() <= 3) {
			addComment(ConstantesMethod.LONGUEUR);
		}
	}

	public void verifDocMethod() {
		if (this.method.getDocComment() == null) {
			addComment(ConstantesMethod.DOCUMENTATION);
		}
	}

	public void verifMajuscule() {
		String nameMethod = this.method.getSimpleName();
		String restMethod = nameMethod.substring(1);
		if (!containMaj(restMethod))
			addComment(ConstantesMethod.MAJUSCULE);
	}

	private boolean containMaj(String method) {
		int length = method.length();
		for (int i = 0; i < length; i++) {
			Character c = method.charAt(i);
			if (Character.isUpperCase(c)) {
				return true;
			}
		}
		return false;
	}

	public void verifLigne() {
		if (method.getBody().getStatements().size() > ConstantesMethod.NBLIGNE) {
			addComment(ConstantesMethod.TROPDELIGNE);
		}
	}

	public void verif() {
		System.out.println(this.method.isShadow());
	}

	public void addComment(String comment) {
		CtCodeSnippetStatement stmt = method.getFactory().Core().createCodeSnippetStatement();
		stmt.setValue("//TODO " + comment);
		method.getBody().insertBegin(stmt);
		CtClass<?> classe = (CtClass<?>) (method.getParent());
		String name = classe.getSimpleName();
		if (!this.useFile.contains(name + ConstantesMethod.EXTENSION)) {
			this.useFile.add(name + ConstantesMethod.EXTENSION);
		}
	}
	// TODO Method Verif (changer ==False en l'objet lui meme)

	public void verifier() {
		this.verifDocMethod();
		this.verifLengthMethod();
		this.verifMajuscule();
		this.verifLigne();
	}
}
