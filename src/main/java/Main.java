import java.beans.Statement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import CheckClassOK.CheckClassOK;
import DuplicateCode.Duplicate;
import Field.FieldCheckProcessor;
import Method.MethodCheckProcessor;
import MethodUnused.CheckBodyProcessor;
import MethodUnused.MethodUnusedProcessor;
import spoon.Launcher;
import spoon.reflect.code.CtStatement;

public class Main {
	public static void main(String[] args) throws IOException {
		final Launcher launcher = new Launcher();
		launcher.createFactory();
		final String inputResource = "../blob";
		final String outputDirectory = "../output";
		launcher.addInputResource(inputResource);
		launcher.setSourceOutputDirectory(outputDirectory);
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		HashMap<List<CtStatement>, ArrayList<String>> duplicate = new HashMap<List<CtStatement>, ArrayList<String>>();
		ArrayList<String> useFile = new ArrayList<String>();
		launcher.addProcessor(new MethodCheckProcessor(map, useFile,duplicate));
		launcher.addProcessor(new FieldCheckProcessor());
		launcher.addProcessor(new CheckBodyProcessor(map));
		launcher.addProcessor(new MethodUnusedProcessor(map, useFile,duplicate));
		launcher.prettyprint();
		System.out.println("Before intrumentation...");
		launcher.run();
		new CheckClassOK(useFile, inputResource).listFileUnused();
		System.out.println("Instrumentation done! Output directory: " + outputDirectory);
	}

}
