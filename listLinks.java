package scrapyJava;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.select.Elements;

public class listLinks {
	
	public static List<String> titulares = new ArrayList<String>();
	public static List<String> enlaces   = new ArrayList<String>();
	public static List<String> urlImagenes   = new ArrayList<String>();
	private static String IMAGE_DESTINATION_FOLDER = "imagenes";
	private static String urlPublico = "https://www.publico.es/ciencias";
	
	public static void main(String[] args) throws IOException{
		//Borrar ambas listas
		titulares.clear();
		enlaces.clear();
		//Borrar fotos almacenadas
		File f = new File(IMAGE_DESTINATION_FOLDER);
		borrarImagenesAlmacenadas(f);
		//Obtener nuevas imágenes y datos de las noticias
		almacenarTitularesYEnlaces();
		almacenarNuevasImagenes();
	}

	private static void almacenarTitularesYEnlaces() throws IOException{
	
		Document doc = Jsoup.connect(urlPublico).get();
		Elements articulos = doc.select("div.listing-item");
		Elements imageElements = doc.select("img");
		
		for (Element elem : articulos) {
			String titular = elem.getElementsByClass("page-link").text();
			titulares.add(titular+" - ");
			String enlace = "https://www.publico.es"+(elem.getElementsByAttribute("href").attr("href"));
			enlaces.add(enlace+ " - ");
			Elements imageElementss = elem.select("img");
			String titularr = imageElementss.attr("abs:src");
			String strImageName = titularr.substring( titularr.lastIndexOf("/") + 1 );
			urlImagenes.add(strImageName);		
		}
	}

	private static void almacenarNuevasImagenes() throws IOException{
		Document document = Jsoup.connect(urlPublico).get();

		//select all img tags
		Elements imageElements = document.select("img");
		System.out.println(imageElements.size());
		//iterate over each image
		
		//for(Element imageElement : imageElements){
		for(int i=11;i<imageElements.size();i++) {

			//make sure to get the absolute URL using abs: prefix
			String strImageURL = imageElements.get(i).attr("abs:src");

			//download image one by one
			downloadImage(strImageURL);
		}
	}

	private static void downloadImage(String strImageURL) {
		String strImageName = strImageURL.substring( strImageURL.lastIndexOf("/") + 1 );
		System.out.println("Saving: " + strImageName + ", from: " + strImageURL);
		try {
			//open the stream from URL
			URL urlImage = new URL(strImageURL);
			InputStream in = urlImage.openStream();
			byte[] buffer = new byte[4096];
			int n = -1;
			OutputStream os = new FileOutputStream( IMAGE_DESTINATION_FOLDER + "/" + strImageName );
			//write bytes to the output stream
			while ( (n = in.read(buffer)) != -1 ){
				os.write(buffer, 0, n);
			}
			//close the stream
			os.close();
			System.out.println("Image saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void borrarImagenesAlmacenadas(File directorio) {
		File[] ficheros = directorio.listFiles();
		for (int x=1;x<ficheros.length;x++){
			ficheros[x].delete();
		}
	}
}

