package Catch;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCatch;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtComment.CommentType;
import spoon.reflect.code.CtTry;

public class CatchProcessor extends AbstractProcessor<CtCatch> {
	public void process(CtCatch element) {
		if (element.getBody().getStatements().size() == 0) {
			CtTry method=(CtTry) element.getParent();
			method.addComment(createComment(ConstantesCatchUnused.CATCHEMPTY, element));
		}
	}

	public CtComment createComment(String Comment, CtCatch element) {
		String todo = "TODO ";
		return element.getFactory().Code().createComment(todo + Comment, CommentType.INLINE);
	}
}
