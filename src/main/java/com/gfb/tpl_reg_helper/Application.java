package com.gfb.tpl_reg_helper;

import com.gfb.tpl_reg_helper.domain.*;
import com.gfb.tpl_reg_helper.ui.DateBlock;
import com.gfb.tpl_reg_helper.ui.EnumSelectBlock;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
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

public class Application {

    private static final SimpleDateFormat RUSSIAN_DATE_FORMAT = new SimpleDateFormat("ddMMyyyy");

    private static HSSFWorkbook book;
    private static HSSFCellStyle style;

    public static void main(String[] args) throws IOException, ParseException {

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

        String appDir = System.getProperty("user.home") + "/tpl-reg-helper";
        String filenameSuffix = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + "-" + citizen.getLastName() + "-" + citizen.getFirstName();
        File file = cloneBlankDoc(appDir, "blank-form.xls", filenameSuffix);
        book = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = book.getSheet("стр.1");

        writePersonalData(citizen, sheet);

        writeIdentityDocumentData(citizen, sheet);

        writeRightToStayDocumentData(citizen, sheet);

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

        writePeriodToStay(citizen, sheet);

        writeMigrationCard(citizen, sheet);

        // Записываем всё в файл
        book.write(new FileOutputStream(file));
        book.close();

//        Desktop.getDesktop().open(new File(appDir));

        System.out.println("\n\nSee you later! =))");
    }

    private static void writePersonalData(Person citizen, HSSFSheet sheet) {
        writeToDoc(sheet, new CellReference("W13"), citizen.getLastName());
        writeToDoc(sheet, new CellReference("W15"), citizen.getFirstName());
        writeToDoc(sheet, new CellReference("AA18"), citizen.getNationality());

        writeToDoc(sheet, new CellReference("W69"), citizen.getLastName());
        writeToDoc(sheet, new CellReference("W71"), citizen.getFirstName());
        writeToDoc(sheet, new CellReference("AA74"), citizen.getNationality());

        writeDateToDoc(sheet, citizen.getBirthday(), "AE21", "AU21", "BG21");
        writeDateToDoc(sheet, citizen.getBirthday(), "AE77", "AU77", "BG77");

        boolean isMale = citizen.getGender() == Person.Genders.MALE;
        writeToDoc(sheet, new CellReference(isMale ? "CY21" : "DS21"), "х");
        writeToDoc(sheet, new CellReference(isMale ? "DC77" : "DW77"), "х");

        writeToDoc(sheet, new CellReference("AE24"), citizen.getPlaceOfBirth().getCounty());
        writeToDoc(sheet, new CellReference("AE27"), citizen.getPlaceOfBirth().getCity());
    }

    private static void writeIdentityDocumentData(Person citizen, HSSFSheet sheet) {
        IdentityDocument identityDocument = citizen.getIdentityDocument();

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

        writeToDoc(sheet, new CellReference("BC80"), idDocumentCellValue);
        writeToDoc(sheet, new CellReference("DC80"), identityDocument.getSeries());
        writeToDoc(sheet, new CellReference("DW80"), identityDocument.getIdentifier());

        writeDateToDoc(sheet, identityDocument.getDateOfIssueDate(), "AA32", "AQ32", "BC32");
        writeDateToDoc(sheet, identityDocument.getValidityTillDate(), "CM32", "DC32", "DO32");
    }

    private static void writeRightToStayDocumentData(Person citizen, HSSFSheet sheet) {
        RightToStayConfirmingDocument stayConfirmingDocument = citizen.getStayConfirmingDocument();
        String roscDocumentCellName = null;
        switch (stayConfirmingDocument.getType()) {
            case NONE:
                return;
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
    }

    private static void writePeriodToStay(Person citizen, HSSFSheet sheet) {
        writeDateToDoc(sheet, citizen.getArrivalDate(), "AI47", "AY47", "BK47");
        writeDateToDoc(sheet, citizen.getDurationOfStay(), "DO47", "EE47", "EQ47");
        writeDateToDoc(sheet, citizen.getDurationOfStay(), "AQ95", "BG95", "BS95");
    }

    private static void writeMigrationCard(Person citizen, HSSFSheet sheet) {
        writeToDoc(sheet, new CellReference("AQ49"), citizen.getMigrationCard().getSeries());
        writeToDoc(sheet, new CellReference("BK49"), citizen.getMigrationCard().getNumber());
    }

    //

    private static void writeDateToDoc(HSSFSheet sheet, Date date, String daysPos, String monthPos, String yearPos) {
        if (null == date)
            return;
        writeToDoc(sheet, new CellReference(daysPos), new SimpleDateFormat("dd").format(date));
        writeToDoc(sheet, new CellReference(monthPos), new SimpleDateFormat("MM").format(date));
        writeToDoc(sheet, new CellReference(yearPos), new SimpleDateFormat("yyyy").format(date));
    }

    private static void writeToDoc(HSSFSheet sheet, CellReference start, String data) {
        // TODO: set font style
        initFontStyle();

        Row row = sheet.getRow(start.getRow());

        int offset = 0, step = 4;

        while (offset < data.length()) {
            int cellPos = start.getCol() + offset * step;
            Cell cell = row.getCell(cellPos);
            if (null == cell) cell = row.createCell(cellPos);

            cell.setCellStyle(style);
            cell.setCellType(Cell.CELL_TYPE_STRING);

            cell.setCellValue(
                    data.substring(offset, offset + 1)
            );

            offset++;
        }
    }

    private static void initFontStyle() {
        // TODO: move to external config

        if (null == style) {
            HSSFFont font = book.createFont();
            font.setFontHeightInPoints((short) 8);
            font.setFontName("Arial Narrow");
            font.setBold(true);
            style = book.createCellStyle();
            style.setFont(font);

//            style.setBorderTop((short) 1);
//            style.setBorderRight((short) 1);
//            style.setBorderBottom((short) 1);
//            style.setBorderLeft((short) 1);
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
