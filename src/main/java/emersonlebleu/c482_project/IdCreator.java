package emersonlebleu.c482_project;

public class IdCreator {
    static int currId = 0;
    public static int generate() {
        currId++;
        return currId;
    }
}
