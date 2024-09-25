package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.dl.entities.Tournament;

public interface BracketService {
    void generateAndSaveBrackets(Tournament tournament);
}
