package Field;

import spoon.reflect.declaration.CtField;

public class CheckField {
	private CtField<?> field;

	public CheckField(CtField<?> field){
		this.field=field;
	}
	public void verifSyntax(){
		System.out.println(field.getDefaultExpression());
	}
	
}
