package emersonlebleu.c482_project;
/**
 * Outsourced class extends the Part class
 */

/**
 *
 * @author Emerson Lebleu
 */
public class Outsourced extends Part{
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    public String getCompanyName(){
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
