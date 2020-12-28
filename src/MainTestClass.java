import org.junit.Test;

public class MainTestClass {
    @Test
    public void testGetLocalNumber(){
        MainClass obj = new MainClass();
        assert obj.getLocalNumber() == 14 : "Ожидалось число 14";
    }

    @Test
    public void testGetClassNumber(){
        MainClass obj = new MainClass();
        int number = obj.getClassNumber();
        assert number > 45 : "Номер класса " + number + " не больше 45";
    }

    @Test
    public void testGetClassString(){
        MainClass obj = new MainClass();
        String str = obj.getClassString();
        assert str.contains("Hello") | str.contains("hello"): "Не найдено hello/Hello";
    }

}
