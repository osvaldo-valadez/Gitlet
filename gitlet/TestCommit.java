package gitlet;

import ucb.junit.textui;
import org.junit.Test;

import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class TestCommit {
    @Test
    public void printtime() {
        DateTimeFormatter plot = DateTimeFormatter.ISO_DATE_TIME;
        String dateTime = plot.toString();
        System.out.println(dateTime);

        assertEquals(true, 1==1);
    }

}
