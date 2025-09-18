package universe.core.servlets;

import com.day.cq.dam.api.Asset;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component(service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/universe/readExcel",
                "sling.servlet.methods=GET",})

public class ReadExcelServlet extends SlingAllMethodsServlet {

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException, ServletException {

        String filePath = "/content/dam/universe-project/xlxs-files/dam-file.xlsx";

        ResourceResolver resourceResolver = request.getResourceResolver();
        Resource resource = resourceResolver.getResource(filePath);

        List<Map<Object, Object>> dataList = new ArrayList<>();

        response.setContentType("application/json");

        if (resource == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("File not found");
        }

        if (resource != null) {
            try (InputStream inputStream = Objects.requireNonNull(resource.adaptTo(Asset.class)).getOriginal().getStream();
                 Workbook workbook = new XSSFWorkbook(inputStream)) {

                Sheet sheet = workbook.getSheetAt(0); // getSheetAt - A single sheet (tab) within an Excel file.
                Row sheetHeader = sheet.getRow(0);  // assuming the first row is the header row - extracting the number of header

                int columnCount = sheetHeader.getLastCellNum();
                int rowCount = sheet.getPhysicalNumberOfRows();

                // Loop through rows starting from 1 (skip header)
                for (int i = 1; i < rowCount; i++) {
                    Row sheetRows = sheet.getRow(i);
                    if (sheetRows == null) continue;

                    Map<Object, Object> sheetMap = new LinkedHashMap<>();

                    for (int j = 0; j < columnCount; j++) {
                        Cell headerCellValue = sheetHeader.getCell(j);
                        Cell sheetRowsCellValue = sheetRows.getCell(j);

                        if (headerCellValue == null && sheetRowsCellValue == null) continue;

                        if (headerCellValue != null && sheetRowsCellValue != null) {
                            Object key = cellValueMethod(headerCellValue);
                            Object value = cellValueMethod(sheetRowsCellValue);

                            sheetMap.put(key, value);
                        }
                    }
                    dataList.add(sheetMap);
                }

                // JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(dataList);
                response.getWriter().write(jsonResponse);

            } catch (Exception e) {
                response.getWriter().write("Exception handled");
            }
        }
    }

        // Check the cell type to handle numeric and string values
        private Object cellValueMethod(Cell cell) {
            if (cell == null) return null;

            if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cell.getCellType() == CellType.NUMERIC) {
                return cell.getNumericCellValue();
            } else if (cell.getCellType() == CellType.BOOLEAN) {
                return cell.getBooleanCellValue();
            } else if (cell.getCellType() == CellType.FORMULA) {
                return cell.getCellFormula();
            } return "no value";
        }
}