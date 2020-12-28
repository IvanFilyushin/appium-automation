import org.junit.Test;

public class MainTestClass {
    @Test
    public void testGetLocalNumber(){
        MainClass obj = new MainClass();
        assert obj.getLocalNumber() == 14 : "Ожидалось число 14";
    }
}
