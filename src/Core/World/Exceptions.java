package Core.World;

public class Exceptions {

    public static class IllegalValueException extends RuntimeException {

        public IllegalValueException(String message) {
            super(message);
        }
    }

}
