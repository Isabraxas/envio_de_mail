package com.viridian.kafkajson;

import com.viridian.kafkajson.model.Client;
import com.viridian.kafkajson.producer.ClientSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class KafkaJsonApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KafkaJsonApplication.class, args);

	}

	@Value("${file.name.classpath}")
	private String fileName;

	@Value("${file.extension.classpath}")
	private String fileExtension;

	@Autowired
	private ClientSender sender;

	@Override
	public void run(String... strings) throws Exception {
		Client client = new Client("Client", "juan perez perez", 33);
		// cargar imagen
		File file;
		try{
			//file = new File("/home/isvar/Documents/sensible/Test_image.png");
			file = new ClassPathResource(fileName+fileExtension).getFile();
			BufferedImage img = ImageIO.read(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write( img, "png", baos );
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			client.setExtensionFile(fileExtension);
			client.setFoto(imageInByte);
		}catch (IOException e){
			e.printStackTrace();
		}
		sender.send(client);
	}
}
