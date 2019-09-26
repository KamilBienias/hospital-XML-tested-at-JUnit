import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


public class MarshallerExample {

    public static Marshaller generateMarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(Database.class);
        Marshaller marshaller = ctx.createMarshaller();
        return marshaller;
    }
}