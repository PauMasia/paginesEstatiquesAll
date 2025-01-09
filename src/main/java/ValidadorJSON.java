import com.ctc.wstx.shaded.msv.org_isorelax.verifier.Schema;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.*;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class ValidadorJSON {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);

    public boolean validarAutores() {
    boolean res;
        try {
            JsonNode schemaNode = objectMapper.readTree(new File("src/main/json/schemaCantantes.json"));


            JsonSchema schema = factory.getSchema(schemaNode);

            JsonNode jsonData = objectMapper.readTree(new File("src/main/json/cantantes.json"));

            Set<ValidationMessage> validationErrors = schema.validate(jsonData);

            if (validationErrors.isEmpty()) {
                System.out.println("El JSON es Cantantes valido");
                res = true;
            } else {
                System.out.println("El JSON de Cantantes no es valido");
                validationErrors.stream().forEach(System.out::println);
                res = false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public boolean validarAlbumes() {
        boolean res;
        try {
            JsonNode schemaNode = objectMapper.readTree(new File("src/main/json/schemaAlbums.json"));


            JsonSchema schema = factory.getSchema(schemaNode);

            JsonNode jsonData = objectMapper.readTree(new File("src/main/json/album.json"));

            Set<ValidationMessage> validationErrors = schema.validate(jsonData);

            if (validationErrors.isEmpty()) {
                System.out.println("El JSON de Albumes valido");
                res = true;
            } else {
                System.out.println("El JSON de Albumes no es valido");
                res = false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return res;
    }
}
