package de.blackforestsolutions.frontend.service;

import de.blackforestsolutions.frontend.model.Ernian;

import java.util.Map;
import java.util.UUID;

public interface FileWriterReaderService {


    Map<UUID, Ernian> readErnians();

    void writeErnians(Ernian ernian);
}
