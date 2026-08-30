package com.ayurvana.service.scanner;

import org.springframework.web.multipart.MultipartFile;

public interface PlantIdentificationProvider {

    PlantIdentificationResult identifyPlant(MultipartFile file);
}
