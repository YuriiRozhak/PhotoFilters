package com.rozhak.selfeducation.photofilter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.FilenameUtils;

import com.rozhak.selfeducation.photofilter.filters.Filter;
import com.rozhak.selfeducation.photofilter.filters.FilterFactory;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/file")
public class UploadFileService {

	@POST
	@Path("/filter")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({"image/jpeg,image/png"})
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		ByteArrayOutputStream baos = null;
		BufferedImage outputImage = null;
		String extension = "";
		String fileName = "";
		try {
			fileName = fileDetail.getFileName();
			extension = FilenameUtils.getExtension(fileName);
			BufferedImage inputImage = ImageIO.read(uploadedInputStream);
			
			
			Filter filter = new FilterFactory().getFilter("Sepia");
			outputImage = filter.processImage(inputImage);
			
			baos = new ByteArrayOutputStream();
			ImageIO.write(outputImage, extension, baos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResponseBuilder response = Response.ok(baos.toByteArray());
		response.header("Content-Disposition", "attachment; filename=" + fileName);
		Response res = response.build();

		return res;

	}

	

}