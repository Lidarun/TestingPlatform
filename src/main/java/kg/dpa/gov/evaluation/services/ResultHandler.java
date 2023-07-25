package kg.dpa.gov.evaluation.services;

import org.springframework.security.core.Authentication;

import java.util.Map;

public interface ResultHandler {

    void setResults(Map<Integer, String> results, Authentication authentication);

}
