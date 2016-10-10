package While;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtWhile;
import spoon.reflect.code.CtComment.CommentType;

public class WhileProcessor extends AbstractProcessor<CtWhile>{

	@Override
	public void process(CtWhile element) {
		CtExpression<?> expression=element.getLoopingExpression();
		if (expression.toString().equals("true") || expression.toString().equals("false")){
			element.addComment(createComment(ConstantesWhile.BOUCLEINFINI, element));
		}	
	}
	
	public CtComment createComment(String Comment,CtWhile element) {
		String todo = "TODO ";
		return element.getFactory().Code().createComment(todo + Comment, CommentType.INLINE);
	}

}
