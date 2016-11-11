
public class Employee {

    private String firstName;
    private String lastName;
    private int employeeNumber;

    /**
     *
     * @param fn
     * @param ln
     * @param num
     */

    public Employee(String fn, String ln, int num){
        this.firstName = fn;
        this.lastName = ln;
        this.employeeNumber = num;
    }

    public int getEmployeeNumber(){
        return this.employeeNumber;
    }           //retrieves employee # and returns it

    public String getName(){
        return (lastName + ", " + firstName);
    }        //retrieves name and returns them
}