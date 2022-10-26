package task.c482;

/**
 * CLASS DESCRIPTION: This class describes the InHouse methods and extends Part.
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * METHOD DESCRIPTION: Describes the InHouse arguments.
    */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * METHOD DESCRIPTION: MachineID getter.
    */
    public int getMachineId() {
        return machineId;
    }

    /**
     * METHOD DESCRIPTION: MachineID setter.
    */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
