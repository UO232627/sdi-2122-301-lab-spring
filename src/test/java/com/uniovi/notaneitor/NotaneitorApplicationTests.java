package com.uniovi.notaneitor;

import com.uniovi.notaneitor.pageobjects.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotaneitorApplicationTests {

    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    static String Geckodriver = "D:\\Universidad\\Sistemas Distribuidos e Internet\\Practicas\\Sesion 6\\sesion06\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";

    // Común a Windows y a MACOSX
    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090";

    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp(){
        driver.navigate().to(URL);
    }

    //Después de cada prueba se borran las cookies del navegador
    @AfterEach
    public void tearDown(){
        driver.manage().deleteAllCookies();
    }

    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {}

    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
        //Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }

    @Test
    @Order(1)
    void PR01A(){
        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
    }

    @Test
    @Order(2)
    void PR01B(){
        List<WebElement> welcomeMessageElement = PO_HomeView.getWelcomeMessageText(driver, PO_Properties.getSPANISH());
        Assertions.assertEquals(welcomeMessageElement.get(0).getText(), PO_HomeView.getP().getString("welcome.message", PO_Properties.getSPANISH()));
    }

    //PR02. Opción de navegación. Pinchar en el enlace Registro en la página home
    @Test
    @Order(3)
    public void PR02() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
    }

    //PR03. Opción de navegación. Pinchar en el enlace Identifícate en la página home
    @Test
    @Order(4)
    public void PR03() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
    }

    //PR04. Opción de navegación. Cambio de idioma de Español a Inglés y vuelta a Español
    @Test
    @Order(5)
    public void PR04() {
        PO_HomeView.checkChangeLanguage(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
    }

    //PR05. Prueba del formulario de registro. registro con datos correctos
    @Test
    @Order(6)
    public void PR05() {
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");

        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "77777778A", "Josefo", "Perez", "77777", "77777");

        //Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06A. Prueba del formulario de registro. DNI repetido en la BD
    // Propiedad: Error.signup.dni.duplicate
    @Test
    @Order(7)
    public void PR06A() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990A", "Josefo", "Perez", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.dni.duplicate", PO_Properties.getSPANISH() );

        String checkText = PO_HomeView.getP().getString("Error.signup.dni.duplicate", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }

    //PR06B. Prueba del formulario de registro. Nombre corto.
    // Propiedad: Error.signup.name.length
    @Test
    @Order(8)
    public void PR06B() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Jose", "Perez", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH() );

        String checkText = PO_HomeView.getP().getString("Error.signup.name.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }

    //PR06C. Prueba del formulario de registro. Contraseñas distintas.
    // Propiedad: Error.signup.passwordConfirm.coincidence
    @Test
    @Order(9)
    public void PR06C() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Josefo", "Perez", "77777", "88888");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH() );

        //Comprobamos el error de contraseñas distintas
        String checkText = PO_HomeView.getP().getString("Error.signup.name.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }

    //PR06D. Prueba del formulario de registro. DNI corto.
    // Propiedad: Error.signup.dni.length
    @Test
    @Order(10)
    public void PR06D() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "999", "Josefo", "Perez", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.dni.length", PO_Properties.getSPANISH() );

        //Comprobamos el error de DNI corto
        String checkText = PO_HomeView.getP().getString("Error.signup.dni.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }

    //PR06E. Prueba del formulario de registro. Contraseña corta.
    // Propiedad: Error.signup.password.length
    @Test
    @Order(11)
    public void PR06E() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Josefo", "Perez", "1", "1");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH() );

        //Comprobamos el error de contraseñas distintas
        String checkText = PO_HomeView.getP().getString("Error.signup.password.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }

    //PR06F. Prueba del formulario de registro. DNI vacío.
    // Propiedad: Error.signup.lastname
    @Test
    @Order(12)
    public void PR06F() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Josefo", "Diaz", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH() );

        //Comprobamos el error de contraseñas distintas
        String checkText = PO_HomeView.getP().getString("Error.signup.lastName.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }

    @Test
    @Order(13)
    public void PR07() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");

        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");

        //Comprobamos que entramos en la pagina privada de Alumno
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(14)
    public void PR08() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");

        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999993D", "123456");

        //Comprobamos que entramos en la pagina privada de Alumno
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(15)
    public void PR09() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");

        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999988F", "123456");

        //Comprobamos que entramos en la pagina privada de Alumno
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    /**
    @Test
    @Order(16)
    public void PR10() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");

        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999990A", "111111");

        //Comprobamos que entramos en la pagina privada de Alumno
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }
    */

    /**
    @Test
    @Order(17)
    public void PR11() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");

        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");

        //Comprobamos que entramos en la pagina privada de Alumno
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }
    */

}
