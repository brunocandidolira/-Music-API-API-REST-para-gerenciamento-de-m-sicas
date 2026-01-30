package com.example.music.Controller;


import com.example.music.Service.CsvImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

    @RestController
    @RequestMapping("/api/csv")
    public class CsvImportController {

        private final CsvImportService service;

        public CsvImportController(CsvImportService service) {
            this.service = service;
        }

        @PostMapping("/import")
        public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
            service.importCsv(file);
            return ResponseEntity.ok("CSV importado com sucesso!");
        }
    }

