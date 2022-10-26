package task.c482;

/**
 * CLASS DESCRIPTION: This class describes the Outsourced methods and extends Part.
 */
public class Outsourced extends Part {

    private String companyName;

    /**
     * METHOD DESCRIPTION: Describes the InHouse arguments.

     * RUNTIME ERROR: I encountered a runtime error when I forgot to apply "super" to the arguments so Part could
     * not be applied.
    */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * METHOD DESCRIPTION: Getter for the company name.
    */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * METHOD DESCRIPTION: Setter for the company name.
    */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
