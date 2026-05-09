package bg.tu_varna.sit.cs.f24621858.image.exceptions;

/**
 * Functional interface for validating a single integer value read from a file
 * stream and throwing a typed exception when the value signals an error
 * condition.
 *
 * @author Dmitro Dashchenko
 *
 * @see EOFExceptionChecker
 */
public interface Checker {

    /**
     * Validates {@code fileElement} and throws an appropriate exception if the
     * value represents an error condition.
     *
     * @param fileElement the raw integer value returned by a stream read call
     * @throws Exception a subclass of {@link Exception} specific to the
     * implemented check (e.g. {@link java.io.EOFException})
     */
    void check(int fileElement) throws Exception;
}
