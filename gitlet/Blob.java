package gitlet;

import java.io.File;
import java.io.Serializable;

public class Blob implements Serializable {
    /** The string containing the fileName of the blob. */
    private String fileName;

    /** The contents of the file of the blob. */
    private byte[] contents;

    public byte[] getContents() {
        return contents;
    }

    /**
     * Intitializes the blob object.
     * @param fileName
     * @param contents
     */
    public Blob(String fileName, byte[] contents) {
        this.fileName = fileName;
        this.contents = contents;
        byte[] blobByteArray = Utils.serialize(this);
        File blobFile = new File(System.getProperty("user.dir") + "/.gitlet/blobs/" + Utils.sha1(Utils.serialize(this)));
        Utils.writeContents(blobFile, blobByteArray);

    }
}
