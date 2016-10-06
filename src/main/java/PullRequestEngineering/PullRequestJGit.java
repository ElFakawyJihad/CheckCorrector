package PullRequestEngineering;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;

public class PullRequestJGit {
    
    private String localPath = "D:\\IAGL\\OPL\\PullRequestEngineeringTest";
    private String remotePath = "https://user:mdp@github.com//rbadr/PullRequestEngineeringTest";
	
    private Repository localRepo;
    private Git git;
    
    public void gitInit() throws IOException {
        localRepo = new FileRepository(localPath + "/.git");
        git = new Git(localRepo);
    }
    
    public void createRepo() throws IOException {
        Repository newRepo = new FileRepository(localPath + ".git");
        newRepo.create();
    }
    
    public void cloneRepo() throws IOException, GitAPIException {
        Git.cloneRepository().setURI(remotePath)
                .setDirectory(new File(localPath)).call();
    }
    
    public void addFile(String ValidFileName) throws IOException, GitAPIException {
        File myfile = new File(localPath + "/" +ValidFileName);
        myfile.createNewFile();
        git.add().addFilepattern("myfile").call();
    }
    
    public void commitChanges(String commitMessage) throws IOException, GitAPIException,
            JGitInternalException {
        git.commit().setMessage(commitMessage).call();
    }
    
    public void pushCommit() throws IOException, JGitInternalException,
            GitAPIException {
        git.push().call();
    }
}
