package gitlet;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Osvaldo Valadez
 * @collaborators Thomas Nguyen
 */
public class Main {
    public static Repo mainRepo;
    public static HashMap<String, Commit> commitHashMap;
    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) throws IOException, ClassNotFoundException {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        File workingDir = new File(System.getProperty("user.dir"));
        File gitletDir = new File(workingDir, ".gitlet");
        File commitDir = new File(gitletDir, "commits");
        File blobDir = new File(gitletDir, "blobs");
        File branchDir = new File(gitletDir, "branches");
        File stagingAreaFile = new File(gitletDir, "staging_area");
        File saveRepo = new File(gitletDir,"repo");
        File removeArea = new File(gitletDir,"remove");
        if (args[0].equals("init")) {
            boolean didAlreadyExist = !gitletDir.mkdir();
            try {
                if (didAlreadyExist) {
                    throw new InstantiationException();
                }
            } catch (InstantiationException e) {
                System.out.println("A Gitlet version-control system "
                        + "already exists in the current directory.");
            }
            if (!didAlreadyExist) {
                commitHashMap = new HashMap<>();
                commitDir.mkdir();
                blobDir.mkdir();
                branchDir.mkdir();
                mainRepo = new Repo(commitHashMap);
                File repository = new File(System.getProperty("user.dir")
                        + "/.gitlet/repo");
                Utils.writeContents(repository,Utils.serialize(mainRepo));
            }
        } else {
            commitHashMap = new HashMap<>();
            HashMap<String, Blob> blobHashMap = new HashMap<>();
            fillHashMap(commitDir,commitHashMap);
            fillHashMap(blobDir,blobHashMap);
            File repository = new File(System.getProperty("user.dir")
                    + "/.gitlet/repo");
            mainRepo = Utils.readObject(repository, Repo.class);
            if (args[0].equals("add")) {
                int end = args[0].indexOf(" ") + 1;
                mainRepo.add(args[1]);
                submitRepo();
            } else if (args[0].equals("commit")) {
                mainRepo.commit(args[1]);
                mainRepo.blobTrack = blobHashMap;
                submitRepo();
            } else if (args[0].equals("checkout")) {
                if (args[1].equals("--")) {
                    mainRepo.checkout1(args[2]);
                    submitRepo();
                /*} else if (args.length == 4) {
                    if (args[2].equals("--")) {
                        mainRepo.checkout2(args[1], args[3]);*/
                    } else {
                    System.out.println("Incorrect Operands.");
                    }
            } else if (args[0].equals("log")) {
                mainRepo.logg();
                submitRepo();
            } else {
                System.out.println("No command with that name exists.");

            }
        }
    }

    public static  void fillHashMap(File gitletDir, HashMap hashes) {
        for (File in: gitletDir.listFiles()) {
            if (in.isDirectory()) {
                fillHashMap(in, hashes);
            } else {
                hashes.put(in.getName(),Utils.readContents(in));
            }
        }
    }
    public static void submitRepo() {
        File repository = new File(System.getProperty("user.dir")
                + "/.gitlet/repo");
        Utils.writeContents(repository,Utils.serialize(mainRepo));

    }
}


