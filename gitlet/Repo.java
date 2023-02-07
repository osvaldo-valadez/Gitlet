package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

public class Repo implements Serializable {
    private String head;
    private HashMap<String,String> toBeAdded;
    public HashMap<String,Blob> blobTrack;
    private HashMap<String,String> toBeRemoved;

    public String getHead() {
        return head;
    }
    public Repo(HashMap<String, Commit> hashCommit) {
        Commit start = new Commit("initial commit", "00:00:00 UTC, Thursday, 1 January 1970", null,null);
        String first = Utils.sha1(Utils.serialize(start));
        File CommitSpot = new File(System.getProperty("user.dir") + "/.gitlet/commits/" +
                first);
        Utils.writeObject(CommitSpot,start);
        toBeAdded = new HashMap<>();
        File stage = new File(System.getProperty("user.dir") + "/.gitlet/staging_area");
        Utils.writeObject(stage,toBeAdded);
        blobTrack = new HashMap<>();
        head = first;
        toBeRemoved = new HashMap<>();
        File rem;
    }
    public void add(String file) {
        boolean exists = false;
        File tempFile = new File(System.getProperty("user.dir") + "/testing/src");
        for (String fileNames: Utils.plainFilenamesIn(tempFile)) {
            if (fileNames.equals(file)) {
                File sample = new File(System.getProperty("user.dir") + "/testing/src/"+file);
                exists= true;
            }
        }
        if (!exists) {
            System.out.println("File does not exist.");
        } else {
            File sample = new File(System.getProperty("user.dir") + "/testing/src/"+file);
            File famous = new File(System.getProperty("user.dir")
                    + "/.gitlet/staging_area");
            toBeAdded = Utils.readObject(famous, HashMap.class);
            Blob ultralight = new Blob(file, Utils.readContents(sample));
            toBeAdded.put(file, Utils.sha1(Utils.serialize(ultralight)));
            Utils.writeObject(famous, toBeAdded);
            addBlobToFile(ultralight);
        }
    }

    public void addBlobToFile(Blob blob) {
        File blobFile = new File(System.getProperty("user.dir") + "/.gitlet/blobs/"
                + Utils.sha1(Utils.serialize(blob)));
        Utils.writeContents(blobFile,Utils.serialize(blob));
    }



    public void commit(String args) {
        File staged = new File(System.getProperty("user.dir") + "/.gitlet/staging_area");
        HashMap contents = Utils.readObject(staged,HashMap.class);
        Commit newCommit = new Commit(args,head,contents);
        writeCommitToFile(newCommit);
        this.head = Utils.sha1(Utils.serialize(newCommit));
        HashMap newStaging = new HashMap();
        Utils.writeObject(staged,newStaging);
    }

    public void writeCommitToFile(Commit commit) {
        File CommitSpot = new File(System.getProperty("user.dir") + "/.gitlet/commits/" +
                Utils.sha1(Utils.serialize(commit)));
        Utils.writeObject(CommitSpot,commit);

    }

    public void checkout1(String name) {
        String parent = head;
        File path = new File(System.getProperty("user.dir") + "/.gitlet/commits/" + parent);
        Commit father = Utils.readObject(path,Commit.class);
        HashMap<String, String> mmap = father.pair;
        for (Object each: mmap.keySet()) {
            if (each.equals(name)) {
                File bb = new File("/Users/osvaldovaladez/Desktop/repo/proj3/testing/src/"+name);
                String info = mmap.get(name);
                File pathname = new File(System.getProperty("user.dir") + "/.gitlet/blobs/" + info);
                Blob please = Utils.readObject(pathname,Blob.class);
                Utils.writeContents(bb,please.getContents());
            }
        }

    }

/*    public void checkout2(String ID, String fileName) {
        for (String commit: hashCommit.keySet()) {
            if (commit.equals(ID)) {
                for (String each: hashCommit.get(ID).pair.keySet()) {
                    if (each.equals(fileName)) {
                        File bb = new File(fileName);
                        Utils.writeContents(bb,hashCommit.get(ID).pair.get(fileName));
                    }
                }
            }
        }
    }*/

    public void writeToFile() {
        byte[] stagingAreaByteArray = Utils.serialize(this);
        File stagingAreaFile = new File(System.getProperty("user.dir")
                + "/.gitlet/staging_area");
        Utils.writeContents(stagingAreaFile, stagingAreaByteArray);
    }

    public void logg() {
        String track = this.getHead();
        while (track != null) {
            File tracked = new File(System.getProperty("user.dir") + "/.gitlet/commits/" + track);
            Commit headass = Utils.readObject(tracked,Commit.class);
            System.out.println("===");
            System.out.println("Commit" + head);
            System.out.println(headass.getDate());
            System.out.println(headass.getMessage());
            System.out.println();
            track = headass.getParent();
        }
    }
}
