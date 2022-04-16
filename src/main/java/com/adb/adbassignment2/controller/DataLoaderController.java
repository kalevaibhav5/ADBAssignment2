package com.adb.adbassignment2.controller;

import com.adb.adbassignment2.service.ReadPubMedFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DataLoaderController {

    @Autowired
    private ReadPubMedFileService readPubMedFileService;

    @RequestMapping(path="loaddata", method= RequestMethod.GET)
    public void loadData() throws Exception{
        readPubMedFileService.loadFile();
    }
}
