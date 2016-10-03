package Field;

import java.util.Set;

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
		Set<ModifierKind> modifiers = field.getModifiers();
		if (modifiers.contains(ModifierKind.STATIC) && modifiers.contains(ModifierKind.FINAL)) {
			if (!isUpperCase(field.getSimpleName())) {
				field.setSimpleName(field.getSimpleName().toUpperCase());
			}
		}
	}

	public void verifFieldName() {
		Set<ModifierKind> modifiers = field.getModifiers();
		if (modifiers.contains(ModifierKind.STATIC) && modifiers.contains(ModifierKind.FINAL)) {
			verifStaticFinal();
		} else {
			
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

}
