package com.a18delsol.grattorepo.service;

import com.a18delsol.grattorepo.model.item.ModelItem;
import com.a18delsol.grattorepo.model.sale.ModelSale;
import com.a18delsol.grattorepo.model.sale.ModelSaleOrder;
import com.a18delsol.grattorepo.model.sale.ModelSaleUpdate;
import com.a18delsol.grattorepo.model.stock.ModelStockEntry;
import com.a18delsol.grattorepo.repository.item.RepositoryItem;
import com.a18delsol.grattorepo.repository.sale.RepositorySale;
import com.a18delsol.grattorepo.repository.sale.RepositorySaleOrder;
import com.a18delsol.grattorepo.repository.sale.RepositorySaleUpdate;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ServiceSale {
    @Autowired RepositorySale       repositorySale;
    @Autowired RepositorySaleOrder  repositorySaleOrder;
    @Autowired RepositorySaleUpdate repositorySaleUpdate;
    @Autowired RepositoryStockEntry repositoryStockEntry;
    @Autowired RepositoryItem       repositoryItem;
    @Autowired ServiceAlert         serviceAlert;

    public ResponseEntity<ModelSale> saleGetOne(Integer saleID) {
        return new ResponseEntity<>(repositorySale.findById(saleID).orElseThrow(RuntimeException::new), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelSale>> saleGetAll() {
        return new ResponseEntity<>(repositorySale.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelSale>> saleFind(
            Optional<Float> salePriceMin,
            Optional<Float> salePriceMax,
            Optional<LocalDate> saleDateMin,
            Optional<LocalDate> saleDateMax) {
        return new ResponseEntity<>(repositorySale.findSale(salePriceMin, salePriceMax, saleDateMin, saleDateMax), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelSale>> saleCreate(Iterable<ModelSale> sale) {
        for (ModelSale a : sale) {
            repositorySale.save(a);
        }

        return new ResponseEntity<>(repositorySale.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> saleDelete(Integer saleID) {
        ModelSale sale = repositorySale.findById(saleID).orElseThrow(RuntimeException::new);

        repositorySale.delete(sale);

        return new ResponseEntity<>("Delete OK.", HttpStatus.OK);
    }

    public ResponseEntity<ModelSale> salePatch(JsonPatch patch, Integer saleID) throws JsonPatchException, JsonProcessingException {
        ModelSale sale = repositorySale.findById(saleID).orElseThrow(RuntimeException::new);

        ObjectMapper objectMapper = new ObjectMapper();

        return new ResponseEntity<>(objectMapper.treeToValue(patch.apply(objectMapper.convertValue(sale, JsonNode.class)), ModelSale.class), HttpStatus.OK);
    }

    //========================================================================

    public ResponseEntity<String> saleReport(Optional<LocalDate> saleDateMin, Optional<LocalDate> saleDateMax, Optional<String> salePath) {
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
        headerCell.setCellValue("SKU");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Valor total");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Medio de pago");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Origen de venta");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Fecha");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Hora");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Nombre de cliente");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(7);
        headerCell.setCellValue("Correo de cliente");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(8);
        headerCell.setCellValue("Teléfono de cliente");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(false);

        Iterable<ModelSale> array = repositorySale.findSale(null, null, saleDateMin, saleDateMax);
        HashMap<String, Float> paymentValue = new HashMap<>();

        Integer iterate = 1;

        for (ModelSale s : array) {
            Row row = sheet.createRow(iterate);

            String cellCode = "";
            String cellName = "";

            for (ModelSaleOrder o : s.getSaleOrder()) {
                String itemCode  = o.getOrderEntry().getEntryItem().getItemCode();
                String stockName = o.getOrderEntry().getEntryStock().getStockName();

                if (cellCode.isEmpty()) {
                    cellCode = String.format("%s", itemCode);
                } else {
                    cellCode = String.format("%s/%s", cellCode, itemCode);
                }

                if (cellName.isEmpty()) {
                    cellName = String.format("%s", stockName);
                } else {
                    cellName = String.format("%s/%s", cellName, stockName);
                }
            }

            if (!paymentValue.containsKey(s.getSalePayment())) {
                paymentValue.put(s.getSalePayment(), s.getSalePrice());
            } else {
                paymentValue.put(s.getSalePayment(), paymentValue.get(s.getSalePayment()) + s.getSalePrice());
            }

            Cell cell = row.createCell(0);
            cell.setCellValue(cellCode);
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(s.getSalePrice());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(s.getSalePayment());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(cellName);
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(s.getSaleDate().toString());
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(s.getSaleTime().toString());
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue(s.getSaleName());
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue(s.getSaleMail());
            cell.setCellStyle(style);

            cell = row.createCell(8);
            cell.setCellValue(s.getSaleCall());
            cell.setCellStyle(style);

            iterate++;
        }

        for (String p : paymentValue.keySet()) {
            Row row = sheet.createRow(iterate + 1);

            Cell cell = row.createCell(0);
            cell.setCellValue(String.format("%s: %s", p, paymentValue.get(p).toString()));
            cell.setCellStyle(style);

            iterate++;
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(8);

        String fileLocation = "";

        if (salePath.isEmpty()) {
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            fileLocation = path.substring(0, path.length() - 1) + "sale_" + LocalDate.now() + "_" + LocalTime.now() + ".xlsx";
        } else {
            fileLocation = salePath.get() + "_" + LocalDate.now() + "_" + LocalTime.now() + ".xlsx";
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
        }

        return new ResponseEntity<>("Sale report OK.", HttpStatus.OK);
    }

    public ResponseEntity<ModelSale> saleBuy(ModelSale sale) {
        Float salePrice = 0.0F;

        Set<ModelSaleUpdate> saleUpdate = new HashSet<>();

        for (ModelSaleOrder a : sale.getSaleOrder()) {
            ModelStockEntry stockEntry = repositoryStockEntry.findById(a.getOrderEntry().getEntryID()).orElseThrow(RuntimeException::new);
            ModelItem entryItem = stockEntry.getEntryItem();
            Float   newPrice = stockEntry.getEntryPrice() * a.getOrderAmount();
            Integer newEntry = stockEntry.getEntryCount() - a.getOrderAmount();
            Integer newCount = entryItem.getItemCount() - a.getOrderAmount();
            Boolean alertEntry = newEntry <= entryItem.getItemAlert();
            Boolean alertCount = newCount <= entryItem.getItemAlert();

            if (alertCount) {
                serviceAlert.alertCreate(String.format("[General] El producto %s (%s) tiene una baja cantidad de disponibilidad (cantidad actual: %d)",
                        entryItem.getItemName(),
                        entryItem.getItemCode(),
                        newCount));
            }

            if (alertEntry) {
                serviceAlert.alertCreate(String.format("[Listado] El producto %s (%s) en la ubicación (%s) tiene una baja cantidad de disponibilidad (cantidad actual: %d)",
                        entryItem.getItemName(),
                        entryItem.getItemCode(),
                        stockEntry.getEntryStock().getStockName(),
                        newEntry));
            }

            salePrice += newPrice;
            repositorySaleOrder.save(a);

            entryItem.setItemCount(newCount);
            repositoryItem.save(entryItem);

            stockEntry.setEntryCount(newEntry);
            repositoryStockEntry.save(stockEntry);

            for (ModelStockEntry e : entryItem.getItemEntry()) {
                if (e.getEntryCount() > newCount) {
                    if (alertEntry) {
                        serviceAlert.alertCreate(String.format("[Listado] El producto %s (%s) en la ubicación (%s) tiene una baja cantidad de disponibilidad (cantidad actual: %d)",
                                entryItem.getItemName(),
                                entryItem.getItemCode(),
                                e.getEntryStock().getStockName(),
                                newEntry));
                    }

                    ModelSaleUpdate entryUpdate = new ModelSaleUpdate();
                    entryUpdate.setUpdateCount(e.getEntryCount() - newCount);
                    entryUpdate.setUpdateEntry(e);
                    repositorySaleUpdate.save(entryUpdate);

                    saleUpdate.add(entryUpdate);

                    e.setEntryCount(newCount);
                    repositoryStockEntry.save(e);
                }
            }
        }

        sale.setSalePrice(salePrice);
        sale.setSaleChange(sale.getSaleAmount() - salePrice);
        sale.setSaleDate(LocalDate.now());
        sale.setSaleTime(LocalTime.now());
        sale.setSaleUpdate(saleUpdate);
        repositorySale.save(sale);

        return new ResponseEntity<>(sale, HttpStatus.OK);
    }

    public ResponseEntity<String> saleReturn(Integer saleID) {
        ModelSale sale = repositorySale.findById(saleID).orElseThrow(RuntimeException::new);

        for (ModelSaleOrder a : sale.getSaleOrder()) {
            ModelStockEntry stockEntry = a.getOrderEntry();
            ModelItem entryItem = stockEntry.getEntryItem();
            Integer newEntry = stockEntry.getEntryCount() + a.getOrderAmount();
            Integer newCount = entryItem.getItemCount() + a.getOrderAmount();

            entryItem.setItemCount(newCount);
            repositoryItem.save(entryItem);

            stockEntry.setEntryCount(newEntry);
            repositoryStockEntry.save(stockEntry);
        }

        for (ModelSaleUpdate u : sale.getSaleUpdate()) {
            ModelStockEntry updateEntry = u.getUpdateEntry();
            updateEntry.setEntryCount(updateEntry.getEntryCount() + u.getUpdateCount());
        }

        repositorySale.delete(sale);

        return new ResponseEntity<>("Return OK.", HttpStatus.OK);
    }

    //========================================================================
    // ModelSaleOrder sub-service
    //========================================================================

    public ResponseEntity<ModelSaleOrder> saleOrderGetOne(Integer saleOrderID) {
        return new ResponseEntity<>(repositorySaleOrder.findById(saleOrderID).orElseThrow(RuntimeException::new), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelSaleOrder>> saleOrderGetAll() {
        return new ResponseEntity<>(repositorySaleOrder.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelSaleOrder>> saleOrderFind(
            Optional<Float> orderAmountMin,
            Optional<Float> orderAmountMax) {
        return new ResponseEntity<>(repositorySaleOrder.findSaleOrder(orderAmountMin, orderAmountMax), HttpStatus.OK);
    }

    public ResponseEntity<Iterable<ModelSaleOrder>> saleOrderCreate(Iterable<ModelSaleOrder> saleOrder) {
        for (ModelSaleOrder a : saleOrder) {
            repositorySaleOrder.save(a);
        }

        return new ResponseEntity<>(repositorySaleOrder.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> saleOrderDelete(Integer saleOrderID) {
        ModelSaleOrder saleOrder = repositorySaleOrder.findById(saleOrderID).orElseThrow(RuntimeException::new);

        repositorySaleOrder.delete(saleOrder);

        return new ResponseEntity<>("Delete OK.", HttpStatus.OK);
    }

    public ResponseEntity<ModelSaleOrder> saleOrderPatch(JsonPatch patch, Integer saleOrderID) throws JsonPatchException, JsonProcessingException {
        ModelSaleOrder saleOrder = repositorySaleOrder.findById(saleOrderID).orElseThrow(RuntimeException::new);

        ObjectMapper objectMapper = new ObjectMapper();

        return new ResponseEntity<>(objectMapper.treeToValue(patch.apply(objectMapper.convertValue(saleOrder, JsonNode.class)), ModelSaleOrder.class), HttpStatus.OK);
    }
}
