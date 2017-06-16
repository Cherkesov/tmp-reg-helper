package com.gfb.tpl_reg_helper;

import com.gfb.tpl_reg_helper.domain.IdentityDocument;
import com.gfb.tpl_reg_helper.domain.Person;
import com.gfb.tpl_reg_helper.domain.PlaceOfBirth;
import com.gfb.tpl_reg_helper.domain.RightToStayConfirmingDocument;
import com.gfb.tpl_reg_helper.ui.DateBlock;
import com.gfb.tpl_reg_helper.ui.EnumSelectBlock;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by goforbroke on 12.06.17.
 */
public class Application {
    public static void main(String[] args) throws IOException, ParseException {

        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("---------- ---------- ---------- ----------");
        System.out.println("Hello, " + System.getProperty("user.name") + "! =))");
        System.out.println("---------- ---------- ---------- ----------\n\n");

        System.out.println("Please, enter information about foreign citizen:\n");

        Person citizen = new Person();


        System.out.print("Last name: ");
        citizen.setLastName(reader.readLine());

        System.out.print("First name: ");
        citizen.setFirstName(reader.readLine());

        System.out.print("Nationality: ");
        citizen.setNationality(reader.readLine());

        Date birthday = new DateBlock().apply(reader, "Birthday", null);
        citizen.setBirthday(birthday);

        Person.Genders gender = new EnumSelectBlock<Person.Genders>(Person.Genders.class.getEnumConstants())
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
                    new EnumSelectBlock<IdentityDocument.Types>(IdentityDocument.Types.class.getEnumConstants())
                            .apply(reader, "  Type", document.getType());
            document.setType(type);

            System.out.print("  Series: ");
            document.setSeries(reader.readLine());

            System.out.print("  Number: ");
            document.setIdentifier(reader.readLine());

            Date date1 = new DateBlock().apply(reader, "  Start date", null);
            document.setDateOfIssueDate(date1);

            Date date2 = new DateBlock().apply(reader, "  Stop date", null);
            document.setValidityTillDate(date2);

            citizen.setIdentityDocument(document);
            citizen.setPlaceOfBirth(placeOfBirth);
        }

        System.out.println("Identity document (like a passport): ");
        {
            RightToStayConfirmingDocument document = new RightToStayConfirmingDocument();

            RightToStayConfirmingDocument.Types type =
                    new EnumSelectBlock<RightToStayConfirmingDocument.Types>(RightToStayConfirmingDocument.Types.class.getEnumConstants())
                    .apply(reader, "  Type", document.getType());
            document.setType(type);

            System.out.print("  Series: ");
            document.setSeries(reader.readLine());

            System.out.print("  Number: ");
            document.setIdentifier(reader.readLine());

            Date date1 = new DateBlock().apply(reader, "  Start date", null);
            document.setDateOfIssueDate(date1);

            Date date2 = new DateBlock().apply(reader, "  Stop date", null);
            document.setValidityTillDate(date2);

            citizen.setStayConfirmingDocument(document);
        }

        Person.Purposes purpose =
                new EnumSelectBlock<Person.Purposes>(Person.Purposes.class.getEnumConstants())
                        .apply(reader, "  Purpose", citizen.getPurpose());
        citizen.setPurpose(purpose);

        //

