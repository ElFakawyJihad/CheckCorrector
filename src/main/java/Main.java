import Method.MethodRemoverProcessor;
import spoon.Launcher;

public class Main {
	public static void main(String[] args) {
		final Launcher launcher = new Launcher();
		launcher.createFactory();
		final String inputResource = "../blob";
		final String outputDirectory = "../output";
		launcher.addInputResource(inputResource);
		launcher.setSourceOutputDirectory(outputDirectory);
		launcher.addProcessor(new MethodRemoverProcessor());
		launcher.prettyprint();
		System.out.println("Before intrumentation...");
		launcher.run();
		System.out.println("Instrumentation done! Output directory: " + outputDirectory);
	}

}
