package PullRequestEngineering;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class GitImplementation {
    
    private String localPath,remotePath,user,password;
    
    private Repository localRepo;
    private Git git = new Git(localRepo);
    
    public GitImplementation(String localPath,String remotePath, String user, String password){
        this.localPath=localPath;
        this.remotePath=remotePath;
        this.user=user;
        this.password=password;
    }
    
    CredentialsProvider cp = new UsernamePasswordCredentialsProvider("user", "password");
    
    public void gitInit() throws IOException {
        localRepo = new FileRepository(localPath + "/.git");
        git = new Git(localRepo);
    }
    
    public void createRepo() throws IOException {
        Repository newRepo = new FileRepository(localPath + ".git");
        newRepo.create();
    }
    
    public void cloneRepo() throws IOException, GitAPIException {
        Git.cloneRepository().setCredentialsProvider(cp).setURI(remotePath)
                .setDirectory(new File(localPath)).call();
    }
    
    public void addFile(String ValidFileName) throws IOException, GitAPIException {
        File myfile = new File(localPath + "/" +ValidFileName);
        myfile.createNewFile();
        git.add().addFilepattern(ValidFileName).call();
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