        String appDir = System.getProperty("user.home") + "/tpl-reg-helper";
        String filenameSuffix = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date()) + "-" + citizen.getLastName() + "-" + citizen.getFirstName();
        File file = cloneBlankDoc(appDir, "blank-form.xls", filenameSuffix);
        HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = book.getSheet("стр.1");

        writeToDoc(sheet, new CellReference("W13"), citizen.getLastName());
        writeToDoc(sheet, new CellReference("W15"), citizen.getFirstName());
        writeToDoc(sheet, new CellReference("AA18"), citizen.getNationality());

        writeDateToDoc(sheet, citizen.getBirthday(), "AE21", "AU21", "BG21");

        writeToDoc(sheet,
                new CellReference(
                        citizen.getGender() == Person.Genders.MALE
                                ? "CY21"
                                : "DS21"
                ),
                "х");

        writeToDoc(sheet, new CellReference("AE24"), citizen.getPlaceOfBirth().getCounty());
        writeToDoc(sheet, new CellReference("AE27"), citizen.getPlaceOfBirth().getCity());

        IdentityDocument identityDocument = citizen.getIdentityDocument();
        ;
        IdentityDocument.Types type = identityDocument.getType();

        String idDocumentCellValue = "";
        switch (identityDocument.getType()) {
            case PASSPORT:
                idDocumentCellValue = "паспорт";
                break;
//            case RESIDENCE_PERMIT:
//                roscDocumentCellName = "AY37";
//                break;
//            case TMP_RESIDENCE_PERMIT:
//                roscDocumentCellName = "CM37";
//                break;
        }
        writeToDoc(sheet, new CellReference("BC30"), idDocumentCellValue);
        writeToDoc(sheet, new CellReference("DC30"), identityDocument.getSeries());
        writeToDoc(sheet, new CellReference("DW30"), identityDocument.getIdentifier());

        writeDateToDoc(sheet, identityDocument.getDateOfIssueDate(), "AA32", "AQ32", "BC32");
        writeDateToDoc(sheet, identityDocument.getValidityTillDate(), "CM32", "DC32", "DO32");


        RightToStayConfirmingDocument stayConfirmingDocument = citizen.getStayConfirmingDocument();
        String roscDocumentCellName = "";
        switch (stayConfirmingDocument.getType()) {
            case VISA:
                roscDocumentCellName = "W37";
                break;
            case RESIDENCE_PERMIT:
                roscDocumentCellName = "AY37";
                break;
            case TMP_RESIDENCE_PERMIT:
                roscDocumentCellName = "CM37";
                break;
        }
        writeToDoc(sheet, new CellReference(roscDocumentCellName), "х");

        writeToDoc(sheet, new CellReference("DC37"), stayConfirmingDocument.getSeries());
        writeToDoc(sheet, new CellReference("DW37"), stayConfirmingDocument.getIdentifier());

        writeDateToDoc(sheet, stayConfirmingDocument.getDateOfIssueDate(), "AA40", "AQ40", "BC40");
        writeDateToDoc(sheet, stayConfirmingDocument.getValidityTillDate(), "CM40", "DC40", "DO40");


        String purposeCellName = "";
        switch (citizen.getPurpose()) {
            case BUSINESS_TRIP:
                purposeCellName = "AM43";
                break;
            case TOURISM:
                purposeCellName = "AY43";
                break;
            case BUSINESS:
                purposeCellName = "BO43";
                break;
            case LEARNING:
                purposeCellName = "CA43";
                break;
            case JOB:
                purposeCellName = "CM43";
                break;
            case PRIVATE:
                purposeCellName = "CY43";
                break;
            case TRANSIT:
                purposeCellName = "DK43";
                break;
            case HUMANITARIAN:
                purposeCellName = "EE43";
                break;
            case ANOTHER:
                purposeCellName = "EQ43";
                break;
        }
        writeToDoc(sheet, new CellReference(purposeCellName), "х");


        // Записываем всё в файл
        book.write(new FileOutputStream(file));
        book.close();

        Desktop.getDesktop().open(new File(appDir));

        System.out.println("\n\nSee you later! =))");
    }

    private static void writeDateToDoc(HSSFSheet sheet, Date data, String daysPos, String monthPos, String yearPos) {
        writeToDoc(sheet, new CellReference(daysPos), new SimpleDateFormat("dd").format(data));
        writeToDoc(sheet, new CellReference(monthPos), new SimpleDateFormat("MM").format(data));
        writeToDoc(sheet, new CellReference(yearPos), new SimpleDateFormat("yyyy").format(data));
    }

    private static void writeToDoc(HSSFSheet sheet, CellReference start, String data) {

        Row row = sheet.getRow(start.getRow());

        int offset = 0, step = 4;

        while (offset < data.length()) {
            int cellPos = start.getCol() + offset * step;
            Cell cell = row.getCell(cellPos);

            if (null == cell) cell = row.createCell(cellPos);
            cell.setCellType(Cell.CELL_TYPE_STRING);

            cell.setCellValue(
                    data.substring(offset, offset + 1)
            );

            offset++;
        }
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
