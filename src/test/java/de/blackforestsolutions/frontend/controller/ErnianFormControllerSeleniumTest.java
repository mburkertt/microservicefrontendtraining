package de.blackforestsolutions.frontend.controller;


import de.blackforestsolutions.frontend.model.Ernian;
import de.blackforestsolutions.frontend.service.FileWriterReaderService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Matchers.any;

@RunWith(SpringRunner.class)
@WebMvcTest(ErnianFormController.class)
public class ErnianFormControllerSeleniumTest {

    @MockBean
    FileWriterReaderService fileWriterReaderService;
    @Autowired
    private WebDriver webDriver;
    @Value("${application.host}")
    String host = "http://localhost:8080";
    String BASEURL = host + "/ernian";

    @Test
    public void fill_in_form() {
        ArgumentCaptor<Ernian> captor = ArgumentCaptor.forClass(Ernian.class);

        this.webDriver.get(BASEURL);
        WebElement firstName = webDriver.findElement(By.id("firstName"));
        WebElement lastName = webDriver.findElement(By.id("lastName"));
        WebElement country = webDriver.findElement(By.id("country"));
        WebElement town = webDriver.findElement(By.id("town"));
        WebElement zip = webDriver.findElement(By.id("zip"));
        WebElement street = webDriver.findElement(By.id("street"));
        WebElement submit = webDriver.findElement(By.id("submit"));

        firstName.sendKeys("Marc");
        lastName.sendKeys("Lehmann");
        country.sendKeys("ch");
        town.sendKeys("Zuerich");
        zip.sendKeys("8090");
        street.sendKeys("Thurgauerstrasse 40");
        submit.submit();

        Mockito.verify(fileWriterReaderService).writeErnians(captor.capture());
        Mockito.verify(fileWriterReaderService, Mockito.times(1)).writeErnians(any());
        assertThat(captor.getValue()).extracting(
                Ernian::getFirstName,
                Ernian::getLastName,
                Ernian::getCountry,
                Ernian::getTown,
                Ernian::getZip,
                Ernian::getStreet)
                .containsExactly("Marc","Lehmann",new Locale("ch"),"Zuerich","8090","Thurgauerstrasse 40");
    }
}
