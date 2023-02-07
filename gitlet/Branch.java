package gitlet;

import java.io.File;
import java.io.Serializable;

public class Branch implements Serializable {
    public String branchName;
    public Commit endCommit;
    private boolean isHead;

    public Branch(Commit first) {
        this.endCommit = first;
        this.branchName = "master";
        this.isHead = true;
        byte[] branchByteArray = Utils.serialize(this);
        File branchFile = new File(System.getProperty("user.dir")
                + "/.gitlet/branches/" + branchName);
        Utils.writeContents(branchFile, branchByteArray);
    }

    public Branch(Commit end, String name) {
        this.endCommit = end;
        this.branchName = name;
        this.isHead = false;
        byte[] branchByteArray = Utils.serialize(this);
        File branchFile = new File(System.getProperty("user.dir")
                + "/.gitlet/branches/" + branchName);
        Utils.writeContents(branchFile, branchByteArray);
    }
}
