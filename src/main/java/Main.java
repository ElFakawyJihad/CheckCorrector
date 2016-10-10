import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import CheckClassOK.CheckClassOK;
import Field.FieldCheckProcessor;
import Method.MethodCheckProcessor;
import MethodUnused.CheckBodyProcessor;
import MethodUnused.MethodUnusedProcessor;
import PullRequestEngineering.GitImplementation;
import PullRequestEngineering.PullRequestCreation;
import UserConfiguration.UserConfiguration;
import While.WhileProcessor;

import org.eclipse.jgit.api.errors.GitAPIException;

import Catch.CatchProcessor;
import spoon.Launcher;
import spoon.reflect.code.CtStatement;

public class Main {

	private static String commitMessage = "commitMessage";
	private static String branchToMerge = "branchTest";

	public static void main(String[] args) throws IOException, AddressException, MessagingException, GitAPIException {
		final Launcher launcher = new Launcher();
		launcher.createFactory();

		final String inputResource = UserConfiguration.localPath;
		final String outputDirectory = UserConfiguration.localPath;

		launcher.addInputResource(inputResource);
		launcher.setSourceOutputDirectory(outputDirectory);
		launcher.getFactory().getEnvironment().setAutoImports(true);
		launcher.getFactory().getEnvironment().setCommentEnabled(true);
		launcher.getFactory().getEnvironment().useTabulations(true);

		HashMap<String, Integer> map = new HashMap<String, Integer>();
		HashMap<List<CtStatement>, ArrayList<String>> duplicate = new HashMap<List<CtStatement>, ArrayList<String>>();
		ArrayList<String> useFile = new ArrayList<String>();
		launcher.addProcessor(new MethodCheckProcessor(map, useFile, duplicate));
		launcher.addProcessor(new FieldCheckProcessor());
		launcher.addProcessor(new CheckBodyProcessor(map));
		launcher.addProcessor(new MethodUnusedProcessor(map, useFile, duplicate));
		launcher.addProcessor(new CatchProcessor());
		launcher.addProcessor(new WhileProcessor());
		launcher.prettyprint();

		System.out.println("Before intrumentation...");
		launcher.run();

		new CheckClassOK(useFile, inputResource).listFileOK();
		String ValidFile[] = { "src\\main\\java\\ResultMainTest\\Person.java"};

		for (String ValidFileName : ValidFile) {
			GitImplementation gitCmd = new GitImplementation(UserConfiguration.localPath, UserConfiguration.remotePath,
					UserConfiguration.user, UserConfiguration.password);
			gitCmd.addFile(ValidFileName);
			gitCmd.commitChanges(commitMessage);
			gitCmd.pushCommit();

			PullRequestCreation newPullRequest = new PullRequestCreation(UserConfiguration.user,
					UserConfiguration.password, UserConfiguration.repoOwner, UserConfiguration.repoName, commitMessage);

			newPullRequest.CreatePullRequest(branchToMerge, commitMessage, UserConfiguration.UncompletePullRequest);
		}

		System.out.println("Instrumentation done! Output directory: " + outputDirectory);
                System.out.println("Pull Request Created");
	}
}
