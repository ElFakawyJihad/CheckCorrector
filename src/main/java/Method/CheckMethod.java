package Method;

import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.declaration.CtMethod;

public class CheckMethod {
	private CtMethod<?> method;

	public CheckMethod(CtMethod<?> method) {
		this.method = method;
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

	public void addComment(String comment) {
		CtCodeSnippetStatement stmt = method.getFactory().Core().createCodeSnippetStatement();
		stmt.setValue("//TODO " + comment);
		method.getBody().insertBegin(stmt);
	} 
	//TODO Method Verif (changer ==False en l'objet lui meme)

	public void verifier() {
		this.verifDocMethod();
		this.verifLengthMethod();
		this.verifMajuscule();
		this.verifLigne();
	}
}
