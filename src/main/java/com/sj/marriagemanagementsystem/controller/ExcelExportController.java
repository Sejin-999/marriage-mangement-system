package com.sj.marriagemanagementsystem.controller;

import com.sj.marriagemanagementsystem.service.ExcelExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
public class ExcelExportController {

    private final ExcelExportService excelExportService;

    @GetMapping("/excel")
    public ResponseEntity<byte[]> downloadExcel(@RequestParam String username,
                                                @RequestParam String password) {
        try {
            byte[] excel = excelExportService.exportExcel(username, password);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=축의금내역.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(excel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
