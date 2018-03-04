package com.racoonberus.tplRegHelper;

import com.racoonberus.tplRegHelper.domain.IdentityDocument;
import com.racoonberus.tplRegHelper.domain.Person;
import com.racoonberus.tplRegHelper.domain.RightToStayConfirmingDocument;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class XlsDecorator {

    private Workbook book;
    private CellStyle style;

    public XlsDecorator(Workbook book) {
        this.book = book;
        initDefaultFontStyle();
    }

    private void initDefaultFontStyle() {
        Font font = book.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setFontName("Arial Narrow");
        font.setBold(true);
        style = book.createCellStyle();
        style.setFont(font);

//        style.setBorderTop((short) 1);
//        style.setBorderRight((short) 1);
//        style.setBorderBottom((short) 1);
//        style.setBorderLeft((short) 1);
    }

    public void write(Person person) {
        Sheet sheet = book.getSheetAt(0);

        writePersonalData(person, sheet);

        writeIdentityDocumentData(person, sheet);

        writeRightToStayDocumentData(person, sheet);

        String purposeCellName = "";
        switch (person.getPurpose()) {
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

        writePeriodToStay(person, sheet);

        writeMigrationCard(person, sheet);
    }


    private void writePersonalData(Person citizen, Sheet sheet) {
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

    private void writeIdentityDocumentData(Person citizen, Sheet sheet) {
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

    private void writeRightToStayDocumentData(Person citizen, Sheet sheet) {
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

    private void writePeriodToStay(Person citizen, Sheet sheet) {
        writeDateToDoc(sheet, citizen.getArrivalDate(), "AI47", "AY47", "BK47");
        writeDateToDoc(sheet, citizen.getDurationOfStay(), "DO47", "EE47", "EQ47");
        writeDateToDoc(sheet, citizen.getDurationOfStay(), "AQ98", "BG98", "BS98");
    }

    private void writeMigrationCard(Person citizen, Sheet sheet) {
        writeToDoc(sheet, new CellReference("AQ49"), citizen.getMigrationCard().getSeries());
        writeToDoc(sheet, new CellReference("BK49"), citizen.getMigrationCard().getNumber());
    }

    private void writeDateToDoc(Sheet sheet, Date date, String daysPos, String monthPos, String yearPos) {
        if (null == date)
            return;
        writeToDoc(sheet, new CellReference(daysPos), new SimpleDateFormat("dd").format(date));
        writeToDoc(sheet, new CellReference(monthPos), new SimpleDateFormat("MM").format(date));
        writeToDoc(sheet, new CellReference(yearPos), new SimpleDateFormat("yyyy").format(date));
    }

    private void writeToDoc(Sheet sheet, CellReference start, String data) {

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
}
