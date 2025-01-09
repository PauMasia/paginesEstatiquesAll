import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class main {
    public static void main(String[] args) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ArrayList<Autor> autorArrayList = objectMapper.readValue(new File("src/main/json/cantantes.json"),
                    new TypeReference<ArrayList<Autor>>() {
                    });

            ArrayList<Album> albumArrayList = objectMapper.readValue(new File("src/main/json/album.json"),
                    new TypeReference<ArrayList<Album>>() {
                    });

//            for (Autor a : autorArrayList) {
//                System.out.println(a.getName());
//                for (Album e : albumArrayList) {
//                    if (a.getArtistName().equals(e.getNomArt())) {
//                        System.out.println("\t·" + e);
//                    }
//                }
//            }
            ValidadorJSON vJSON = new ValidadorJSON();

            if (vJSON.validarAutores() && vJSON.validarAlbumes()) {
                generarRSS(autorArrayList, albumArrayList);
                montarHtmlAutores(autorArrayList);
                montarHtmlAlbums(albumArrayList, autorArrayList, autorArrayList);
            }
        } catch (IOException a) {
            System.out.println(a.getMessage());
        }
    }


    private static void montarHtmlAlbums(ArrayList<Album> albumArrayList, ArrayList<Autor> autorArrayList, ArrayList<Autor> autors) {
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setPrefix("src/main/template/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");

        for (Autor a : autors) {
            ArrayList<Album> albumFin = new ArrayList<>();
            for (Album e : albumArrayList) {
                if (a.getArtistName().equals(e.getNomArt())) {
                    albumFin.add(e);
                }
            }


            TemplateEngine tEngine = new TemplateEngine();
            tEngine.setTemplateResolver(templateResolver);

            Properties conf= generarIni();
            Context context = new Context();
            context.setVariable("prefijoAlbum", conf.getProperty("prefijoAlbum"));

            context.setVariable("album", albumFin);

            String contengut = tEngine.process("album", context);

            try (
                BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/htmlFinals/" + a.getArtistName() + ".html"))) {
                writer.write(contengut);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void montarHtmlAutores(ArrayList<Autor> autorArrayList) {
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setPrefix("src/main/template/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");

        TemplateEngine tEngine = new TemplateEngine();
        tEngine.setTemplateResolver(templateResolver);

        Properties conf= generarIni();
        Context context = new Context();
        context.setVariable("autor", autorArrayList);
        context.setVariable("nameIndex", conf.getProperty("nameIndex"));

        String contengut = tEngine.process("autor", context);

        // Index html antes se dia autor, per aixo esta el autor html que no se generara mes, si el borres
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("index.html"))) {
            writer.write(contengut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties generarIni() {

        Properties config = new Properties();
        try {
            config.load(Files.newBufferedReader(Path.of("src/main/resources/config.ini")));
        } catch (IOException e) {
            System.out.println("Error en generarIni");
        }
        return config;
    }

    public static void generarRSS(ArrayList<Autor> autors, ArrayList<Album> albums) {
        //Tt esto es una locura lo que me ha costat

        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("src/main/template/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("XML");
        resolver.setCharacterEncoding("UTF-8");

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Map<Autor,ArrayList<Album>> autorsTotales= new HashMap<>();
        for (Autor au: autors){
            ArrayList<Album> albumOut=new ArrayList<>();
            for (Album al: albums){
                if (al.getNomArt().equals(au.getArtistName())) {
                    albumOut.add(al);
                }
            }
            autorsTotales.put(au,albumOut);
        }
        Context context = new Context();
        context.setVariable("autorsTotales", autorsTotales);

        String rssOutput = engine.process("rss", context);

        try (Writer writer = new FileWriter("src/main/resources/rss_output.xml")) {
            writer.write(rssOutput);
        }catch (IOException e){
            System.out.println("error en RSS");
        }


    }

}
