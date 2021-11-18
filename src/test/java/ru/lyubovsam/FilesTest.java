package ru.lyubovsam;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class FilesTest {
    @Test
    @DisplayName("Загрузка файла по абсолютному пути")
    void uploadFileTest(){
        open("https://dropmefiles.com/");
        File exampleFile = new File("/Users/admin/IdeaProjects/hw7-workWithFiles/src/test/resources/example.txt");
        $("input[type='file']").uploadFile(exampleFile);
        $("[class='expand']").click();
        $("[id = upfiles]").shouldHave(text("example.txt"));
    }
    @Test
    @DisplayName("Загрузка файла по относительному пути")
    void uploadFileTestFromClassPath(){
        open("https://dropmefiles.com/");
        $("input[type='file']").uploadFromClasspath("example.txt");
        $("[class='expand']").click();
        $("[id = upfiles]").shouldHave(text("example.txt"));
    }

    @Test
    @DisplayName("Скачивание файла .txt и проверка его содержимого")
    void downloadSimpleFileTest() throws IOException {
        open("https://github.com/allure-framework/allure2/blob/master/README.md");
        File download = $("#raw-url").download();
        String fileContent = IOUtils.toString(new FileReader(download));
        Assertions.assertTrue(fileContent.contains("Allure Framework is a flexible"));
    }

    @Test
    @DisplayName("Скачивание PDF файла и проверка его содержимого")
    void downloadPdfFileTest() throws IOException {
        open("https://digis.ru/support/biblio/regulations_and_guidelines/");
        File pdf = $("[class='ico ico-download']").download();
        PDF parsedPdf = new PDF(pdf);
        Assertions.assertEquals(108, parsedPdf.numberOfPages);
    }

    @Test
    @DisplayName("Скачивание XLS файла и проверка его содержимого")
    void downloadXlsFileTest() throws IOException {
        open("https://nadezhda-nv.ru/docs/price.php");
        File xls = $("[class='btn btn-default']").download();
        XLS parsedXls = new XLS(xls);
        parsedXls.excel
                .getSheetAt(0)
                .getRow(1)
                .getCell(1)
                .getStringCellValue();
        Assertions.assertTrue(true);
    }
}
