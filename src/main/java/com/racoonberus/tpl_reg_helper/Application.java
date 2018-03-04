package com.racoonberus.tpl_reg_helper;

import com.racoonberus.tpl_reg_helper.ui.DateBlock;
import com.racoonberus.tpl_reg_helper.ui.EnumSelectBlock;
import com.racoonberus.tpl_reg_helper.domain.*;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Application {

    private static final SimpleDateFormat RUSSIAN_DATE_FORMAT = new SimpleDateFormat("ddMMyyyy");

//    private static HSSFWorkbook book;


    public static void main(String[] args) throws IOException, ParseException {

        File proto = new File(Application.class.getResource("/blank.xlsx").getFile());


        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("---------- ---------- ---------- ----------");
        System.out.println("Hello, " + System.getProperty("user.name") + "! =))");
        System.out.println("---------- ---------- ---------- ----------\n\n");

        System.out.println("Please, enter information about foreign citizen:\n");

        Person citizen = new Person();


        System.out.print("Last name: ");
        citizen.setLastName(reader.readLine());

        System.out.print("First (and second if exists) name: ");
        citizen.setFirstName(reader.readLine());

        System.out.print("Nationality: ");
        citizen.setNationality(reader.readLine());

        Date birthday = new DateBlock(RUSSIAN_DATE_FORMAT)
                .apply(reader, "Birthday", null);
        citizen.setBirthday(birthday);

        Person.Genders gender = new EnumSelectBlock<>(Person.Genders.class.getEnumConstants())
                .apply(reader, "Gender", citizen.getGender());
        citizen.setGender(gender);

        PlaceOfBirth placeOfBirth = new PlaceOfBirth();
        System.out.print("Birth country: ");
        placeOfBirth.setCounty(reader.readLine());
        System.out.print("Birth city: ");
        placeOfBirth.setCity(reader.readLine());
        citizen.setPlaceOfBirth(placeOfBirth);

        System.out.println("Identity document (like a passport): ");
        {
            IdentityDocument document = new IdentityDocument();

            IdentityDocument.Types type =
                    new EnumSelectBlock<>(IdentityDocument.Types.class.getEnumConstants())
                            .apply(reader, "  Type", document.getType());
            document.setType(type);

            System.out.print("  Series: ");
            document.setSeries(reader.readLine());

            System.out.print("  Number: ");
            document.setIdentifier(reader.readLine());

            Date date1 = new DateBlock(RUSSIAN_DATE_FORMAT)
                    .apply(reader, "  Start date", null);
            document.setDateOfIssueDate(date1);

            Date date2 = new DateBlock(RUSSIAN_DATE_FORMAT)
                    .apply(reader, "  Stop date", null);
            document.setValidityTillDate(date2);

            citizen.setIdentityDocument(document);
            citizen.setPlaceOfBirth(placeOfBirth);
        }

        System.out.println("Right to stay confirming document (like a VISA): ");
        {
            RightToStayConfirmingDocument document = new RightToStayConfirmingDocument();

            RightToStayConfirmingDocument.Types type =
                    new EnumSelectBlock<>(RightToStayConfirmingDocument.Types.class.getEnumConstants())
                            .apply(reader, "  Type", document.getType());
            document.setType(type);

            if (document.getType() != RightToStayConfirmingDocument.Types.NONE) {
                System.out.print("  Series: ");
                document.setSeries(reader.readLine());

                System.out.print("  Number: ");
                document.setIdentifier(reader.readLine());

                Date date1 = new DateBlock(RUSSIAN_DATE_FORMAT)
                        .apply(reader, "  Start date", null);
                document.setDateOfIssueDate(date1);

                Date date2 = new DateBlock(RUSSIAN_DATE_FORMAT)
                        .apply(reader, "  Stop date", null);
                document.setValidityTillDate(date2);
            }

            citizen.setStayConfirmingDocument(document);
        }

        Person.Purposes purpose = new EnumSelectBlock<>(Person.Purposes.class.getEnumConstants())
                .apply(reader, "  Purpose", citizen.getPurpose());
        citizen.setPurpose(purpose);

        Date arrivalDate = new DateBlock(RUSSIAN_DATE_FORMAT)
                .apply(reader, "  Arrival date", new Date());
        citizen.setArrivalDate(arrivalDate);

        Date durationDate = new DateBlock(RUSSIAN_DATE_FORMAT)
                .apply(reader, "  Duration of stay", new Date());
        citizen.setDurationOfStay(durationDate);

        final MigrationCard migrationCard = new MigrationCard();
        System.out.println("Migration card: ");
        System.out.print("  Series: ");
        migrationCard.setSeries(reader.readLine());
        System.out.print("  Number: ");
        migrationCard.setNumber(reader.readLine());
        citizen.setMigrationCard(migrationCard);

        //

//        String appDir = System.getProperty("user.home") + "/tpl-reg-helper";
//        String filenameSuffix = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + "-" + citizen.getLastName() + "-" + citizen.getFirstName();
//        File file = cloneBlankDoc(appDir, "blank-form.xls", filenameSuffix);
//        book = new HSSFWorkbook(new FileInputStream(file));

        String outDir = System.getProperty("user.home") + "/tpl-reg-helper";
        String fn = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + "-" + citizen.getLastName() + "-" + citizen.getFirstName();
        File outFile = getBlankCopy(proto, outDir, fn);
        Workbook book = new XSSFWorkbook(new FileInputStream(outFile));

        new XlsDecorator(book).write(citizen);

        // Записываем всё в файл
        book.write(new FileOutputStream(outFile));
        book.close();

//        Desktop.getDesktop().open(new File(appDir));

        System.out.println("\n\nSee you later! =))");
    }

    private static File getBlankCopy(File proto, String distDir, String docFileName) throws IOException {
        File bfile = new File(distDir + "/" + docFileName + ".xls");

        InputStream inStream = new FileInputStream(proto);
        OutputStream outStream = new FileOutputStream(bfile);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inStream.read(buffer)) > 0) {
            outStream.write(buffer, 0, length);
        }

        inStream.close();
        outStream.close();

        return bfile;
    }

    private static File cloneBlankDoc(String appDir, String blankFilename, String resultFilename) throws IOException {
        InputStream inStream = null;
        OutputStream outStream = null;

        File afile = new File(appDir + "/" + blankFilename);
        File bfile = new File(appDir + "/" + resultFilename + ".xls");

        inStream = new FileInputStream(afile);
        outStream = new FileOutputStream(bfile);

        byte[] buffer = new byte[1024];

        int length;
        //copy the file content in bytes
        while ((length = inStream.read(buffer)) > 0) {
            outStream.write(buffer, 0, length);
        }

        inStream.close();
        outStream.close();

        System.out.println("File is copied successful!");

        return bfile;
    }
}
