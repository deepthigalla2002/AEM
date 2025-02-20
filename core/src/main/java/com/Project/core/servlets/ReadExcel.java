package com.Project.core.servlets;

import org.apache.poi.ss.usermodel.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component(service = Servlet.class, property = {
        "sling.servlet.methods=" + HttpConstants.METHOD_POST,
        "sling.servlet.paths=/bin/readexcel"
})
public class ReadExcel extends SlingAllMethodsServlet {

    private boolean isValidAssetPath(String assetPath) {
        return assetPath.startsWith("/content/dam/");
    }

    private Set<String> getRequiredFields(Sheet sheet) {
        Set<String> requiredFields = new HashSet<>();
        // The first row contains the header names
        Row headerRow = sheet.getRow(0);
        if (headerRow != null) {
            for (Cell cell : headerRow) {
                String headerName = cell.getStringCellValue().trim();
                // Add your logic to identify required fields based on header names.
                if (headerName.contains("*")) { // Assuming required fields are marked with asterisk (*)
                    requiredFields.add(headerName.replace("*", "").trim());
                }
            }
        }
        return requiredFields;
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        StringBuilder responseText = new StringBuilder();

        try (InputStream inputStream = request.getInputStream()) {
            if (inputStream == null || inputStream.available() <= 0) {
                responseText.append("No file uploaded or file is empty.\n");
                response.getWriter().write(responseText.toString());
                return;
            }

            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            Set<String> requiredFields = getRequiredFields(sheet);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell assetPathCell = row.getCell(0);
                if (assetPathCell == null || assetPathCell.getCellTypeEnum() != CellType.STRING) {
                    responseText.append("Invalid row: Asset path is missing or not a string.\n");
                    continue;
                }

                String assetPath = assetPathCell.getStringCellValue();
                if (!isValidAssetPath(assetPath)) {
                    responseText.append(assetPath).append(": Invalid asset path.\n");
                    continue;
                }

                boolean allRequiredPresent = requiredFields.stream().allMatch(field -> {
                    for (Cell cell : row) {
                        if (cell.getCellTypeEnum() == CellType.STRING
                                && cell.getStringCellValue().equalsIgnoreCase(field)) {
                            return true;
                        }
                    }
                    return false;
                });

                if (allRequiredPresent) {
                    responseText.append(assetPath).append(": Thanks for providing all the mandatory fields.\n");
                } else {
                    responseText.append(assetPath).append(": One or more required fields are missing. Kindly check.\n");
                }
            }

            workbook.close();
        } catch (Exception e) {
            responseText.append("Error processing the uploaded file: ").append(e.getMessage()).append("\n");
        }

        response.getWriter().write(responseText.toString());
    }
}
