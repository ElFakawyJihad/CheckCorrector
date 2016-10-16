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
import java.io.File;
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

		System.out.println("Check Corrector running...");
		launcher.run();

		File[] ValidFile=new CheckClassOK(useFile, inputResource).listFileOK();
                File[] InvalidFile=new CheckClassOK(useFile, inputResource).listFileKO();


                if(ValidFile.length >0 && InvalidFile.length== 0){
                    for (File ValidFileName : ValidFile) {
                        GitImplementation gitCmd = new GitImplementation(UserConfiguration.localPath, UserConfiguration.remotePath, UserConfiguration.user, UserConfiguration.password);
                        gitCmd.addFile(ValidFileName.getName());
			gitCmd.commitChanges(commitMessage);
			gitCmd.pushCommit();

                        PullRequestCreation newPullRequest = new PullRequestCreation(UserConfiguration.user, UserConfiguration.password, UserConfiguration.repoOwner, UserConfiguration.repoName, commitMessage);
                        newPullRequest.CreatePullRequest(branchToMerge, commitMessage, UserConfiguration.CompletePullRequest);
                        System.out.println("Code Analysis done. No Invalid Files discovered !");
                        System.out.println("Pull Request Created and Complete.");
                    }
                }
                
                else if(ValidFile.length >0 && InvalidFile.length >0){
                    for (File ValidFileName : ValidFile) {
                        GitImplementation gitCmd = new GitImplementation(UserConfiguration.localPath, UserConfiguration.remotePath, UserConfiguration.user, UserConfiguration.password);
                        gitCmd.addFile(ValidFileName.getPath());
			gitCmd.commitChanges(commitMessage);
			gitCmd.pushCommit();

                        PullRequestCreation newPullRequest = new PullRequestCreation(UserConfiguration.user, UserConfiguration.password, UserConfiguration.repoOwner, UserConfiguration.repoName, commitMessage);
                        newPullRequest.CreatePullRequest(branchToMerge, commitMessage, UserConfiguration.UncompletePullRequest);
                        System.out.println("Code Analysis and Correction done. Invalid Files discovered !");
                        System.out.println("Pull Request Created but Uncomplete");
                        System.out.println("Please Review the Invalid Files and run the program again to finalize the Pull Request.");
                    }
                }
                
                else if(ValidFile.length ==0 && InvalidFile.length >0){
                        System.out.println("Code Analysis and Correction done. Invalid Files discovered !");
                        System.out.println("Pull Request Not Created.");
                        System.out.println("Please Review the Invalid Files and run the program again to create the Pull Request.");
                    }
                
                else {
                    System.out.println("No Files found in the local repository!!");
                }
        }
}
