package com.example.demo;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

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
		
		//load a pdf document
		PdfDocument doc = new PdfDocument();
        doc.loadFromFile("C:\\Users\\yerin\\Desktop\\sample_contract.pdf"); 

        //Load the certificate
        PdfCertificate cert = new PdfCertificate("C:\\Users\\yerin\\Desktop\\testCert.pfx", "test"); // not sign image

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
        signature.setSignImageSource(PdfImage.fromFile("C:\\Users\\yerin\\Desktop\\sample_sign.png")); 

        //Set the signature font
        signature.setSignDetailsFont(new PdfFont(PdfFontFamily.Helvetica, 10f, PdfFontStyle.Bold));

        //Set the document permission
        signature.setDocumentPermissions(PdfCertificationFlags.Forbid_Changes);
        signature.setCertificated(true);

        //Save to file
        doc.saveToFile("AddSignature.pdf");
        doc.close();
		
	}
}
