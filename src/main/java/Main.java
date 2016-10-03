import java.util.HashMap;

import Field.FieldCheckProcessor;
import Method.MethodCheckProcessor;
import MethodUnused.CheckBodyProcessor;
import MethodUnused.MethodUnusedProcessor;
import spoon.Launcher;

public class Main {
	public static void main(String[] args) {
		final Launcher launcher = new Launcher();
		launcher.createFactory();
		final String inputResource = "../blob";
		final String outputDirectory = "../output";
		launcher.addInputResource(inputResource);
		launcher.setSourceOutputDirectory(outputDirectory);
		HashMap<String,Integer> map=new HashMap<String,Integer>();
		launcher.addProcessor(new FieldCheckProcessor());
		launcher.addProcessor(new MethodCheckProcessor(map));
		launcher.addProcessor(new CheckBodyProcessor(map));
		launcher.addProcessor(new MethodUnusedProcessor(map));
		launcher.prettyprint();
		System.out.println("Before intrumentation...");
		launcher.run();
		System.out.println("Instrumentation done! Output directory: " + outputDirectory);
	}

}
