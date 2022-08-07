package emersonlebleu.c482_project;

/**
 * InHouse class extends the Part class
 */

/**
 *
 * @author Emerson Lebleu
 */
public class InHouse extends Part{
    private int machineid;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineid){
        super(id, name, price, stock, min, max);
        this.machineid = machineid;
    }

    public int getMachineid(){
        return machineid;
    }

    public void setMachineid(int machineid) {
        this.machineid = machineid;
    }
}
