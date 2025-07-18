package com.sj.marriagemanagementsystem.service;

import com.sj.marriagemanagementsystem.domain.CongratulatoryMoney;
import com.sj.marriagemanagementsystem.domain.Wedding;
import com.sj.marriagemanagementsystem.repository.CongratulatoryMoneyRepository;
import com.sj.marriagemanagementsystem.repository.WeddingRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelExportService {

    private final WeddingRepository weddingRepository;
    private final CongratulatoryMoneyRepository moneyRepository;

    public byte[] exportExcel(String username, String password) throws IOException {
        Wedding wedding = weddingRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 username의 웨딩 정보 없음"));

        if (!wedding.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        List<CongratulatoryMoney> moneyList = moneyRepository.findAll().stream()
                .filter(m -> m.getWedding().getId().equals(wedding.getId()))
                .toList();

        int totalAmount = moneyList.stream().mapToInt(CongratulatoryMoney::getAmount).sum();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("축의금 내역");

            // 스타일 생성
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle totalStyle = workbook.createCellStyle();
            Font boldFont = workbook.createFont();
            boldFont.setBold(true);
            totalStyle.setFont(boldFont);
            totalStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
            totalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            totalStyle.setAlignment(HorizontalAlignment.CENTER);
            totalStyle.setBorderBottom(BorderStyle.THICK);
            totalStyle.setBorderTop(BorderStyle.THICK);
            totalStyle.setBorderLeft(BorderStyle.THICK);
            totalStyle.setBorderRight(BorderStyle.THICK);

            // 헤더
            Row header = sheet.createRow(0);
            String[] columns = {"No", "이름", "금액", "시간", "식권 수"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // 데이터
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            int rowIdx = 1;
            for (int i = 0; i < moneyList.size(); i++) {
                CongratulatoryMoney m = moneyList.get(i);
                Row row = sheet.createRow(rowIdx++);

                Cell cell0 = row.createCell(0);
                cell0.setCellValue(i + 1);
                cell0.setCellStyle(dataStyle);

                Cell cell1 = row.createCell(1);
                cell1.setCellValue(m.getName());
                cell1.setCellStyle(dataStyle);

                Cell cell2 = row.createCell(2);
                cell2.setCellValue(m.getAmount());
                cell2.setCellStyle(dataStyle);

                Cell cell3 = row.createCell(3);
                cell3.setCellValue(m.getTime().format(formatter));
                cell3.setCellStyle(dataStyle);

                Cell cell4 = row.createCell(4);
                cell4.setCellValue(m.getMealTicketCount());
                cell4.setCellStyle(dataStyle);
            }

            // 총합계 행
            Row totalRow = sheet.createRow(rowIdx);
            Cell labelCell = totalRow.createCell(1);
            labelCell.setCellValue("총 합계");
            labelCell.setCellStyle(totalStyle);

            Cell sumCell = totalRow.createCell(2);
            sumCell.setCellValue(totalAmount);
            sumCell.setCellStyle(totalStyle);

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }
}
