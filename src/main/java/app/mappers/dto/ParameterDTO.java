package app.mappers.dto;

import app.domain.model.ParameterCategory;

public class ParameterDTO {

    private String parameterCode;

    private String shortName;

    private String description;

    private ParameterCategory pc;

    public ParameterDTO(String parameterCode, String shortName, String description, ParameterCategory pc){
        this.parameterCode = parameterCode;
        this.shortName = shortName;
        this.description = description;
        this.pc = pc;
    }

    public String getParameterCode() {
        return parameterCode;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDescription() {
        return description;
    }

    public ParameterCategory getPc() {
        return pc;
    }

    @Override
    public String toString() {
        return String.format("Parameter: %s%nDescription: %s%nCode: %s%nCategory: %s%n", shortName, description, parameterCode, pc);
    }
}
