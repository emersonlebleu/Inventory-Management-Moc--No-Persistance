package emersonlebleu.c482_project;
/** Outsourced class extends the Part class. This class extends the part class by adding companyName
 * and a getter and setter method for this additional variable.
 * @author Emerson Lebleu */
public class Outsourced extends Part{
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** This method gets the companyName and returns it.
      @return the companyName which is a string*/
    public String getCompanyName(){
        return companyName;
    }

    /** This method takes a parameter and sets that as the company name.
      @param companyName sets the companyName */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
