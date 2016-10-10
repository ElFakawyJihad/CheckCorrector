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
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.PullRequestMarker;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.PullRequestService;
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
                
                
                
                
                //Creer la PR. 
                PullRequestService pullRequestService = new PullRequestService();
                pullRequestService.getClient().setCredentials("user", "password");
                
                RepositoryId repository = new RepositoryId("rbadr", "PullRequestEngineeringTest");
                PullRequest pullRequest = new PullRequest();
                
                PullRequestMarker branchToMerge= new PullRequestMarker();
                PullRequestMarker master= new PullRequestMarker();
                
                branchToMerge.setLabel("branchTest");
                master.setLabel("master");
                 
                pullRequest.setTitle("mettre ici nom du commit");
                pullRequest.setBody("Uncomplete Pull Request : waiting for author to fix code violations in the invalid files before pushing");
                pullRequest.setHead(branchToMerge);
                pullRequest.setBase(master);

                pullRequestService.createPullRequest(repository, pullRequest);
	}
}
