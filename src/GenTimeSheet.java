import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;


public class GenTimeSheet {

    private String timeIntervalSelected;
    private int projectIDSeleted;
    private int taskIDSelected;
    private int employeeStatus;     /* 0 if not manager, 1 if manager */
    private int employeeID;

    private Date start;
    private Date end;

    /**
     *
     * @param status (dtermines permission level)
     * @param employeeNumber
     * @param projID
     * @param taskID
     * @param timeInt
     */

    public GenTimeSheet(String status, int employeeNumber, int projID, int taskID, String timeInt){
        if(status.equals("Manager"))
            this.employeeStatus = 1;
        else
            this.employeeStatus = 0;

        this.timeIntervalSelected = timeInt;
        this.employeeID = employeeNumber;
        this.projectIDSeleted = projID;
        this.taskIDSelected = taskID;

        this.start = null;
        this.end = null;
    }

    /**
     *
     * @return creatDevReport()
     */
    public StringBuilder createReport(){
        this.intervalToDates();

        if(employeeStatus > 0)                      //checks which report to return
            return this.createManagerReport();
        else
            return this.createDevReport();
    }

    /**
     *
     * @return
     */
    private StringBuilder createDevReport(){
        DB_Reader reader = new DB_Reader();        //looks at database
        ArrayList<EmployeeLog> recs = reader.genEmployeeTimeSheet(employeeID, projectIDSeleted, taskIDSelected, start, end);

        return this.listToBuilder(recs);
    }

    /**
     *
     * @return
     */
    private StringBuilder createManagerReport(){
        DB_Reader reader = new DB_Reader();
        ArrayList<EmployeeLog> recs = reader.genManagerTimeSheet(employeeID, projectIDSeleted, taskIDSelected, start, end);

        return this.listToBuilder(recs);
    }

    /**
     *
     * @param lis
     * @return
     */
    private StringBuilder listToBuilder(ArrayList<EmployeeLog> lis){        //makes a SB from ArrayList
        if(lis.size() > 0) {
            StringBuilder out = new StringBuilder();
            for (EmployeeLog el : lis) {
                out.append(el.printLog());
                out.append(System.getProperty("line.separator"));
            }

            return out;
        }else{
            return null;
        }
    }


    private void intervalToDates(){
        long intervalStartTime = System.currentTimeMillis();
        long intervalEndTime = 0;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        if(timeIntervalSelected.equals("Current Week")){            //determines what to display
            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
            intervalEndTime = cal.getTimeInMillis();
        }else if(timeIntervalSelected.equals("Current Month")){
            cal.set(Calendar.DAY_OF_MONTH, 1);
            intervalEndTime = cal.getTimeInMillis();
        }else{
            cal.set(Calendar.DAY_OF_YEAR, 1);
            intervalEndTime = cal.getTimeInMillis();
        }

        this.start = new Date(intervalStartTime);
        this.end = new Date(intervalEndTime);
    }
}