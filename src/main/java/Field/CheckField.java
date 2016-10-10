package Field;

import java.util.Set;

import spoon.refactoring.Refactoring;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtComment.CommentType;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.ModifierKind;

public class CheckField {
	private CtField<?> field;

	public CheckField(CtField<?> field) {
		this.field = field;
	}

	public void verifSyntax() {
		verifFieldName();
	}

	public void verifStaticFinal() {
		String name = field.getSimpleName();
		if (!isUpperCase(name)) {
			field.setSimpleName(name.toUpperCase());
			field.addComment(this
					.createComment(ConstantesField.STATICFINAL + "\"" + name + "\" en \"" + name.toUpperCase() + "\""));
		}
	}

	public void verifStatic() {
		String name = field.getSimpleName();
		if (!isFirstCaracterStatic(name)) {
			field.setSimpleName("_" + name);
			field.addComment(this.createComment(ConstantesField.STATIC + "\"" + name + "\" en \"_" + name + "\""));
		}
	}

	public void verifNoStaticNoFinal() {
		String name = field.getSimpleName();
		if (!isFirstLowerCase(name)) {
			String firstLower = Character.toLowerCase(name.charAt(0)) + name.substring(1);
			field.setSimpleName(firstLower);
			field.addComment(this.createComment(ConstantesField.NOSTATICNOFINAL+ "\"" + name + "\" en \"" + firstLower + "\""));
		}
	}

	public void verifFieldName() {
		Set<ModifierKind> modifiers = field.getModifiers();
		if (modifiers.contains(ModifierKind.STATIC) && modifiers.contains(ModifierKind.FINAL)) {
			verifStaticFinal();
			return;
		} else {
			if (modifiers.contains(ModifierKind.STATIC)) {
				verifStatic();
				return;
			} else {

			}

		}
	}

	public boolean isUpperCase(String world) {
		int length = world.length();
		for (int i = 0; i < length - 1; i++) {
			if (!Character.isUpperCase(world.charAt(i)))
				return false;
		}
		return true;
	}

	public boolean isFirstLowerCase(String world) {
		return Character.isLowerCase(world.charAt(0));
	}

	public boolean isFirstCaracterStatic(String world) {
		return world.charAt(0) == '_';
	}

	public CtComment createComment(String Comment) {
		String todo = "TODO ";
		return this.field.getFactory().Code().createComment(todo + Comment, CommentType.INLINE);
	}

}
