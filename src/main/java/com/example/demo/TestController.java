package com.example.demo;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.net.URL;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.graphics.PdfFont;
import com.spire.pdf.graphics.PdfFontFamily;
import com.spire.pdf.graphics.PdfFontStyle;
import com.spire.pdf.graphics.PdfImage;
import com.spire.pdf.security.GraphicMode;
import com.spire.pdf.security.PdfCertificate;
import com.spire.pdf.security.PdfCertificationFlags;
import com.spire.pdf.security.PdfSignature;

@RestController
public class TestController {
	
	@GetMapping(path="/pdfTest")
	public void pdfTest() throws Exception {
		
		ClassPathResource resourceContract = new ClassPathResource("testFile/sample_contract.pdf");
		ClassPathResource resourceSign = new ClassPathResource("testFile/sample_sign.png");
		ClassPathResource resourceCert = new ClassPathResource("testFile/testCert.pfx");
		
        File fileContract = new File(resourceContract.getURI());
        File fileSign = new File(resourceSign.getURI());
        File fileCert = new File(resourceCert.getURI());
		
		//load a pdf document
		PdfDocument doc = new PdfDocument();
        doc.loadFromFile(fileContract.getPath()); 

        //Load the certificate
        PdfCertificate cert = new PdfCertificate(fileCert.getPath(), "test"); // not sign image

        //Create a PdfSignature object and specify its position and size
        PdfSignature signature = new PdfSignature(doc, doc.getPages().get(0), cert, "MySignature");
        Rectangle2D rect = new Rectangle2D.Float();
        rect.setFrame(new Point2D.Float((float) doc.getPages().get(0).getActualSize().getWidth() - 300, (float) doc.getPages().get(0).getActualSize().getHeight() - 250), new Dimension(250, 150));
        signature.setBounds(rect);

        //Set the graphics mode
        signature.setGraphicMode(GraphicMode.Sign_Image_Only);

        //Set the signature content (text)
//        signature.setNameLabel("Signer:");
//        signature.setName("Jessie");
//        signature.setContactInfoLabel("ContactInfo:");
//        signature.setContactInfo("xxxxxxxxx");
//        signature.setDateLabel("Date:");
//        signature.setDate(new java.util.Date());
//        signature.setLocationInfoLabel("Location:");
//        signature.setLocationInfo("Florida");
//        signature.setReasonLabel("Reason: ");
//        signature.setReason("The certificate of this document");
//        signature.setDistinguishedNameLabel("DN: ");
//        signature.setDistinguishedName(signature.getCertificate().get_IssuerName().getName());
        
        //Set the signature content (image)
        signature.setSignImageSource(PdfImage.fromFile(fileSign.getPath())); 

        //Set the signature font
        signature.setSignDetailsFont(new PdfFont(PdfFontFamily.Helvetica, 10f, PdfFontStyle.Bold));

        //Set the document permission
        signature.setDocumentPermissions(PdfCertificationFlags.Forbid_Changes);
        signature.setCertificated(true);

        //Save to file
        doc.saveToFile("AddSignature.pdf");
        doc.close();
		
	}
	
	@GetMapping(path="/pdfBoxTest")
	public void pdfBoxTest() throws Exception {
		// path: /sch-sign/target/classes/testFile
		URL resource = getClass().getClassLoader().getResource("testFile");
		String path = resource.getFile();
		
		GraphicSignature sign = new GraphicSignature(path+"/sample_contract.pdf", path+"/sample_contract_sign.pdf", path+"/sample_sign.png");
		// set page
		sign.setPageNumber(0);
		// set width
		sign.setSignatureWidth(100);
		// set position
		sign.setCordinate(650, 150);
		// save
		sign.Append();
		
	}
	
	
}
