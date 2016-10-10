package PullRequestEngineering;

import java.io.IOException;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.PullRequestMarker;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.PullRequestService;

public class PullRequestCreation {
    
    private String user,password,repoOwner,repoName;
    
    public PullRequestCreation(String user, String password, String repoOwner, String repoName, String commitMessage){
        this.user=user;
        this.password=password;
        this.repoOwner=repoOwner;
        this.repoName=repoName;
    }
    
    public void CreatePullRequest(String branchName, String commitMessage, String PullRequestStatus) throws IOException{
        PullRequestService pullRequestService = new PullRequestService();
        pullRequestService.getClient().setCredentials(user,password);
        
        RepositoryId repository = new RepositoryId(repoOwner, repoName);
        PullRequest pullRequest = new PullRequest();
        
        PullRequestMarker branchToMerge= new PullRequestMarker();
        PullRequestMarker master= new PullRequestMarker();
        branchToMerge.setLabel(branchName);
        master.setLabel("master");
        
        pullRequest.setTitle(commitMessage);
        pullRequest.setBody(PullRequestStatus);
        pullRequest.setHead(branchToMerge);
        pullRequest.setBase(master);
        
        pullRequestService.createPullRequest(repository, pullRequest);
    }             
}
