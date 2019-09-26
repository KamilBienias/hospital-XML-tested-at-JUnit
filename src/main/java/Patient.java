public class Patient extends Person{

    private String disease;

    //a non-argument constructor is needed to create an xml file
    public Patient(){}

    //getters are needed to xml file
    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", disease='" + disease + '\'';
    }
}

