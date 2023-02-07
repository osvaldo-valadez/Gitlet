package gitlet;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class Commit implements Serializable {
    private String message;
    private String date;
    private String parent;
    public HashMap<String,String> pair;
    private SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String hash;


    public String getDate() {
        return date;
    }
    public String getMessage() {
        return message;
    }

    public String getParent() {
        return parent;
    }

    public Commit(String message, String parent, HashMap<String,String> pair) {
        this.message = message;
        this.date = timestampFormat.format(new Date());
        this.parent = parent;
        this.pair = pair;
        this.hash = Utils.sha1(Utils.serialize(this));
        byte[] commitByteArray = Utils.serialize(this);
    }
    public Commit(String message, String date, String parent, HashMap<String,String> pair) {
        this.message = message;
        this.date = date;
        this.parent = parent;
        this.pair = pair;
        this.hash = Utils.sha1(Utils.serialize(this));
        byte[] commitByteArray = Utils.serialize(this);

    }
}

