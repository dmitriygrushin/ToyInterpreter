package tests;

import com.company.Lexer;
import com.company.Parser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    public void test1() throws IOException {
        String code = new String(Files.readAllBytes(Paths.get("C:\\Users\\dmitr\\Desktop\\ToyInterpreter\\src\\tests\\cases\\test1.txt")));
        Throwable exception = assertThrows(Exception.class, () -> interpreter(code));
        String expected = "Lexer: Grammar Error | Literals can NOT have superfluous zeros";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test2() throws Exception {
        String code = new String(Files.readAllBytes(Paths.get("C:\\Users\\dmitr\\Desktop\\ToyInterpreter\\src\\tests\\cases\\test2.txt")));
        String expected = "x_2 = 0";
        String result = interpreter(code);
        assertEquals(expected, result);
    }

    @Test
    public void test3() throws IOException {
        String code = new String(Files.readAllBytes(Paths.get("C:\\Users\\dmitr\\Desktop\\ToyInterpreter\\src\\tests\\cases\\test3.txt")));
        Throwable exception = assertThrows(Exception.class, () -> interpreter(code));
        String expected = "Syntax Error";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test4() throws Exception {
        String code = new String(Files.readAllBytes(Paths.get("C:\\Users\\dmitr\\Desktop\\ToyInterpreter\\src\\tests\\cases\\test4.txt")));
        String expected = "x = 1\ny = 2\nz = 3\n";
        String result = interpreter(code);
        assertEquals(expected, result);
    }

    private String interpreter(String code) throws Exception {
        Lexer lexer = new Lexer(code);
        Parser parser = new Parser(lexer);
        parser.program();
        return parser.printTable().toString();
    }
}