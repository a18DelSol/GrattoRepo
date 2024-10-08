package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.item.ModelItem;
import com.a18delsol.grattorepo.model.stock.ModelStock;
import com.a18delsol.grattorepo.model.stock.ModelStockEntry;
import com.a18delsol.grattorepo.repository.item.RepositoryItem;
import com.a18delsol.grattorepo.repository.stock.RepositoryStock;
import com.a18delsol.grattorepo.repository.stock.RepositoryStockEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class ServiceStock {
    @Autowired RepositoryStock       repositoryStock;
    @Autowired RepositoryStockEntry  repositoryStockEntry;
    @Autowired RepositoryItem        repositoryItem;
    @Autowired ServiceHistory        serviceHistory;

    public ResponseEntity<ModelStock> stockGetOne(Integer stockID) {
        return new ResponseEntity<>(repositoryStock.findByIDAndEntityDeleteFalse(stockID).orElseThrow(RuntimeException::new), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelStock>> stockGetAll() {
        return new ResponseEntity<>(repositoryStock.findByEntityDeleteFalse(), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelStock>> stockFind(
            Optional<String> stockName) {
        return new ResponseEntity<>(repositoryStock.findStock(stockName), HttpStatus.OK);
    }

    public ResponseEntity<String> stockCreate(ModelStock stock) {
        repositoryStock.save(stock);
        serviceHistory.historyCreate(String.format("[Stock] Creación (%s)",
                stock.getStockName()
        ));

        return new ResponseEntity<>("Creation OK.", HttpStatus.OK);
    }

    public ResponseEntity<String> stockDelete(Integer stockID) {
        ModelStock stock = repositoryStock.findById(stockID).orElseThrow(RuntimeException::new);

        for (ModelStockEntry i : stock.getStockEntry()) {
            stockEntryDelete(i.getID());
        }

        stock.setEntityDelete(true);

        serviceHistory.historyCreate(String.format("[Stock] Eliminación (%s)",
                stock.getStockName()
        ));

        return new ResponseEntity<>("Delete OK.", HttpStatus.OK);
    }

    public ResponseEntity<String> stockPatch(JsonPatch patch, Integer stockID) throws JsonPatchException, JsonProcessingException {
        ModelStock stock = repositoryStock.findById(stockID).orElseThrow(RuntimeException::new);

        ObjectMapper objectMapper = new ObjectMapper();

        repositoryStock.save(objectMapper.treeToValue(patch.apply(objectMapper.convertValue(stock, JsonNode.class)), ModelStock.class));

        return new ResponseEntity<>("Patch OK.", HttpStatus.OK);
    }

    //========================================================================
    // ModelStockEntry sub-service
    //========================================================================

    public ResponseEntity<ModelStockEntry> stockEntryGetOne(Integer stockEntryID) {
        return new ResponseEntity<>(repositoryStockEntry.findByIDAndEntityDeleteFalse(stockEntryID).orElseThrow(RuntimeException::new), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelStockEntry>> stockEntryGetAll() {
        return new ResponseEntity<>(repositoryStockEntry.findByEntityDeleteFalse(), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelStockEntry>> stockEntryFind(
            Optional<Integer> entryCountMin,
            Optional<Integer> entryCountMax,
            Optional<Float> entryPriceMin,
            Optional<Float> entryPriceMax) {
        return new ResponseEntity<>(repositoryStockEntry.findStockEntry(entryCountMin, entryCountMax, entryPriceMin, entryPriceMax), HttpStatus.OK);
    }

    public ResponseEntity<String> stockEntryCreate(ModelStockEntry stockEntry) {
        repositoryStockEntry.save(stockEntry);

        ModelStock historyStock = repositoryStock.findById(stockEntry.getEntryStock().getID()).orElseThrow(RuntimeException::new);
        ModelItem  historyItem  = repositoryItem.findById(stockEntry.getEntryItem().getID()).orElseThrow(RuntimeException::new);

        serviceHistory.historyCreate(String.format("[Listado] Creación (%s : %s [%s])",
                historyStock.getStockName(),
                historyItem.getItemName(),
                historyItem.getItemSKU()
        ));

        return new ResponseEntity<>("Creation OK.", HttpStatus.OK);
    }

    public ResponseEntity<String> stockEntryDelete(Integer stockEntryID) {
        ModelStockEntry stockEntry = repositoryStockEntry.findById(stockEntryID).orElseThrow(RuntimeException::new);

        stockEntry.setEntityDelete(true);

        serviceHistory.historyCreate(String.format("[Listado] Eliminación (%s : %s [%s])",
                stockEntry.getEntryStock().getStockName(),
                stockEntry.getEntryItem().getItemName(),
                stockEntry.getEntryItem().getItemSKU()
        ));

        return new ResponseEntity<>("Delete OK.", HttpStatus.OK);
    }

    public ResponseEntity<String> stockEntryPatch(JsonPatch patch, Integer stockEntryID) throws JsonPatchException, JsonProcessingException {
        ModelStockEntry stockEntry = repositoryStockEntry.findById(stockEntryID).orElseThrow(RuntimeException::new);

        ObjectMapper objectMapper = new ObjectMapper();

        repositoryStockEntry.save(objectMapper.treeToValue(patch.apply(objectMapper.convertValue(stockEntry, JsonNode.class)), ModelStockEntry.class));

        return new ResponseEntity<>("Patch OK.", HttpStatus.OK);
    }

    //========================================================================

    public ResponseEntity<String> stockEntryUpdateCount(Integer stockEntryID, Integer entryCount) {
        ModelStockEntry stockEntry = repositoryStockEntry.findById(stockEntryID).orElseThrow(RuntimeException::new);
        Integer oldCount = stockEntry.getEntryCount();
        Integer newCount = oldCount + entryCount;

        if (newCount < 0) {
            newCount = 0;
        } else if (newCount > stockEntry.getEntryItem().getItemCount()) {
            newCount = stockEntry.getEntryItem().getItemCount();
        }

        stockEntry.setEntryCount(newCount);
        repositoryStockEntry.save(stockEntry);
        serviceHistory.historyCreate(String.format("[Listado] Actualización (%s : %s [%s], stock anterior: %d, stock actual: %d)",
                stockEntry.getEntryStock().getStockName(),
                stockEntry.getEntryItem().getItemName(),
                stockEntry.getEntryItem().getItemSKU(),
                oldCount,
                newCount
        ));

        return new ResponseEntity<>("Update OK.", HttpStatus.OK);
    }

    public ResponseEntity<String> stockEntryUpdatePrice(Integer stockEntryID, Float entryPrice) {
        ModelStockEntry stockEntry = repositoryStockEntry.findById(stockEntryID).orElseThrow(RuntimeException::new);
        Float oldPrice = stockEntry.getEntryPrice();
        Float newPrice = oldPrice + entryPrice;

        if (newPrice < 0.0F) {
            newPrice = 0.0F;
        }

        stockEntry.setEntryPrice(newPrice);
        repositoryStockEntry.save(stockEntry);
        serviceHistory.historyCreate(String.format("[Listado] Actualización (%s : %s [%s], precio anterior: %f, precio actual: %f)",
                stockEntry.getEntryStock().getStockName(),
                stockEntry.getEntryItem().getItemName(),
                stockEntry.getEntryItem().getItemSKU(),
                oldPrice,
                newPrice
        ));

        return new ResponseEntity<>("Update OK.", HttpStatus.OK);
    }

    public ResponseEntity<String> stockEntryReport(Optional<String> reportPath) {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("General");

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Nombre");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Código");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Bodega");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Ubicación");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Cantidad");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Precio");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(false);

        Iterable<ModelStockEntry> array = repositoryStockEntry.findByEntityDeleteFalse();

        Integer iterate = 1;

        for (ModelStockEntry e : array) {
            Row row = sheet.createRow(iterate);

            Cell cell = row.createCell(0);
            cell.setCellValue(e.getEntryItem().getItemName());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(e.getEntryItem().getItemSKU());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(e.getEntryItem().getItemCompany().getCompanyName());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(e.getEntryStock().getStockName());
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(e.getEntryCount());
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(e.getEntryPrice());
            cell.setCellStyle(style);

            iterate++;
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);

        String fileLocation = "";

        if (reportPath.isEmpty()) {
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            fileLocation = path.substring(0, path.length() - 1) + "stock_" + LocalDate.now() + "_" + LocalTime.now() + ".xlsx";
        } else {
            fileLocation = reportPath.get() + "stock_" + LocalDate.now() + "_" + LocalTime.now() + ".xlsx";
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
        }

        return new ResponseEntity<>("Report OK.", HttpStatus.OK);
    }
}
