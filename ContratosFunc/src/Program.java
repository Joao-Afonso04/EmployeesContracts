import java.util.Locale;
import java.util.Scanner;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {

    public static void main(String [] args) throws ParseException{
        Locale.setDefault(Locale.US);

        try(Scanner sc = new Scanner(System.in)){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            System.out.print("Enter department`s name: ");
            String nameDepart = sc.nextLine();


            System.out.println("Enter worker data");
            System.out.print("Name: ");
            
            String empName = sc.nextLine();

            System.out.print("Level: ");
            String empLevel = sc.nextLine();

            System.out.print("Base salary: ");
            Double baseSalary = sc.nextDouble();


            Worker emp = new Worker(empName, WorkerLevell.valueOf(empLevel), baseSalary, new Departament(nameDepart));

            System.out.print("How many contracts to this worker: ");
            int quantContracts = sc.nextInt();


            for(int i = 0 ; i < quantContracts ; i++) {

                System.out.print("Enter contract #" + (i+1) + " data: ");
                Date contractDate = sdf.parse(sc.next());

                System.out.print("Value per hour: ");
                Double valuePHour = sc.nextDouble();

                System.out.print("Duration (hours): ");
                Integer durationHour = sc.nextInt();

                HourContract hc = new HourContract(contractDate, valuePHour, durationHour);

                emp.addContract(hc);

            }

            System.out.println("Enter month and year to cauculate income (MM/YYYY) ");

            String monthAndYear = sc.next();

            int month = Integer.parseInt(monthAndYear.substring(0, 2));
            int year = Integer.parseInt(monthAndYear.substring(3));

            System.out.println("Name: " + emp.getName());
            System.out.println("Department: " + emp.getDepartament().getName());
            System.out.println("Income for " + monthAndYear + ":" + String.format("%.2f%n",  emp.income(year, month)));
        }
    }
}
