# pdf sign test


#### Request
```
  url: /pdfTest
  Method: Get
```

#### 디렉토리구조
``` bash
  |- src
  |-- main
  |--- java
  |---- com/example/demo/TestController.java          # pdf 테스트 코드가 있는 소스
  |--- resources/testFile
  |---- sample_contract.pdf                           #  샘플 계약서
  |---- sample_sign.png                               #  샘플 사인 이미지
  |---- sample_sigin2.png                             #  샘플 사인 이미지2 
  |---- testCert.pfx                                  #  샘플 인증서
  | AddSignature.pdf                                  #  완성된 pdf파일
  | pom.xml                                           #  pom.xml
```  
  
#### 로직
``` java 
  // 1. pom.xml에 spire관련 의존성 추가
  <dependency>
    <groupId>e-iceblue</groupId>
    <artifactId>spire.pdf.free</artifactId>
    <version>5.1.0</version>
  </dependency>
  <repository>
    <id>com.e-iceblue</id>
    <name>e-iceblue</name>
    <url>https://repo.e-iceblue.com/nexus/content/groups/public/</url>
  </repository>
  
  // 2. pdf 불러오기 
  PdfDocument doc = new PdfDocument();
  doc.loadFromFile(pdfPath); 
  
  // 3. 인증서 불러오기 혹은 생성
  PdfCertificate cert = new PdfCertificate(CertPath, password);
  
  // 4. 사인 객체 생성
  PdfSignature signature = new PdfSignature(pdfFile, pdfFilePage, certificate, signName);
  
  // 5. 사인 위치 및 크기 세팅
  Rectangle2D rect = new Rectangle2D.Float();
 rect.setFrame(new Point2D.Float((float) doc.getPages().get(0).getActualSize().getWidth() - 300, (float) doc.getPages().get(0).getActualSize().getHeight() - 250), new Dimension(250, 150));
 signature.setBounds(rect);
 
 // 6. 사인 그래픽 모드 설정
 signature.setGraphicMode(GraphicMode.Sign_Image_Only);
 
 // 7. 사인 이미지 추가
 signature.setSignImageSource(PdfImage.fromFile(fileSign.getPath())); // 사인 이미지 투명화 필수

// 8. pdf 저장
doc.saveToFile("AddSignature.pdf");
doc.close();
```  
