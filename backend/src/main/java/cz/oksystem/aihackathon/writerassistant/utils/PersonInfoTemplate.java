package cz.oksystem.aihackathon.writerassistant.utils;

import java.time.LocalDate;

public class PersonInfoTemplate {

    private String name;
    private String surname;
    private LocalDate birthDate;
    private String pernamentAddress;
    private String currentAddress;

    public PersonInfoTemplate() {
    }

    public PersonInfoTemplate(String name, String surname, LocalDate birthDate, String pernamentAddress, String currentAddress) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.pernamentAddress = pernamentAddress;
        this.currentAddress = currentAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPernamentAddress() {
        return pernamentAddress;
    }

    public void setPernamentAddress(String pernamentAddress) {
        this.pernamentAddress = pernamentAddress;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public static final String OSOBA_INFO_TEMPLATE = """
            Dostupné informace o osobě: 
            Jméno: {name},
            Příjmení: {surname},
            Datum narození: {born},
            Trvalé bydliště: {permanentAddress},
            Faktické bydliště: {currentAddress}
            """;

    public String getOsobaMetadataPreprompt() {
        return  OSOBA_INFO_TEMPLATE
                .replace("{name}", getPropertyOrEmpty(this.name))
                .replace("{surname}", getPropertyOrEmpty(this.surname))
                .replace("{born}", getPropertyOrEmpty(this.birthDate.toString()))
                .replace("{permanentAddress}", getPropertyOrEmpty(this.pernamentAddress))
                .replace("{currentAddress}", getPropertyOrEmpty(this.currentAddress));
    }

    public String getPropertyOrEmpty(String property) {
        return property == null ? "V současné době neznámé" : property;
    }

    public static PersonInfoTemplate getFilledPersonInfo() {
        PersonInfoTemplate personInfo = new PersonInfoTemplate();
        personInfo.name = "Hugo";
        personInfo.surname = "Myslík";
        personInfo.birthDate = LocalDate.now().minusYears(50);
        personInfo.currentAddress = "Šumavská 38, Cheb, 35002";
        personInfo.pernamentAddress = "U družstva Život 2, Praha 4, 14000";
        return personInfo;
    }

    public static  PersonInfoTemplate getPersonWithoutCurrentAddress() {
        PersonInfoTemplate personInfo = new PersonInfoTemplate();
        personInfo.name = "Evžen";
        personInfo.surname = "Hedvábný";
        personInfo.birthDate = LocalDate.now().minusYears(50);
        personInfo.pernamentAddress = "Lipová 14, Tanvald, 12352";
        return personInfo;
    }

}
