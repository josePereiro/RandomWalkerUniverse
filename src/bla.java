import org.junit.jupiter.api.function.Executable;

public class bla {

    public static void main(String[] args) throws Throwable {

        executeExecutable(() -> System.out.println("Hi"));

    }

    private static void executeExecutable(Executable executable) throws Throwable {
        executable.execute();
    }

}
